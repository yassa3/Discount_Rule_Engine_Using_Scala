<h1>Discount Rule Engine</h1>

<h1>Overview</h1>
This Scala project implements a rule engine for a retail store to qualify orders' transactions for discounts based on various rules. The engine automatically calculates the proper discount based on predefined calculation rules and logs events during the processing. The final processed data is loaded into a PostgreSQL database.

<h1>Table of Contents</h1>
1. Prerequisites
2. Setup
3. Usage
4. Discount Rules
5. Logging
6. Database Integration
7. File Output

<h1>Prerequisites</h1>
1. Scala 2.13.x
2. Java 8 or higher
3. PostgreSQL database

<h1>Setup</h1>
1. Clone this repository to your local machine.
2. Ensure you have the required dependencies installed (Scala, Java, PostgreSQL).
3. Set up your environment variables in the .env file located in the env directory.
4. Create a PostgreSQL database and update the DB_URL, DB_USER, and DB_PASSWORD in the .env file.
5. Execute the Discount Rule Engine.

<h1>Usage</h1>
1. Place your order data in a CSV file according to the format specified in the .env file (CSV_FILE_PATH).
2. Execute the Discount Rule Engine using sbt run.
3. Monitor the logs in the logs/rules_engine.log file for event details.
4. The processed data will be stored in the specified output file (OUTPUT_FILE_PATH) and loaded into the PostgreSQL database.

<h1>Discount Rules</h1>
1. Expiry Discount: Calculates discounts based on the remaining days for product expiry.
2. Type Discount: Provides discounts for specific product types (Cheese, Wine).
3. Date Discount: Special discounts for orders made on specific dates.
4. Quantity Discount: Discounts based on the quantity of products purchased.
5. App Discount: Encourages app usage with discounts based on the quantity rounded up to the nearest multiple of 5.
6. Visa Discount: Promotes Visa card usage with a minor discount.

<h1>Logging</h1>
The Discount Rule Engine uses Java's logging framework to log events in the logs/rules_engine.log file. Log levels include INFO, WARNING, and ERROR for detailed event tracking.

<h1>Database Integration</h1>
Processed order data is inserted into a PostgreSQL database using JDBC. Database connection parameters are specified in the .env file.

<h1>File Output</h1>
The processed order data is written to a CSV file (OUTPUT_FILE_PATH) following the specified format.
