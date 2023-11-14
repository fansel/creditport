FROM gradle:8.4.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

ARG DB_HOST
ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_PORT
ARG DB_NAME

# ENV DB_HOST=$db_host
# ENV DB_USERNAME=$db_username
# ENV DB_PASSWORD=$db_password
# ENV DB_PORT=$db_port
# ENV DB_NAME=$db_name

COPY build.gradle settings.gradle /home/gradle/src/

COPY . .

RUN gradle build -x test


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

#Die JAR-Datei in das Arbeitsverzeichnis des Containers kopieren
COPY --from=build /home/gradle/src/build/libs/creditport-backend-0.0.1-SNAPSHOT.jar /app/creditport-backend.jar
#Port freigeben, auf dem die Anwendung laufen soll
EXPOSE 8080

#Befehl zum Starten der Anwendung festlegen
CMD ["java", "-jar", "/app/creditport-backend.jar"]