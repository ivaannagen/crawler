# character

Character Project

Application to supply information on Characters

Currently focused on Marvel Characters

-------------------------

Instructions to Run

-------------------------


GET /characters
    Returns character ids once they have been retrieved from Marvel
    A start-up background job will retrieve the ids from Marvel and cache them
    The job takes some time but is non blocking of the other api-calls
    Refreshing the ids from Marvel on start-up makes sure we have no stale ids...

GET /characters/{characterId}
    Returns character of given characterId from real-time Marvel API

-------------------------


Please ask for Encryption Password

Encryption done via Jasypt
http://www.java2s.com/Code/Jar/j/Downloadjasypt191jar.htm

-------------------------


Prerequisites
 - Java 11 and Maven
 - Docker
 - You can run the application via your IDE,
   just remember to pass the encryption password
   in as an environment variable.
   
-------------------------


Maven
   - Run the following commands...
     mvn clean install
     mvn spring-boot:run -Dspring-boot.run.arguments=--jasypt.encryptor.password=<password>
     
-------------------------

Jar
   - Run: mvn clean package
     Locate jar file in /character-service/target
     Run : java -jar character-application.jar --jasypt.encryptor.password=<password>
     
-------------------------


Docker

    - You can build the image and run the container locally which has
    everything packaged in it. I have created a script which will stop
    local containers, and build/run the container. You need to pass the encryption
    password as an argument in to the script.

    Run: ./run-character-service.sh <password>
