/**
 * 
 */
package com.crossover.techtrial.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.exceptions.RideException;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.RideService;
import lombok.extern.slf4j.Slf4j;

/**
 * RideController for Ride related APIs.
 * 
 * @author crossover
 *
 */
@RestController
@Slf4j
public class RideController {
	
	public static final String START_AFTER_END_ERROR_MESSAGE = "Ride start time should before end time";
	public static final String DRIVER_AND_RIDER_EMPTY_ERROR_MESSAGE = "Must at least has rider or driver";
	public static final String RIDER_NOT_REGISTERED_ERROR_MESSAGE = "Rider has not been registered";
	public static final String DRIVER_NOT_REGISTERED_ERROR_MESSAGE = "Driver has not been registered";

	@Autowired
	private RideService rideService;
	
	@Autowired
	private PersonService personService;

	@PostMapping(path = "/api/ride")
	public ResponseEntity<Ride> createNewRide(@Valid @RequestBody Ride ride) {
		// if start time after end time, throw exception
		if (ride.getStartTime().isAfter(ride.getEndTime())) {
			throw new RideException(START_AFTER_END_ERROR_MESSAGE);
		}
		if (ride.getRider() == null && ride.getDriver() == null) {
			throw new RideException(DRIVER_AND_RIDER_EMPTY_ERROR_MESSAGE);
		}
		// if rider has not been registered before
		if (ride.getRider() != null && ride.getRider().getId() == null && personService.findById(ride.getRider().getId()) == null) {
			throw new RideException(RIDER_NOT_REGISTERED_ERROR_MESSAGE);
		}
		// if driver has not been registered before
		if (ride.getDriver() != null && ride.getDriver().getId() == null && personService.findById(ride.getDriver().getId()) == null) {
			throw new RideException(DRIVER_NOT_REGISTERED_ERROR_MESSAGE);
		}
		rideService.save(ride);
		log.info("Ride {} was registered successfully", ride);
		return ResponseEntity.ok(ride);
	}

	@GetMapping(path = "/api/ride/{ride-id}")
	public ResponseEntity<Ride> getRideById(@PathVariable(name = "ride-id", required = true) Long rideId) {
		Ride ride = rideService.findById(rideId);
		if (ride != null) {
			log.info("Getting ride successfully");
			return ResponseEntity.ok(ride);
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * This API returns the top 5 drivers with their email,name, total minutes,
	 * maximum ride duration in minutes. Only rides that starts and ends within the
	 * mentioned durations should be counted. Any rides where either start or
	 * endtime is outside the search, should not be considered. DONT CHANGE METHOD
	 * SIGNATURE AND RETURN TYPES
	 * 
	 * @return
	 */
	@GetMapping(path = "/api/top-rides")
	public ResponseEntity<List<TopDriverDTO>> getTopDriver(@RequestParam(value = "max", defaultValue = "5") Long count,
			@RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
			@RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime) {
		List<TopDriverDTO> topDrivers = rideService.getTopRides(count, startTime, endTime);
		return ResponseEntity.ok(topDrivers.stream().collect(Collectors.toList()));
	}
}
