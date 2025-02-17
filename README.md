# Crawler

Crawler Project

Application provides a rest interface to crawl a given url for linked urls

Crawler Max Level
Default = 1

-------------------------
Improvements

- Improve performance by introducing CrawlerWorker Runnable delegated to finding urls (use thread ExecutorsService)
- Introduce database layer - application currently stateless (concurrent hashmap replaces an in memory db such as h2 in:mem)
- Application currently unencrypted, no concept of security context for user/principal

-------------------------
Caching

- Local caching only works for a single instance of the Crawler application - distributed caching or database required for
  use in a distributed system.

-------------------------
API Interface

GET /crawl
    Returns all visited URLs up to a given depth
    - address: queryParam to provide address to crawl
    - maxLevel queryParam to provide max level to crawl

PUT /crawl/refresh
    Refreshes entire caching mechanism (helps with testing)

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

    Run: ./run-crawler-service.sh <password>

Alternatively...

Jar
   - Run: mvn clean package
     Locate jar file in /crawler-service/target
     Run : java -jar crawler-application.jar
     
-------------------------