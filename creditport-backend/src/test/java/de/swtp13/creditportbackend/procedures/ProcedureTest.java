package de.swtp13.creditportbackend.procedures;

import de.swtp13.creditportbackend.v1.courses.Course;
import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.v1.universities.University;
import de.swtp13.creditportbackend.v1.universities.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class ProcedureTest {
    @Autowired
    ProcedureRepository procedureRepository;
    @Autowired
    UniversityRepository universityRepository;
    @Test
    public void procedureIdShouldHaveSixFigures(){
        for(int i=0; i<1000; i++){
            University uni = new University(("Uni"));
            universityRepository.save(uni);
            Course course = new Course(uni.getUniId(),"kurs", List.of());
            Procedure testProcedure = new Procedure(uni,course);
            procedureRepository.save(testProcedure);
            int procedureId = testProcedure.getProcedureId();
            int length = String.valueOf(procedureId).length();
            Assert.state(length == 6, "procedure ID has 6 digits");
        }
    }
}
