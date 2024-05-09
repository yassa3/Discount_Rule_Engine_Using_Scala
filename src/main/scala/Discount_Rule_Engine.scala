import java.io.{File, FileInputStream, FileOutputStream, PrintWriter}
import scala.io.Source
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import scala.math.{ceil, round}
import java.util.logging.{Level, Logger}
import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.Properties

object Discount_Rule_Engine extends App{
  // Get the current directory where the program is executed
  val currentDir = new File(".").getCanonicalFile

  // Define the path to the .env file by moving back three folders and then entering the env directory
  val envFilePath = new File(currentDir, "env/environment_variables.env")
  val envProperties = new Properties()

  envProperties.load(new FileInputStream(envFilePath))

  // Get database connection parameters from environment variables
  val url = envProperties.getProperty("DB_URL")
  val user = envProperties.getProperty("DB_USER")
  val password = envProperties.getProperty("DB_PASSWORD")

  // Get paths from environment variables
  val csvFilePath = envProperties.getProperty("CSV_FILE_PATH")
  val outputFilePath = envProperties.getProperty("OUTPUT_FILE_PATH")
  val logFilePath = envProperties.getProperty("LOG_FILE_PATH")

  // Read input from CSV file
  val lines = Source.fromFile(csvFilePath).getLines().toList.tail

  // Create output file
  val f: File = new File(outputFilePath)
  val writer = new PrintWriter(new FileOutputStream(f,true))

  // Set up logging
  val logger = Logger.getLogger("DiscountRuleEngineLogger")
  val fileHandler = new java.util.logging.FileHandler(logFilePath)
  fileHandler.setFormatter(new java.util.logging.SimpleFormatter)
  logger.addHandler(fileHandler)
  val dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  // Logging function
  def log(level: Level, message: String): Unit = {
    val timestamp = LocalDateTime.now().format(dateTimeFormat)
    logger.log(level, s"$timestamp ${level.toString.toUpperCase} $message")
  }

  // Define Order class and parsing function
  case class Order(timestamp: String, product_name: String, expiry_date: String,quantity: Int,unit_price: Double,channel: String,payment_method: String)
  def toOrder(line: String): Order = {
    Order(line.split(',')(0),line.split(',')(1),line.split(',')(2),line.split(',')(3).toInt,line.split(',')(4).toDouble,line.split(',')(5),line.split(',')(6))
  }

  // Date format for parsing
  val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  // Check if order is qualified for expiry discount
  def isExpiryQualified(order:Order): Boolean = {
    val timestampDate = LocalDate.parse(order.timestamp.substring(0, 10), dateFormat)
    val expiryDate = LocalDate.parse(order.expiry_date, dateFormat)
    val daysDifference = java.time.temporal.ChronoUnit.DAYS.between(timestampDate, expiryDate)
    if (daysDifference < 30) true
    else false
  }
  // Calculate expiry discount for qualified orders
  def Expiry(order:Order): Double = {
    val expiry_date = LocalDate.parse(order.expiry_date, dateFormat)
    val timestamp = LocalDate.parse(order.timestamp.substring(0, 10), dateFormat)
    val Discount = 30 - java.time.temporal.ChronoUnit.DAYS.between(timestamp,expiry_date)
    log(Level.INFO, s"Order ${order.timestamp} is expiry qualified for discount")
    Discount
  }

  // Check if order is qualified for type discount (Cheese or Wine)
  def isTypeQualified(order:Order): Boolean = {
    if (order.product_name.contains("Cheese") || order.product_name.contains("Wine"))
      true
    else
      false
  }
  // Calculate type discount for qualified orders
  def Type(order:Order): Double = {
    log(Level.INFO, s"Order ${order.timestamp} is type qualified for discount")
    if (order.product_name.contains("Cheese")) 10
    else 5
  }

  // Check if order is qualified for date discount (specific date)
  def isDateQualified(order:Order): Boolean = {
    val timestampDate = order.timestamp.substring(0, 10)
    if (timestampDate == "2023-03-23") true
    else false
  }
  // Calculate date discount for qualified orders
  def Date(order:Order): Double = {
    log(Level.INFO, s"Order ${order.timestamp} is date qualified for discount")
    50
  }

