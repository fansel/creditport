package de.swtp13.creditportbackend.procedures;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@EnableAutoConfiguration
@SpringBootTest
public class ProcedureControllerTest {


    //private MockMvc mockMvc;

   /* @Test
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
    }*/
    @Test
    public void procedureIdShouldHaveSixFigures(){
        Procedure testProcedure = new Procedure("Uni","Kurs");

        System.out.println(testProcedure.getProcedureId());
        Assert.isTrue(String.valueOf(testProcedure.getProcedureId()).length()==6);
    }
    @Test public void procedureShouldNotBeNull(){
        Procedure testProcedure = new Procedure("j","j");
        Assert.notNull(testProcedure);
    }
}

