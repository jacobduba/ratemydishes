package edu.iastate.jduba.coms309;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    /**
     * Hello
     * @return String
     */
    @RequestMapping
    public String helloWorld() {
        return "Hello World from Spring Boot";
    }

    /**
     * Goodbye
     * @return String
     */
    @RequestMapping("/goodbye")
    public String goodbye() {
        return "Goodbye from Spring Boot";
    }
}
