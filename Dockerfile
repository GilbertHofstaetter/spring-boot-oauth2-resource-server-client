#
# Build stage
#

FROM maven:latest
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests -P docker-keycloak
WORKDIR /app/target
CMD "java" "-jar" "spring-boot-oauth2-resource-server-client-1.0-SNAPSHOT.jar"
#https://github.com/bitnami/charts/issues/5178
#https://github.com/bitnami/bitnami-docker-keycloak#extra-arguments-to-keycloak-startup
#
# Deploy stage
#

#FROM adoptopenjdk/openjdk11:latest
#RUN mkdir /app
#COPY ./target/spring-boot-oauth2-resource-server-client-1.0-SNAPSHOT.jar /app/spring-boot-oauth2-resource-server-client-1.0-SNAPSHOT.jar
#WORKDIR /app
#CMD "java" "-jar" "spring-boot-oauth2-resource-server-client-1.0-SNAPSHOT.jar"
#RUN echo "Springboot-microservice deploy stage"
#docker-compose -f docker-compose-bitnami-custom.yml up
#https://stackoverflow.com/questions/71974489/cannot-make-keycloak-work-inside-docker-compose-with-spring-boot-application