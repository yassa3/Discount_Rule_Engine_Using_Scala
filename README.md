<h1>Discount Rule Engine</h1>

<h2>Overview</h2>
This Scala project implements a rule engine for a retail store to qualify orders' transactions for discounts based on various rules. The engine automatically calculates the proper discount based on predefined calculation rules and logs events during the processing. The final processed data is loaded into a PostgreSQL database.

<h2>Table of Contents</h2>
1. Prerequisites<br>
2. Setup<br>
3. Usage<br>
4. Discount Rules<br>
5. Logging<br>
6. Database Integration<br>
7. File Output<br>

<h2>Prerequisites</h2>
1. Scala 2.13.x<br>
2. Java 8 or higher<br>
3. PostgreSQL database<br>

<h2>Setup</h2>
1. Clone this repository to your local machine.<br>
2. Ensure you have the required dependencies installed (Scala, Java, PostgreSQL).<br>
3. Set up your environment variables in the .env file located in the env directory.<br>
4. Create a PostgreSQL database and update the DB_URL, DB_USER, and DB_PASSWORD in the .env file.<br>
5. Execute the Discount Rule Engine.<br>

<h2>Usage</h2>
1. Place your order data in a CSV file according to the format specified in the .env file (CSV_FILE_PATH).<br>
2. Execute the Discount Rule Engine using sbt run.<br>
3. Monitor the logs in the logs/rules_engine.log file for event details.<br>
4. The processed data will be stored in the specified output file (OUTPUT_FILE_PATH) and loaded into the PostgreSQL database.<br>

<h2>Discount Rules</h2>
1. Expiry Discount: Calculates discounts based on the remaining days for product expiry.<br>
2. Type Discount: Provides discounts for specific product types (Cheese, Wine).<br>
3. Date Discount: Special discounts for orders made on specific dates.<br>
4. Quantity Discount: Discounts based on the quantity of products purchased.<br>
5. App Discount: Encourages app usage with discounts based on the quantity rounded up to the nearest multiple of 5.<br>
6. Visa Discount: Promotes Visa card usage with a minor discount.<br>

<h2>Logging</h2>
The Discount Rule Engine uses Java's logging framework to log events in the logs/rules_engine.log file. Log levels include INFO, WARNING, and ERROR for detailed event tracking.<br>

<h2>Database Integration</h2>
Processed order data is inserted into a PostgreSQL database using JDBC. Database connection parameters are specified in the .env file.<br>

<h2>File Output</h2>
The processed order data is written to a CSV file (OUTPUT_FILE_PATH) following the specified format.<br>
