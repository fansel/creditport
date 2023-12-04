package de.swtp13.creditportbackend.v1.dto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;


import java.util.List;

/**
 * @author Maike
 * Die Klasse ist ein Controller für die Vorgänge.
 * REST-Schnittstelle: /api/procedure
 */

@RestController
@RequestMapping("/dto")
public class DTOController {

    @Autowired
    private DTORepository dtoRepository;
    //private final DTOService dtoService;

    @GetMapping
    public ResponseEntity<List<ProcedureRequestDTO>> getAllProceduresWithDetails() {
        DTOService dtoService1 = new DTOService(dtoRepository);
        List<ProcedureRequestDTO> procedureRequestDTOs = dtoService1.getProcedureRequests();
        return ResponseEntity.ok(procedureRequestDTOs);
    }

}
