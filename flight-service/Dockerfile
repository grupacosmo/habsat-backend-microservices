FROM openjdk:17-slim
MAINTAINER cosmopk
ARG VERSION=0.0.1-SNAPSHOT
COPY target/habsat_backend-${VERSION}.jar habsat_backend.jar
ENTRYPOINT ["java", "-jar", "/habsat_backend.jar"]