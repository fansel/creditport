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
}
