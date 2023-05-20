FROM amazoncorretto:17-alpine-jdk as build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .

COPY pom.xml .
COPY /google_checks.xml . 
COPY sonar-project.properties .

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package

FROM amazoncorretto:17-alpine-jdk

VOLUME /tmp

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
