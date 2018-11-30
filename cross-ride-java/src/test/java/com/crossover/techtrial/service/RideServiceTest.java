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
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.repositories.RideRepository;
import com.crossover.test.builder.PersonBuilder;
import com.crossover.test.builder.RideBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
public class RideServiceTest {

	@InjectMocks
	private RideServiceImpl rideService;
	
	@Mock
	RideRepository rideRepository;
	
	@Test
	public void testGetAllShouldReturnAllRide() {
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
		Ride firstRide = new RideBuilder().withId(first.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(first).withRider(null).build();
		Ride secondRide = new RideBuilder().withId(second.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(second).withRider(null).build();
		
		when(rideRepository.findAll()).thenReturn(Arrays.asList(firstRide, secondRide));
		assertThat(rideService.getAll(), CoreMatchers.hasItems(firstRide, secondRide));
		verify(rideRepository, times(1)).findAll();
        verifyNoMoreInteractions(rideRepository);
	}
	
	@Test
	public void testSaveRideShouldReturnThatRide() {
		
		Person person = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		Ride savedEntry = new RideBuilder().withId(person.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(person).withRider(null).build();
		when(rideRepository.save(any(Ride.class))).thenReturn(savedEntry);
		assertTrue(rideService.save(savedEntry).equals(savedEntry));
		ArgumentCaptor<Ride> entryCaptor = ArgumentCaptor.forClass(Ride.class);
        verify(rideRepository, times(1)).save(entryCaptor.capture());
        verifyNoMoreInteractions(rideRepository);
        
        Ride entryArgument = entryCaptor.getValue();
        assertTrue(entryArgument.equals(savedEntry));
	}
	
	@Test
	public void testFindByIdShouldReturnThatRide() {
		Person person = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		Ride entry = new RideBuilder().withId(person.getId()).withStartTime("2018-09-18T14:00:00").withEndTime("2018-09-18T14:01:00")
				.withDistance(10L).withDriver(person).withRider(null).build();
		Optional<Ride> optional = Optional.of(entry);
		when(rideRepository.findById(1L)).thenReturn(optional);
		assertTrue(rideService.findById(1L).equals(optional.get()));
		verify(rideRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(rideRepository);
	}
	
	@Test
	public void testFindByIdShouldReturnNullIfEntryInputNull() {
		Optional<Ride> optional = Optional.ofNullable(null);
		when(rideRepository.findById(1L)).thenReturn(optional);
		assertTrue(rideService.findById(1L) == null);
		verify(rideRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(rideRepository);
	}
}
