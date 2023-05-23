FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /opt/app
COPY monolith/ adapters/ domain/ application/ mvnw pom.xml ./
COPY .mvn/ .mvn
RUN ./mvnw dependency:go-offline
COPY ./monolith/target ./monolith/target
RUN ./mvnw clean install

FROM eclipse-temurin:17-jre-alpine as deployer
WORKDIR /opt/app
COPY --from=builder /opt/app/monolith/target/*.jar /opt/app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]

#docker build -t bookstore-app:1.0 .

