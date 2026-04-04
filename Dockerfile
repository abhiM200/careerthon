# --- Build Stage ---
FROM eclipse-temurin:21-jdk-alpine AS build
# Install Maven
RUN apk add --no-cache maven
WORKDIR /app
COPY . .
# Build the JAR using installed Maven
RUN mvn clean package -DskipTests

# --- Run Stage ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
