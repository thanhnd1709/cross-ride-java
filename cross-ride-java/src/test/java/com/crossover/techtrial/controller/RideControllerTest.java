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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.exceptions.GlobalExceptionHandler;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.RideService;
import com.crossover.test.builder.PersonBuilder;
import com.crossover.test.builder.RideBuilder;
import com.crossover.test.builder.TestUtil;
import com.crossover.test.builder.TopDriverDTOBuilder;

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
		mockMvc = MockMvcBuilders.standaloneSetup(rideController).setControllerAdvice(new GlobalExceptionHandler()).build();
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
				.withStartTime("2018-09-18 14:00:01")
				.withEndTime("2018-09-18 15:00:01")
				.withDistance(1L)
				.withDriver(driver)
				.withRider(rider)
				.build();
		when(rideService.findById(1L)).thenReturn(found);
		MvcResult result =  mockMvc.perform(get("/api/ride/{ride-id}", 1L))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(jsonPath("$.id", is(1)))
		        .andExpect(jsonPath("$.startTime[0]", is(2018)))
		        .andExpect(jsonPath("$.startTime[1]", is(9)))
		        .andExpect(jsonPath("$.startTime[2]", is(18)))
		        .andExpect(jsonPath("$.startTime[3]", is(14)))
		        .andExpect(jsonPath("$.startTime[4]", is(00)))
		        .andExpect(jsonPath("$.startTime[5]", is(01)))
		        .andExpect(jsonPath("$.endTime[0]", is(2018)))
		        .andExpect(jsonPath("$.endTime[1]", is(9)))
		        .andExpect(jsonPath("$.endTime[2]", is(18)))
		        .andExpect(jsonPath("$.endTime[3]", is(15)))
		        .andExpect(jsonPath("$.endTime[4]", is(00)))
		        .andExpect(jsonPath("$.endTime[5]", is(01)))
		        .andExpect(jsonPath("$.distance", is(1)))
		        .andExpect(jsonPath("$.driver.id", is(driver.getId().intValue())))
		        .andExpect(jsonPath("$.driver.name", is(driver.getName())))
				.andExpect(jsonPath("$.driver.email", is(driver.getEmail())))
				.andExpect(jsonPath("$.driver.registrationNumber", is(driver.getRegistrationNumber())))
				.andExpect(jsonPath("$.rider.id", is(rider.getId().intValue())))
		        .andExpect(jsonPath("$.rider.name", is(rider.getName())))
				.andExpect(jsonPath("$.rider.email", is(rider.getEmail())))
				.andExpect(jsonPath("$.rider.registrationNumber", is(rider.getRegistrationNumber())))
				.andReturn();

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
	/*@Test
	public void createNewRide_WithNullId_ShouldReturnBadRequest() throws Exception {
		Ride entry = new RideBuilder()
				.withId(null)
				.withStartTime("2018-09-18 14:00:00")
				.withEndTime("2018-09-18 15:00:00")
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
	
	*/
	/**
	 * This method test the validation step (@Valid) of startTime field
	 * with the input of the startTime is null.
	 * @throws Exception
	 */
	@Test
	public void createNewRide_WithNullStartTime_ShouldReturnBadRequest() throws Exception {
		Ride entry = new RideBuilder()
				.withId(1L)
				.withStartTime(null)
				.withEndTime("2018-09-18 14:00:00")
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
	public void createNewRide_WithNullEndTime_ShouldReturnBadRequest() throws Exception {
		Ride entry = new RideBuilder()
				.withId(1L)
				.withStartTime("2018-09-18 14:00:00")
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
	 * 
	 * @throws Exception
	 */
	@Test
	public void createNewRide_WithStartTimeAfterEndTime_ShouldReturnBadRequest() throws Exception {
		Ride entry = new RideBuilder()
				.withId(1L)
				.withStartTime("2018-09-18 14:00:00")
				.withEndTime("2017-09-18 14:00:00")
				.withDistance(1L)
				.withDriver(new Person())
				.withRider(new Person())
				.build();
		MvcResult result = mockMvc.perform(post("/api/ride")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entry)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(RideController.START_AFTER_END_ERROR_MESSAGE))
                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
	    verifyZeroInteractions(rideService);
	}
	
	@Test
	public void createNewRide_WithBothNullDriverAndRider_ShouldReturnBadRequest() throws Exception {
		Ride entry = new RideBuilder()
				.withId(1L)
				.withStartTime("2018-09-18 14:00:00")
				.withEndTime("2019-09-18 14:00:00")
				.withDistance(1L)
				.withDriver(null)
				.withRider(null)
				.build();
		MvcResult result = mockMvc.perform(post("/api/ride")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entry)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(RideController.DRIVER_AND_RIDER_EMPTY_ERROR_MESSAGE))
                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
	    verifyZeroInteractions(rideService);
	}
	
	@Test
	public void createNewRide_WithNotRegisteredRider_ShouldReturnBadRequest() throws Exception {
		Person person = new PersonBuilder()
				.withId(1L)
				.withName("david")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		Ride entry = new RideBuilder()
				.withId(1L)
				.withStartTime("2018-09-18 14:00:00")
				.withEndTime("2019-09-18 14:00:00")
				.withDistance(1L)
				.withDriver(null)
				.withRider(person)
				.build();
		when(personService.findById(1L)).thenReturn(null);
		MvcResult result = mockMvc.perform(post("/api/ride")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entry)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(RideController.RIDER_NOT_REGISTERED_ERROR_MESSAGE))
                .andReturn();
		assertThat(result.getResolvedException(), is(notNullValue()));
	    verifyZeroInteractions(rideService);
	}
	
	@Test
	public void createNewRide_WithNotRegisteredDriver_ShouldReturnBadRequest() throws Exception {
		Person person = new PersonBuilder()
				.withId(1L)
				.withName("david")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		Ride entry = new RideBuilder()
				.withId(1L)
				.withStartTime("2018-09-18 14:00:00")
				.withEndTime("2019-09-18 14:00:00")
				.withDistance(1L)
				.withDriver(person)
				.withRider(null)
				.build();
		when(personService.findById(1L)).thenReturn(null);
		MvcResult result = mockMvc.perform(post("/api/ride")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entry)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(RideController.DRIVER_NOT_REGISTERED_ERROR_MESSAGE))
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
		TopDriverDTO first = new TopDriverDTOBuilder()
				.withName("first")
				.withEmail("first@crossover.com")
				.withTotalRideDurationInSeconds(100L)
				.withMaxRideDurationInSecods(100L)
				.withAverageDistance(10.0).build();
		TopDriverDTO second = new TopDriverDTOBuilder()
				.withName("second")
				.withEmail("second@crossover.com")
				.withTotalRideDurationInSeconds(200L)
				.withMaxRideDurationInSecods(200L)
				.withAverageDistance(20.0).build();
		TopDriverDTO third = new TopDriverDTOBuilder()
				.withName("third")
				.withEmail("third@crossover.com")
				.withTotalRideDurationInSeconds(300L)
				.withMaxRideDurationInSecods(300L)
				.withAverageDistance(30.0).build();
		TopDriverDTO fouth = new TopDriverDTOBuilder()
				.withName("fouth")
				.withEmail("fouth@crossover.com")
				.withTotalRideDurationInSeconds(400L)
				.withMaxRideDurationInSecods(400L)
				.withAverageDistance(40.0).build();
		TopDriverDTO fifth = new TopDriverDTOBuilder()
				.withName("fifth")
				.withEmail("fifth@crossover.com")
				.withTotalRideDurationInSeconds(500L)
				.withMaxRideDurationInSecods(500L)
				.withAverageDistance(50.0).build();
		List<TopDriverDTO> topDriverList = new ArrayList<TopDriverDTO>();
		topDriverList.add(first);
		topDriverList.add(second);
		topDriverList.add(third);
		topDriverList.add(fouth);
		topDriverList.add(fifth);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime startTime = LocalDateTime.parse("2018-09-18 14:00:00", formatter);
		LocalDateTime endTime = LocalDateTime.parse("2018-09-18 14:03:00", formatter);
		when(rideService.getTopRides(5L, startTime, endTime)).thenReturn(topDriverList);
		mockMvc.perform(get("/api/top-rides?max=5&startTime=2018-09-18T14:00:00&endTime=2018-09-18T14:03:00"))
		.andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[0].name", is(first.getName())))
        .andExpect(jsonPath("$[0].email", is(first.getEmail())))
        .andExpect(jsonPath("$[0].totalRideDurationInSeconds", is(100)))
        .andExpect(jsonPath("$[0].maxRideDurationInSecods", is(100)))
        .andExpect(jsonPath("$[0].averageDistance", is(10.0)))
		.andExpect(jsonPath("$[1].name", is(second.getName())))
        .andExpect(jsonPath("$[1].email", is(second.getEmail())))
        .andExpect(jsonPath("$[1].totalRideDurationInSeconds", is(200)))
        .andExpect(jsonPath("$[1].maxRideDurationInSecods", is(200)))
        .andExpect(jsonPath("$[1].averageDistance", is(20.0)))
		.andExpect(jsonPath("$[2].name", is(third.getName())))
        .andExpect(jsonPath("$[2].email", is(third.getEmail())))
        .andExpect(jsonPath("$[2].totalRideDurationInSeconds", is(300)))
        .andExpect(jsonPath("$[2].maxRideDurationInSecods", is(300)))
        .andExpect(jsonPath("$[2].averageDistance", is(30.0)))
		.andExpect(jsonPath("$[3].name", is(fouth.getName())))
        .andExpect(jsonPath("$[3].email", is(fouth.getEmail())))
        .andExpect(jsonPath("$[3].totalRideDurationInSeconds", is(400)))
        .andExpect(jsonPath("$[3].maxRideDurationInSecods", is(400)))
        .andExpect(jsonPath("$[3].averageDistance", is(40.0)))
		.andExpect(jsonPath("$[4].name", is(fifth.getName())))
        .andExpect(jsonPath("$[4].email", is(fifth.getEmail())))
        .andExpect(jsonPath("$[4].totalRideDurationInSeconds", is(500)))
        .andExpect(jsonPath("$[4].maxRideDurationInSecods", is(500)))
        .andExpect(jsonPath("$[4].averageDistance", is(50.0)));

		verify(rideService, times(1)).getTopRides(5L, startTime, endTime);
		//verify(rideService, times(1)).getTopRides();
		verifyNoMoreInteractions(personService);
	}

}
