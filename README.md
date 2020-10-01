#Reactive-Collector

##Running the application
Prerequisite: 
For this to run you must have:
* java 11
* apache maven 3.5
* cassandra 3.11

1. Download and install cassandra in docker.
    ```
    docker run --name some-cassandra --network some-network -d cassandra:tag
    ```
   
2. Run the scripts in src/resources/cql-schema.sql.
This script will create keyspace (mykespace), tables (users,accounts,transactions), custom types and also will populate the user table with data.

3. Start the application
    ```    
    mvn spring-boot:run
    ```
Default port is 8180, the provider runs on 8080.
In resources you will find a Tes.postman_collection.json in order to test the endpoints.
##Considerations
There are to main components:
#### Importer
This component will get the data from the provider and pus it to the db.
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
 
## Future work
* As any application there are always things that can or need to be improved.
* What happens if will have 1 millions of users to update each day?
Currently the application can be scaled verticaly.
In order to scale horizontal and benefit from the cloud infrastructure we need to have a coordinator to push data to the individual instances of the application.
In this direction we can add KAFKA add instead of reading the users from the db we will read it them from topic by partitions.
* Self healing with readiness and liveness probes
* Availability 
Depending on the replication factor of cassandra and the number of pods(instances of the application) this app can be highly available.

   