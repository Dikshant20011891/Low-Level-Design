package parkinglot.entities;

import java.util.Date;
import java.util.UUID;

import parkinglot.vehicle.Vehicle;

public class ParkingTicket {
	private final String ticketId;
	private final Vehicle vehicle;
	private final ParkingSpot spot;
	private final long entryTimeSpamp;
	private long exitTimeStamp;
	
	public ParkingTicket(Vehicle vehicle,ParkingSpot spot) {
		this.ticketId = UUID.randomUUID().toString();
		this.vehicle = vehicle;
		this.spot = spot;
		this.entryTimeSpamp = new Date().getTime();	
	}

	public long getExitTimeStamp() {
		return exitTimeStamp;
	}

	public void setExitTimeStamp() {
		this.exitTimeStamp = new Date().getTime();
	}

	public String getTicketId() {
		return ticketId;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public ParkingSpot getSpot() {
		return spot;
	}

	public long getEntryTimeSpamp() {
		return entryTimeSpamp;
	}
	
}
