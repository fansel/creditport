package de.swtp13.creditportbackend.v1.externalmodules;

import de.swtp13.creditportbackend.v1.universities.University;
import de.swtp13.creditportbackend.v1.users.ManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/modules/external")
public class ExternalModuleController {

    @Autowired
    private ExternalModuleRepository moduleRepository;
    @Autowired
    private ManagementService managementService;

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

    @Operation(summary = "returns a single external module", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExternalModule.class))),
            @ApiResponse(responseCode = "404", description = "Module id not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExternalModule> getModuleById(@PathVariable("id") UUID uuid) {
        return moduleRepository.findById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "creates an external module", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExternalModule.class)))
    })
    @PostMapping
    public ResponseEntity<ExternalModule> createModule(
            @RequestBody ExternalModule Module,
            @RequestHeader(value =HttpHeaders.AUTHORIZATION, required = false, defaultValue="") String token) {
        System.out.println("Create Module: " + Module.getModuleName());
        if(managementService.isAuthorized(token)){
            Module.setVerified(true);
        } else {
            Module.setVerified(false);
        }
        return ResponseEntity.status(201).body(moduleRepository.save(Module));
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
                    Module.setModuleNumber(ModuleDetails.getModuleNumber());
                    Module.setCreditPoints(ModuleDetails.getCreditPoints());
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
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // import Module data from json file
    @Operation(summary = "creates all external modules listed in the json of the body", responses = {
            @ApiResponse(responseCode = "200")
    })
    @PostMapping("/import")
    public ResponseEntity<List<ExternalModule>> importModules(@RequestBody List<ExternalModule> modules) {
        moduleRepository.saveAll(modules);
        return ResponseEntity.ok(modules);
    }
}
