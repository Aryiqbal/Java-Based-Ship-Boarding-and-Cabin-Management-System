package task3;

import java.io.Serializable;

public class Passenger implements Comparable<Passenger>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//represent first name of passenger
	private String firstName;
	//represent last name of passenger
	private String surName;
	//represent expenses of passenger
	private double expenses;
	
	//constructor
	public Passenger(String firstName, String surName, double expenses) {
		//initializes the instance variables
		this.firstName = firstName;
		this.surName = surName;
		this.expenses = expenses;
	}
	
	//getter of first name
	public String getFirstName() {
		return firstName;
	}
	
	//getter of surname
	public String getSurName() {
		return surName;
	}
	
	//getter of expenses
	public double getExpenses() {
		return expenses;
	}

	//string representation of class
	@Override
	public String toString() {
		return "firstName=" + firstName + ", surName=" + surName + ", expenses=" + expenses;
	}

	//sorts the passengers by first name first
	//sorts the passengers by surname then
	//sorts the passengers by expenses then
	@Override
	public int compareTo(Passenger o) {
		if(this.firstName.compareTo(o.firstName) != 0){
			return this.firstName.compareTo(o.firstName);
		}
		else {
			if(this.surName.compareTo(o.surName) != 0) {
				return this.surName.compareTo(o.surName);
			}
			else {
				if(this.expenses > o.expenses) {
					return 1;
				}
				else if(this.expenses < o.expenses) {
					return -1;
				}
				else {
					return 0;
				}
			}
		}
	}

}
