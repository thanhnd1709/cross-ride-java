/**
 * 
 */
package com.crossover.techtrial.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ride")
public class Ride implements Serializable{

  private static final long serialVersionUID = 9097639215351514001L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  Long id;
  
  @NotNull
  @Column(name = "start_time")
  String startTime;
  
  @NotNull
  @Column(name = "end_time")
  String endTime;
  
  @Column(name = "distance")
  Long distance;
  
  @ManyToOne
  @JoinColumn(name = "driver_id", referencedColumnName = "id")
  Person driver;
  
  @ManyToOne
  @JoinColumn(name = "rider_id", referencedColumnName = "id")
  Person rider;

  
  public Ride() {};
  public Ride(Long id, String startTime, String endTime, Long distance, Person driver, Person rider) {
	  this.id = id;
	  this.startTime = startTime;
	  this.endTime = endTime;
	  this.distance = distance;
	  this.driver = driver;
	  this.rider = rider;
}

public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Long getDistance() {
    return distance;
  }

  public void setDistance(Long distance) {
    this.distance = distance;
  }

  public Person getDriver() {
    return driver;
  }

  public void setDriver(Person driver) {
    this.driver = driver;
  }

  public Person getRider() {
    return rider;
  }

  public void setRider(Person rider) {
    this.rider = rider;
  }
  
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object other) {
	  if (other == null) {
		  return false;
	  }
	  if (!(other instanceof Ride)) {
		  return false;
	  } else {
		  Ride ride = (Ride) other;
		  return this.getId() != null ? this.getId().equals(ride.getId()) : false;
	  }
  }

  @Override
  public String toString() {
    return "Ride [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", distance=" + distance + ", driver=" + driver + ", rider=" + rider + "]";
  }
  
  
  
}
