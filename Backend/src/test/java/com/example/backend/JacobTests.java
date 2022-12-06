package com.example.backend;

import com.example.backend.user.JwtTokenHelper;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import com.example.backend.user.payload.AuthRequest;
import com.example.backend.user.payload.LoginRequest;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JacobTests {

    @LocalServerPort
    private int port;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    public void beforeAll() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    // Generate token for admin user, if user does not exist return invalid token
    public String getToken(String netId) {
        User user = userRepository.findByNetId(netId);
        if (user == null)
            return "Invalid token";

        return jwtTokenHelper.generateAccessToken(userRepository.findByNetId(netId));
    }

    @ParameterizedTest
    @CsvSource({"admin,admin,200", "admin,poop,401", "poop,admin,401" })
    public void login(String netId, String password, int status) throws JSONException {
        RestAssured.given()
                .contentType("application/json")
                .body(new LoginRequest(netId, password))
                .post("/user/login")
                .then()
                .statusCode(status);
    }

    @ParameterizedTest
    @CsvSource({"200,admin", "401,invalid_user"})
    public void ping(int status, String netId) throws JSONException {
        RestAssured.given()
                .contentType("application/json")
                .body(new AuthRequest(getToken(netId)))
                .post("/user/ping")
                .then()
                .statusCode(status);
    }
}
