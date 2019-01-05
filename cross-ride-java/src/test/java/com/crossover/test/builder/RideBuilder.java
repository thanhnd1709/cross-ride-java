package com.crossover.test.builder;

import java.time.LocalDateTime;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;

public class RideBuilder {

	Long id;
	LocalDateTime startTime;
	LocalDateTime endTime;
	Long distance;
	Person driver;
	Person rider;

	public RideBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public RideBuilder withStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		return this;
	}

	public RideBuilder withEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		return this;
	}

	public RideBuilder withDistance(Long distance) {
		this.distance = distance;
		return this;
	}

	public RideBuilder withDriver(Person driver) {
		this.driver = driver;
		return this;
	}

	public RideBuilder withRider(Person rider) {
		this.rider = rider;
		return this;
	}
	
	public Ride build() {
		return new Ride(id, startTime, endTime, distance, driver, rider);
	}

}