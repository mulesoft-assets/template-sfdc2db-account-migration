
# Anypoint Template: Salesforce to Database Account Migration

# License Agreement
This template is subject to the conditions of the 
<a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>.
Review the terms of the license before downloading and using this template. You can use this template for free 
with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.

# Use Case
I want to migrate accounts from Salesforce to Database. 

This Template should serve as a foundation for the process of migrating accounts from Salesforce to Database, being able to specify filtering criteria and desired behavior when an account already exists in the Database. 

As implemented, this Template leverages the [Batch Module](http://www.mulesoft.org/documentation/display/current/Batch+Processing). 

The batch job is divided in *Process* and *On Complete* stages. 
Migration process starts from fetching all the existing Accounts that match the filter criteria from Salesforce. 
On *Process* stage matched accounts will be grouped and inserted or updated in Database. 
Finally during the *On Complete* stage the Template will both output statistics data into the console and send a notification email with the results of the batch execution. 

The template is covered by the integration tests using the [MUnit](https://docs.mulesoft.com/munit). To be able to run the tests, see the example configuration of the test property file.

# Considerations

**Note:** This particular Anypoint Template illustrate the migration use case between Salesforce and a Database, thus it requires a Database instance to work.
The Anypoint Template comes packaged with a SQL script to create the DB table that uses.
It is the user responsibility to use that script to create the table in an available schema and change the configuration accordingly.
The SQL script file can be found in [src/main/resources/account.sql](../master/src/main/resources/account.sql)

This template is customized for MySQL. To use it with different SQL implementation, some changes are necessary:

* update SQL script dialect to desired one
* replace MySQL driver library dependency to desired one in [POM](pom.xml)
* update Database Config to suitable connection instead of db:my-sql-connection in global elements in [config.xml](../master/src/main/mule/config.xml)
* update database properties in `mule.*.properties` file

## DB Considerations

To get this template to work:

This template may use date time or timestamp fields from the database to do comparisons and take further actions.
While the template handles the time zone by sending all such fields in a neutral time zone, it cannot handle time offsets.
We define time offsets as the time difference that may surface between date time and timestamp fields from different systems due to a differences in the system's internal clock.
Take this in consideration and take the actions needed to avoid the time offset.


### As a Data Destination

There are no considerations with using a database as a data destination.

## Salesforce Considerations

Here's what you need to know about Salesforce to get this template to work.

### FAQ

- Where can I check that the field configuration for my Salesforce instance is the right one? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=checking_field_accessibility_for_a_particular_field.htm&language=en_US">Salesforce: Checking Field Accessibility for a Particular Field</a>
- Can I modify the Field Access Settings? How? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=modifying_field_access_settings.htm&language=en_US">Salesforce: Modifying Field Access Settings</a>

### As a Data Source

If the user who configured the template for the source system does not have at least *read only* permissions for the fields that are fetched, then an *InvalidFieldFault* API fault displays.

```
java.lang.RuntimeException: [InvalidFieldFault [ApiQueryFault [ApiFault  exceptionCode='INVALID_FIELD'
exceptionMessage='
Account.Phone, Account.Rating, Account.RecordTypeId, Account.ShippingCity
^
ERROR at Row:1:Column:486
No such column 'RecordTypeId' on entity 'Account'. If you are attempting to use a custom field, be sure to append the '__c' after the custom field name. Reference your WSDL or the describe call for the appropriate names.'
]
row='1'
column='486'
]
]
```











# Run it!
Simple steps to get Salesforce to Database Account Migration running.


## Running On Premises
In this section we help you run your template on your computer.


### Where to Download Anypoint Studio and the Mule Runtime
If you are a newcomer to Mule, here is where to get the tools.

+ [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
+ [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)


### Importing a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your
Anypoint Platform credentials, search for the template, and click **Open**.


### Running on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources.
+ Complete all the properties required as per the examples in the "Properties to Configure" section.
+ Right click the template project folder.
+ Hover your mouse over `Run as`
+ Click `Mule Application (configure)`
+ Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`
+ Click `Run`


### Running on Mule Standalone
Complete all properties in one of the property files, for example in mule.prod.properties and run your app with the corresponding environment variable. To follow the example, this is `mule.env=prod`. 
After this, to trigger the use case you just need to hit the local http endpoint with the port you configured in your file. If this is, for instance, `9090` then you should hit: `http://localhost:9090/migrateaccounts` and this will create a CSV report and send it to the mails set.

## Running on CloudHub
While creating your application on CloudHub (or you can do it later as a next step), go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the **mule.env**.
Once your app is all set and started, supposing you choose as domain name `sfdc2dbaccountmigration` to trigger the use case you just need to hit `http://sfdc2dbaccountmigration.cloudhub.io/migrateaccounts` and report will be sent to the e-mails configured.

### Deploying your Anypoint Template on CloudHub
Studio provides an easy way to deploy your template directly to CloudHub, for the specific steps to do so check this


## Properties to Configure
To use this template, configure properties (credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.
### Application Configuration
**HTTP Connector configuration**
+ http.port `9090`

**Batch Aggregator configuration**
+ page.size `1000`

**Salesforce Connector configuration**
+ sfdc.username `bob.dylan@org`
+ sfdc.password `DylanPassword123`
+ sfdc.securityToken `avsfwCUl7apQs56Xq2AKi3X`

**Database Connector configuration**
+ db.host `localhost`
+ db.port `3306`
+ db.user `joan.baez`
+ db.password `JoanBaez456`
+ db.databasename `template-sfdc2db-account-migration`

**SMTP Services configuration**
+ smtp.host `smtp.gmail.com`
+ smtp.port `587`
+ smtp.user `email%40example.com`
+ smtp.password `password`

**Email Details**
+ mail.from `batch.migrateaccounts.migration%40mulesoft.com`
+ mail.to `your.email@gmail.com`
+ mail.subject `Batch Job Finished Report`

# API Calls
Salesforce imposes limits on the number of API Calls that can be made. Therefore calculating this amount may be an important factor to consider. The Anypoint Template calls to the API can be calculated using the formula:

***X + X / ${page.size}***

Being ***X*** the number of Accounts to be synchronized on each run.

The division by ***${page.size}*** is because, by default, Accounts are gathered in groups of ${page.size} for each Upsert API Call in the commit step.

For instance if 10 records are fetched from origin instance, then 20 api calls will be made (10 + 10).


# Customize It!
This brief guide intends to give a high level idea of how this template is built and how you can change it according to your needs.
As Mule applications are based on XML files, this page describes the XML files used with this template.

More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

* config.xml
* businessLogic.xml
* endpoints.xml
* errorHandling.xml


## config.xml
Configuration for connectors and configuration properties are set in this file. Even change the configuration here, all parameters that can be modified are in properties file, which is the recommended place to make your changes. However if you want to do core changes to the logic, you need to modify this file.

In the Studio visual editor, the properties are on the *Global Element* tab.


## businessLogic.xml
Functional aspect of the Template is implemented on this XML, directed by one flow responsible of excecuting the logic.
For the purpose of this particular Template the *mainFlow* uses a [Batch Job](http://www.mulesoft.org/documentation/display/current/Batch+Processing), which handles all the logic of it.



## endpoints.xml
This is the file where you will found the inbound and outbound sides of your integration app.
This Template has only an [HTTP Listener Connector](http://www.mulesoft.org/documentation/display/current/HTTP+Listener+Connector) as the way to trigger the use case.

### Inbound Flow
**HTTP Listener Connector** - Start Report Generation

+ `${http.port}` is set as a property to be defined either in a property file or in CloudHub environment variables.
+ The path configured by default is `migrateaccounts` and you are free to change for the one you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub will then route requests from your application domain URL to the endpoint.
+ The endpoint is configured as a *request-response* since as a result of calling it the response will be the total of Accounts synced and filtered by the criteria specified.



## errorHandling.xml
This is the right place to handle how your integration reacts depending on the different exceptions. 
This file provides error handling that is referenced by the main flow in the business logic.




