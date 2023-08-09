# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled Spring Boot JAR file to the working directory
COPY target/UrlShortenerService-0.0.1-SNAPSHOT.jar app.jar


# Add docker-compose-wait tool -------------------
ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

# Expose the application's port (adjust as needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]