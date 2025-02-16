# crawler

Crawler Project

Application to Crawl URLs
Provides a rest interface to get visited URLs for a given URL

-------------------------

Future Plans

- Improve performance but introducing CrawlerWorker Runnable delegated to finding urls
- Introduce database layer - application currently stateless (concurrent cache replaces an in memory db such as h2 in:mem)
- Local caching only works for a single instance of the Crawler application - distributed caching or database required for
  use in a distributed system.

-------------------------


GET /crawl
    Returns all visited URLs up to a given depth
    - address: queryParam to provide address to crawl
    - maxLevel queryParam to provide max level to crawl

PUT /crawl/refresh
    Refreshes entire caching mechanism

-------------------------

Prerequisites
 - Java 11 and Maven
 - Docker
 - You can run the application via your IDE,
   just remember to pass the encryption password
   in as an environment variable.



Please ask for Encryption Password

Encryption done via Jasypt
http://www.java2s.com/Code/Jar/j/Downloadjasypt191jar.htm


-------------------------

Instructions to Run

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
    
-------------------------

