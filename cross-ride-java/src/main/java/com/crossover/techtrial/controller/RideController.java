/**
 * 
 */
package com.crossover.techtrial.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.RideService;

/**
 * RideController for Ride related APIs.
 * 
 * @author crossover
 *
 */
@RestController
public class RideController {

	@Autowired
	private RideService rideService;

	@Autowired
	private PersonService personService;

	@PostMapping(path = "/api/ride")
	public ResponseEntity<Ride> createNewRide(@Valid @RequestBody Ride ride) {
		return ResponseEntity.ok(rideService.save(ride));
	}

	@GetMapping(path = "/api/ride/{ride-id}")
	public ResponseEntity<Ride> getRideById(@PathVariable(name = "ride-id", required = true) Long rideId) {
		Ride ride = rideService.findById(rideId);
		if (ride != null)
			return ResponseEntity.ok(ride);
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

		List<TopDriverDTO> topDrivers = new ArrayList<TopDriverDTO>();
		// start implementation
		// get rideList
		List<Ride> rideList = rideService.getAll();
		// filter to driver list which satisfied the start time and end time
		rideList = rideList.stream().filter(ride -> ride.getDriver() != null)
				.filter(ride -> !startTime.isAfter(LocalDateTime.parse(ride.getStartTime())))
				.filter(ride -> !endTime.isBefore(LocalDateTime.parse(ride.getEndTime()))).collect(Collectors.toList());

		// Get person list
		List<Person> personList = personService.getAll();
		for (Person person : personList) {
			// for each person, get personal ride list
			List<Ride> personalRideList = rideList.stream().filter(ride -> ride.getDriver().getId() == person.getId())
					.collect(Collectors.toList());
			if (personalRideList.size() > 0) {
				// convert to TopDriverDTO with each person
				TopDriverDTO tempDTO = convertToDto(person, personalRideList);
				topDrivers.add(tempDTO);
			}
		}
		// Here I a little bit confuse about the TOP 5 expression.
		// What do you mean by "TOP", base on he is fastest in one ride, or fastest in
		// average, or max total distance?
		// Therefore, i just return 5 (or count) top rows
		return ResponseEntity.ok(topDrivers.stream().limit(count).collect(Collectors.toList()));

	}

	/**
	 * This function is used to create the TopDriverDTO from current person and his
	 * rides
	 * 
	 * @param person
	 *            current person
	 * @param personalRideList
	 *            ride list of this person
	 * @return
	 */
	private TopDriverDTO convertToDto(Person person, List<Ride> personalRideList) {
		// initiate the top driver DTO
		TopDriverDTO topDriverDTO = new TopDriverDTO();
		// set the name and email of this dto to the value of current person
		topDriverDTO.setName(person.getName());
		topDriverDTO.setEmail(person.getEmail());
		// initiate other properties
		double averageDistance = 0.0;
		double totalDistance = 0.0;
		Long totalRideDurationInSeconds = 0L;
		Long maxRideDurationInSecods = 0L;
		int count = personalRideList.size();
		// for loop to calculate total distance, total ride duration and maxride
		// duration in seconds
		for (Ride ride : personalRideList) {
			// get totalDistance
			totalDistance += ride.getDistance();
			// get total ride duration in seconds
			totalRideDurationInSeconds += Duration
					.between(LocalDateTime.parse(ride.getStartTime()), LocalDateTime.parse(ride.getEndTime()))
					.getSeconds();
			// calculate the max ride duration in second
			Long currentRideDuration = Duration
					.between(LocalDateTime.parse(ride.getStartTime()), LocalDateTime.parse(ride.getEndTime()))
					.getSeconds();
			if (maxRideDurationInSecods < currentRideDuration) {
				maxRideDurationInSecods = currentRideDuration;
			}
		}
		// calculate the average distance value
		averageDistance = totalDistance / count;
		// set attributes to topDriverDTO
		topDriverDTO.setAverageDistance(averageDistance);
		topDriverDTO.setTotalRideDurationInSeconds(totalRideDurationInSeconds);
		topDriverDTO.setMaxRideDurationInSecods(maxRideDurationInSecods);
		return topDriverDTO;
	}

}
