FROM maven:3.6.3-jdk-11-slim as builder
ENV SRC_ROOT=/home
WORKDIR $SRC_ROOT
COPY . .
RUN mvn clean package

FROM openjdk:11.0.4-slim as server
VOLUME /temp
WORKDIR /app
COPY --from=builder /home/character-service/target/character-application.jar ./character-service.jar
 
ENTRYPOINT java -jar character-service.jar --jasypt.encryptor.password=$ENCRYPTION_PASSWORD
EXPOSE 8080