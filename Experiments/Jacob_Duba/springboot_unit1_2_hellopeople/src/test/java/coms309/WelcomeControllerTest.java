package coms309;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // TIL you have to have this...
class WelcomeControllerTest {

    @Autowired
    private WelcomeController welcomeController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("WelcomeController != null")
    public void contextLoads() throws Exception {
        assertNotNull(welcomeController);
    }

    @Test
    @DisplayName("GET /")
    void welcome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello and welcome to COMS 309"));
    }
}