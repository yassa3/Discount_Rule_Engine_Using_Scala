<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Discount Rule Engine</title>
</head>
<body>
  <h1 style="font-size: 36px;">Discount Rule Engine</h1>

  <h2>Overview</h2>
  <p>This Scala project implements a rule engine for a retail store to qualify orders' transactions for discounts based on various rules. The engine automatically calculates the proper discount based on predefined calculation rules and logs events during the processing. The final processed data is loaded into a PostgreSQL database.</p>

  <h2>Table of Contents</h2>
  <ol>
    <li><a href="#prerequisites">Prerequisites</a></li>
    <li><a href="#setup">Setup</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#discount-rules">Discount Rules</a></li>
    <li><a href="#logging">Logging</a></li>
    <li><a href="#database-integration">Database Integration</a></li>
    <li><a href="#file-output">File Output</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>

  <h2 id="prerequisites">Prerequisites</h2>
  <ol>
    <li>Scala 2.13.x</li>
    <li>Java 8 or higher</li>
    <li>PostgreSQL database</li>
  </ol>

  <h2 id="setup">Setup</h2>
  <ol>
    <li>Clone this repository to your local machine.</li>
    <li>Ensure you have the required dependencies installed (Scala, Java, PostgreSQL).</li>
    <li>Set up your environment variables in the .env file located in the env directory.</li>
    <li>Create a PostgreSQL database and update the DB_URL, DB_USER, and DB_PASSWORD in the .env file.</li>
    <li>Execute the Discount Rule Engine.</li>
  </ol>
  
  <!-- Usage, Discount Rules, Logging, Database Integration, File Output sections -->

  <h2 id="discount-rules">Discount Rules</h2>
  <table>
    <tr>
      <th>Qualifying Rules</th>
      <th>Calculation Rules</th>
    </tr>
    <tr>
      <td>Expiry Discount: Calculates discounts based on the remaining days for product expiry.</td>
      <td>Expiry: Discounts based on the remaining days for product expiry.</td>
    </tr>
    <tr>
      <td>Type Discount: Provides discounts for specific product types (Cheese, Wine).</td>
      <td>Type: Discounts for specific product types (Cheese, Wine).</td>
    </tr>
    <!-- Add more rows for other discount rules -->
  </table>

  <!-- Logging, Database Integration, File Output, Contributing, License sections -->
</body>
</html>
