package de.swtp13.creditportbackend.v1.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Felix
 * Diese Klasse ist ein Controller für die Module.
 * Sie stellt eine REST-Schnittstelle für die Module bereit.
 * Die REST-Schnittstelle ist unter /api/modules erreichbar.
 */
@RestController
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;

    @GetMapping("/modules")
    public List<Module> getAllModules() {
        System.out.println("Get all modules");
        return moduleRepository.findAll();
    }
}
