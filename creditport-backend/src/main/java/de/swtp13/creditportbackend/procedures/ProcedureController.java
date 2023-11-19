package de.swtp13.creditportbackend.procedures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Maike
 * Die Klasse ist ein Controller für die Vorgänge.
 * REST-Schnittstelle: /api/procedure
 */
@RestController
@RequestMapping("/api/procedure")
public class ProcedureController {

    @Autowired
    private ProcedureRepository procedureRepository;

    /**
     * @author Maike
     * @return List of procedures in JSON Format
     */
    @GetMapping
    public ResponseEntity<List<Procedure>> getAllProceduresWithDetails() {
        List<Procedure> procedures = procedureRepository.findAllWithDetails();
        return ResponseEntity.ok(procedures);
    }
}