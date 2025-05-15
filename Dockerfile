# Use a stable JDK image (Java 21)
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the jar file into the image
COPY target/app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (adjust if your app runs on a different port)
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
