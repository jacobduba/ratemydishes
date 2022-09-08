package com.cs309.tutorial.tests;

public class TestData { //set what parameters Controller is receiving from request
	
	private String racf;
	private String name;

	public TestData(String name, String racf) { //Constructors for parameters
		this.name = name;
		this.racf = racf;
	}

	public String getName() { //return methods for parameters
		return name;
	}
	public String getRacf() {
		return racf;
	}
}
