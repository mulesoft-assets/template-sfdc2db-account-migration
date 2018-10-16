
# Anypoint Template: Salesforce to Database Account Migration	

<!-- Header (start) -->

<!-- Header (end) -->

# License Agreement
This template is subject to the conditions of the <a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>. Review the terms of the license before downloading and using this template. You can use this template for free with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio. 
# Use Case
<!-- Use Case (start) -->
I want to migrate accounts from Salesforce to Database. 

This template serves as a foundation for the process of migrating accounts from Salesforce to Database, being able to specify filtering criteria and desired behavior when an account already exists in the Database. 

As implemented, this Template leverages the Mule batch module. 

The batch job is divided in *Process* and *On Complete* stages. 
Migration process starts from fetching all the existing Accounts that match the filter criteria from Salesforce. 
On *Process* stage matched accounts will be grouped and inserted or updated in Database. 
Finally during the *On Complete* stage the Template will both output statistics data into the console and send a notification email with the results of the batch execution. 

The template is covered by the integration tests using the [MUnit](https://docs.mulesoft.com/munit). To be able to run the tests, see the example configuration of the test property file.
<!-- Use Case (end) -->

# Considerations
<!-- Default Considerations (start) -->

<!-- Default Considerations (end) -->

<!-- Considerations (start) -->
**Note:** This template illustrates the migration use case between Salesforce and a Database, thus it requires a Database instance to work.
The template comes packaged with a SQL script to create the database table that it uses.
It is the user responsibility to use that script to create the table in an available schema and change the configuration accordingly.
The SQL script file can be found in [src/main/resources/account.sql](../master/src/main/resources/account.sql)

This template is customized for MySQL. To use it with different SQL implementation, some changes are necessary:

* Update SQL script dialect to desired one
* Replace MySQL driver library dependency to desired one in pom.xml file
* Update Database Config to suitable connection instead of db:my-sql-connection in global elements in config.xml in /src/main/mule/
* Update database properties in `mule.*.properties` file
<!-- Considerations (end) -->

## DB Considerations

This template uses date time or timestamp fields from the database to do comparisons and take further actions. While the template handles the time zone by sending all such fields in a neutral time zone, it cannot handle time offsets. (Time offsets are time differences that may surface between date time and timestamp fields from different systems due to a differences in each system's internal clock.)
Take this in consideration and take the actions needed to avoid the time offset.


### As a Data Destination

There are no considerations with using a database as a data destination.

## Salesforce Considerations

Here's what you need to know about Salesforce to get this template to work:

- Where can I check that the field configuration for my Salesforce instance is the right one? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=checking_field_accessibility_for_a_particular_field.htm&language=en_US">Salesforce: Checking Field Accessibility for a Particular Field</a>.
- Can I modify the Field Access Settings? How? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=modifying_field_access_settings.htm&language=en_US">Salesforce: Modifying Field Access Settings</a>.

### As a Data Source

If the user who configured the template for the source system does not have at least *read only* permissions for the fields that are fetched, then an *InvalidFieldFault* API fault displays.

```
java.lang.RuntimeException: [InvalidFieldFault [ApiQueryFault 
[ApiFault  exceptionCode='INVALID_FIELD'
exceptionMessage='Account.Phone, Account.Rating, Account.RecordTypeId, 
Account.ShippingCity
^
ERROR at Row:1:Column:486
No such column 'RecordTypeId' on entity 'Account'. If you are attempting to 
use a custom field, be sure to append the '__c' after the custom field name. 
Reference your WSDL or the describe call for the appropriate names.'
]
row='1'
column='486'
]
]
```











# Run it!
Simple steps to get this template running.
<!-- Run it (start) -->

<!-- Run it (end) -->

## Running On Premises
In this section we help you run this template on your computer.
<!-- Running on premise (start) -->

<!-- Running on premise (end) -->

### Where to Download Anypoint Studio and the Mule Runtime
If you are new to Mule, download this software:

+ [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
+ [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)

**Note:** Anypoint Studio requires JDK 8.
<!-- Where to download (start) -->

<!-- Where to download (end) -->

### Importing a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your Anypoint Platform credentials, search for the template, and click Open.
<!-- Importing into Studio (start) -->

<!-- Importing into Studio (end) -->

### Running on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources.
+ Complete all the properties required as per the examples in the "Properties to Configure" section.
+ Right click the template project folder.
+ Hover your mouse over `Run as`.
+ Click `Mule Application (configure)`.
+ Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`.
+ Click `Run`.
<!-- Running on Studio (start) -->

<!-- Running on Studio (end) -->

### Running on Mule Standalone
Update the properties in one of the property files, for example in mule.prod.properties, and run your app with a corresponding environment variable. In this example, use `mule.env=prod`. 
After this, to trigger the use case you just need to browse to the local http endpoint with the port you configured in your file. If this is, for instance, `9090` then you should browse to: `http://localhost:9090/migrateaccounts` and this will create a CSV report and send it to the mails set.

## Running on CloudHub
When creating your application in CloudHub, go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the mule.env value.
<!-- Running on Cloudhub (start) -->
Once your app is all set and started, supposing you choose as domain name `sfdc2dbaccountmigration` to trigger the use case you just need to browse to `http://sfdc2dbaccountmigration.cloudhub.io/migrateaccounts` and report will be sent to the e-mails configured.
<!-- Running on Cloudhub (end) -->

### Deploying a Template in CloudHub
In Studio, right click your project name in Package Explorer and select Anypoint Platform > Deploy on CloudHub.
<!-- Deploying on Cloudhub (start) -->

<!-- Deploying on Cloudhub (end) -->

## Properties to Configure
To use this template, configure properties such as credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.
### Application Configuration
<!-- Application Configuration (start) -->
**HTTP Connector Configuration**
+ http.port `9090`

**Batch Aggregator Configuration**
+ page.size `1000`

**Salesforce Connector Configuration**
+ sfdc.username `bob.dylan@org`
+ sfdc.password `DylanPassword123`
+ sfdc.securityToken `avsfwCUl7apQs56Xq2AKi3X`

**Database Connector Configuration**
+ db.host `localhost`
+ db.port `3306`
+ db.user `joan.baez`
+ db.password `JoanBaez456`
+ db.databasename `template-sfdc2db-account-migration`

**SMTP Services Configuration**
+ smtp.host `smtp.gmail.com`
+ smtp.port `587`
+ smtp.user `email%40example.com`
+ smtp.password `password`

**Email Details**
+ mail.from `batch.migrateaccounts.migration%40mulesoft.com`
+ mail.to `your.email@gmail.com`
+ mail.subject `Batch Job Finished Report`
<!-- Application Configuration (end) -->

# API Calls
<!-- API Calls (start) -->
Salesforce imposes limits on the number of API Calls that can be made. Therefore calculating this amount may be an important factor to consider. The template calls to the API can be calculated using the formula:

***X + X / ${page.size}***

Being ***X*** the number of Accounts to be synchronized on each run.

The division by ***${page.size}*** is because, by default, Accounts are gathered in groups of ${page.size} for each Upsert API Call in the commit step.

For instance if 10 records are fetched from origin instance, then 20 api calls will be made (10 + 10).
<!-- API Calls (end) -->

# Customize It!
This brief guide provides a high level understanding of how this template is built and how you can change it according to your needs. As Mule applications are based on XML files, this page describes the XML files used with this template. More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

* config.xml
* businessLogic.xml
* endpoints.xml
* errorHandling.xml<!-- Customize it (start) -->

<!-- Customize it (end) -->

## config.xml
<!-- Default Config XML (start) -->
This file provides the configuration for connectors and configuration properties. Only change this file to make core changes to the connector processing logic. Otherwise, all parameters that can be modified should instead be in a properties file, which is the recommended place to make changes.<!-- Default Config XML (end) -->

<!-- Config XML (start) -->

<!-- Config XML (end) -->

## businessLogic.xml
<!-- Default Business Logic XML (start) -->
Functional aspect of the Template is implemented on this XML, directed by one flow responsible of excecuting the logic.
For the purpose of this particular Template the *mainFlow* uses a batch job, which handles all the logic of it.<!-- Default Business Logic XML (end) -->

<!-- Business Logic XML (start) -->

<!-- Business Logic XML (end) -->

## endpoints.xml
<!-- Default Endpoints XML (start) -->
This file provides the inbound and outbound sides of your integration app.
This Template has only an HTTP Listener as the way to trigger the use case.

### Inbound Flow
**HTTP Listener Connector** - Start Report Generation

+ `${http.port}` is set as a property to be defined either in a property file or in CloudHub environment variables.
+ The path configured by default is `migrateaccounts` and you are free to change for the one you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub will then route requests from your application domain URL to the endpoint.
+ The endpoint is configured as a *request-response* since as a result of calling it the response will be the total of Accounts synced and filtered by the criteria specified.<!-- Default Endpoints XML (end) -->

<!-- Endpoints XML (start) -->

<!-- Endpoints XML (end) -->

## errorHandling.xml
<!-- Default Error Handling XML (start) -->
This file handles how your integration reacts depending on the different exceptions. This file provides error handling that is referenced by the main flow in the business logic.<!-- Default Error Handling XML (end) -->

<!-- Error Handling XML (start) -->

<!-- Error Handling XML (end) -->

<!-- Extras (start) -->

<!-- Extras (end) -->
