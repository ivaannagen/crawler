FROM maven:3.6.3-jdk-11-slim as builder
ENV SRC_ROOT=/home
WORKDIR $SRC_ROOT
COPY . .
RUN mvn clean package

FROM openjdk:11.0.4-slim as server
VOLUME /temp
WORKDIR /app
COPY --from=builder /home/customerRequest-service/target/customerRequest-application.jar ./customerRequest-service.jar
 
ENTRYPOINT java -jar customerRequest-service.jar --jasypt.encryptor.password=$ENCRYPTION_PASSWORD
EXPOSE 8285