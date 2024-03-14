package de.swtp13.creditportbackend.v1.internalmodules;

import de.swtp13.creditportbackend.v1.courses.Course;
import de.swtp13.creditportbackend.v1.courses.CourseRepository;
import de.swtp13.creditportbackend.v1.courses.CourseService;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private RequestRepository requestRepository;

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
    public ResponseEntity<InternalModule> createInternalModule(
            @RequestBody InternalModuleDTO moduleDetails,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true, defaultValue="") String token) {
        List<Course> courses = new ArrayList<>();
        for (UUID courseId: moduleDetails.getCourseIds()){
            if (courseRepository.existsById(courseId)){
                courses.add(courseRepository.findById(courseId).get());
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
        for (UUID courseId: moduleDetails.getCourseIds()){
            if (courseRepository.existsById(courseId)){
                Course course = courseRepository.findById(courseId).get();
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
    public ResponseEntity<InternalModule> updateModule(
            @PathVariable UUID moduleId,
            @RequestBody InternalModuleDTO ModuleDetails,
            @RequestHeader(value =HttpHeaders.AUTHORIZATION, required = true, defaultValue="") String token) {
        return moduleRepository.findById(moduleId)
                .map(Module -> {
                    Module.setNumber(ModuleDetails.getNumber());
                    Module.setModuleName(ModuleDetails.getModuleName());
                    Module.setModuleDescription(ModuleDetails.getModuleDescription());
                    Module.setCreditPoints(ModuleDetails.getCreditPoints());
                    List<Course> courses = new ArrayList<>();
                    Module.setCourses(courses); //sic
                    for(UUID courseId:ModuleDetails.getCourseIds()){
                        if(courseRepository.findById(courseId).isPresent()){
                            Course course = courseRepository.findById(courseId).get();
                            courses.add(course);
                        }
                    }
                    Module.setCourses(courses);
                    InternalModule updatedModule = moduleRepository.save(Module);
                    for(Course course:courseRepository.findAllByInternalModulesContains(moduleRepository.findById(moduleId).get())){
                        course.getInternalModules().remove(moduleRepository.findById(moduleId).get());
                    }
                    for(UUID courseId:ModuleDetails.getCourseIds()){
                        if(courseRepository.findById(courseId).isPresent()){
                            Course course = courseRepository.findById(courseId).get();
                            if( !course.getInternalModules().contains(moduleRepository.findById(ModuleDetails.getId()).get())){
                                courseService.addInternalModules(course);
                                course.addInternalModule(updatedModule);
                                courseRepository.save(course);
                            }



                        }
                    }
                    return ResponseEntity.ok(updatedModule);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a Module
    @Operation(summary = "deletes an internal module", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Internal module id not found", content = @Content)
    })
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<?> deleteModule(
            @PathVariable UUID moduleId,
            @RequestHeader(value =HttpHeaders.AUTHORIZATION, required = true, defaultValue="") String token) {
        InternalModule module = moduleRepository.getReferenceById(moduleId);
        for (Course course: module.getCourses()) {
            course.getInternalModules().remove(module);
            courseRepository.save(course);
            //course.removeInternalModule(module);
        }
        for(Request request:requestRepository.findAllByInternalModulesContains(module)){
            request.getInternalModules().remove(module);
        }
        module.getCourses().clear();
        module.removeCourseAssociations();
        moduleRepository.save(module);
        moduleRepository.delete(module);
        return ResponseEntity.ok().build();
       /* return moduleRepository.findById(moduleId)
                .map(Module -> {
                    moduleRepository.delete(Module);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());*/
    }

    // import Module data from json file
    @Operation(summary = "creates all internal modules listed in the json of the body", responses = {
            @ApiResponse(responseCode = "200")
    })
    @PostMapping("/import")
    public ResponseEntity<List<InternalModule>> importModules(
            @RequestBody List<InternalModule> modules,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true, defaultValue="") String token) {
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