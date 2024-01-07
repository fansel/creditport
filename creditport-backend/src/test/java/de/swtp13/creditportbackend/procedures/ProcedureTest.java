package de.swtp13.creditportbackend.procedures;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ProcedureTest {
    @Autowired
    ProcedureRepository procedureRepository;
    @Test
    public void procedureIdShouldHaveSixFigures(){
        for(int i=0; i<1000; i++){
            Procedure testProcedure = new Procedure("Uni","Kurs");
            procedureRepository.save(testProcedure);
            int procedureId = testProcedure.getProcedureId();
            int length = String.valueOf(procedureId).length();
            Assert.state(length == 6, "procedure ID has 6 digits");
        }
    }
}
