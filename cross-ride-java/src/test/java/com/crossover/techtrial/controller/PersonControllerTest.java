/**
 * 
 */
package com.crossover.techtrial.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.PersonServiceImpl;
import com.crossover.test.builder.PersonBuilder;
import com.crossover.test.builder.TestUtil;


/**
 * @author thanhnd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

	MockMvc mockMvc;

	@InjectMocks
	private PersonController personController;
	
	@Mock
	private PersonService personService;

	@Autowired
	PersonRepository personRepository;

	/**
	 * Method to setting up
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	}

	/**
	 * This method test the 'api/person' to get list of people available
	 * @throws Exception
	 */
	@Test
	public void getAllPersons_PersonFound_ShouldReturnFoundPersonEntries() throws Exception {
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
		when(personService.getAll()).thenReturn(Arrays.asList(first, second));
		mockMvc.perform(get("/api/person"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andDo(print())
				//.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("David")))
				.andExpect(jsonPath("$[0].email", is("david@crossover.com")))
				.andExpect(jsonPath("$[0].registrationNumber", is("P001")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Tom")))
				.andExpect(jsonPath("$[1].email", is("tom@crossover.com")))
				.andExpect(jsonPath("$[1].registrationNumber", is("P002")));
		verify(personService, times(1)).getAll();
        verifyNoMoreInteractions(personService);
	}
	/**
	 * This method test '/api/person/{person-id}' api to get the person with
	 * given person id
	 * @throws Exception
	 */
	@Test
	public void findById_PersonEntryFound_ShouldReturnFoundPersonEntry() throws Exception {
		Person found = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		when(personService.findById(1L)).thenReturn(found);
		mockMvc.perform(get("/api/person/{person-id}", 1L))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(jsonPath("$.id", is(1)))
		        .andExpect(jsonPath("$.name", is("David")))
		        .andExpect(jsonPath("$.email", is("david@crossover.com")))
		        .andExpect(jsonPath("$.registrationNumber", is("P001")));

		verify(personService, times(1)).findById(1L);
		verifyNoMoreInteractions(personService);
	}
	
	/**
	 * This method test api '/api/person/{person-id}' when no user found
	 * @throws Exception
	 */
	@Test public void findById_PersonNotFound_ShouldReturnNotFoundEntity() throws Exception {
		when(personService.findById(2L)).thenReturn(null);
		mockMvc.perform(get("/api/person/{person-id}", 2L))
			.andDo(print())
			.andExpect(status().isNotFound());
		verify(personService, times(1)).findById(2L);
		verifyNoMoreInteractions(personService);
	}
	
	/**
	 * This method test the validation step (@Valid) of email field
	 * with the input of the email is null.
	 * @throws Exception
	 */
	@Test
	public void add_NullEmail_ShouldReturnValidationErrorsForNullEmail() throws Exception {
		Person entry = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail(null)
				.withRegistrationNumber("P001")
				.build();
		
		MvcResult result = mockMvc.perform(post("/api/person")
	                .contentType(TestUtil.APPLICATION_JSON_UTF8)
	                .content(TestUtil.convertObjectToJsonBytes(entry)))
	                .andExpect(status().isBadRequest())
	                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
        verifyZeroInteractions(personService);
	}
	
	/**
	 * This method test the validation step (@Valid) of id field
	 * with the input of the id is null.
	 * @throws Exception
	 */
	@Test
	public void add_NullId_ShouldReturnValidationErrorsForNullId() throws Exception {
		Person entry = new PersonBuilder()
				.withId(null)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		
		MvcResult result = mockMvc.perform(post("/api/person")
	                .contentType(TestUtil.APPLICATION_JSON_UTF8)
	                .content(TestUtil.convertObjectToJsonBytes(entry)))
	                .andExpect(status().isBadRequest())
	                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
        verifyZeroInteractions(personService);
	}
	
	/**
	 * This method test the successfully case when saving person
	 * @throws Exception
	 */
	@Test
    public void save_NewPersonEntry_ShouldSavePersonEntryAndReturnSavedEntry() throws Exception {
		Person savedEntry = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		when(personService.save(any(Person.class))).thenReturn(savedEntry);
		mockMvc.perform(post("/api/person")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(savedEntry)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("David")))
                .andExpect(jsonPath("$.email", is("david@crossover.com")))
                .andExpect(jsonPath("$.registrationNumber", is("P001")));
		
		ArgumentCaptor<Person> entryCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService, times(1)).save(entryCaptor.capture());
        verifyNoMoreInteractions(personService);
        
        Person entryArgument = entryCaptor.getValue();
        assertThat(entryArgument.getId(), is(1L));
        assertThat(entryArgument.getName(), is("David"));
        assertThat(entryArgument.getEmail(), is("david@crossover.com"));
        assertThat(entryArgument.getRegistrationNumber(), is("P001"));
        
        
	}
	

	// ThanhND
	/*@Test
	public void testPanelShouldBeRegistered() throws Exception {
		HttpEntity<Object> person = getHttpEntity("{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\","
				+ " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");
		ResponseEntity<Person> response = template.postForEntity("/api/person", person, Person.class);
		// Delete this user
		personRepository.deleteById(response.getBody().getId());
		Assert.assertEquals("test 1", response.getBody().getName());
		Assert.assertEquals("test10000000000001@gmail.com", response.getBody().getEmail());
		Assert.assertEquals("41DCT", response.getBody().getRegistrationNumber());
		Assert.assertEquals(200, response.getStatusCode().value());
	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}

	@Test
	public void testShouldBeDelete() throws Exception {
		HttpEntity<Object> person = getHttpEntity("{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\","
				+ " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");
		ResponseEntity<Person> response = template.postForEntity("/api/person", person, Person.class);
		personRepository.deleteById(response.getBody().getId());
	}*/

}
