/**
 * 
 */
package com.crossover.techtrial.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

@Entity
@Table(name = "ride")
public @Data class Ride implements Serializable {

	private static final long serialVersionUID = 9097639215351514001L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotNull
	@Column(name = "start_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime startTime;

	@NotNull
	@Column(name = "end_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime endTime;

	@Column(name = "distance")
	@Min(value = 0L)
	Long distance;

	@ManyToOne
	@JoinColumn(name = "driver_id", referencedColumnName = "id")
	Person driver;

	@ManyToOne
	@JoinColumn(name = "rider_id", referencedColumnName = "id")
	Person rider;

	public Ride(@NotNull Long id, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime,@Min(value = 0L) Long distance, Person driver,
			Person rider) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.distance = distance;
		this.driver = driver;
		this.rider = rider;
	}

	public Ride() {
	};

}
