package parkinglot.strategy.fee;

import parkinglot.entities.ParkingTicket;

public class FlatFeeStrategy implements FeeStrategy{
	private static final double RATE_PER_HOUR = 10.0;

	@Override
	public double calculateFee(ParkingTicket parkingTicket) {
		long duration = parkingTicket.getExitTimeStamp() - parkingTicket.getEntryTimeSpamp();
		long hours = (duration/ (1000 * 60 * 60)) + 1;
		return hours * RATE_PER_HOUR;
	} 
	
}
