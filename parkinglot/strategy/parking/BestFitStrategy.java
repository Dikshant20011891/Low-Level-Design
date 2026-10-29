package parkinglot.strategy.parking;

import java.util.List;
import java.util.Optional;

import parkinglot.entities.ParkingFloor;
import parkinglot.entities.ParkingSpot;
import parkinglot.vehicle.Vehicle;

public class BestFitStrategy implements ParkingStrategy{

	@Override
	public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
		Optional<ParkingSpot> bestSpot = Optional.empty();
		
		for(ParkingFloor floor : floors) {
			Optional<ParkingSpot> spotOnThisFloor = floor.findAvailableSpot(vehicle);
			
			if(spotOnThisFloor.isPresent()) {
				if(bestSpot.isEmpty())
					bestSpot = spotOnThisFloor;
				else {
					if(spotOnThisFloor.get().getSpotSize().ordinal() < bestSpot.get().getSpotSize().ordinal())
						bestSpot = spotOnThisFloor;
				}
			}
		}
		
		return bestSpot;
	}

}
