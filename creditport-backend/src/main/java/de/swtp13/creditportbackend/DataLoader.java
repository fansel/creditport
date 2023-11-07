package de.swtp13.creditportbackend;

/**
 * DataLoader Klasse zum Laden von Anfangsdaten in die Datenbank.
 * Diese Klasse wird beim Start der Anwendung ausgeführt und speichert
 * eine Liste von Modulen in der Datenbank.
 *
 * @author Felix
 */

import de.swtp13.creditportbackend.modules.Module;
import de.swtp13.creditportbackend.modules.ModuleRepository;
import de.swtp13.creditportbackend.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.procedures.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Arrays;

@Component  // Diese Annotation gibt an, dass diese Klasse ein Component ist, und von Spring automatisch erkannt und instanziiert wird.
public class DataLoader implements CommandLineRunner {

    @Autowired  // Diese Annotation ermöglicht die Injektion des ModuleRepository.
    private ProcedureRepository procedureRepository;

    @Autowired  // Diese Annotation ermöglicht die Injektion des ModuleRepository.
    private ModuleRepository moduleRepository;

    /**
     * Diese Methode wird beim Start der Anwendung ausgeführt.
     * Sie initialisiert und speichert eine Liste von Modulen in der Datenbank.
     *
     * @param args Argumente der Kommandozeile. Nicht verwendet in dieser Implementierung.
     * @throws Exception wenn ein Fehler während der Ausführung auftritt.
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Test procedure is being created!");

        // Erstellen einer Liste von Modulen mit vordefinierten Daten.
        Procedure procedure = new Procedure(1,"only a test procedure");


        // Speichern der Module in der Datenbank
        procedureRepository.save(procedure);

        System.out.println("Procedures were saved in the database!");
        System.out.println("DataLoader is being executed!");

        // Erstellen einer Liste von Modulen mit vordefinierten Daten.
        List<Module> modules = Arrays.asList(
                new Module("10-201-2012", "Einführung in die objektorientierte Modellierung und Programmierung",""),
                new Module("10-201-2005-2", "Programmierparadigmen",""),
                new Module("10-201-2001-1", "Algorithmen und Datenstrukturen 1",
                        "Nach der Teilnahme am Modul \"Algorithmen und Datenstrukturen 1\" können die Studierenden:\n" +
                        "    Grundlegende Datenstrukturen erklären.\n" +
                        "    Einfache Algorithmen analysieren und ihre Funktionsweise reproduzieren.\n" +
                        "    Einfache Textaufgaben mithilfe der erlernten Algorithmen und Datenstrukturen lösen.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Arbeiten mit großen Datenmengen: Effektive Datenstrukturen, Sortieren, Suchen.\n" +
                        "    Algorithmen für Graphen.\n" +
                        "    Kompressionsalgorithmen.\n" +
                        "    Grundlegende Strategien von Algorithmen."),
                new Module("10-201-2001-2", "Algorithmen und Datenstrukturen 2",
                        "Nach der Teilnahme am Modul \"Algorithmen und Datenstrukturen 2\" können die Studierenden:\n" +
                        "    Erweiterte Datenstrukturen erklären.\n" +
                        "    Komplexere Algorithmen analysieren und ihre Funktionsweise reproduzieren.\n" +
                        "    Für ein gegebenes Anwendungsszenario geeignete Algorithmen und Datenstrukturen wählen.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Arbeiten mit großen Datenmengen: Effektive Datenstrukturen, Sortieren, Suchen.\n" +
                        "    Algorithmen für Graphen.\n" +
                        "    Kompressionsalgorithmen.\n" +
                        "    Grundlegende Strategien von Algorithmen."),
                new Module("10-201-2006-1", "Grundlagen der Technischen Informatik 1",
                        "Nach der Teilnahme am Modul \"Grundlagen der Technischen Informatik 1\" können die Studierenden:\n" +
                        "    Grundlegende Elektronikbegriffe definieren.\n" +
                        "    Bauteile aus der technischen Informatik beschreiben, analysieren und ihre Funktionsweise erläutern.\n" +
                        "    Einfache analoge und digitale Schaltungen berechnen, analysieren, konzipieren und erklären.\n" +
                        "    \n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Grundlagen der Schaltungstechnik und die Verwendung von Transistoren als Schalter.\n" +
                        "    Darstellung, Entwurfsminimierung und Realisierung digitaler Schaltungen.\n" +
                        "    Den Aufbau und die Funktionsweise von Rechnersystemen und deren Peripheriegeräte."),
                new Module("10-201-2006-2", "Grundlagen der Technischen Informatik 2",
                        "Nach der Teilnahme am Modul \"Grundlagen der Technischen Informatik 2\" können die Studierenden:\n" +
                        "    Grundlegende Elektronikbegriffe definieren.\n" +
                        "    Bauteile aus der technischen Informatik beschreiben, analysieren und ihre Funktionsweise erläutern.\n" +
                        "    Einfache analoge und digitale Schaltungen berechnen, analysieren, konzipieren und erklären.\n" +
                        "    Experimente nach Vorgaben durchführen, protokollieren und die Ergebnisse analysieren und erklären.\n" +
                        "    Verständliche Versuchsmitschriften und Protokolle erstellen.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Grundlagen der Schaltungstechnik und die Verwendung von Transistoren als Schalter.\n" +
                        "    Darstellung, Entwurfsminimierung und Realisierung digitaler Schaltungen.\n" +
                        "    Den Aufbau und die Funktionsweise von Rechnersystemen und deren Peripheriegeräte."),
                new Module("10-201-2004", "Betriebs- und Kommunikationssysteme",
                        "Nach der Teilnahme am Modul \"Betriebs- und Kommunikationssysteme\" sind die Studierenden in der Lage, folgende Fähigkeiten zu erlangen:\n" +
                        "    Sie können die Grundlagen des Internets, einschließlich seiner Technologien und Konzepte, erläutern.\n" +
                        "    Die Studierenden sind in der Lage, die Aufgaben, die von den einzelnen Schichten des TCP/IP-Protokoll-Stacks wahrgenommen werden, zu definieren und die grundlegenden Protokolle, die in diesem Zusammenhang eine Rolle spielen, zu erklären.\n" +
                        "    Die Studierenden können einfache Client/Server-Anwendungen sowie Peer-to-Peer-Anwendungen entwickeln.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Einführung in die Programmiersprache C++\n" +
                        "    Verständnis von Prozessen und Threads\n" +
                        "    LAN- und WAN-Technologien\n" +
                        "    Vertiefte Betrachtung von Protokollen und Schichten\n" +
                        "    Themen wie Internet-Routing und Datentransport\n" +
                        "    Anwendung von Client/Server- und Peer-to-Peer-Paradigmen in Internetanwendungen\n" +
                        "    Untersuchung spezifischer Anwendungen wie E-Mail, World Wide Web, Internet-Suchmaschinen, Peer-to-Peer-Dateienaustausch und Peer-to-Peer-Instant-Messaging."),
                new Module("10-201-2108-1", "Logik",
                        "Nach der Teilnahme am Modul \"Logik\" können die Studierenden:\n" +
                        "    Sachverhalte präzise formalisieren, indem sie Aussagen- und Prädikatenlogik anwenden.\n" +
                        "    Bestimmen, ob eine Formel aus anderen logisch hergeleitet werden kann.\n" +
                        "    Grundlegende automatische und formale Beweisverfahren nutzen.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Aussagenlogik\n" +
                        "    Resolution\n" +
                        "    Endlichkeitssatz\n" +
                        "    Prädikate\n" +
                        "    Modelle\n" +
                        "    Unentscheidbarkeit\n" +
                        "    Grundlagen der Logikprogrammierung"),
                new Module("10-201-2108-2", "Automaten und Sprachen",
                        "Nach der Teilnahme am Modul \"Automaten und Sprachen\" können die Studierenden:\n" +
                        "    Grundlegende Begriffe und Konzepte aus der Automatentheorie sowie über formale Sprachen präzise definieren.\n" +
                        "    Mathematische Aussagen bezüglich von Automaten und formalen Sprachen überprüfen und nachweisen oder widerlegen.\n" +
                        "    Grundlegende formale Beweisverfahren auf verschiedene Automatenmodelle und Sprachklassen anwenden.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Formale Sprachen und Grammatiken\n" +
                        "    Die Chomsky-Hierarchie\n" +
                        "    Endliche Automaten und reguläre Sprachen\n" +
                        "    Keller-Automaten und kontextfreie Sprachen\n" +
                        "    Kontextsensitive Sprachen."),
                new Module("10-201-2009","Berechenbarkeit",
                        "Nach der Teilnahme am Modul \"Berechenbarkeit\" erwerben die Studierenden die Fähigkeit:\n" +
                        "    Grundlegende Begriffe und Konzepte aus der Algorithmentheorie und der Komplexitätstheorie präzise zu formalisieren.\n" +
                        "    Mathematische Aussagen bezüglich Berechenbarkeitskonzepten zu überprüfen, nachzuweisen oder zu widerlegen.\n" +
                        "    Grundlegende formale Beweisverfahren für Entscheidbarkeits-, Berechenbarkeits- und Komplexitätsfragen anzuwenden.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Begriff des Algorithmus und des Kalküls\n" +
                        "    Turingmaschinen und Registermaschinen\n" +
                        "    Partiell rekursive Funktionen\n" +
                        "    Churchsche Hypothese und Äquivalenzsätze\n" +
                        "    Kleenesche Normaltheoreme\n" +
                        "    Berechenbare Numerierungen\n" +
                        "    Rekursiv aufzählbare und entscheidbare Mengen\n" +
                        "    Halteproblem\n" +
                        "    Elemente der Komplexitätstheorie"),
                new Module("10-201-2211", "Datenbanksysteme 1",
                        "Nach der Teilnahme am Modul \"Datenbanksysteme 1\" beherrschen Studierende:\n" +
                        "    Grundlagen von Datenbanksystemen für große Datensätze.\n" +
                        "    Modellierung in Entity-Relationship und UML-Klassendiagrammen anhand von Anwendungsbeschreibungen.\n" +
                        "    Kenntnisse über relationale Datenbanksysteme, Relationenalgebra und SQL-Abfragen.\n" +
                        "    Fähigkeit zur Formulierung und Ausführung von SQL-Abfragen.\n" +
                        "    Problemerkennung und Lösung in relationalen Datenbankschemata durch Normalisierungslehre.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Datenbankverwaltungssysteme: Aufbau und Merkmale\n" +
                        "    Modellierung: Entity-Relationship und UML\n" +
                        "    Das relationale Modell und Normalformen\n" +
                        "    Relationenalgebra und SQL-Abfragen."),
                new Module("10-201-2321", "Software Engineering",
                        "Nach der Teilnahme am Modul \"Software Engineering\" sind die Studierenden in der Lage:\n" +
                        "    Prinzipien, Methoden und Werkzeuge für die ingenieurmäßige Entwicklung und Anwendung umfangreicher Software-Systeme zu verstehen und anzuwenden.\n" +
                        "    Verschiedene Vorgehensweisen der Softwareentwicklung zu vergleichen und anhand von Anwendungsbeispielen darzustellen.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Einführung in relevante und aktuelle Paradigmen der Softwareentwicklung.\n" +
                        "    Vorstellung von Methoden, Notationen und Techniken zur Softwareentwicklung.\n" +
                        "    Betrachtung von Software-Architekturen in Bezug auf funktionale und nicht-funktionale Anforderungen.\n" +
                        "    Darstellung der Softwarelebenszyklusaktivitäten in verschiedenen Prozessmodellen, unter Berücksichtigung von Einschränkungen und Randbedingungen.\n" +
                        "    Untersuchung leicht- und schwergewichtiger Entwicklungsprozesse.\n" +
                        "    Behandlung ausgewählter Diagramme der UML in Bezug auf Notation und Verwendung sowie modellbasierte Entwicklung."),
                new Module("10-201-2320", "Software Engineering Praktikum",
                        "Nach der Teilnahme am Modul \"Software Engineering Praktikum\" erwerben die Studierenden die Fähigkeit:\n" +
                        "    Die Aufgabenstellung eines größeren IT-Projekts im Team zu analysieren und deren Umsetzung gemeinsam zu organisieren.\n" +
                        "    Verschiedene Rollen innerhalb eines IT-Projekts selbstständig zu übernehmen.\n" +
                        "    Kommunikationsmittel systematisch zur Planung, Vorbereitung, Durchführung und Auswertung angemessen einzusetzen.\n" +
                        "    Fachkenntnisse zur Lösung von Problemen zu erwerben und anzuwenden.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Umsetzung eines umfangreicheren Softwareprojekts in Phasen, darunter Anforderungsanalyse, Vorprojekt, Modellierung, Implementierung und Test bis zur Fertigstellung eines lauffähigen Prototypen.\n" +
                        "    Arbeit in Projektgruppen, in denen verschiedene Rollen innerhalb des Software-Entwicklungsprozesses wahrgenommen werden, einschließlich der Rolle des Projektleiters.\n" +
                        "    Methodologie, die sich an [Balzert] orientiert, und die Durchführung von Anforderungsanalyse, Spezifikation, Modellierung und Implementierung.\n" +
                        "    Erstellung von begleitender Projekt- und Produkt-Dokumentation, darunter Design-Beschreibungen, Javadoc-Kommentare und Inline-Kommentare.\n" +
                        "    Regelmäßige Reviews wichtiger Entwicklungsphasen zu vorgegebenen Terminen."),
                new Module("10-201-1602", "Diskrete Strukturen",
                        "Nach der Teilnahme am Modul \"Diskrete Strukturen\" erlangen die Studierenden folgende Fähigkeiten:\n" +
                        "    Präzise formale Spezifikation grundlegender Begriffe und Konzepte aus der diskreten Mathematik.\n" +
                        "    Überprüfung algebraischer Aussagen im Zusammenhang mit diskreten Strukturen, sowie die Fähigkeit, diese nachzuweisen oder zu widerlegen.\n" +
                        "    Anwendung grundlegender formaler Beweisverfahren im Kontext diskreter Strukturen.\n" +
                        "\n" +
                        "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Mengen, Relationen und Funktionen.\n" +
                        "    Beweistechniken mittels Induktion.\n" +
                        "    Grundlagen der Aussagenlogik.\n" +
                        "    Studium relationaler und algebraischer Strukturen, inklusive Gruppen, Ringe und Körper.\n" +
                        "    Einführung in die Graphentheorie.\n" +
                        "    Untersuchung geordneter Strukturen und Fixpunktsätze.\n" +
                        "    Boolesche Algebren.\n" +
                        "    Anwendungen dieser Konzepte in der Informatik."),
                new Module("10-201-1011", "Analysis",
                        "Nach der Teilnahme am Modul \"Analysis\" erwerben die Studierenden folgende Fähigkeiten:\n" +
                        "    Definition und Erklärung grundlegender analytischer Begriffe, einschließlich Folgen und Reihen, Funktionen, Stetigkeit, Differentiation und Integration.\n" +
                        "    Verständnis des deduktiven Aufbaus der Mathematik.\n" +
                        "    Kenntnis mathematischer Beweismethoden wie direkter und indirekter Beweis sowie vollständige Induktion und die Fähigkeit, mathematische Beweise nachzuvollziehen.\n" +
                        "    Fähigkeit zur Bearbeitung und Diskussion von Fragestellungen aus dem Bereich der Analysis, insbesondere in kleinen Gruppen.\n" +
                        "\n" +
                        "Die Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Das Induktionsprinzip.\n" +
                        "    Folgen und Reihen.\n" +
                        "    Funktionenfolgen und -reihen.\n" +
                        "    Stetigkeit von Funktionen einer Veränderlichen.\n" +
                        "    Elementare Funktionen, wie die Exponentialfunktion, trigonometrische Funktionen und Umkehrfunktionen.\n" +
                        "    Differentiation und Integration von Funktionen einer Veränderlichen, einschließlich des Fundamentalsatzes, der Taylorentwicklung und uneigentlicher Integrale.\n" +
                        "    Partielle Ableitungen von Funktionen mehrerer Veränderlicher.\n" +
                        "    Lösungsformeln für spezielle gewöhnliche Differentialgleichungen erster Ordnung, sowohl lineare als auch separierbare.\n" +
                        "    Interpolation und das Newton-Verfahren oder approximative Differentiation und Integration."),
                new Module("10-201-1015", "Lineare Algebra",
                        "Nach der Teilnahme am Modul \"Lineare Algebra\" erwerben die Studierenden die folgenden Fähigkeiten:\n" +
                        "    Definition und Kenntnis grundlegender Begriffe der Linearen Algebra wie Vektorraum, Lineare Abbildung, Matrix und Determinante sowie deren Eigenschaften.\n" +
                        "    Anwendung mathematischer Beweismethoden, darunter direkter und indirekter Beweis sowie vollständige Induktion, zur Lösung von Problemstellungen im Zusammenhang mit der Linearen Algebra.\n" +
                        "    Fähigkeit zur Bearbeitung und Diskussion von Fragestellungen aus dem Bereich der Linearen Algebra, auch in kleinen Gruppen.\n" +
                        "\n" +
                        "Die Inhalt des Moduls umfasst die folgenden Themen:\n" +
                        "    Zahlbereiche, Mathematische Grundlagen, Mengen und Aussagenlogik.\n" +
                        "    Behandlung von Relationen und Lösung linearer Gleichungssysteme.\n" +
                        "    Einführung in die Grundbegriffe der Algebra, einschließlich Gruppe, Körper und Vektorraum, mit Beispielen.\n" +
                        "    Betrachtung von Basis und Dimension.\n" +
                        "    Grundlagen der Matrizentheorie.\n" +
                        "    Lineare Abbildungen und ihre Darstellung in Matrixform.\n" +
                        "    Determinanten.\n" +
                        "    Eigenwerte.\n" +
                        "    Numerische Lösung linearer Gleichungssysteme."),
                new Module("10-201-1802", "Wahrscheinlichkeitstheorie",
                        "Das Modul umfasst die folgenden Themen:" +
                        "    Behandlung von diskreten Wahrscheinlichkeitsräumen und Wahrscheinlichkeiten mit Dichten," +
                        "    grundlegende Konzepte wie Erwartungswert, Varianz, Unabhängigkeit und Zufallsgrößen, " +
                        "    Beispiele für Wahrscheinlichkeitsverteilungen, " +
                        "    das Gesetz der Großen Zahlen und der Satz von Moivre-Laplace, " +
                        "    Einführende Betrachtungen zur mathematischen Statistik, einschließlich Schätztheorie, Konfidenzbereiche und Testtheorie."),
                new Module("", "Seminarmodule (Wahlpflichtfach)",""),
                new Module("", "Kermodul (Wahlpflichtfach)",""),
                new Module("", "Vertiefungsmodul (Wahlpflichtfach)",""),
                new Module("", "Ergänzungsbereich (Wahlpflichtfach)",""),
                new Module("", "Schlüsselqualifikation (Wahlpflichtfach)",""),
                new Module("10-201-2108-1", "Bachelorseminar","Nach der  Teilnahme am Bachelorseminar erlangen die Studierenden folgende Fähigkeiten:\n"+
                        "Selbständige Einarbeitung in ein wissenschaftliches Thema der Informatik\n" +
                        "Vorbereitung auf die Bachelorarbeit\n" +
                        "Präsentation selbst erarbeiteten Wissens")
        );



        // Speichern der Module in der Datenbank
        moduleRepository.saveAll(modules);

        System.out.println("Modules were saved in the database!");
    }
}
