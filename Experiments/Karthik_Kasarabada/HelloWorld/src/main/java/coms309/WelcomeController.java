package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/welcome") // change url path
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}") //input name, name becomes parameter value for path variable
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }
}
