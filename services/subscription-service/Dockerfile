FROM openjdk:22-jdk
LABEL authors="Vladimir Marianciuc"

ARG JAR_FILE=target/*.jar
ARG DEPENDENCY=target/dependency

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]