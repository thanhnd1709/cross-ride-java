/**
 * 
 */
package com.crossover.techtrial.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.techtrial.exceptions.PersonException;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.service.PersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author crossover
 */

@RestController
@RequestMapping("/api/person")
@Slf4j
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping(path = "/")
	public ResponseEntity<Person> register(@Valid @RequestBody Person p, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {			
			log.error("Binding error!", p);
			//throw new PersonException("", HttpStatus.BAD_REQUEST);
		}
		personService.save(p);
		log.info("Person {} was registered successfully", p);
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/")
	public ResponseEntity<List<Person>> getAllPersons() {
		return ResponseEntity.ok(personService.getAll());
	}

	@GetMapping(path = "/{person-id}")
	public ResponseEntity<Person> getPersonById(@PathVariable(name = "person-id", required = true) Long personId) {
		Person person = personService.findById(personId);
		if (person != null) {
			return ResponseEntity.ok(person);
		}
		return ResponseEntity.notFound().build();
	}

}
