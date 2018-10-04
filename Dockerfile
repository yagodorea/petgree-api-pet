FROM openjdk:8-jdk-slim

LABEL maintainter="yago.dorea@gmail.com"

VOLUME /tmp

EXPOSE 4242

ARG JAR_FILE=target/petgree-api-pet*.jar

ADD ${JAR_FILE} petgree-api-pet.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/petgree-api-pet.jar"]
