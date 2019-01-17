package com.crossover.techtrial.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.test.builder.PersonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
public class PersonServiceTest {
	
	@InjectMocks
	private PersonServiceImpl personService;
	
	@Mock
	PersonRepository personRepository;
	
	@Test
	public void testGetAllShouldReturnAllPerson() {
		Person first = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		Person second = new PersonBuilder()
				.withId(2L)
				.withName("Tom")
				.withEmail("tom@crossover.com")
				.withRegistrationNumber("P002")
				.build();
		when(personRepository.findAll()).thenReturn(Arrays.asList(first, second));
		assertThat(personService.getAll(), CoreMatchers.hasItems(first, second));
		verify(personRepository, times(1)).findAll();
        verifyNoMoreInteractions(personRepository);
	}
	
	@Test
	public void testSavePersonShouldReturnThatPerson() {
		
		Person savedEntry = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		
		when(personRepository.save(any(Person.class))).thenReturn(savedEntry);
		assertTrue(personService.save(savedEntry).equals(savedEntry));
		ArgumentCaptor<Person> entryCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepository, times(1)).save(entryCaptor.capture());
        verifyNoMoreInteractions(personRepository);
        
        Person entryArgument = entryCaptor.getValue();
        assertTrue(entryArgument.equals(savedEntry));
	}
	
	@Test
	public void testFindByIdShouldReturnThatPerson() {
		Person entry = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		Optional<Person> optional = Optional.of(entry);
		when(personRepository.findById(1L)).thenReturn(optional);
		assertTrue(personService.findById(1L).equals(optional.get()));
		verify(personRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(personRepository);
	}
	
}
