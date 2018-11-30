package com.crossover.test.builder;
import com.crossover.techtrial.model.Person;

public class PersonBuilder {
	
	Long id;
	String name;
	String email;
	String registrationNumber;
	
	public PersonBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public PersonBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public PersonBuilder withEmail(String email) {
		this.email = email;
		return this;
	}
	
	public PersonBuilder withRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
		return this;
	}
	
	public Person build() {
		return new Person(id, name, email, registrationNumber);
	}
	

}