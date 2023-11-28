package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.util.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Die Klasse enthält alle Methoden zu Vorgängen.
 */
@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    /**
     * Die Methode generiert einen neuen Vorgang und setzt die ID.
     * @return gibt den neuen Vorgang zurück.
     */
    public Procedure createProcedure() {
        String id;
        do {
            id = IDGenerator.generateID();
        } while (procedureRepository.existsById(id));

        Procedure procedure = new Procedure();

        procedure.setProcedureId(id);
        procedureRepository.save(procedure);
        return procedure;
    }

    /*
    public Procedure updateProcedure(String procedureId, Procedure procedureDetails) {
        // Retrieve the existing procedure from the repository
        Procedure existingProcedure = procedureRepository.findById(procedureId)
                .orElseThrow(() -> new EntityNotFoundException("Procedure not found with id: " + procedureId));

        // Update the fields of the existing procedure with the values from the details provided
        existingProcedure.setSomeField(procedureDetails.getSomeField());            //TODO: anpassen
        existingProcedure.setAnotherField(procedureDetails.getAnotherField());      //TODO: anpassen
        // ... update other fields as needed

        // Save the updated procedure back to the repository
        return procedureRepository.save(existingProcedure);
    }
     */
    public boolean existsById(String id) {
        return procedureRepository.existsById(id);
    }
}
