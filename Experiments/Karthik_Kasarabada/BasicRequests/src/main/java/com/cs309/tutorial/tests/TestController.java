package com.cs309.tutorial.tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	
	@PostMapping("/JDWorker")
	public String postTest2(@RequestBody TestData testData) { //Changed to String as I will return Json as String

		JSONObject json = new JSONObject(); //Creating Json

		json.put("name", testData.getName()); //inputting paramter keys and values
		json.put("racf", testData.getRacf());

		return json.toString(); //returning as a string
	}

	@DeleteMapping("/deleteTest")
	public String deleteTest(@RequestParam(value = "name") String name) { //inputted one parameter

		return "Deleting Karthik"; //if name passes for deletion, return with deleting "name"
	}

	
	@PutMapping("/getName/{id}")
	public String putTest(@PathVariable String id) { //setting id as path variable
		if (id.isEmpty()) {

			return String.format("You did not state a Name parameter value!");
		}
		//simple check to make sure name is inputted

		return String.format("Hello, %s! You sent a put request with a parameter!", id);
		//Updating string to new string value
	}
	}

