package com.example.backend;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class JacobTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @ParameterizedTest
    @CsvSource({"admin,admin,200", "admin,poop,401", "poop,admin,401" })
    public void login(String netId, String password, int status) {
        RestAssured.given()
                .contentType("application/json")
                .body("{\"netId\":\"" + netId + "\",\"password\":\"" + password + "\"}")
                .when()
                .post("/user/login")
                .then()
                .statusCode(status);
    }
}
