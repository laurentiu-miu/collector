# Reactive-Collector
## Running the application
Prerequisite - For this to run you must have:
* java 11
* apache maven 3.5
* cassandra 3.11

1. Download and install cassandra in docker.
    ```
    docker run --name my-cassandra-1 -p 9042:9042 -d cassandra:3.11
    docker start CONTAINER_ID
    ```
   
   
2. Run the scripts in src/resources/cql-schema.sql.
This script will create keyspace (mykespace), tables (users,accounts,transactions), custom types and also will populate the user table with data.
    ```
    docker exec -it CONTAINER_ID sh
    cqlsh
    ```
    copy-paste content of the cql-schema.sql
    verify
    
    ```
    use mykeyspace; 
    select count(*) from users;
    ``` 
   
3. Start the application
    ```    
    mvn spring-boot:run
    ```
Default port is 8180, the provider runs on 8080.
Provider url/port and application port can be configured in application.yaml. 

In order to test the endpoints:
* In resources you will find a postman collection Test.postman_collection.json.
* And also you can use the swagger docs http://localhost:8180/docs-ui.html

## Considerations
There are two main components:
#### Importer
This component will get the data from the provider and push it to the cassandra db.
This will be done at each startup if the user importer date is different from the current date.
Or each time the scheduler runs (configured in application.yaml default is set to run 1 per day). 
The intended flow of the application. 
- Read all the users from the db.
- For each user get the token from the provider.
- Read the accounts from the provider and push them to the db.
- If ok read the transactions from the provider and push them to the db.
- If ok update the user with the last day of update.
#### Exporter
This component is the web controller that exposes accounts and transactions reactively.

The db holds all the data for each day.
Basic monitor of a flow with 177 users imported

![memory](https://github.com/laurentiu-miu/collector/blob/master/monitor.png)

## Future work
* As any application there are always things that can or need to be improved.
* What happens if will have 1 millions of users to update each day?

Currently the application can be scaled verticaly.
In order to scale horizontal and benefit from the cloud infrastructure we need to have a user coordinator in order to distribute 
the load to different instances of the application. 
We can add KAFKA and push to a topic all the user that needs to be updated (were the scheduler runs). 
Listen with the importer (importFluxOfUsers method) to the particular topic.
Scale the application with same applicationId but not more then the number of partitions.
* Self healing with readiness and liveness probes
* Availability - Depending on the replication factor of cassandra and the number of pods(instances of the application) this app can become highly available.
* Performance - complicated topic depends on the number of cassandra and application nodes
* Improve tasting
* Monitoring with micrometer

## Requierments

- [x] 2 endpoints to expose accounts and transactions from my cassandra db
- [x] scheduler running at 1 o'clock can be customized in application.yaml importer.scheduler.cron
- [x] Exception handling 
- [x] Nice to have: tests - hard to test reactiv env, also hard to mock the cassandra database only Utility class is tested
- [x] Frontend - (swagger not realy an UI) - http://localhost:8180/docs-ui.html
- [x] README.md
- [x] https://github.com/laurentiu-miu/collector
- [x] java 11
   