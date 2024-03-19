# SWTP-2023-13 Creditport

## Voraussetzungen
- Docker und Docker Compose müssen installiert sein.

## Struktur
- `db`: PostgreSQL-Datenbank
- `frontend`: Frontend der Anwendung
- `backend`: Backend der Anwendung

## Anleitung zum Starten des Projekts

1. **Klonen des Repositories**
   - Klone das Projekt-Repository auf deinen lokalen Computer.

2. **Starten der Dienste**
   - Öffne ein Terminal im Stammverzeichnis des Projekts.
   - Führe aus: `docker-compose up --build`

3. **Zugriff auf Dienste**
   - **PostgreSQL-Datenbank:** Port `1432`
   - **Frontend-Anwendung:** [http://localhost:5173](http://localhost:5173)
   - **Backend-Anwendung:** [http://localhost:8080](http://localhost:8080)

4. **Beenden der Dienste**
   - Verwende `Ctrl+C` im Terminal oder `docker-compose down`

## Standard-Login-Daten
- **Admin-Zugang:** `admin` / `admin`
- **Office-Zugang:** `office` / `office`
- **Exam Committee-Zugang:** `examComitte` / `examComitee`


## Docker auf der VM
- VM-Adresse: `172.26.92.92`
**Zugriff auf Dienste**
   - **PostgreSQL-Datenbank:** Port `1432`
   - **Frontend-Anwendung:** [http://172.26.92.92](http://172.26.92.92)
   - **Backend-Anwendung:** [http://172.26.92.92:8080](http://172.26.92.92:8080)

## Docker-Befehle
- Container auflisten: `docker ps`
- Container-Logs anzeigen: `docker logs -f [container_name]`

## Swagger-Dokumentation
- Erreichbar unter: [http://172.26.92.92:8080/api/v1/swagger-ui/index.html](http://172.26.92.92:8080/api/v1/swagger-ui/index.html)
