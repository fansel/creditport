package de.swtp13.creditportbackend.v1.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModuleRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import de.swtp13.creditportbackend.v1.courses.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Course")
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
     @Column(
             name = "course_id",
             updatable = false,
             nullable = false
     )
    private UUID courseId;

    @Column(
            name = "course_name",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String courseName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,CascadeType.MERGE
})
    @JoinTable(
            name= "course_intmodule",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "internal_module_id")
    )
    @JsonIgnore
    private List<InternalModule> internalModules;

    public Course(String courseName, List<InternalModule> internalModules){
        this.courseName=courseName;
        this.internalModules = new ArrayList<>(internalModules);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                // Add other relevant fields
                '}';
    }


    public List<InternalModule> getInternalModules() {
        if (internalModules == null){
            internalModules = new ArrayList<>();
        }
        return internalModules;
    }

    public void addInternalModules(List<InternalModule> newInternalModules){
        if (internalModules == null) {
            internalModules = new ArrayList<>();
        }
        internalModules.addAll(newInternalModules);
    }

    public void addInternalModule(InternalModule newInternalModule){
        if (internalModules == null) {
            internalModules = new ArrayList<>();
        }
        internalModules.add(newInternalModule);
    }
    public void removeInternalModule(InternalModule internalModule){
        this.internalModules.remove(internalModule);
        internalModule.getCourses().remove(this);
    }
}
