# CreditPort Backend

Das CreditPort Backend ist eine Spring Boot-Anwendung, die als Backend-System für das CreditPort-Projekt dient. Es ermöglicht Funktionen wie Authentifizierung, Datenbankzugriff und API-Routenverwaltung.

## Voraussetzungen

- Java JDK 17
- Gradle
- (Optional) IntelliJ IDEA - Die Ultimate Edition ist für Studierende kostenlos verfügbar.

## Entwicklung 

| Teammitglied  | möglicher Aufgabenbereich    ?                |
|---------------|-----------------------------------------------|
| Felix         | - Architektur und Gesamtstruktur des Backend-Systems|
| Daniel        | - Authentifizierung und Sicherheitsaspekte (JWT, Passwortschutz)|
| Frida         | - Datenbankintegration und -zugriff (JPA, Repository)|
| Maike         | - Implementierung von Vorgangs-API-Endpunkten (GET, POST usw.)|


## Aufbau und Ausführung


1. Das Projekt klonen oder herunterladen.
2. Im Projektverzeichnis den folgenden Befehl ausführen:
    ```shell
    ./gradlew build
    ```

## Projektstruktur

### Model-Klassen
Befinden sich im Verzeichnis `src/main/java/de/swtp13/creditportbackend/model` und definieren die Struktur der in der Datenbank gespeicherten Daten.

### Controller-Klassen
Zu finden unter `src/main/java/de/swtp13/creditportbackend/controller`. Sie behandeln Clientanfragen und leiten sie an die entsprechenden Services weiter.

### Service-Klassen
Unter `src/main/java/de/swtp13/creditportbackend/service` enthalten und führen die Geschäftslogik aus, die auf die entsprechenden Anfragen folgt.

### Konfigurationsdateien
Zu finden im Verzeichnis `src/main/resources`.


# Verwendung von Lombok

Lombok ist eine coole Java-Bibliothek, die hilft, den Boilerplate-Code zu reduzieren. Mit verschiedenen Annotationen kannst du deinen Code einfacher und sauberer gestalten.

## Gradle Dependency

Folgende Dependency muss für die Verwendung in die  `build.gradle` Datei:
```
dependencies {
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
}
```

## Kurze Ausführung der wichtigsten Annotationen

### @Data

Ein Shortcut für `@ToString`, `@EqualsAndHashCode`, `@Getter`, `@Setter`, und `@RequiredArgsConstructor`.
```
import lombok.Data;

@Data
public class User {
private String name;
private int age;
}
```

### @Getter / @Setter

Kein manuelles Schreiben von Getter- und Setter-Methoden mehr nötig.
```
import lombok.Getter;
import lombok.Setter;

public class User {
@Getter @Setter
private String name;
@Getter @Setter
private int age;
}
```

### Konstruktor Annotationen

- `@NoArgsConstructor`: Erstellt einen Konstruktor ohne Parameter.
- `@RequiredArgsConstructor`: Erstellt einen Konstruktor für alle finalen Felder.
- `@AllArgsConstructor`: Erstellt einen Konstruktor für alle Felder der Klasse.
```
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
private String name;
private int age;
}

```



