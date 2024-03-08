package de.swtp13.creditportbackend.v1.internalmodules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Felix
 * Diese Klasse ist ein Controller für die Module.
 * Sie stellt eine REST-Schnittstelle für die Module bereit.
 * Die REST-Schnittstelle ist unter /api/modules erreichbar.
 */

@RestController
@RequestMapping("/modules/internal")
public class InternalModuleController {

    @Autowired
    private InternalModuleRepository moduleRepository;

    @GetMapping
    public ResponseEntity<List<InternalModule>> getAllModules() {
        System.out.println("Get all modules");
        return ResponseEntity.ok(moduleRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createModule(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false, defaultValue = "") String token,
            @RequestBody InternalModule Module) {
        System.out.println("Create Module: " + Module.getModuleName());
        return ResponseEntity.ok(moduleRepository.save(Module));
    }


    // PUT: Update a Module
    @PutMapping("/{moduleId}")
    public ResponseEntity<InternalModule> updateModule(@PathVariable UUID moduleId, @RequestBody InternalModule ModuleDetails) {
        return moduleRepository.findById(moduleId)
                .map(Module -> {
                    Module.setNumber(ModuleDetails.getNumber());
                    Module.setModuleName(ModuleDetails.getModuleName());
                    Module.setModuleDescription(ModuleDetails.getModuleDescription());
                    Module.setCreditPoints(ModuleDetails.getCreditPoints());
                    InternalModule updatedModule = moduleRepository.save(Module);
                    return ResponseEntity.ok(updatedModule);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a Module
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable UUID moduleId ) {
        return moduleRepository.findById(moduleId)
                .map(Module -> {
                    moduleRepository.delete(Module);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // import Module data from json file
    @PostMapping("/import")
    public ResponseEntity<List<InternalModule>> importModules(@RequestBody List<InternalModule> modules) {
        moduleRepository.saveAll(modules);
        return ResponseEntity.ok(modules);
    }
}