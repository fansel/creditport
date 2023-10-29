package de.swtp13.creditportbackend.procedures;

import de.swtp13.creditportbackend.procedures.util.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;

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
