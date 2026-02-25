#Maven Build
FROM maven:3.9.9-eclipse-temurin-23-alpine AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -B package --file **/pom.xml

FROM eclipse-temurin:23-alpine
EXPOSE 8080
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} /opt/app/integrations.jar
ENTRYPOINT ["java","-jar","/opt/app/integrations.jar"]
