# Customer

Customer Project

Application provides a rest interface create and retrieve customers

-------------------------
Improvements


-------------------------
API Interface

POST /customer
    Creates a customer

GET /customer
    Retrieves all customers

-------------------------

Prerequisites
 - Java 11 and Maven
 - Docker
 - You can also run the application via IDE

-------------------------

Instructions to Run

Docker

    - You can build the image and run the container locally which has
    everything packaged in it. I have created a script which will stop
    local containers, and build/run the container. You need to pass the encryption
    password as an argument in to the script.

    Run: ./run-customer-service.sh <password>

Alternatively...

Jar
   - Run: mvn clean package
     Locate jar file in /customer-service/target
     Run : java -jar customer-application.jar
     
-------------------------