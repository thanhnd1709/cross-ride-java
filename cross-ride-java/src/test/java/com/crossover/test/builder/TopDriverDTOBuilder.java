package com.crossover.test.builder;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;

public class TopDriverDTOBuilder {
	
	private String name;

	private String email;

	private Long totalRideDurationInSeconds;

	private Long maxRideDurationInSecods;

	private Double averageDistance;
	
	
	public TopDriverDTOBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public TopDriverDTOBuilder withEmail(String email) {
		this.email = email;
		return this;
	}
	
	public TopDriverDTOBuilder withTotalRideDurationInSeconds(Long totalRideDurationInSeconds) {
		this.totalRideDurationInSeconds = totalRideDurationInSeconds;
		return this;
	}
	
	public TopDriverDTOBuilder withMaxRideDurationInSecods(Long maxRideDurationInSecods) {
		this.maxRideDurationInSecods = maxRideDurationInSecods;
		return this;
	}
	
	public TopDriverDTOBuilder withAverageDistance(Double averageDistance) {
		this.averageDistance = averageDistance;
		return this;
	}

	/**
	 * @param name
	 * @param email
	 * @param totalRideDurationInSeconds
	 * @param maxRideDurationInSecods
	 * @param averageDistance
	 */
	public TopDriverDTOBuilder(String name, String email, Long totalRideDurationInSeconds, Long maxRideDurationInSecods,
			Double averageDistance) {
		super();
		this.name = name;
		this.email = email;
		this.totalRideDurationInSeconds = totalRideDurationInSeconds;
		this.maxRideDurationInSecods = maxRideDurationInSecods;
		this.averageDistance = averageDistance;
	}
	
	public TopDriverDTO build() {
		return new TopDriverDTO(name, email, totalRideDurationInSeconds, maxRideDurationInSecods, averageDistance);
	}
	
	public TopDriverDTOBuilder() {}
}