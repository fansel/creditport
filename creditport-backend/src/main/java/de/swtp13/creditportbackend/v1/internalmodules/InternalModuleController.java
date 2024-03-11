package de.swtp13.creditportbackend.v1.internalmodules;

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
@RequestMapping("/modules/internal")
public class InternalModuleController {

    @Autowired
    private InternalModuleRepository moduleRepository;

    @Operation(summary = "returns a list of all internal modules", responses = {
            @ApiResponse(responseCode = "200", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = InternalModule.class))
            ))
    })
    @GetMapping
    public ResponseEntity<List<InternalModule>> getAllModules() {
        System.out.println("Get all modules");
        return ResponseEntity.ok(moduleRepository.findAll());
    }

    @Operation(summary = "returns a single internal module", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalModule.class))),
            @ApiResponse(responseCode = "404", description = "Module id not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<InternalModule> getModuleById(@PathVariable("id") UUID uuid) {
        return moduleRepository.findById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "creates an internal module", responses = {
            @ApiResponse(responseCode = "201")
    })
    @PostMapping
    public ResponseEntity<?> createModule(@RequestBody InternalModule Module) {
        System.out.println("Create Module: " + Module.getModuleName());
        return ResponseEntity.status(201).body(moduleRepository.save(Module));
    }


    // PUT: Update a Module
    @Operation(summary = "updates the internal module with the given id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Internal module id not found", content = @Content)
    })
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
    @Operation(summary = "deletes an internal module", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Internal module id not found", content = @Content)
    })
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable UUID moduleId ) {
        return moduleRepository.findById(moduleId)
                .map(Module -> {
                    moduleRepository.delete(Module);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // import Module data from json file
    @Operation(summary = "creates all internal modules listed in the json of the body", responses = {
            @ApiResponse(responseCode = "200")
    })
    @PostMapping("/import")
    public ResponseEntity<List<InternalModule>> importModules(@RequestBody List<InternalModule> modules) {
        moduleRepository.saveAll(modules);
        return ResponseEntity.ok(modules);
    }
}