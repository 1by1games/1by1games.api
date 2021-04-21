FROM gradle:6.7.1-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
USER root
WORKDIR app
EXPOSE 8080
ENV SPRING_DATASOURCE_URL="fill it" SPRING_DATASOURCE_USERNAME="fill it" SPRING_DATASOURCE_PASSWORD="fill it"

COPY --from=build /home/gradle/src/web/build/libs/*.jar /app/1by1games.jar

ENTRYPOINT ["java", "-jar","1by1games.jar"]
