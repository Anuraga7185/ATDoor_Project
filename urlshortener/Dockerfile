FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file into the container at /app
COPY target/urlshortener-0.0.1-SNAPSHOT.jar /app/urlshortener.jar

# Expose port 8080 — this is the internal app port Spring Boot runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/urlshortener.jar"]
