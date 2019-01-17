package com.crossover.techtrial.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
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
	public void testSaveRideShouldReturnThatRide() {
		Person person = new PersonBuilder()
				.withId(1L)
				.withName("David")
				.withEmail("david@crossover.com")
				.withRegistrationNumber("P001")
				.build();
		Ride savedEntry = new RideBuilder().withId(person.getId()).withStartTime("2018-09-18 14:00:00").withEndTime("2018-09-18 14:01:00")
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
		Ride entry = new RideBuilder().withId(person.getId()).withStartTime("2018-09-18 14:00:00").withEndTime("2018-09-18 14:01:00")
				.withDistance(10L).withDriver(person).withRider(null).build();
		Optional<Ride> optional = Optional.of(entry);
		when(rideRepository.findById(1L)).thenReturn(optional);
		assertTrue(rideService.findById(1L).equals(optional.get()));
		verify(rideRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(rideRepository);
	}
	
}
