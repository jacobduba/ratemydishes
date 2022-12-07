package com.example.backend;

import com.example.backend.user.JwtTokenHelper;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import com.example.backend.user.payload.AuthRequest;
import com.example.backend.user.payload.ChangePasswordRequest;
import com.example.backend.user.payload.LoginRequest;
import com.example.backend.user.payload.RegisterRequest;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


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

    @AfterAll
    public void afterAll() {
        userRepository.deleteByNetId("user1");
    }

    // Generate token for admin user, if user does not exist return invalid token
    public String getToken(String netId) {
        User user = userRepository.findByNetId(netId);
        if (user == null)
            return "Invalid token";

        return jwtTokenHelper.generateAccessToken(userRepository.findByNetId(netId));
    }

    @ParameterizedTest
    @CsvSource({"admin,admin,200", "admin,poop,401", "poop,admin,401"})
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

    @ParameterizedTest
    @CsvSource({"user1,aaaaa,200,true", "us,aaaaa,401,false", "user3,aaa,401,false", "user1,aaaaa,401,true"})
    public void register(String netId, String password, int status, boolean shouldUserExists) throws JSONException {
        RestAssured.given()
                   .contentType("application/json")
                   .body(new RegisterRequest(netId, password))
                   .post("/user/register")
                   .then()
                   .statusCode(status);

        User user = userRepository.findByNetId(netId);
        if (shouldUserExists)
            assertNotNull(user);
        else
            assertNull(user);
    }

    @ParameterizedTest
    @CsvSource({"user1,aaaaa,newpw,200", "user1,aaaaa,newpw,401", "user2,aaaaa,aaaaa,401"})
    public void changePassword(String netId, String oldPassword, String newPassword, int status) {
        RestAssured.given()
                   .contentType("application/json")
                   .body(new ChangePasswordRequest(getToken(netId), oldPassword, newPassword))
                   .post("/user/changepw")
                   .then()
                   .statusCode(status);
    }
}
