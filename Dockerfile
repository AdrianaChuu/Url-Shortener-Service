# Use an official OpenJDK runtime as the base image
FROM openjdk:11

# Set the working directory
WORKDIR /app

# Copy the compiled Spring Boot JAR file to the working directory
COPY target/UrlShortenerService-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port (adjust as needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]