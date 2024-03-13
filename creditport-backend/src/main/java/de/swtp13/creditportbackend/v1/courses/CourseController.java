package de.swtp13.creditportbackend.v1.courses;

import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModuleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InternalModuleRepository internalModuleRepository;
    @Operation(summary = "returns a list of all courses", responses = {
            @ApiResponse(responseCode = "200", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Course.class))
            ))
    })
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        List<CourseDTO> courses = new ArrayList<>();
        for(Course course:courseRepository.findAll()){
            courses.add(new CourseDTO(course.getCourseId(),course.getCourseName(),internalModuleRepository.findInternalModulesByCoursesContains(course)));
        }
        return ResponseEntity.ok(courses);
    }
    @Operation(summary = "returns a single course", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "Course id not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable("id") UUID courseId){
        if (courseRepository.findById(courseId).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CourseDTO(
                courseId,
                courseRepository.findById(courseId).get().getCourseName(),
                internalModuleRepository.findInternalModulesByCoursesContains(courseRepository.findById(courseId).get()))
                );
    }
    @Operation(summary = "creates a course", responses = {
            @ApiResponse(responseCode = "201")
    })
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.status(201).body(courseRepository.save(course));
    }
    @Operation(summary = "updates the course with the given id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Course id not found", content = @Content)
    })
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable UUID courseId, @RequestBody Course CourseDetails) {
        if (CourseDetails.getInternalModules() == null){
            CourseDetails.setInternalModules(courseRepository.getReferenceById(courseId).getInternalModules());
        }
        return courseRepository.findById(courseId)
                .map(Course -> {
                    Course.setCourseName(CourseDetails.getCourseName());
                    Course.setInternalModules(CourseDetails.getInternalModules());
                    Course updatedCourse = courseRepository.save(Course);
                    return ResponseEntity.ok(updatedCourse);
                }).orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "deletes a course", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course id not found", content = @Content)
    })
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable UUID courseId){
        return courseRepository.findById(courseId)
                .map(course -> {
                    courseRepository.delete(course);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
