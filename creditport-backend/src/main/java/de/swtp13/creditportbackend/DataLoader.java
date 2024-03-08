package de.swtp13.creditportbackend;

/**
 * DataLoader Klasse zum Laden von Anfangsdaten in die Datenbank.
 * Diese Klasse wird beim Start der Anwendung ausgeführt und speichert
 * eine Liste von Modulen in der Datenbank.
 *
 * @author Felix
 */

import de.swtp13.creditportbackend.v1.internalmodules.InternalModuleRepository;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModuleRepository;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import de.swtp13.creditportbackend.v1.universities.University;
import de.swtp13.creditportbackend.v1.universities.UniversityRepository;
import de.swtp13.creditportbackend.v1.users.Role;
import de.swtp13.creditportbackend.v1.users.User;
import de.swtp13.creditportbackend.v1.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;

/*
@Component  // Diese Annotation gibt an, dass diese Klasse ein Component ist, und von Spring automatisch erkannt und instanziiert wird.
public class DataLoader implements CommandLineRunner {

    @Autowired  // Diese Annotation ermöglicht die Injektion des ProcedureRepository.
    private ProcedureRepository procedureRepository;

    @Autowired  // Diese Annotation ermöglicht die Injektion des ModuleRepository.
    private ExternalModuleRepository externalModuleRepository;

    @Autowired
    private InternalModuleRepository internalModuleRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Diese Methode wird beim Start der Anwendung ausgeführt.
     * Sie initialisiert und speichert eine Liste von Modulen in der Datenbank.
     *
     * @param args Argumente der Kommandozeile. Nicht verwendet in dieser Implementierung.
     * @throws Exception wenn ein Fehler während der Ausführung auftritt.
     */
  /*  @Override
    public void run(String... args) throws Exception {
        System.out.println("DataLoader is being executed!");


        InternalModule internalModule1 = new InternalModule("10-201-2012", "Einführung in die objektorientierte Modellierung und Programmierung", "",5);
        InternalModule internalModule2 = new InternalModule("10-201-2005-2", "Programmierparadigmen", "",5);

        internalModuleRepository.save(internalModule1);
        internalModuleRepository.save(internalModule2);
        List<InternalModule> internalModules = Arrays.asList(

                new InternalModule("10-201-2001-1", "Algorithmen und Datenstrukturen 1",
                        "Nach der Teilnahme am Modul \"Algorithmen und Datenstrukturen 1\" können die Studierenden:\n" +
                                "    Grundlegende Datenstrukturen erklären.\n" +
                                "    Einfache Algorithmen analysieren und ihre Funktionsweise reproduzieren.\n" +
                                "    Einfache Textaufgaben mithilfe der erlernten Algorithmen und Datenstrukturen lösen.\n" +
                                "\n" +
                                "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                                "    Arbeiten mit großen Datenmengen: Effektive Datenstrukturen, Sortieren, Suchen.\n" +
                                "    Algorithmen für Graphen.\n" +
                                "    Kompressionsalgorithmen.\n" +
                                "    Grundlegende Strategien von Algorithmen.",5),
                new InternalModule("10-201-2001-2", "Algorithmen und Datenstrukturen 2",
                        "Nach der Teilnahme am Modul \"Algorithmen und Datenstrukturen 2\" können die Studierenden:\n" +
                                "    Erweiterte Datenstrukturen erklären.\n" +
                                "    Komplexere Algorithmen analysieren und ihre Funktionsweise reproduzieren.\n" +
                                "    Für ein gegebenes Anwendungsszenario geeignete Algorithmen und Datenstrukturen wählen.\n" +
                                "\n" +
                                "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                                "    Arbeiten mit großen Datenmengen: Effektive Datenstrukturen, Sortieren, Suchen.\n" +
                                "    Algorithmen für Graphen.\n" +
                                "    Kompressionsalgorithmen.\n" +
                                "    Grundlegende Strategien von Algorithmen.",5),
                new InternalModule("10-201-2006-1", "Grundlagen der Technischen Informatik 1",
                        "Nach der Teilnahme am Modul \"Grundlagen der Technischen Informatik 1\" können die Studierenden:\n" +
                                "    Grundlegende Elektronikbegriffe definieren.\n" +
                                "    Bauteile aus der technischen Informatik beschreiben, analysieren und ihre Funktionsweise erläutern.\n" +
                                "    Einfache analoge und digitale Schaltungen berechnen, analysieren, konzipieren und erklären.\n" +
                                "    \n" +
                                "Der Inhalt des Moduls umfasst die folgenden Themen:\n" +
                                "    Grundlagen der Schaltungstechnik und die Verwendung von Transistoren als Schalter.\n" +
                                "    Darstellung, Entwurfsminimierung und Realisierung digitaler Schaltungen.\n" +
                                "    Den Aufbau und die Funktionsweise von Rechnersystemen und deren Peripheriegeräte.",5),
                new InternalModule("10-201-2006-2", "Grundlagen der Technischen Informatik 2",
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
                                "    Den Aufbau und die Funktionsweise von Rechnersystemen und deren Peripheriegeräte.",5),
                new InternalModule("10-201-2004", "Betriebs- und Kommunikationssysteme",
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
                                "    Untersuchung spezifischer Anwendungen wie E-Mail, World Wide Web, Internet-Suchmaschinen, Peer-to-Peer-Dateienaustausch und Peer-to-Peer-Instant-Messaging.",5),
                new InternalModule("10-201-2108-1", "Logik",
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
                                "    Grundlagen der Logikprogrammierung",5),
                new InternalModule("10-201-2108-2", "Automaten und Sprachen",
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
                                "    Kontextsensitive Sprachen.",5),
                new InternalModule("10-201-2009", "Berechenbarkeit",
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
                                "    Elemente der Komplexitätstheorie",5),
                new InternalModule("10-201-2211", "Datenbanksysteme 1",
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
                                "    Relationenalgebra und SQL-Abfragen.",5),
                new InternalModule("10-201-2321", "Software Engineering",
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
                                "    Behandlung ausgewählter Diagramme der UML in Bezug auf Notation und Verwendung sowie modellbasierte Entwicklung.",5),
                new InternalModule("10-201-2320", "Software Engineering Praktikum",
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
                                "    Regelmäßige Reviews wichtiger Entwicklungsphasen zu vorgegebenen Terminen.",5),
                new InternalModule("10-201-1602", "Diskrete Strukturen",
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
                                "    Anwendungen dieser Konzepte in der Informatik.",5),
                new InternalModule("10-201-1011", "Analysis",
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
                                "    Interpolation und das Newton-Verfahren oder approximative Differentiation und Integration.",10),
                new InternalModule("10-201-1015", "Lineare Algebra",
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
                                "    Numerische Lösung linearer Gleichungssysteme.",10),
                new InternalModule("10-201-1802", "Wahrscheinlichkeitstheorie",
                        "Das Modul umfasst die folgenden Themen:" +
                                "    Behandlung von diskreten Wahrscheinlichkeitsräumen und Wahrscheinlichkeiten mit Dichten," +
                                "    grundlegende Konzepte wie Erwartungswert, Varianz, Unabhängigkeit und Zufallsgrößen, " +
                                "    Beispiele für Wahrscheinlichkeitsverteilungen, " +
                                "    das Gesetz der Großen Zahlen und der Satz von Moivre-Laplace, " +
                                "    Einführende Betrachtungen zur mathematischen Statistik, einschließlich Schätztheorie, Konfidenzbereiche und Testtheorie.",5),
                new InternalModule("", "Seminarmodule (Wahlpflichtfach)", "",5),
                new InternalModule("", "Kermodul (Wahlpflichtfach)", "",5),
                new InternalModule("", "Vertiefungsmodul (Wahlpflichtfach)", "",10),
                new InternalModule("", "Ergänzungsbereich (Wahlpflichtfach)", "",10),
                new InternalModule("", "Schlüsselqualifikation (Wahlpflichtfach)", "",10),
                new InternalModule("10-201-2108-1", "Bachelorseminar", "Nach der  Teilnahme am Bachelorseminar erlangen die Studierenden folgende Fähigkeiten:\n" +
                        "Selbständige Einarbeitung in ein wissenschaftliches Thema der Informatik\n" +
                        "Vorbereitung auf die Bachelorarbeit\n" +
                        "Präsentation selbst erarbeiteten Wissens",5)
        );
        internalModuleRepository.saveAll(internalModules);
        System.out.println("Test procedure is being created!");
        University testuni = new University(new UUID(1, 8), "hibh", true);
        universityRepository.save(testuni);

        // Erstelle einen neuen Vorgang mit Antrag 1
        Procedure procedure = new Procedure("only a test procedure",
                testuni, "Informatik Bachelor");
        Procedure procedure2 = new Procedure(testuni, "Data Science Master");
        Procedure procedure3 = new Procedure("Das ist eine Bemerkung", testuni, "Info");
        // Speichern des Vorgangs in der Datenbank
        procedureRepository.save(procedure);
        procedureRepository.save(procedure2);
        procedureRepository.save(procedure3);


        System.out.println("Procedures were saved in the database!");

        // Erstellen einer Liste von Modulen mit vordefinierten Daten.

        List<University> unis = Arrays.asList(
                new University("accadis Hochschule Bad Homburg"),
                new University("AKAD Hochschule Stuttgart"),
                new University("Akademie der Bildenden Künste München"),
                new University("Akademie der Bildenden Künste Nürnberg"),
                new University("Akademie der Polizei Hamburg"),
                new University("Akkon-Hochschule"),
                new University("Alanus Hochschule"),
                new University("Albert-Ludwigs-Universität Freiburg im Breisgau"),
                new University("Alice Salomon Hochschule Berlin"),
                new University("Allensbach Hochschule Konstanz"),
                new University("Archivschule Marburg – Hochschule für Archivwissenschaft"),
                new University("APOLLON Hochschule der Gesundheitswirtschaft"),
                new University("Augustana-Hochschule Neuendettelsau"),
                new University("Bard College Berlin, A Liberal Arts University"),
                new University("Barenboim-Said Akademie"),
                new University("Bauhaus-Universität Weimar"),
                new University("bbw Hochschule"),
                new University("Bergische Universität Wuppertal"),
                new University("Berlin International University of Applied Sciences"),
                new University("Berliner Hochschule für Technik"),
                new University("Berufliche Hochschule Hamburg (BHH)"),
                new University("Brand University of Applied Sciences"),
                new University("Brandenburgische Technische Universität Cottbus-Senftenberg"),
                new University("BSP Business and Law School – Hochschule für Management und Recht"),
                new University("Bucerius Law School"),
                new University("Burg Giebichenstein Kunsthochschule Halle"),
                new University("Carl von Ossietzky Universität Oldenburg"),
                new University("CBS International Business School – University of Applied Sciences"),
                new University("Christian-Albrechts-Universität zu Kiel"),
                new University("CODE University of Applied Sciences"),
                new University("Constructor University"),
                new University("Cusanus Hochschule für Gesellschaftsgestaltung"),
                new University("CVJM-Hochschule Kassel"),
                new University("Deutsche Hochschule der Polizei"),
                new University("Deutsche Hochschule für Prävention und Gesundheitsmanagement GmbH"),
                new University("Deutsche Sporthochschule Köln"),
                new University("Deutsche Universität für Verwaltungswissenschaften Speyer"),
                new University("DHGS Deutsche Hochschule für Gesundheit und Sport"),
                new University("Digital Business University of Applied Sciences"),
                new University("DIPLOMA Hochschule"),
                new University("DIU – Dresden International University GmbH"),
                new University("Duale Hochschule Baden-Württemberg"),
                new University("Duale Hochschule Gera-Eisenach"),
                new University("Duale Hochschule Schleswig-Holstein (DHSH)"),
                new University("Eberhard Karls Universität Tübingen"),
                new University("EBS Universität für Wirtschaft und Recht"),
                new University("EBZ Business School – University of Applied Sciences"),
                new University("École supérieure de commerce de Paris"),
                new University("Ernst-Abbe-Hochschule Jena – University of Applied Sciences"),
                new University("ESMT European School of Management and Technology"),
                new University("Europa-Universität Flensburg"),
                new University("Europa-Universität Viadrina Frankfurt (Oder)"),
                new University("Europäische Fachhochschule Rhein/Erft, european university of applied sciences"),
                new University("Europäische Fernhochschule Hamburg"),
                new University("Europäische Hochschule für Innovation und Perspektive (EHiP)"),
                new University("Evangelische Hochschule Berlin"),
                new University("Evangelische Hochschule Darmstadt"),
                new University("Evangelische Hochschule Dresden"),
                new University("Evangelische Hochschule Freiburg"),
                new University("Evangelische Hochschule für angewandte Wissenschaften – Evangelische Fachhochschule Nürnberg"),
                new University("Evangelische Hochschule für Kirchenmusik"),
                new University("Evangelische Hochschule für Soziale Arbeit & Diakonie"),
                new University("Evangelische Hochschule Ludwigsburg"),
                new University("Evangelische Hochschule Rheinland-Westfalen-Lippe"),
                new University("Evangelische Hochschule Tabor"),
                new University("Fachhochschule Aachen"),
                new University("Fachhochschule Bielefeld"),
                new University("Fachhochschule der Diakonie – Diaconia – University of Applied Sciences"),
                new University("Fachhochschule der Verwaltung des Saarlandes"),
                new University("Fachhochschule der Wirtschaft"),
                new University("Fachhochschule des Mittelstands (FHM)"),
                new University("Fachhochschule Dortmund"),
                new University("Fachhochschule Dresden"),
                new University("Fachhochschule Erfurt"),
                new University("Fachhochschule für die Wirtschaft Hannover"),
                new University("Fachhochschule für Finanzen des Landes Brandenburg"),
                new University("Fachhochschule für Interkulturelle Theologie Hermannsburg"),
                new University("Fachhochschule für öffentliche Verwaltung, Polizei und Rechtspflege des Landes Mecklenburg-Vorpommern"),
                new University("Fachhochschule für Rechtspflege Nordrhein-Westfalen"),
                new University("Fachhochschule für Sport und Management Potsdam"),
                new University("Fachhochschule für Verwaltung und Dienstleistung in Schleswig-Holstein"),
                new University("Fachhochschule Kiel"),
                new University("Fachhochschule Polizei Sachsen-Anhalt"),
                new University("Fachhochschule Potsdam"),
                new University("Fachhochschule Südwestfalen"),
                new University("Fachhochschule Wedel"),
                new University("Fachhochschule Westküste, Hochschule für Wirtschaft und Technik"),
                new University("Fernuniversität in Hagen"),
                new University("FH Münster University of Applied Sciences"),
                new University("Filmuniversität Babelsberg Konrad Wolf"),
                new University("Fliedner Fachhochschule Düsseldorf"),
                new University("Folkwang Universität der Künste"),
                new University("FOM Hochschule für Oekonomie & Management – University of Applied Sciences"),
                new University("Frankfurt School of Finance & Management"),
                new University("Frankfurt University of Applied Sciences"),
                new University("Freie Hochschule Stuttgart – Seminar für Waldorfpädagogik"),
                new University("Freie Theologische Hochschule Gießen"),
                new University("Freie Universität Berlin"),
                new University("Friedrich-Alexander-Universität Erlangen-Nürnberg"),
                new University("Friedrich-Schiller-Universität Jena"),
                new University("Georg-August-Universität Göttingen"),
                new University("GISMA Business School"),
                new University("Gottfried Wilhelm Leibniz Universität Hannover"),
                new University("HafenCity Universität Hamburg"),
                new University("Hamburger Fern-Hochschule, gemeinnützige GmbH"),
                new University("HAWK Hochschule für angewandte Wissenschaft und Kunst Hildesheim/Holzminden/Göttingen"),
                new University("Health and Medical University Potsdam"),
                new University("Heinrich-Heine-Universität Düsseldorf"),
                new University("Helmut-Schmidt-Universität/Universität der Bundeswehr Hamburg"),
                new University("Hertie School"),
                new University("Hessische Hochschule für Finanzen und Rechtspflege Rotenburg a.d. Fulda"),
                new University("Hessische Hochschule für Polizei und Verwaltung"),
                new University("HHL Leipzig Graduate School of Management"),
                new University("HMKW Hochschule für Medien, Kommunikation und Wirtschaft"),
                new University("hochschule 21"),
                new University("Hochschule Aalen – Technik und Wirtschaft"),
                new University("Hochschule Albstadt-Sigmaringen"),
                new University("Hochschule Anhalt – Anhalt University of Applied Sciences"),
                new University("Hochschule Biberach – Hochschule für Architektur und Bauwesen, Betriebswirtschaft und Biotechnologie"),
                new University("Hochschule Bochum – University of Applied Sciences"),
                new University("Hochschule Bonn-Rhein-Sieg, University of Applied Sciences"),
                new University("Hochschule Braunschweig/Wolfenbüttel, Ostfalia Hochschule für angewandte Wissenschaften"),
                new University("Hochschule Bremen"),
                new University("Hochschule Bremerhaven"),
                new University("Hochschule Clara Hoffbauer Potsdam"),
                new University("Hochschule Darmstadt"),
                new University("Hochschule der Bayerischen Wirtschaft für angewandte Wissenschaften – HDBW"),
                new University("Hochschule der bildenden Künste (HBK) Essen"),
                new University("Hochschule der Bildenden Künste Saar"),
                new University("Hochschule der Bundesagentur für Arbeit"),
                new University("Hochschule der Deutschen Bundesbank"),
                new University("Hochschule der Medien Stuttgart"),
                new University("Hochschule der Polizei des Landes Brandenburg"),
                new University("Hochschule der Polizei Rheinland-Pfalz"),
                new University("Hochschule der Sächsischen Polizei (FH)"),
                new University("Hochschule der Wirtschaft für Management"),
                new University("Hochschule des Bundes für öffentliche Verwaltung"),
                new University("Hochschule Düsseldorf"),
                new University("Hochschule Emden/Leer"),
                new University("Hochschule Esslingen"),
                new University("Hochschule Flensburg"),
                new University("Hochschule Fresenius"),
                new University("Hochschule Fresenius Heidelberg"),
                new University("Hochschule Fulda – University of Applied Sciences"),
                new University("Hochschule für angewandte Pädagogik"),
                new University("Hochschule für angewandte Wissenschaften Ansbach"),
                new University("Hochschule für angewandte Wissenschaften Coburg"),
                new University("Hochschule für Angewandte Wissenschaften Hamburg"),
                new University("Hochschule für Angewandte Wissenschaften Hof"),
                new University("Hochschule für angewandte Wissenschaften Kempten"),
                new University("Hochschule für angewandte Wissenschaften Landshut"),
                new University("Hochschule für angewandte Wissenschaften München"),
                new University("Hochschule für angewandte Wissenschaften Neu-Ulm"),
                new University("Hochschule für angewandte Wissenschaften Weihenstephan-Triesdorf"),
                new University("Hochschule für angewandtes Management"),
                new University("Hochschule für Bildende Künste (Städelschule)"),
                new University("Hochschule für Bildende Künste Braunschweig"),
                new University("Hochschule für Bildende Künste Dresden"),
                new University("Hochschule für Bildende Künste Hamburg"),
                new University("Hochschule für den öffentlichen Dienst in Bayern"),
                new University("Hochschule für evangelische Kirchenmusik der Evangelisch-Lutherischen Kirche in Bayern"),
                new University("Hochschule für Fernsehen und Film München"),
                new University("Hochschule für Finanzen NRW"),
                new University("Hochschule für Finanzen Rheinland-Pfalz"),
                new University("Hochschule für Finanzwirtschaft & Management GmbH"),
                new University("Hochschule für Forstwirtschaft Rottenburg"),
                new University("Hochschule für Gestaltung Offenbach"),
                new University("Hochschule für Gestaltung Schwäbisch Gmünd"),
                new University("Hochschule für Gesundheit – University of Applied Sciences"),
                new University("Hochschule für Grafik und Buchkunst Leipzig"),
                new University("Hochschule für Jüdische Studien Heidelberg"),
                new University("Hochschule für Katholische Kirchenmusik und Musikpädagogik"),
                new University("Hochschule für Kirchenmusik der Diözese Rottenburg-Stuttgart"),
                new University("Hochschule für Kirchenmusik der Evangelisch-Lutherischen Landeskirche Sachsens"),
                new University("Hochschule für Kirchenmusik der Evangelischen Kirche von Westfalen"),
                new University("Hochschule für Kirchenmusik der Evangelischen Landeskirche in Baden"),
                new University("Hochschule für Kirchenmusik der Evangelischen Landeskirche in Württemberg"),
                new University("Hochschule für Kommunikation und Gestaltung"),
                new University("Hochschule für Künste Bremen"),
                new University("Hochschule für Künste im Sozialen, Ottersberg"),
                new University("Hochschule für Musik Carl Maria von Weber Dresden"),
                new University("Hochschule für Musik Detmold"),
                new University("Hochschule für Musik Franz Liszt Weimar"),
                new University("Hochschule für Musik Freiburg im Breisgau"),
                new University("Hochschule für Musik Hanns Eisler Berlin"),
                new University("Hochschule für Musik Karlsruhe"),
                new University("Hochschule für Musik Nürnberg"),
                new University("Hochschule für Musik Saar"),
                new University("Hochschule für Musik und Darstellende Kunst Frankfurt am Main"),
                new University("Hochschule für Musik und Tanz Köln"),
                new University("Hochschule für Musik und Theater Felix Mendelssohn Bartholdy Leipzig"),
                new University("Hochschule für Musik und Theater Hamburg"),
                new University("Hochschule für Musik und Theater München"),
                new University("Hochschule für Musik und Theater Rostock"),
                new University("Hochschule für Musik Würzburg"),
                new University("Hochschule für Musik, Theater und Medien Hannover"),
                new University("Hochschule für nachhaltige Entwicklung Eberswalde"),
                new University("Hochschule für Öffentliche Verwaltung Bremen"),
                new University("Hochschule für öffentliche Verwaltung Kehl"),
                new University("Hochschule für öffentliche Verwaltung Rheinland-Pfalz"),
                new University("Hochschule für öffentliche Verwaltung und Finanzen Ludwigsburg"),
                new University("Hochschule für öffentliche Verwaltung und Rechtspflege (FH), Fortbildungszentrum des Freistaates Sachsen"),
                new University("Hochschule für Philosophie"),
                new University("Hochschule für Polizei Baden-Württemberg"),
                new University("Hochschule für Polizei und öffentliche Verwaltung Nordrhein-Westfalen"),
                new University("Hochschule für Rechtspflege Schwetzingen"),
                new University("Hochschule für Schauspielkunst Ernst Busch"),
                new University("Hochschule für Technik Stuttgart"),
                new University("Hochschule für Technik und Wirtschaft Berlin"),
                new University("Hochschule für Technik und Wirtschaft des Saarlandes"),
                new University("Hochschule für Technik und Wirtschaft Dresden – University of Applied Sciences"),
                new University("Hochschule für Technik, Wirtschaft und Kultur Leipzig"),
                new University("Hochschule für Technik, Wirtschaft und Medien Offenburg"),
                new University("Hochschule für Wirtschaft und Gesellschaft Ludwigshafen"),
                new University("Hochschule für Wirtschaft und Recht Berlin"),
                new University("Hochschule für Wirtschaft und Umwelt Nürtingen-Geislingen"),
                new University("Hochschule Furtwangen – Informatik, Technik, Wirtschaft, Medien, Gesundheit"),
                new University("Hochschule Geisenheim"),
                new University("Hochschule Hamm-Lippstadt"),
                new University("Hochschule Hannover"),
                new University("Hochschule Harz, Hochschule für angewandte Wissenschaften (FH)"),
                new University("Hochschule Heilbronn, Technik, Wirtschaft, Informatik"),
                new University("Hochschule Kaiserslautern (University of Applied Sciences)"),
                new University("Hochschule Karlsruhe – Technik und Wirtschaft"),
                new University("Hochschule Koblenz"),
                new University("Hochschule Konstanz Technik, Wirtschaft und Gestaltung"),
                new University("Hochschule Macromedia"),
                new University("Hochschule Magdeburg-Stendal"),
                new University("Hochschule Mainz"),
                new University("Hochschule Mannheim"),
                new University("Hochschule Merseburg"),
                new University("Hochschule Mittweida, University of Applied Sciences"),
                new University("Hochschule Neubrandenburg – University of Applied Sciences"),
                new University("Hochschule Niederrhein"),
                new University("Hochschule Nordhausen"),
                new University("Hochschule Osnabrück"),
                new University("Hochschule Pforzheim – Gestaltung, Technik, Wirtschaft und Recht"),
                new University("Hochschule Ravensburg-Weingarten"),
                new University("Hochschule Reutlingen"),
                new University("Hochschule Rhein-Waal – University of Applied Sciences"),
                new University("Hochschule RheinMain"),
                new University("Hochschule Ruhr West – University of Applied Sciences"),
                new University("Hochschule Schmalkalden"),
                new University("Hochschule Stralsund"),
                new University("Hochschule Trier – Trier University of Applied Sciences"),
                new University("Hochschule Weserbergland"),
                new University("Hochschule Wismar – University of Applied Sciences: Technology, Business and Design"),
                new University("Hochschule Worms, University of Applied Sciences"),
                new University("Hochschule Zittau/Görlitz"),
                new University("HSBA Hamburg School of Business Administration"),
                new University("HSD Hochschule Döpfer"),
                new University("Humboldt-Universität zu Berlin"),
                new University("IB Hochschule für Gesundheit und Soziales"),
                new University("International Psychoanalytic University Berlin"),
                new University("International School of Management"),
                new University("Internationale Hochschule Liebenzell (IHL)"),
                new University("Internationale Hochschule SDI München – Hochschule für angewandte Wissenschaften"),
                new University("IST-Hochschule für Management"),
                new University("IU Internationale Hochschule"),
                new University("Jade Hochschule – Wilhelmshaven/Oldenburg/Elsfleth"),
                new University("Johann Wolfgang Goethe-Universität, Frankfurt am Main"),
                new University("Johannes Gutenberg-Universität Mainz"),
                new University("Julius-Maximilians-Universität Würzburg"),
                new University("Justus-Liebig-Universität Gießen"),
                new University("Karlshochschule International University"),
                new University("Karlsruher Institut für Technologie"),
                new University("Katholische Hochschule Freiburg"),
                new University("Katholische Hochschule für Sozialwesen Berlin"),
                new University("Katholische Hochschule Mainz Catholic University of Applied Sciences"),
                new University("Katholische Hochschule Nordrhein-Westfalen – Catholic University of Applied Sciences"),
                new University("Katholische Stiftungshochschule München"),
                new University("Katholische Universität Eichstätt-Ingolstadt"),
                new University("Kirchliche Hochschule Wuppertal/Bethel (Hochschule für Kirche und Diakonie)"),
                new University("Kölner Hochschule für Katholische Theologie (KHKT)"),
                new University("Kolping Hochschule"),
                new University("Kommunale Hochschule für Verwaltung in Niedersachsen"),
                new University("Kühne Logistics University – Wissenschaftliche Hochschule für Logistik und Unternehmensführung"),
                new University("Kunstakademie Düsseldorf"),
                new University("Kunstakademie Münster, Hochschule für Bildende Künste"),
                new University("Kunsthochschule für Medien Köln"),
                new University("Leibniz-Fachhochschule"),
                new University("Leuphana Universität Lüneburg"),
                new University("Ludwig-Maximilians-Universität München"),
                new University("Lutherische Theologische Hochschule Oberursel"),
                new University("Martin-Luther-Universität Halle-Wittenberg"),
                new University("media Akademie – Hochschule Stuttgart"),
                new University("Mediadesign Hochschule für Design und Informatik"),
                new University("Medical School Berlin – Hochschule für Gesundheit und Medizin (MSB)"),
                new University("Medizinische Hochschule Brandenburg Theodor Fontane"),
                new University("Medizinische Hochschule Hannover (MHH)"),
                new University("Merz Akademie Hochschule für Gestaltung, Kunst und Medien, Stuttgart"),
                new University("MSH Medical School Hamburg – University of Applied Sciences and Medical University"),
                new University("Munich Business School"),
                new University("Musikhochschule Lübeck"),
                new University("Muthesius Kunsthochschule"),
                new University("NBS Northern Business School – University of Applied Sciences"),
                new University("Nordakademie Hochschule der Wirtschaft"),
                new University("Norddeutsche Akademie für Finanzen und Steuerrecht"),
                new University("Norddeutsche Hochschule für Rechtspflege – Niedersachsen"),
                new University("Ostbayerische Technische Hochschule Amberg-Weiden"),
                new University("Ostbayerische Technische Hochschule Regensburg"),
                new University("Otto-Friedrich-Universität Bamberg"),
                new University("Otto-von-Guericke-Universität Magdeburg"),
                new University("Pädagogische Hochschule Freiburg"),
                new University("Pädagogische Hochschule Heidelberg"),
                new University("Pädagogische Hochschule Karlsruhe"),
                new University("Pädagogische Hochschule Ludwigsburg"),
                new University("Pädagogische Hochschule Schwäbisch Gmünd"),
                new University("Pädagogische Hochschule Weingarten"),
                new University("Palucca Hochschule für Tanz Dresden"),
                new University("PFH – Private Hochschule Göttingen"),
                new University("Philipps-Universität Marburg"),
                new University("Philosophisch-Theologische Hochschule Münster"),
                new University("Philosophisch-Theologische Hochschule Sankt Georgen Frankfurt am Main"),
                new University("Private Hochschule für Wirtschaft und Technik Vechta/Diepholz"),
                new University("Private Universität Witten/Herdecke gGmbH"),
                new University("Provadis School of International Management and Technology"),
                new University("Psychologische Hochschule Berlin (PHB)"),
                new University("Quadriga Hochschule Berlin"),
                new University("Rheinisch-Westfälische Technische Hochschule Aachen"),
                new University("Rheinische Fachhochschule Köln"),
                new University("Rheinische Friedrich-Wilhelms-Universität Bonn"),
                new University("Rheinland-Pfälzische Technische Universität Kaiserslautern-Landau"),
                new University("Robert-Schumann-Hochschule Düsseldorf"),
                new University("Ruhr-Universität Bochum"),
                new University("Ruprecht-Karls-Universität Heidelberg"),
                new University("SRH Berlin University of Applied Sciences"),
                new University("SRH Fernhochschule – The Mobile University"),
                new University("SRH Hochschule für Gesundheit"),
                new University("SRH Hochschule Heidelberg"),
                new University("SRH Hochschule in Nordrhein-Westfalen"),
                new University("SRH Wilhelm Löhe Hochschule"),
                new University("Staatliche Akademie der Bildenden Künste Karlsruhe"),
                new University("Staatliche Akademie der Bildenden Künste Stuttgart"),
                new University("Staatliche Hochschule für Gestaltung Karlsruhe"),
                new University("Staatliche Hochschule für Musik Trossingen"),
                new University("Staatliche Hochschule für Musik und Darstellende Kunst Mannheim"),
                new University("Staatliche Hochschule für Musik und Darstellende Kunst Stuttgart"),
                new University("Steinbeis Hochschule"),
                new University("Stiftung Tierärztliche Hochschule Hannover"),
                new University("Technische Hochschule Aschaffenburg"),
                new University("Technische Hochschule Augsburg"),
                new University("Technische Hochschule Bingen"),
                new University("Technische Hochschule Brandenburg"),
                new University("Technische Hochschule Deggendorf"),
                new University("Technische Hochschule Georg Agricola"),
                new University("Technische Hochschule Ingolstadt"),
                new University("Technische Hochschule Köln"),
                new University("Technische Hochschule Lübeck"),
                new University("Technische Hochschule Mittelhessen – THM"),
                new University("Technische Hochschule Nürnberg Georg Simon Ohm"),
                new University("Technische Hochschule Ostwestfalen-Lippe"),
                new University("Technische Hochschule Rosenheim"),
                new University("Technische Hochschule Ulm"),
                new University("Technische Hochschule Wildau"),
                new University("Technische Hochschule Würzburg-Schweinfurt (THWS)"),
                new University("Technische Universität Bergakademie Freiberg"),
                new University("Technische Universität Berlin"),
                new University("Technische Universität Carolo-Wilhelmina zu Braunschweig"),
                new University("Technische Universität Chemnitz"),
                new University("Technische Universität Clausthal"),
                new University("Technische Universität Darmstadt"),
                new University("Technische Universität Dortmund"),
                new University("Technische Universität Dresden"),
                new University("Technische Universität Hamburg"),
                new University("Technische Universität Ilmenau"),
                new University("Technische Universität München"),
                new University("Theologische Fakultät Fulda"),
                new University("Theologische Fakultät Paderborn"),
                new University("Theologische Fakultät Trier"),
                new University("Theologische Hochschule Elstal"),
                new University("Theologische Hochschule Friedensau"),
                new University("Theologische Hochschule Reutlingen"),
                new University("Theologisches Seminar Ewersbach"),
                new University("Thüringer Fachhochschule für öffentliche Verwaltung"),
                new University("Touro College Berlin"),
                new University("Ukrainische Freie Universität"),
                new University("Universität Augsburg"),
                new University("Universität Bayreuth"),
                new University("Universität Bielefeld"),
                new University("Universität Bremen"),
                new University("Universität der Bundeswehr München"),
                new University("Universität der Künste Berlin"),
                new University("Universität des Saarlandes"),
                new University("Universität Duisburg-Essen"),
                new University("Universität Erfurt"),
                new University("Universität Greifswald"),
                new University("Universität Hamburg"),
                new University("Universität Hildesheim"),
                new University("Universität Hohenheim"),
                new University("Universität Kassel"),
                new University("Universität Koblenz"),
                new University("Universität Konstanz"),
                new University("Universität Leipzig"),
                new University("Universität Mannheim"),
                new University("Universität Osnabrück"),
                new University("Universität Paderborn"),
                new University("Universität Passau"),
                new University("Universität Potsdam"),
                new University("Universität Regensburg"),
                new University("Universität Rostock"),
                new University("Universität Siegen"),
                new University("Universität Stuttgart"),
                new University("Universität Trier"),
                new University("Universität Ulm"),
                new University("Universität Vechta"),
                new University("Universität zu Köln"),
                new University("Universität zu Lübeck"),
                new University("University of Europe for Applied Sciences"),
                new University("University of Labour"),
                new University("Victoria – Internationale Hochschule"),
                new University("VPU Vinzenz Pallotti University"),
                new University("VWA-Hochschule für berufsbegleitendes Studium"),
                new University("weißensee kunsthochschule berlin"),
                new University("Westfälische Hochschule Gelsenkirchen, Bocholt, Recklinghausen"),
                new University("Westfälische Wilhelms-Universität Münster"),
                new University("Westsächsische Hochschule Zwickau"),
                new University("WHU – Otto Beisheim School of Management"),
                new University("Wilhelm Büchner Hochschule – Private Fernhochschule Darmstadt"),
                new University("XU Exponential University of Applied Sciences"),
                new University("Zeppelin Universität – Hochschule zwischen Wirtschaft, Kultur und Politik")
        );

        universityRepository.saveAll(unis);
        System.out.println("Universities were saved in the database!");

        University newuni = new University("name");
        universityRepository.save(newuni);

        List<InternalModule> internalModules1 = Arrays.asList(internalModule1,internalModule2);
        List<InternalModule> internalModules2 = Arrays.asList(internalModule2);

        ExternalModule externalModule1 = new ExternalModule("20.300a", "Informatik101", "Informatik für Studienanfänger", testuni, 2.5);
        ExternalModule externalModule2 = new ExternalModule("20.300b", "Informatik102", "Informatik für Fortgeschrittene", testuni,6.0);
        List<ExternalModule> externalModules1 = Arrays.asList(externalModule1,externalModule2);
        List<ExternalModule> externalModules2 = Arrays.asList(externalModule2);

        externalModuleRepository.save(externalModule2);
        externalModuleRepository.save(externalModule1);
        List<Request> requests = Arrays.asList(
                new Request(procedure, externalModules1,
                        internalModules1, "Algorithmen und Datenstrukturen 1", "an"),
                new Request(procedure, externalModules2,
                        internalModules2, "Analysis für Informatiker", "an")
        );

        Request request1 = new Request(procedure3, externalModules1, internalModules1, "Anmerkung Antrag 1/ Vorgang 3", "an");
        Request request2 = new Request(procedure3, externalModules1, internalModules2, "Anmerkung Antrag 2/ Vorgang 3", "an");
        Request request3 = new Request(procedure2, externalModules2, internalModules1, "Anmerkung Antrag 1/ Vorgang 2", "an");
        Request request4 = new Request(procedure2, externalModules2, internalModules2, "Anmerkung Antrag 2/ Vorgang 2", "an");
        //Erstelle einen neuen Antrag
        requestRepository.saveAll(requests);
        requestRepository.save(request1);
        requestRepository.save(request2);
        requestRepository.save(request3);
        requestRepository.save(request4);
        System.out.println("Requests were saved in the database!");


        // Speichern der Module in der Datenbank

        System.out.println("Modules were saved in the database!");

        var testuser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();
        var studyOffice = User.builder()
                .username("studyOffice")
                .password(passwordEncoder.encode("studyOffice"))
                .role(Role.STUDY_OFFICE)
                .build();
        var examComittee = User.builder()
                .username("examComittee")
                .password(passwordEncoder.encode("examComittee"))
                .role(Role.EXAM_COMMITTEE)
                .build();
        userRepository.save(testuser);
        userRepository.save(studyOffice);
        userRepository.save(examComittee);
        System.out.println("Users were saved in the database!");

    }}*/
public class DataLoader { }


