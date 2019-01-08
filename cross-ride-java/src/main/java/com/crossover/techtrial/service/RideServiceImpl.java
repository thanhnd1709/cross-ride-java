/**
 * 
 */
package com.crossover.techtrial.service;

import java.math.BigDecimal;
import java.math.BigInteger;
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
	
	private static final int POSITION_ZERO = 0;
	private static final int POSITION_ONE = 1;
	private static final int POSITION_TWO = 2;
	private static final int POSITION_THREE = 3;
	private static final int POSITION_FOUR = 4;

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
		List<Object[]> objectList= rideRepository.getTopRides(count,startTime, endTime);
		for (Object[] currentObject : objectList) {
			String name = (String) currentObject[POSITION_ZERO];
			String email = (String) currentObject[POSITION_ONE];
			BigDecimal totalRideDurationInSeconds = (BigDecimal) currentObject[POSITION_TWO];
			BigInteger maxRideDurationInSecods = (BigInteger) currentObject[POSITION_THREE];
			BigDecimal averageDistance = (BigDecimal) currentObject[POSITION_FOUR];
			TopDriverDTO tempTopDriverDTO = new TopDriverDTO( name, email, totalRideDurationInSeconds.longValue(), maxRideDurationInSecods.longValue(), averageDistance.doubleValue());
			rideList.add(tempTopDriverDTO);
		}
		return rideList;
	}

}
