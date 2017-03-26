# Gemfire Demo

## Included Modules
   * **GemFire** - A set of gemfire related modules and scripts. This is to be used to start a local instance of gemfire. Jar files are stored in cluster and gemfire.properties/server-cache.xml is in conf. 

   * **Gemfire-demo-customer-order-client** - A simple GemFire client that has rest endpoints to execute functions. Used to load/ list data in regions. 

   * **Gemfire-demo-customer-order-server** - A GemFire server configuration package. This is the main driving force within the demo. This module includes the java api region creation as well as implementations of functions and cache loaders from an embedded h2 database. 

   * **Gemfire-demo-db-repo** - A set of Spring Data Gemfire repositories to be used for CRUD operations for GemFire. 

   * **Gemfire-demo-model** - A pojo model to be used for the demo. 

   * **Gemfire-demo-scs-sink** - A sample Spring Cloud Stream sink to load data into Gemfire. This is not a necessary part of the demo, but showcases yet another way to load data into GemFire. 

   * **Gemfire-demo-server-boot** - An initializer for GemFire using Java Api and Spring.

   * **Spring-cloud-gemfire-support** - A support module to allow for spring boot to run on GemFire server. This allows for the use of @ spring annotations when using server side java api.  Gemfire-demo-customer-order-server uses this when creating regions. 
   
## GemFire Local with Cache.xml
   In a local development environment, a developer can start a GemFire instance same as one would in other environments. This includes gfsh commands as well as cache.xml. In the GemFire directory of the demo project, these scripts as well as the jar files are included to make starting GemFire easy. For this example, we still create regions using the Java api and Spring and deploy this to the server in our script. 
   
   * To begin, download the gemfire-demo project and run 
   ```
   $ mvn clean install
   ```
   
   * Then change directories into the GemFire module. 
   
   ```
   $ ll
   total 80
   drwxr-xr-x   8  user staff    272 Mar 14 11:34 cluster
   -rw-r--r--   1  user staff  40293 Mar 14 11:34 cluster_config.zip
   drwxr-xr-x   4  user staff    136 Mar 14 11:34 conf
   drwxr-xr-x  56  user staff   1904 Mar 14 11:34 lib
   drwxr-xr-x   4  user staff    136 Mar 14 11:34 scripts
   ```
   
   
   
   * In the scripts directory there are start scripts for gemfire. To start the cluster for the demo, we need to execute the script start_cluster_config_gemfire.sh.
   Once this scripts is executed, you should see a list members and list regions appear as output to the script. 
   
![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.52.15 AM.png?raw=true)
   
   * This is now available for clients, gfsh, and monitoring tools to connect to. 
   A question around this is what created the regions, as we did not have any region config in the cache.xml or start script for gfsh. 
   The regions are created by deploying the jar file of the built Gemfire-demo-customer-order-server. 
   We can see this jar file in the directory cluster. 
   
   ```
   $ ll
   total 128
   -rw-r--r--  1 user  staff      0 Mar 14 11:34 cluster.properties
   -rw-r--r--  1 user  staff   1193 Mar 14 11:34 cluster.xml
   -rw-r--r--  1 user  staff  18650 Mar 14 11:34 gemfire-demo-customer-order-server-0.0.1-SNAPSHOT.jar
   -rw-r--r--  1 user  staff   4823 Mar 14 11:34 gemfire-demo-db-repo-0.0.1-SNAPSHOT.jar
   -rw-r--r--  1 user  staff  20766 Mar 14 11:34 gemfire-demo-model-0.0.1-SNAPSHOT.jar
   -rw-r--r--  1 user  staff   6098 Mar 14 11:34 gemfire-demo-server-boot-0.0.1-SNAPSHOT.jar
   ```
   
   
   * We can also verify that these were deployed via gfsh. To check connect to the locally running GemFire cluster and execute list deployed. 
   
![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.58.29 AM.png?raw=true)
   
   
   * We could have just as easily created these regions and functions using cache.xml, but now instead of having to stop and start the server for changes to server side code and regions, all we have to do is undeploy and redeploy the jar. 

## Embedded Gemfire in IDE
GemFire allows for the opportunity to launch both locators and servers from the developer's ide. Included in the demo are .launch files in which can be ran via Eclipse/STS or InteliJ with the eclipser plugin. These files launch a local gemfire cluster that can be connected to via clients, gfsh, and monitoring tools. This is especially powerful in the development lifecycle because it provides opportunity to debug server side code. Normally in a GemFire instance, there is no debugger experience for code deployed to a standalone server. While starting it in an IDE, the classpath of the project is what the classpath of GemFire is. Another benefit to starting gemfire this way is that you can start it in the working directory of target. This means that on any new code build, the GemFire related directories will be deleted thus allowing for quick and easy cleanup; circumventing any required cli time. 

* To begin: 

   * Download the code repo labeled gemfire-demo and import it into the IDE and install into Maven repository. 

      ```
      $ mvn clean install
      ```

   * Now find the project labeled Gemfire-demo-customer-order-server with functionality described above.
     * In this project you should find two .launch files. 


![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.11.13 AM.png?raw=true)
   

   * The example above shows an inteliJ configuration. To run these files all we need to do is execute the .launch files. In order to do so, we must convert using eclipser plugin:
   
![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.13.39 AM.png?raw=true)
   


* We can now launch. To do so, click run configurations. The only change is to select your working directory which is where the GemFire locator and server files will be hosted. 

![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.16.22 AM.png?raw=true)
  
   * Now launch the locator file first followed by the server file. 

![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.19.06 AM.png?raw=true)
  
![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.19.34 AM.png?raw=true)








* We have showcased how to start GemFire via an IDE. To debug gemfire code such as functions all we need to do is set a breakpoint in one of the server side code classes. For example we can set one in the cache loader CustomerCacheLoader.java. 
Now to test all we need to do is hit the endpoint on the client that executes this function. In the IDE where Gemfire is running, you should see the breakpoint hit as in any standard java debugger.

![Screenshot](screen_shots/Screen Shot 2017-03-15 at 11.27.51 AM.png?raw=true)

## Running the demo 

Once we have started GemFire in one of the above configurations, we can now run the rest of the demo. This includes the client that has rest endpoints to execute functions running on the server. 

* To begin, build the client application if not already done. 
  * Start the application.
```
$ java -jar target/gemfire-demo-customer-order-client-0.0.1-SNAPSHOT.jar
```

   * Or from IDE

![Screenshot](screen_shots/Screen Shot 2017-03-15 at 12.27.53 PM.png?raw=true)


* Data for the demo is hosted in an embedded h2 database to showcase inline caching. 
  * To load this data via cache loader we can hit one of the rest endpoints on the client by executing one of the following commands. 
   ```
   $ curl -XPOST http://localhost:8080/loadDataByServerCachLoader
   $ curl -XPOST http://localhost:8080/loadDataByRegionPutAll
   ```

* To check to see if the data is loaded in Gemfire, connect via gfsh to the cluster and execute a show metrics command: 

![Screenshot](screen_shots/Screen Shot 2017-03-15 at 12.34.43 PM.png?raw=true)
* We can now execute via curl or browser to list Customers. This is a function that will grab Customer, Orders, and Items for a given customer id. 

![Screenshot](screen_shots/Screen Shot 2017-03-15 at 12.37.17 PM.png?raw=true)
* The function executed from the client is CustomerOrderListFunction which is a data aware function.
