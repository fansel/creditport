package de.swtp13.creditportbackend.modules;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExternalModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void endpointWorks() throws Exception {
        mockMvc.perform(get("/modules/external"))
                .andExpect(status().isOk());
    }

    /*@Test
    public void getbyidendpointworks() throws Exception {
        mockMvc.perform(get("/modules/external/7b456ab2-fd34-430b-bb39-9ad15f4b851a"))
                .andExpect(status().isOk());
    }*/

    @Test
    public void postendpointworks() throws Exception {
        //mockMvc.perform(post("/modules/external/").requestAttr());
    }

}