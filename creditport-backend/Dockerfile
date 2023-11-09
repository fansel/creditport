# Starten mit einem Java 17 Image
FROM openjdk:17-jdk-slim

# Arbeitsverzeichnis im Container festlegen
WORKDIR /app

# Übernehmen der .env-Datei in das Arbeitsverzeichnis
COPY .env /app/.env

# Die JAR-Datei in das Arbeitsverzeichnis des Containers kopieren
COPY build/libs/creditport-backend-0.0.1-SNAPSHOT.jar /app/creditport-backend.jar
# Port freigeben, auf dem die Anwendung laufen soll
EXPOSE 8080

# Befehl zum Starten der Anwendung festlegen
CMD ["java", "-jar", "/app/creditport-backend.jar"]
