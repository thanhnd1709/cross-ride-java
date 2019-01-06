package com.crossover.techtrial.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.RideService;
import com.crossover.test.builder.PersonBuilder;
import com.crossover.test.builder.RideBuilder;
import com.crossover.test.builder.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RideControllerTest {
	MockMvc mockMvc;

	@InjectMocks
	private RideController rideController;
	
	@Mock
	private RideService rideService;
	
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
		mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
	}
	
	/**
	 * This method test '/api/ride/{ride-id}' api to get the ride with
	 * given ride id
	 * @throws Exception
	 */
	@Test
	public void findById_RideEntryFound_ShouldReturnFoundRideEntry() throws Exception {
		Person driver = new Person(2L, "David", "david@crossover.com", "P001");
		Person rider = new Person(3L, "Tom", "tom@crossover.com", "P002");
		Ride found = new RideBuilder()
				.withId(1L)
				//.withStartTime("300")
				//.withEndTime("300")
				.withDistance(1L)
				.withDriver(driver)
				.withRider(rider)
				.build();
		when(rideService.findById(1L)).thenReturn(found);
		mockMvc.perform(get("/api/ride/{ride-id}", 1L))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(jsonPath("$.id", is(1)))
		        .andExpect(jsonPath("$.startTime", is("300")))
		        .andExpect(jsonPath("$.endTime", is("300")))
		        .andExpect(jsonPath("$.distance", is(1)))
		        .andExpect(jsonPath("$.driver.id", is(driver.getId().intValue())))
		        .andExpect(jsonPath("$.driver.name", is(driver.getName())))
				.andExpect(jsonPath("$.driver.email", is(driver.getEmail())))
				.andExpect(jsonPath("$.driver.registrationNumber", is(driver.getRegistrationNumber())))
				.andExpect(jsonPath("$.rider.id", is(rider.getId().intValue())))
		        .andExpect(jsonPath("$.rider.name", is(rider.getName())))
				.andExpect(jsonPath("$.rider.email", is(rider.getEmail())))
				.andExpect(jsonPath("$.rider.registrationNumber", is(rider.getRegistrationNumber())));
		        //.andExpect(jsonPath("$.rider", is(rider.toString())));

		verify(rideService, times(1)).findById(1L);
		verifyNoMoreInteractions(rideService);
	}
	
	
	/**
	 * This method test api '/api/ride/{ride-id}' when no ride found
	 * @throws Exception
	 */
	@Test public void findById_PersonNotFound_ShouldReturnNotFoundEntity() throws Exception {
		when(rideService.findById(2L)).thenReturn(null);
		mockMvc.perform(get("/api/ride/{ride-id}", 2L))
			.andDo(print())
			.andExpect(status().isNotFound());
		verify(rideService, times(1)).findById(2L);
		verifyNoMoreInteractions(rideService);
	}
	
	/**
	 * This method test the validation step (@Valid) of id field
	 * with the input of the id is null.
	 * @throws Exception
	 */
	@Test
	public void add_NewRideWithNullId_ShouldReturnValidationError() throws Exception {
		Ride entry = new RideBuilder()
				.withId(null)
				//.withStartTime("300")
				//.withEndTime("300")
				.withDistance(1L)
				.withDriver(new Person())
				.withRider(new Person())
				.build();
		
		MvcResult result = mockMvc.perform(post("/api/ride")
	                .contentType(TestUtil.APPLICATION_JSON_UTF8)
	                .content(TestUtil.convertObjectToJsonBytes(entry)))
	                .andExpect(status().isBadRequest())
	                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
        verifyZeroInteractions(rideService);
	}
	
	
	/**
	 * This method test the validation step (@Valid) of startTime field
	 * with the input of the startTime is null.
	 * @throws Exception
	 */
	@Test
	public void add_NewRideWithNullStartTime_ShouldReturnValidationError() throws Exception {
		Ride entry = new RideBuilder()
				.withId(1L)
				.withStartTime(null)
				//.withEndTime("300")
				.withDistance(1L)
				.withDriver(new Person())
				.withRider(new Person())
				.build();
		
		MvcResult result = mockMvc.perform(post("/api/ride")
	                .contentType(TestUtil.APPLICATION_JSON_UTF8)
	                .content(TestUtil.convertObjectToJsonBytes(entry)))
	                .andExpect(status().isBadRequest())
	                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
        verifyZeroInteractions(rideService);
	}
	
	/**
	 * This method test the validation step (@Valid) of endTime field
	 * with the input of the endTime is null.
	 * @throws Exception
	 */
	@Test
	public void add_NewRideWithNullEndTime_ShouldReturnValidationError() throws Exception {
		Ride entry = new RideBuilder()
				.withId(1L)
				//.withStartTime("300")
				.withEndTime(null)
				.withDistance(1L)
				.withDriver(new Person())
				.withRider(new Person())
				.build();
		
		MvcResult result = mockMvc.perform(post("/api/ride")
	                .contentType(TestUtil.APPLICATION_JSON_UTF8)
	                .content(TestUtil.convertObjectToJsonBytes(entry)))
	                .andExpect(status().isBadRequest())
	                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
        verifyZeroInteractions(rideService);
	}
	
	/**
	 * This method test the '/api/top-rides' to get top count driver
	 * In this test I create 5 people with 7 rides, in that the second ride is not for driver
	 * and the first person has 3 rides. 
	 * The result should be 3 DTO correspond to first, third and fouth people, other values are calculated by hand
	 * @throws Exception
	 */
	@Test
	public void get_TopDriverShouldReturnFoundDriver()  throws Exception{
		Person first = new PersonBuilder().withId(1L).withName("first")
				.withEmail("first@crossover.com") .withRegistrationNumber("P001").build();
		Person second = new PersonBuilder().withId(2L).withName("second")
				.withEmail("second@crossover.com").withRegistrationNumber("P002").build();
		Person third = new PersonBuilder().withId(3L).withName("third")
				.withEmail("third@crossover.com").withRegistrationNumber("P003").build();
		Person fouth = new PersonBuilder().withId(4L).withName("fouth")
				.withEmail("fouth@crossover.com").withRegistrationNumber("P004").build();
		Person fifth = new PersonBuilder().withId(5L).withName("fifth")
				.withEmail("fifth@crossover.com").withRegistrationNumber("P005").build();
		List<Person> personList = new ArrayList<Person>();
		personList.add(first);
		personList.add(second);
		personList.add(third);
		personList.add(fouth);
		personList.add(fifth);
		/*Ride firstRide = new RideBuilder().withId(first.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(first).withRider(null).build();
		// Be carefule that we set the driver of secondride is 0 here
		Ride secondRide = new RideBuilder().withId(second.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(null).withRider(null).build();
		Ride thirdRide = new RideBuilder().withId(third.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(third).withRider(null).build();
		Ride fouthRide = new RideBuilder().withId(fouth.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(fouth).withRider(null).build();
		Ride fifthRide = new RideBuilder().withId(fifth.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(fifth).withRider(null).build();
		Ride sixthRide = new RideBuilder().withId(first.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:02:00")
				.withDistance(20L).withDriver(first).withRider(null).build();
		Ride seventhRide = new RideBuilder().withId(first.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:03:00")
				.withDistance(30L).withDriver(first).withRider(null).build();
		List<Ride> rideList = new ArrayList<Ride>();
		rideList.add(firstRide);
		rideList.add(secondRide);
		rideList.add(thirdRide);
		rideList.add(fouthRide);
		rideList.add(fifthRide);
		rideList.add(sixthRide);
		rideList.add(seventhRide);
		
		when(personService.getAll()).thenReturn(personList);
		when(rideService.getAll()).thenReturn(rideList);
		mockMvc.perform(get("/api/top-rides?max=3&startTime=2018-09-18T14:00:00&endTime=2018-09-18T14:03:00"))
		.andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[0].name", is(first.getName())))
        .andExpect(jsonPath("$[0].email", is(first.getEmail())))
        .andExpect(jsonPath("$[0].totalRideDurationInSeconds", is(360)))
        .andExpect(jsonPath("$[0].maxRideDurationInSecods", is(180)))
        .andExpect(jsonPath("$[0].averageDistance", is(20.0)))
		.andExpect(jsonPath("$[1].name", is(third.getName())))
        .andExpect(jsonPath("$[1].email", is(third.getEmail())))
        .andExpect(jsonPath("$[1].totalRideDurationInSeconds", is(60)))
        .andExpect(jsonPath("$[1].maxRideDurationInSecods", is(60)))
        .andExpect(jsonPath("$[1].averageDistance", is(10.0)))
		.andExpect(jsonPath("$[2].name", is(fouth.getName())))
        .andExpect(jsonPath("$[2].email", is(fouth.getEmail())))
        .andExpect(jsonPath("$[2].totalRideDurationInSeconds", is(60)))
        .andExpect(jsonPath("$[2].maxRideDurationInSecods", is(60)))
        .andExpect(jsonPath("$[2].averageDistance", is(10.0)));*/

		verify(personService, times(1)).getAll();
		//verify(rideService, times(1)).getTopRides();
		verifyNoMoreInteractions(rideService);
		verifyNoMoreInteractions(personService);
	}

}
