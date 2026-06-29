# Step 1: Use an official Java 25 runtime parent image
FROM eclipse-temurin:25-jre-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy your project's built JAR file into the container
# Note: If you use Gradle, change 'target/*.jar' to 'build/libs/*.jar'
COPY target/*.jar app.jar

# Step 4: Inform Docker/Northflank that the container listens on port 10000
EXPOSE 10000

# Step 5: Run the jar file cleanly
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]