package de.swtp13.creditportbackend.modules;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void endpointShouldExist() throws Exception {
        mockMvc.perform(get("/api/modules"))
                .andExpect(status().is(not(HttpStatus.NOT_FOUND.value())));
    }

}
