package de.swtp13.creditportbackend.v1.externalmodules;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/modules/external")
public class ExternalModuleController {

    @Autowired
    private ExternalModuleRepository moduleRepository;

    @Operation(summary = "returns a list of all external modules", responses = {
            @ApiResponse(responseCode = "200", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ExternalModule.class))
            ))
    })
    @GetMapping
    public ResponseEntity<List<ExternalModule>> getAllModules() {
        System.out.println("Get all modules");
        return ResponseEntity.ok(moduleRepository.findAll());
    }

    @Operation(summary = "creates a procedure", responses = {
            @ApiResponse(responseCode = "201")
    })
    @PostMapping
    public ResponseEntity<ExternalModule> createModule(@RequestBody ExternalModule Module) {
        System.out.println("Create Module: " + Module.getModuleName());
        return ResponseEntity.ok(moduleRepository.save(Module));
    }


    // PUT: Update a Module
    @Operation(summary = "updates the external module with the given id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "External module id not found", content = @Content)
    })
    @PutMapping("/{moduleId}")
    public ResponseEntity<ExternalModule> updateModule(@PathVariable UUID moduleId, @RequestBody ExternalModule ModuleDetails) {
        return moduleRepository.findById(moduleId)
                .map(Module -> {
                    Module.setModuleName(ModuleDetails.getModuleName());
                    Module.setModuleDescription(ModuleDetails.getModuleDescription());
                    Module.setUniversity(ModuleDetails.getUniversity());
                    ExternalModule updatedModule = moduleRepository.save(Module);
                    return ResponseEntity.ok(updatedModule);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a Module
    @Operation(summary = "deletes an external module", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "External module id not found", content = @Content)
    })
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable UUID moduleId) {
        return moduleRepository.findById(moduleId)
                .map(Module -> {
                    moduleRepository.delete(Module);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // import Module data from json file
    @Operation(summary = "creates all external modules listed in the json of the body", responses = {
            @ApiResponse(responseCode = "200")
    })
    @PostMapping("/import")
    public ResponseEntity<List<ExternalModule>> importUniversities(@RequestBody List<ExternalModule> modules) {
        moduleRepository.saveAll(modules);
        return ResponseEntity.ok(modules);
    }
}
