package com.cs309.tutorial.tests;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	
	@PostMapping("/postTest1")
	public String postTest1(@RequestParam(value = "username", defaultValue = "World") String message) {
		//TODO
		return String.format("Hello, %s! You sent a post request with a parameter!", message);
	}
	
	@PostMapping("/postTest2")
	public String postTest2(@RequestBody TestData testData) {
		//TODO
		return String.format("Hello, %s! You sent a post request with a requestbody!", testData.getMessage());
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