  // Check if order is qualified for quantity discount
  def isQuantityQualified(order:Order): Boolean = {
    if (order.quantity > 5) true
    else false
  }
  // Calculate quantity discount for qualified orders
  def Quantity(order:Order): Double = {
    log(Level.INFO, s"Order ${order.timestamp} is quantity qualified for discount")
    if (order.quantity >=6 && order.quantity <= 9) 5
    else if (order.quantity >=10 && order.quantity <= 14) 7
    else 10
  }

  // Check if order is qualified for app discount
  def isAppQualified(order:Order): Boolean = {
    if (order.channel == "App") true
    else false
  }
  // Calculate app discount for qualified orders
  def App(order:Order): Double = {
    log(Level.INFO, s"Order ${order.timestamp} is app qualified for discount")
    ceil(order.quantity / 5.0) * 5
  }

  // Check if order is qualified for Visa payment discount
  def isVisaQualified(order:Order): Boolean = {
    if (order.payment_method ==  "Visa") true
    else false
  }
  // Calculate Visa payment discount for qualified orders
  def Visa(order:Order): Double = {
    log(Level.INFO, s"Order ${order.timestamp} is visa qualified for discount")
    5
  }

  // GetDiscountRules function to list all rule functions
  def GetDiscountRules(): List[(Order => Boolean, Order => Double)] = {
    List((isExpiryQualified, Expiry), (isTypeQualified, Type), (isDateQualified, Date),
         (isQuantityQualified, Quantity), (isAppQualified, App), (isVisaQualified, Visa))
  }

  // Function to insert data into PostgreSQL table
  def writeToPostgres(orderData: String): Unit = {
    var connection: Connection = null
    var preparedStatement: PreparedStatement = null

    try {
      connection = DriverManager.getConnection(url, user, password)

      val insertQuery = "INSERT INTO orders (timestamp, product_name, expiry_date, quantity, unit_price, channel, payment_method, average_discount, discounted_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
      preparedStatement = connection.prepareStatement(insertQuery)

      // Split the order data and set parameters for the prepared statement
      val orderParams = orderData.split(',')
      preparedStatement.setString(1, orderParams(0))
      preparedStatement.setString(2, orderParams(1))
      preparedStatement.setString(3, orderParams(2))
      preparedStatement.setInt(4, orderParams(3).toInt)
      preparedStatement.setDouble(5, orderParams(4).toDouble)
      preparedStatement.setString(6, orderParams(5))
      preparedStatement.setString(7, orderParams(6))
      preparedStatement.setDouble(8, orderParams(7).toDouble)
      preparedStatement.setDouble(9, orderParams(8).toDouble)

      preparedStatement.executeUpdate()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (preparedStatement != null) preparedStatement.close()
      if (connection != null) connection.close()
    }
  }

  // Process an order and calculate the discount based on discount rules
  def processOrder(o: Order): String = {
    val discount = GetDiscountRules.filter(_._1(o)).map(_._2(o)).sorted.reverse.take(2)
    val averageDiscount = if (discount.length == 2) (discount.head + discount(1)) / 2
                          else if (discount.length == 1) discount.head else 0.0
    val discountedPrice = round((o.unit_price * o .quantity * (1 - averageDiscount / 100)) * 100) / 100.0
    s"${o.timestamp},${o.product_name},${o.expiry_date},${o.quantity},${o.unit_price},${o.channel},${o.payment_method},${averageDiscount},${discountedPrice}"
  }

  // Write a line to the output file
  writer.write(s"timestamp,product_name,expiry_date,quantity,unit_price,channel,payment_method,discount,discounted_price"+"\n")
  def writeLine(line: String): Unit = {
    writer.write(line+"\n")
  }

  // Process each line of input, write output to Postgresql and csv, and log processing
  lines.map(toOrder).foreach { order =>
    val processedOrder = processOrder(order)
    writeToPostgres(processedOrder)
    writer.write(s"$processedOrder\n")
    log(Level.INFO, s"Order ${order.timestamp} processed and written to file and database")
  }
  log(Level.INFO, s"Orders Processed Successfully")
  writer.close()
}
