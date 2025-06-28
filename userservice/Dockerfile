# Use the official OpenJDK 21 image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file into the container at /app
COPY target/userservice-0.0.1-SNAPSHOT.jar /app/userservice-0.0.1-SNAPSHOT.jar

# Expose port 8080 (or whichever port your Spring Boot app uses)
EXPOSE 8080

# Set the command to run the jar file
ENTRYPOINT ["java", "-jar", "/app/userservice-0.0.1-SNAPSHOT.jar"]
