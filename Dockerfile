FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/incomeexpense-0.0.1-SNAPSHOT.jar incomeexpense.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "incomeexpense.jar"]
