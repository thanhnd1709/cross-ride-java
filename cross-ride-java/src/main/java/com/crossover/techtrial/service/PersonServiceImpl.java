/**
 * 
 */
package com.crossover.techtrial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;

/**
 * @author crossover
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;

	@Override
	@Cacheable (value = "person", key = "#personId")
	public List<Person> getAll() {
		List<Person> personList = new ArrayList<>();
		personRepository.findAll().forEach(personList::add);
		return personList;

	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@CachePut (value = "person", key = "#result.id")
	public Person save(Person p) {
		return personRepository.save(p);
	}

	@Override
	@Cacheable (value = "person", key = "#personId")
	public Person findById(Long personId) {
		Optional<Person> dbPerson = personRepository.findById(personId);
		return dbPerson.orElse(null);
	}

}
