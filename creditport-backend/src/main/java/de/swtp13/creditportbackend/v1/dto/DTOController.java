package de.swtp13.creditportbackend.v1.dto;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


import java.util.List;

/**
 * @author Maike
 * Die Klasse ist ein Controller für die Vorgänge.
 * REST-Schnittstelle: /api/v1/dto
 */

@RestController
@RequestMapping("/dto")
@RequiredArgsConstructor
public class DTOController {

    private final DTOService dtoService;

    @GetMapping
    public ResponseEntity<List<ProcedureRequestDTO>> getAllProceduresWithDetails() {
        return ResponseEntity.ok(dtoService.getProcedureRequests());
    }

}
