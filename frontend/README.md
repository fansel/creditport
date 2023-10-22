# Creditport Frontend 

## Installation für Entwickler

```bash
git clone git@git.informatik.uni-leipzig.de:SWS/lehre/ws-2023-2024/swt-p/projects/swtp-2023-13.git
cd swtp-2023-13/frontend
git checkout developer

npm i
npm run dev -- --open

```

## VSCode Einstellungen

**Extension**
- Svelte for VS Code
- Svelte 3 Snippets
- Svette Intellisense

**Settings**
*Kann durch Ctrl+, aufgerufen werden*
- @lang:svelte "editor.DefaultFormatter" : "Svelte for VSCode"
- "editor.tabsize": 2

## Seiten Übersicht fürs Frontend

| Urls              | Designer  | Beschreibung                                            |
|------------------ |---------- | ---                                                     |
| /                 | Leopold   | Startseite                                              |
| /login            | Leopold   | Login für Bearbeiter                                    |
| /vorgang          | Leopold   | Formular zum erstellen eines Vorgang                    |
| /status           | Leopold   | Status abfragen / Übersicht bekommen                    |
| /faq              | Elias     | Seite auf der Fragen beantwortet werden                 |
| /impressum        | Elias     | |
| /dashboard        | Leopold   | Übersicht für Bearbeiter                                |
| /profile          | Elias     | Email und Passwort ändern                               |
| /settings         | Elias     | Allgemeine Einstellungen                                |
| /procedures       | Leopold   | Liste mit allen Vorgängen                               |
| /procedures/[id]  | Leopold   | Bearbeitung / Übersicht eines Vorgangs                  |
| /requests         | Leopold   | Liste aller Anträge (verlinkung zu jeweiligem Vorgang)  |
| /requests/[id]    | Leopold   | Bearbeitung / Übersicht eines Antrags                   |

