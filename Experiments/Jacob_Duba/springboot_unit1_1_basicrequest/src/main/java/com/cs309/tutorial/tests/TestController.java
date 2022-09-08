package com.cs309.tutorial.tests;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TestController {
	private ArrayList<TestObject> testObjects = new ArrayList<TestObject>();
	
	@GetMapping("/getTest")
	public ArrayList<TestObject> getTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		return testObjects;
	}

	@PostMapping("/postTest1")
	public String postTest1(@RequestParam(value = "username", defaultValue = "World") String message) {
		testObjects.add(new TestObject(message));
		return String.format("Hello, %s! You sent a post request with a parameter!", message);
	}

	@PostMapping("/postTest2")
	public TestObject postTest2(@RequestBody TestData testData) {
		TestObject obj = new TestObject(testData.getMessage());
		testObjects.add(obj);
		return obj; // Return a JSON object
	}
	
	@DeleteMapping("/deleteTest")
	public void deleteTest(@RequestParam(value = "index") int i) {
		testObjects.remove(i);
	}
	
	@PutMapping("/putTest")
	public void putTest(@RequestParam(value = "index") int i, @RequestParam(value = "message") String message) {
		testObjects.set(i, new TestObject(message));
	}
}
