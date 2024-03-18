package de.swtp13.creditportbackend.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModuleRepository;
import de.swtp13.creditportbackend.v1.universities.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExternalModuleControllerTest {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private ExternalModuleRepository externalModuleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void endpointWorks() throws Exception {
        mockMvc.perform(get("/modules/external"))
                .andExpect(status().isOk());
    }

    @Test
    public void getbyidendpointworks() throws Exception {
        UUID id = externalModuleRepository.findAll().get(0).getModuleId();
        mockMvc.perform(get("/modules/external/{id}", id))
                .andExpect(status().isForbidden());
    }

    @Test
    public void postendpointworks() throws Exception {
        ExternalModule module = new ExternalModule(null, "name", "desc.", universityRepository.findAll().get(0), 5.0);
        ObjectMapper mapper = new ObjectMapper();
        String modulejson = mapper.writeValueAsString(module);

        mockMvc.perform(
                post("/modules/external")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(modulejson)
        ).andExpect(status().is2xxSuccessful());
    }

}