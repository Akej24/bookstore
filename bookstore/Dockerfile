FROM openjdk
ARG JAR_FILE=monolith/target/*.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
