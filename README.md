# swtp-2023-13

## Übersicht

Dieses Projekt umfasst eine Docker-Konfiguration für die Entwicklungsumgebung von Creditport, einer Anwendung mit PostgreSQL-Datenbank, pgAdmin für Datenbankverwaltung, sowie Frontend- und Backend-Services.

## Voraussetzungen

Stellen Sie sicher, dass Docker und Docker Compose auf Ihrem System installiert sind. Besuchen Sie die offizielle [Docker-Website](https://www.docker.com/) für Installationsanleitungen.

## Struktur

Das Projekt beinhaltet folgende Dienste:
- `db`: PostgreSQL-Datenbank
- `db-gui`: pgAdmin für Datenbankverwaltung
- `frontend`: Frontend der Anwendung
- `backend`: Backend der Anwendung

## Anleitung zum Starten des Projekts

Führen Sie die folgenden Schritte aus, um die Umgebung zu starten:

1. **Klonen des Repositories**

   Klone das Projekt-Repository auf deinen lokalen Computer.

2. **Starten der Dienste**

   Öffnen Sie ein Terminal im Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

   ```bash
   docker-compose up --build
   ```

   Dieser Befehl startet alle in der `docker-compose.yml` definierten Dienste und erstellt sie, falls nötig. Die `--build`-Flag sorgt dafür, dass alle Images neu erstellt werden.

3. **Zugriff auf Dienste**

   - **PostgreSQL-Datenbank:** Zugänglich über Port `1432`
   - **pgAdmin:** Zugänglich über [http://localhost:5050](http://localhost:5050)
   - **Frontend-Anwendung:** Zugänglich über [http://localhost:5173](http://localhost:5173)
   - **Backend-Anwendung:** Zugänglich über [http://localhost:8080](http://localhost:8080)

4. **Beenden der Dienste**

   Um die Dienste zu stoppen, verwenden Sie `Ctrl+C` im Terminal oder führen Sie folgenden Befehl aus:

   ```bash
   docker-compose down
   ```

## Wichtige Hinweise

- Stellen Sie sicher, dass keine anderen Dienste auf den Ports laufen, die von diesem Projekt verwendet werden.

