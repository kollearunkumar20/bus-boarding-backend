# Use lightweight Java image
FROM eclipse-temurin:17-jdk-alpine

# Create app directory
WORKDIR /app

# Copy project files
COPY . .

# Build jar
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Expose port (Render will override)
EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","target/*.jar"]
