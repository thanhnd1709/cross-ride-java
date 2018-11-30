/**
 * 
 */
package com.crossover.techtrial.service;

import java.util.List;
import com.crossover.techtrial.model.Ride;

/**
 * RideService for rides.
 * @author crossover
 *
 */
public interface RideService {
  
  public Ride save(Ride ride);
  
  public Ride findById(Long rideId);
  
  public List<Ride> getAll();
}
