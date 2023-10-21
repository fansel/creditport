package de.swtp13.creditportbackend.procedures.config;

/**
 * DataLoader Klasse zum Laden von Anfangsdaten in die Datenbank.
 * Diese Klasse wird beim Start der Anwendung ausgeführt und speichert
 * eine Liste von Modulen in der Datenbank.
 *
 * @author Felix
 */

import de.swtp13.creditportbackend.procedures.repository.ProcedureRepository;
import de.swtp13.creditportbackend.procedures.model.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Arrays;

@Component  // Diese Annotation gibt an, dass diese Klasse ein Component ist, und von Spring automatisch erkannt und instanziiert wird.
public class DataLoader implements CommandLineRunner {

    @Autowired  // Diese Annotation ermöglicht die Injektion des ModuleRepository.
    private ProcedureRepository procedureRepository;

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
    }
}
