FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY build/libs/my-AccountService-App.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

