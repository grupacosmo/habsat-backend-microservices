################################
######       BUILD        ######
################################

FROM maven:3.8.6-jdk-11 AS build

WORKDIR /app

COPY pom.xml /app
RUN mvn dependency:go-offline

COPY . /app
RUN mvn package -DskipTests

################################
######      RELEASE       ######
################################

FROM adoptopenjdk/openjdk11:jdk-11.0.16.1_1-alpine-slim AS release

LABEL maintainer="grupacosmo/webdev"
WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/*.jar /app/service.jar

RUN addgroup --system app && adduser -S -s /bin/false -G app app
RUN chown -R app:app /app

USER app
CMD ["java", "-jar", "service.jar"]
