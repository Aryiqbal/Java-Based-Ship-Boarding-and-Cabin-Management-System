package task3;

import java.io.Serializable;
import java.util.ArrayList;

public class Cabin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//this will store passengers in the current cabin
	private ArrayList<Passenger> passengers;
	
	public Cabin() {
		//initialize the list
		passengers = new ArrayList<>();
	}
	
	//return true if there is no passengers in current cabin
	//return false otherwise
	public boolean isEmpty() {
		return passengers.isEmpty();
	}
	//return true if there are less than 3 passengers in the cabin
	//return false otherwise
	public boolean hasSpace() {
		return passengers.size() < 3;
	}
	
	//adds the passengers to the passengers list in the current cabin
	public void addPassenger(Passenger passenger) {
		passengers.add(passenger);
	}
	//remove the passenger from the list by first name
	public boolean removePassenger(String firstName) {
		//find the passenger to delete 
		Passenger toDelete = null;
		//iterate over all the passengers
		for(Passenger passenger : passengers) {
			//if the given name equals to the current passengers name
			if(passenger.getFirstName().equals(firstName)) {
				toDelete = passenger;
				break;
			}
		}
		
		if(toDelete != null) {
			//if passenger found by the given first name
			return passengers.remove(toDelete);
		}
		//if there is not passenger found by first name
		return false;
	}
	
	//return all the passengers
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}
}
