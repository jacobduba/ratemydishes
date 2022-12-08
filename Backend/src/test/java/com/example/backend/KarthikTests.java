package com.example.backend;
import com.example.backend.location.Location;
import com.example.backend.location.LocationRepository;
import com.example.backend.menu.Menu;
import com.example.backend.menu.MenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.Matchers.hasItems;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KarthikTests {

    @LocalServerPort
    private int port;

    @Autowired
    LocationRepository lr;

    @Autowired
    MenuRepository mr;

    @BeforeAll
    public void beforeAll() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
    @AfterAll
    public void AfterAll() {
        try {
            lr.deleteByTitle("Windows");
            mr.deleteByTitle("Test");
        }
        catch (Exception e) {
        }
    }

    @Test
    public void testDiningCentersAreOpen() {

        if (!lr.existsByTitle("Friley Windows")) {
            Location loc = new Location(null, null, "[\"dining-center\"]", "friley-windows-2-2", "Windows");
            lr.save(loc);
        }

        RestAssured.get("/location/get-dining-centers").then().statusCode(200).assertThat().body("slug", hasItems("friley-windows-2-2"));

    }
    // @Test
    // public void testSingleMenuOfWindows() {
    //     ObjectMapper mapper = new ObjectMapper();
    //     if (!mr.existsByTitle("Friley Windows")) {
    //         ObjectNode test = mapper.createObjectNode();
    //         test.put("This is a test", "Test");
    //         Menu menu = new Menu();
    //         menu.setTitle("Test");
    //         menu.setClearMenus(test.textValue());
    //     }

    //     RestAssured.get("/menu/get-menu/friley-windows-2-2").then().statusCode(200).assertThat().body("menu", hasItems());
    // }

    @Test
    public void testPopCategories() {
        RestAssured.get("/menu/populate-categories").then().statusCode(200);
    }

    @Test
    public void testPopulateDB() {
        RestAssured.get("/location/populate-db").then().statusCode(200);
    }
}
