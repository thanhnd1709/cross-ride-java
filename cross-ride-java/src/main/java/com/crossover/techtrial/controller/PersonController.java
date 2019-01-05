/**
 * 
 */
package com.crossover.techtrial.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.service.PersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author crossover
 */

@RestController
@Slf4j
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping(path = "/api/person")
	public ResponseEntity<Person> register(@Valid @RequestBody Person p) {
		personService.save(p);
		log.info("Person {} was registered successfully", p);
		return ResponseEntity.ok(p);
	}

	
	@GetMapping(path = "/api/person")
	public ResponseEntity<List<Person>> getAllPersons() {
		List<Person> people = personService.getAll();
		if (CollectionUtils.isEmpty(people)) {
			return ResponseEntity.notFound().build();
		}
	    else {
	    	log.info("Getting all person successfully");
	    	return ResponseEntity.ok(people);
	    }
	}

	@GetMapping(path = "/api/person/{person-id}")
	public ResponseEntity<Object> getPersonById(@PathVariable(name = "person-id", required = true) Long personId) {
		Person person = personService.findById(personId);
		if (person != null) {
			log.info("Getting person successfully");
			return ResponseEntity.ok(person);
		}
		return ResponseEntity.notFound().build();
	}

}
