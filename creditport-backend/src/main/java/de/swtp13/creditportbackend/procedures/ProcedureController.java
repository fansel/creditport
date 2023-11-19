package de.swtp13.creditportbackend.procedures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/procedure")
public class ProcedureController {

    @Autowired
    private ProcedureRepository ProcedureRepository;

    // GET all procedures
    @GetMapping
    public List<procedure> getAllProcedures() {
        System.out.println("Get all procedures");
        return procedureRepository.findAll();
        }
    }
}