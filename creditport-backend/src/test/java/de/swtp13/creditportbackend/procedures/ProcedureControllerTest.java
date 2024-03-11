package de.swtp13.creditportbackend.procedures;

import de.swtp13.creditportbackend.v1.courses.Course;
import de.swtp13.creditportbackend.v1.courses.CourseRepository;
import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureController;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.v1.universities.University;
import de.swtp13.creditportbackend.v1.universities.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProcedureControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    ProcedureController procedureController;
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    CourseRepository courseRepository;
    @Test
    public void endpointShouldExist() throws Exception {
        System.out.println("Procedure Test started");
        mockMvc.perform(get("/procedures"))
                .andExpect(status().is(not(HttpStatus.NOT_FOUND.value())));
        System.out.println("Procedure Test finished");
    }
    @Test
    public void procedureShouldExist() throws Exception {
        System.out.println("Procedure Test started");
        mockMvc.perform(get("/procedures/ids"))
                .andExpect(status().is(not(HttpStatus.NOT_FOUND.value())));
        System.out.println("Procedure Test finished");
    }
    @Test
    public void procedureIdShouldHaveSixFigures(){
        for(int i=0; i<1000; i++){
            University uni = new University("Uni");
            universityRepository.save(uni);
            Course course = new Course(uni.getUniId(),"kurs", List.of());
            courseRepository.save(course);
            Procedure testProcedure = new Procedure(uni,course);
            procedureRepository.save(testProcedure);
            int procedureId = testProcedure.getProcedureId();
            int length = String.valueOf(procedureId).length();
            Assert.state(length == 6, "procedure ID has 6 digits");
        }
    }
    @Test
    public void procedureShouldNotBeNull(){
        University uni = new University(("Uni"));
        Course course = new Course(uni.getUniId(),"kurs", List.of());
        Procedure testProcedure = new Procedure(uni,course);
        Assert.state(testProcedure != null, "test procedure is not null");
    }

}

