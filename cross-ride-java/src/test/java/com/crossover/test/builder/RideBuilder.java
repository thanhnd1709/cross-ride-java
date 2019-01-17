package com.crossover.test.builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;

public class RideBuilder {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

	public RideBuilder withStartTime(String startTime) {
		if (startTime == null) {
			this.startTime = null;
		} else {
			this.startTime = LocalDateTime.parse(startTime, formatter);
		}
		return this;
	}

	public RideBuilder withEndTime(String endTime) {
		if (endTime == null) {
			this.endTime = null;
		} else {
			this.endTime = LocalDateTime.parse(endTime, formatter);
		}
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