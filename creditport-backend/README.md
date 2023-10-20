
# CreditPort Backend

 Backend-System für das CreditPort-Projekt. Es handelt sich um eine Spring Boot Anwendung, die mithilfe von Gradle gebaut wird.

## Struktur

Die wichtigsten Teile der Codebasis sind:

- **Model-Klassen**: `src/main/java/de/swtp13/creditportbackend/model`
- **Controller**: `src/main/java/de/swtp13/creditportbackend/controller`
- **Service-Klassen**: `src/main/java/de/swtp13/creditportbackend/service`
- **Konfigurationsdateien**: `src/main/resources`

## Voraussetzungen

- Java JDK
- Gradle

## Build-Anleitung

Um das Projekt zu bauen, den folgenden Befehl im Projektverzeichnis ausführen:
```shell
./gradlew build
```
# Projektstruktur Übersicht

Hier ist ein einfacher Überblick über die Struktur des Backends:

## Modellklassen

Modellklassen repräsentieren die Struktur der Daten, die wir in der Datenbank speichern möchten. 

## Controller-Klassen

Die Controller-Klassen sind so etwas wie die Gatekeeper unserer Anwendung. Sie empfangen HTTP-Anfragen von Clients und entscheiden, was mit diesen Anfragen geschehen soll. (z.B Abrufen von Daten aus der Datenbank, das Aktualisieren von Daten etc.

## Service-Klassen
## Konfigurationsdateien

Die Konfigurationsdateien sind essenziell, um zu steuern, wie die Anwendung und die Datenbank laufen und interagieren. Hier stellen wir verschiedene Einstellungen und Parameter ein, um die Umgebung nach unseren Bedürfnissen anzupassen. Sie enthalten spezifische Details, wie die Datenbankverbindungen hergestellt werden, welche Ports der Server verwendet und viele andere Einstellungen, die das Verhalten der Anwendung bestimmen.

Service-Klassen enthalten die eigentliche Logik, die ausgeführt wird, nachdem die Controller eine Anfrage erhalten haben.

## Repository-Klassen

Repository-Klassen sind die Schnittstelle zwischen dem Backend und der Datenbank. Sie enthalten Methoden, die es uns ermöglichen, Daten aus der Datenbank abzurufen, zu aktualisieren, zu löschen und zu speichern.

