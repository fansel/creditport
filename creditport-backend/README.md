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
Service-Klassen enthalten die eigentliche Logik, die ausgeführt wird, nachdem die Controller eine Anfrage erhalten haben.

## Repository-Klassen

Repository-Klassen sind die Schnittstelle zwischen dem Backend und der Datenbank. Sie enthalten Methoden, die es uns ermöglichen, Daten aus der Datenbank abzurufen, zu aktualisieren, zu löschen und zu speichern.



| Teammitglied  | möglicher Aufgabenbereich    ?                |
|---------------|-----------------------------------------------|
| Felix         | - Architektur und Gesamtstruktur des Backend-Systems|
| Daniel        | - Authentifizierung und Sicherheitsaspekte (JWT, Passwortschutz)|
| Frida         | - Datenbankintegration und -zugriff (JPA, Repository)|
| Maike         | - Implementierung von Vorgangs-API-Endpunkten (GET, POST usw.)|

# To-Do für das Backend könnte ungefähr so aussehen:

## Projektsetup und Architektur
- [ ] Client-Server-Architektur erstellen.
- [ ] Backend auf Microservice-Architektur umsetzen.

## Backend-Entwicklung
- [ ] Authentifizierungsfunktionen für Studienbüro und Prüfungsausschussvorsitzenden implementieren.

## API-Routen und Endpunkte
- [ ] API-Route für Antragstellung durch Studierende implementieren.
- [ ] API-Route für Dokumentenhochladen im Antrag umsetzen.
- [ ] API-Route für Herunterladen einer Antragszusammenfassung hinzufügen.
- [ ] API-Route für Einsehen des Bearbeitungsstatus eines Antrags implementieren.
- [ ] API-Routen für das Studienbüro zur Bearbeitung von Anträgen umsetzen.
- [ ] API-Routen für den Prüfungsausschussvorsitzenden zur Genehmigung oder Ablehnung von Anträgen hinzufügen.

## Datenbank
- [ ] Datenbanktabellen zur Speicherung von Antragsinformationen erstellen.
- [ ] Logik zur Speicherung und Aktualisierung von Antragsdaten in der Datenbank implementieren.
- [ ] Mechanismen zur Vermeidung von Dopplungen von Anträgen umsetzen.

## Sicherheit und Zugriffskontrolle
- [ ] Sichere Authentifizierung für alle Benutzerrollen implementieren.
- [ ] Zugriff nur für autorisierte Benutzer sicherstellen.

## Entwicklungsprozess
- [ ] CI/CD-Pipeline für automatisierte Tests und Deployment implementieren.
