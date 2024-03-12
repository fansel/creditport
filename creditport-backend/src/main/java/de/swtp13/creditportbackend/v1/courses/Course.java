package de.swtp13.creditportbackend.v1.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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

    @ManyToMany
    @JoinTable(
            name= "course_intmodule",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "internal_module_id")
    )
    private List<InternalModule> internalModules;

    public Course(String courseName, List<InternalModule> internalModules){
        this.courseName=courseName;
        this.internalModules=internalModules;
    }
}
