FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package

FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
