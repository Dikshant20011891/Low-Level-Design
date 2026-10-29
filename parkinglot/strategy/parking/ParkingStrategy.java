package parkinglot.strategy.parking;

import java.util.List;
import java.util.Optional;

import parkinglot.entities.ParkingFloor;
import parkinglot.entities.ParkingSpot;
import parkinglot.vehicle.Vehicle;

public interface ParkingStrategy {
	Optional<ParkingSpot> findSpot(List<ParkingFloor> floors,Vehicle vehicle);
}
