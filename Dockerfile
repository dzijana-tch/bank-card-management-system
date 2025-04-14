FROM openjdk:17-alpine
WORKDIR /app
COPY build/libs/bank-card-management-system-0.0.1-SNAPSHOT.jar app.jar
CMD [ "java", "-jar", "app.jar"]