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
        System.out.println("Test-Procedure wird erstellt!");

        // Erstellen einer Liste von Modulen mit vordefinierten Daten.
        Procedure procedure = new Procedure(1,"Nur ein Test-Procedure");


        // Speichern der Module in der Datenbank
        procedureRepository.save(procedure);

        System.out.println("Module wurden in der Datenbank gesichert !");
        System.out.println("DataLoader wird ausgeführt!");

        // Erstellen einer Liste von Modulen mit vordefinierten Daten.
        List<Module> modules = Arrays.asList(
                new Module("10-201-2012", "Einführung in die objektorientierte Modellierung und Programmierung",""),
                new Module("10-201-2005-2", "Programmierparadigmen",""),
                new Module("10-201-2001-1", "Algorithmen und Datenstrukturen 1",""),
                new Module("10-201-2001-2", "Algorithmen und Datenstrukturen 2",""),
                new Module("10-201-2006-1", "Grundlagen der Technischen Informatik 1",""),
                new Module("10-201-2006-2", "Grundlagen der Technischen Informatik 2",""),
                new Module("10-201-2004", "Betriebs- und Kommunikationssysteme",""),
                new Module("10-201-2108-1", "Logik",""),
                new Module("10-201-2108-2", "Automaten und Sprachen",""),
                new Module("10-201-2009","Berechenbarkeit",""),
                new Module("10-201-2211", "Datenbanksysteme 1",""),
                new Module("10-201-2321", "Software Engineering",""),
                new Module("10-201-2320", "Software Engineering Praktikum",""),
                new Module("10-201-1602", "Diskrete Strukturen",""),
                new Module("10-201-1011", "Analysis",""),
                new Module("10-201-1015", "Lineare Algebra",""),
                new Module("10-201-1802", "Wahrscheinlichkeitstheorie",""),
                new Module("", "Seminarmodule (Wahlpflichtfach)",""),
                new Module("", "Kermodul (Wahlpflichtfach)",""),
                new Module("", "Vertiefungsmodul (Wahlpflichtfach)",""),
                new Module("", "Ergänzungsbereich (Wahlpflichtfach)",""),
                new Module("", "Schlüsselqualifikation (Wahlpflichtfach)",""),
                new Module("10-201-2108-1", "Bachelorseminar","Wenn man das hat ist man cool!")
        );



        // Speichern der Module in der Datenbank
        moduleRepository.saveAll(modules);

        System.out.println("Module wurden in der Datenbank gesichert !");
    }
}
