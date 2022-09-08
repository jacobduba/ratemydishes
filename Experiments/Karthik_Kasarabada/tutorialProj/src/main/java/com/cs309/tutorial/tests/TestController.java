package com.cs309.tutorial.tests;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class TestController {
	
	
	@GetMapping("/getName")
	public String getTest(@RequestParam String Name) { //Changed parameter to be a Name I can input. Tested via postman
		if (Name.isEmpty()) {

			return String.format("You did not state a Name parameter value!");
		}
		//simple check to make sure name is inputted

		return String.format("Hello, %s! You sent a get request with a parameter!", Name);
		//In the case of variables, keys are the parameters, while the value is what I want the key to be.
	}

	
	@PostMapping("/postJDCool")
	public String postJDCool(@RequestParam String Name, Boolean Employee) { //Wanted to test Boolean as a parameter type

		if (Employee) {

			return String.format("Hello, %s! John Deere Rocks!", Name);
		}
		else {

			return String.format("Hello, %s! You sent a post successfully!", Name);
		}
	}
	
	@PostMapping(value = "/postTest2", consumes = "application/json", produces = "application/json") //request Consumes Json Data and Produces Json Data
	public String postTest2(@RequestBody TestData testData) { //

		Json =

		return
	}
	
	@DeleteMapping("/deleteTest")
	public void deleteTest() {
		//TODO
	}
	
	@PutMapping("/putTest")
	public void putTest() {
		//TODO
	}
}
