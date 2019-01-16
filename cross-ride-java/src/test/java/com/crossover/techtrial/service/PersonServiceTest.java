package com.crossover.techtrial.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.test.builder.PersonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PersonServiceTest {

	@Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepo;
    
	@Test
	public void testSavePerson() {
		Person originalPerson = new PersonBuilder().withId(1L).withName("David").withEmail("david@crossover.com")
				.withRegistrationNumber("P001").build();
		Person savedPerson = null;
		savedPerson = personService.save(originalPerson);
		assertNotNull(savedPerson.getId());
		Person david = personRepo.findById(savedPerson.getId()).orElse(null);
		assertEquals(originalPerson.getId(), david.getId());
	}

//	@Test
//	public void testAddAirport() {
//		Airport originalAirport = getData("airport");
//		Airport addedAirport = null;
//		try {
//			addedAirport = service.addAirport(originalAirport);
//			assertNotNull(addedAirport.getId());
//			Airport floridaAir = airportRepo.findOne(addedAirport.getId());
//			compareAirport(floridaAir, originalAirport);
//		} finally {
//			// clean data
//			if (addedAirport != null && addedAirport.getId() != null)
//				airportRepo.delete(addedAirport.getId());
//		}
//	}

	// @InjectMocks
	// private PersonServiceImpl personService;
	//
	// @Mock
	// PersonRepository personRepository;
	//
	// @Test
	// public void testGetAllShouldReturnAllPerson() {
	// Person first = new PersonBuilder()
	// .withId(1L)
	// .withName("David")
	// .withEmail("david@crossover.com")
	// .withRegistrationNumber("P001")
	// .build();
	// Person second = new PersonBuilder()
	// .withId(2L)
	// .withName("Tom")
	// .withEmail("tom@crossover.com")
	// .withRegistrationNumber("P002")
	// .build();
	// when(personRepository.findAll()).thenReturn(Arrays.asList(first, second));
	// assertThat(personService.getAll(), CoreMatchers.hasItems(first, second));
	// verify(personRepository, times(1)).findAll();
	// verifyNoMoreInteractions(personRepository);
	// }
	//
	// @Test
	// public void testSavePersonShouldReturnThatPerson() {
	//
	// Person savedEntry = new PersonBuilder()
	// .withId(1L)
	// .withName("David")
	// .withEmail("david@crossover.com")
	// .withRegistrationNumber("P001")
	// .build();
	//
	// when(personRepository.save(any(Person.class))).thenReturn(savedEntry);
	// assertTrue(personService.save(savedEntry).equals(savedEntry));
	// ArgumentCaptor<Person> entryCaptor = ArgumentCaptor.forClass(Person.class);
	// verify(personRepository, times(1)).save(entryCaptor.capture());
	// verifyNoMoreInteractions(personRepository);
	//
	// Person entryArgument = entryCaptor.getValue();
	// assertTrue(entryArgument.equals(savedEntry));
	// }
	//
	// @Test
	// public void testFindByIdShouldReturnThatPerson() {
	// Person entry = new PersonBuilder()
	// .withId(1L)
	// .withName("David")
	// .withEmail("david@crossover.com")
	// .withRegistrationNumber("P001")
	// .build();
	// Optional<Person> optional = Optional.of(entry);
	// when(personRepository.findById(1L)).thenReturn(optional);
	// assertTrue(personService.findById(1L).equals(optional.get()));
	// verify(personRepository, times(1)).findById(1L);
	// verifyNoMoreInteractions(personRepository);
	// }
	//
	// @Test
	// public void testFindByIdShouldReturnNullIfEntryInputNull() {
	// Optional<Person> optional = Optional.ofNullable(null);
	// when(personRepository.findById(1L)).thenReturn(optional);
	// assertTrue(personService.findById(1L) == null);
	// verify(personRepository, times(1)).findById(1L);
	// verifyNoMoreInteractions(personRepository);
	// }

}
