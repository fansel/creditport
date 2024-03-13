package de.swtp13.creditportbackend.v1.internalmodules;

import de.swtp13.creditportbackend.v1.courses.Course;
import de.swtp13.creditportbackend.v1.courses.CourseRepository;
import de.swtp13.creditportbackend.v1.courses.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;

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
    @Transactional
    @PostMapping
    public ResponseEntity<InternalModule> createInternalModule(@RequestBody InternalModule moduleDetails) {
        List<Course> courses = new ArrayList<>();
        for (Course course: moduleDetails.getCourses()){
            if (courseRepository.existsById(course.getCourseId())){
                courses.add(courseRepository.findById(course.getCourseId()).get());
            } else{
                return ResponseEntity.notFound().build();
            }
        }
        for (Course course: courses){
            System.out.println(course.toString());
        }
        InternalModule internalModule = new InternalModule(
                moduleDetails.getNumber(), moduleDetails.getModuleName(), moduleDetails.getModuleDescription(), moduleDetails.getCreditPoints(),
                courses
        );
        moduleRepository.save(internalModule);
        for (Course course: moduleDetails.getCourses()){
            if (courseRepository.existsById(course.getCourseId())){
                course.getInternalModules();
                courseService.addInternalModules(course);
                course.addInternalModule(internalModule);
                courseRepository.save(course);
            } else{
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(internalModule);
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
        InternalModule module = moduleRepository.getReferenceById(moduleId);
        for (Course course: module.getCourses()) course.getInternalModules().remove(module);
        module.getCourses().clear();
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
        for(InternalModule internalModule: modules){
            for(Course course: internalModule.getCourses()){
                if(courseRepository.findById(course.getCourseId()).isEmpty()){
                    return ResponseEntity.notFound().build();
                }
                course.getInternalModules();
                courseService.addInternalModules(course);
                course.addInternalModule(internalModule);
                courseRepository.save(course);
            }
        }
        return ResponseEntity.ok(modules);
    }
}