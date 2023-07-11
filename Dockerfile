FROM eclipse-temurin:17.0.7_7-jre-jammy
WORKDIR /app
COPY /target/*.jar ./irregation-service.jar
EXPOSE 8080
CMD ["java", "-jar", "irregation-service.jar"]