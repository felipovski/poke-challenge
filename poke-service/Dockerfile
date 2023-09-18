FROM gradle:jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

RUN gradle bootJar

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/poke-service-0.0.2-SNAPSHOT.jar //
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/poke-service-0.0.2-SNAPSHOT.jar"]