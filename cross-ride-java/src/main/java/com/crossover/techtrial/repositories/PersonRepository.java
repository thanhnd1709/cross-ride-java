/**
 * 
 */
package com.crossover.techtrial.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crossover.techtrial.model.Person;

/**
 * Person repository for basic operations on Person entity.
 * 
 * @author crossover
 */
@RestResource(exported = false)
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
	
}
