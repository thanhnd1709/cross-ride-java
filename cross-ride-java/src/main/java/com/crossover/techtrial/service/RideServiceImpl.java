/**
 * 
 */
package com.crossover.techtrial.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;

/**
 * @author crossover
 *
 */
@Service
public class RideServiceImpl implements RideService {

	@Autowired
	RideRepository rideRepository;

	public Ride save(Ride ride) {
		return rideRepository.save(ride);
	}

	public Ride findById(Long rideId) {
		Optional<Ride> optionalRide = rideRepository.findById(rideId);
		if (optionalRide.isPresent()) {
			return optionalRide.get();
		} else {
			return null;
		}
	}

	@Override
	public List<TopDriverDTO> getTopRides(Long count, LocalDateTime startTime, LocalDateTime endTime) {
		List<TopDriverDTO> rideList = new ArrayList<>();
		//rideRepository.getTopRides(count,startTime, endTime).forEach(rideList::add);
		List<Object[]> newList= rideRepository.getTopRides(count,startTime, endTime);
		return rideList;
	}

}
