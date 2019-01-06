/**
 * 
 */
package com.crossover.techtrial.dto;

import lombok.Data;

/**
 * @author crossover
 *
 */
public @Data class TopDriverDTO {

	/**
	 * Constructor for TopDriverDTO
	 * 
	 * @param name
	 * @param email
	 * @param totalRideDurationInSeconds
	 * @param maxRideDurationInSecods
	 * @param averageDistance
	 */
	public TopDriverDTO(String name, String email, Long totalRideDurationInSeconds, Long maxRideDurationInSecods,
			Double averageDistance) {
		this.setName(name);
		this.setEmail(email);
		this.setAverageDistance(averageDistance);
		this.setMaxRideDurationInSecods(maxRideDurationInSecods);
		this.setTotalRideDurationInSeconds(totalRideDurationInSeconds);

	}

	public TopDriverDTO() {

	}

	private String name;

	private String email;

	private Long totalRideDurationInSeconds;

	private Long maxRideDurationInSecods;

	private Double averageDistance;
}
