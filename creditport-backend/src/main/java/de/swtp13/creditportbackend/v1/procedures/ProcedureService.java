package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.util.IDGenerator;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import de.swtp13.creditportbackend.v1.requests.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Die Klasse enthält alle Methoden zu Vorgängen.
 */
@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private RequestRepository requestRepository;

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


    public List<Procedure> getProceduresWithRequests() {
        List<Request> requests = requestRepository.findAllWithProcedure();
        Map<String, Procedure> procedureMap = new HashMap<>();
        for (Request request : requests) {
            Procedure procedure = request.getProcedure();
            Procedure finalProcedure = procedureMap.computeIfAbsent(procedure.getProcedureId(), k -> {
                Procedure newProcedure = new Procedure();
                newProcedure.setProcedureId(procedure.getProcedureId());
                newProcedure.setStatus(procedure.getStatus());
                newProcedure.setAnnotation(procedure.getAnnotation());
                newProcedure.setCreatedAt(procedure.getCreatedAt());
                newProcedure.setRequests(new ArrayList<>());
                return newProcedure;
                });
            finalProcedure.getRequests().add(request);
        }
        return new ArrayList<>(procedureMap.values());
        }
}
