package coms309.people;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // TIL you have to have this...
class PeopleControllerTest {
    @Autowired // This flag is pretty cool, allows Spring to inject an already created instance of PeopleController into the test.
    private PeopleController controller;

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new GsonBuilder().create(); // We need GSON to serialize a class into a JSON object which can be sent with MockMVC

    @Test
    @DisplayName("PeopleController != null")
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    // From what I can tell, the controller is magically reset every time.... even though it's being injected?
    @Test
    @DisplayName("GET /people")
    public void getPeople() throws Exception {
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("POST /people")
    public void postPeople() throws Exception {
        mockMvc.perform(
                post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new Person("Jacob", "Duba", "660", "708")))
            ).andExpect(content().string("New person Jacob Saved"));
        assertEquals(1, controller.peopleList.size());
        assertEquals("Jacob", controller.peopleList.get("Jacob").getFirstName());
    }
}
