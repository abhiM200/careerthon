# --- Build Stage ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
# Fix permissions for mvnw and build the JAR
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# --- Run Stage ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/careerthon-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

