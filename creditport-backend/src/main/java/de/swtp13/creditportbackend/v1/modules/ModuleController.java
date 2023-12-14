package de.swtp13.creditportbackend.v1.modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Felix
 * Diese Klasse ist ein Controller für die Module.
 * Sie stellt eine REST-Schnittstelle für die Module bereit.
 * Die REST-Schnittstelle ist unter /api/modules erreichbar.
 */

@RestController
@RequestMapping("/modules")
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;

    @GetMapping
    public ResponseEntity<List<Module>> getAllModules() {
        System.out.println("Get all modules");
        return ResponseEntity.ok(moduleRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createModule(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false, defaultValue = "") String token,
            @RequestBody Module Module) {
        System.out.println("Create Module: " + Module.getModuleName());
        return ResponseEntity.ok(moduleRepository.save(Module));
    }


    // PUT: Update a Module
    @PutMapping("/{number}")
    public ResponseEntity<Module> updateModule(@PathVariable String number, @RequestBody Module ModuleDetails) {
        return moduleRepository.findById(number)
                .map(Module -> {
                    Module.setModuleName(ModuleDetails.getModuleName());
                    Module.setModuleDescription(ModuleDetails.getModuleDescription());
                    Module.setUniversity(ModuleDetails.getUniversity());
                    Module updatedModule = moduleRepository.save(Module);
                    return ResponseEntity.ok(updatedModule);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a Module
    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteModule(@PathVariable String number) {
        return moduleRepository.findById(number)
                .map(Module -> {
                    moduleRepository.delete(Module);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // import Module data from json fil
    @PostMapping("/import")
    public ResponseEntity<List<Module>> importUniversities(@RequestBody List<Module> modules) {
        moduleRepository.saveAll(modules);
        return ResponseEntity.ok(modules);
    }
}
