package task3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Task3 {
	// will represent ship cabins
	public static Cabin[] shipCabins = new Cabin[12];
	// will read input from console
	public static Scanner sc = new Scanner(System.in);

	// will hold the passengers in waiting
	static Queue<Passenger> waitingList;

	public static void main(String[] args) {
		// initialize the waiting list
		waitingList = new LinkedList<>();
		initializeCabins();
		String choice = "";
		do {
			// print menu
			printMenu();
			// read user choice from console
			choice = sc.nextLine();
			switch (choice) {
			// if user wants to add new customer
			case "A":
				// call add customer method
				addCustomer();
				break;
			case "V":
				displayAllCabins();
				break;
			case "E":
				displayEmptyCabins();
				break;
			case "D":
				deleteCustomer();
				break;
			case "F":
				findCabin();
				break;
			case "S":
				saveData();
				break;
			case "L":
				loadData();
				break;
			case "O":
				sort();
				break;
			case "T":
				printExpenses();
				break;
			default:
				System.out.println("invalid option selected");
			}
		} while (!choice.equals("Q"));

		sc.close();
	}

	public static void printExpenses() {
		// will hold all expenses
		double totalExpenses = 0;
		// iterate over all cabins
		for (int i = 0; i < shipCabins.length; i++) {
			// check if cabin is empty
			if (shipCabins[i].isEmpty()) {
				System.out.println("Cabin#" + (i + 1) + " is Empty.");
			} else {
				// print expenses for all cabins
				System.out.println("Cabin#" + (i + 1) + " Expenses:");
				// iterate over all passengers
				for (Passenger passenger : shipCabins[i].getPassengers()) {
					totalExpenses += passenger.getExpenses();
					System.out.println("Name: " + passenger.getFirstName() + " " + passenger.getSurName()
							+ ", Expenses: " + passenger.getExpenses());
				}
			}
		}
		// print all the expenses
		System.out.println("\nTotal Expenses: " + totalExpenses);
	}

	public static void initializeCabins() {
		// initialize all the cabins in the array
		for (int i = 0; i < shipCabins.length; i++) {
			shipCabins[i] = new Cabin();
		}
	}

	private static void findCabin() {
		// ask for customer name
		System.out.print("Enter customer name: ");
		// read customer name
		String custName = sc.nextLine();
		// find the cabin of that customer
		int cabin = findCabinByName(custName);
		// if the customer isn't in any cabin. display error
		if (cabin == -1) {
			System.out.println("Customer not found at any cabin");
		} else {
			// display the cabin number of customer
			System.out.println("Customer found at cabin#" + (cabin + 1));
		}
	}

	private static void deleteCustomer() {
		// ask cabin from which customer is to be delete
		System.out.print("Enter cabin number: ");
		// read cabin number from console
		int cabin = sc.nextInt();
		sc.nextLine();

		// if cabin number entered is invalid
		if (cabin <= 0 || cabin > 12) {
			// display error
			System.out.println("Enter valid cabin number (1 - 12)");
		} else {
			// check if the cabin has customer. if not display error
			if (shipCabins[cabin - 1].isEmpty()) {
				System.out.println("No customer at cabin #" + cabin);
			} else {
				System.out.print("Enter customer first name: ");
				String fname = sc.nextLine();
				
				// show confirmation message
				if (shipCabins[cabin - 1].removePassenger(fname)) {
					System.out.println("Customer deleted from cabin # " + cabin);
					//if there is any passengers in waiting 
					if(!waitingList.isEmpty()) {
						//remove the passenger in waiting
						Passenger toAdd = waitingList.poll();
						//add to the cabin
						shipCabins[cabin-1].addPassenger(toAdd);
						System.out.println("Passenger " + toAdd +" from waiting list added to the cabin");
					}
				} else {
					System.out.println("Customer not found.!");
				}
			}
		}
	}

	private static void addCustomer() {
		// ask for customer name
		System.out.print("Enter customer first name: ");
		String fname = sc.nextLine();

		System.out.print("Enter customer surname: ");
		String surname = sc.nextLine();

		System.out.print("Enter customer expenses: ");
		double expenses = sc.nextDouble();

		// ask for customer cabin number
		System.out.print("Enter cabin number: ");
		int cabin = sc.nextInt();
		sc.nextLine();

		// check if the given cabin is available or empty
		boolean isCabinAvailable = cabinhasSpace(cabin - 1);
		if (!isCabinAvailable) {
			//if ship is full 
			if (isShipFull()) {
				//add the passengers to the waiting list
				waitingList.add(new Passenger(fname, surname, expenses));
				System.out.println("No space right now.! Passenger added to the queue..!");
			}
			else {
				System.out.println("Cabin not available!");
			}
		} else {
			// add passenger to the cabin
			shipCabins[cabin - 1].addPassenger(new Passenger(fname, surname, expenses));
			System.out.println("Customer added at cabin #" + cabin);

		}
	}

	public static boolean isShipFull() {
		// check if any cabin has space
		for (Cabin cabin : shipCabins) {
			// if cabin has space then return false;
			if (cabin.hasSpace()) {
				return false;
			}
		}
		// ship is full return true;
		return true;
	}

	public static boolean cabinhasSpace(int cabin) {
		// if cabin number is not valid, return false
		if (cabin < 0 || cabin >= 12) {
			return false;
		}
		// return true if cabin is null, otherwise return false
		return shipCabins[cabin].hasSpace();
	}

	public static void sort() {
		// iterate over cabins
		for (int i = 0; i < shipCabins.length; i++) {
			// sort all passengers in ship cabins
			Collections.sort(shipCabins[i].getPassengers());
		}
	}

	public static void loadData() {
		try {
			FileInputStream fi = new FileInputStream(new File("data.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			shipCabins = (Cabin[]) oi.readObject();
			oi.close();
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveData() {
		try {
			FileOutputStream f = new FileOutputStream(new File("data.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(shipCabins);
			o.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int findCabinByName(String custName) {
		for (int i = 0; i < shipCabins.length; i++) {
			// if this cabin contains given customer name, return the index
			for (Passenger passenger : shipCabins[i].getPassengers()) {
				if (passenger.getFirstName().equals(custName)) {
					return i;
				}
			}
		}
		// customer name not found in any cabin, so return -1
		return -1;
	}

	public static void displayAllCabins() {
		// iterate over all cabins
		for (int i = 0; i < shipCabins.length; i++) {
			// if cabins is empty, display empty
			if (shipCabins[i].isEmpty()) {
				System.out.println("Cabin#" + (i + 1) + " is Empty.");
			} else {
				// if cabin is not empty display passengers in that cabin
				System.out.println("Customers at cabin#" + (i + 1));
				System.out.println(shipCabins[i].getPassengers());
			}
		}
	}

	public static void displayEmptyCabins() {
		// iterate over all cabins
		for (int i = 0; i < shipCabins.length; i++) {
			// only show if cabin is null
			if (shipCabins[i].isEmpty()) {
				System.out.println("Cabin# " + (i + 1) + " is Empty.");
			}
		}
	}

	public static void printMenu() {
		// create the output string for menu
		String menu = "";
		menu += "A:\tAdd customer\n";
		menu += "V:\tView all cabins\n";
		menu += "E:\tDisplay Empty Cabins\n";
		menu += "D:\tDelete Customer from cabin\n";
		menu += "F:\tFind cabin from customer name\n";
		menu += "S:\tStore data into file\n";
		menu += "L:\tLoad data from file\n";
		menu += "O:\tView Passengers Ordered Alphabetically by name";
		menu += "T:\tExpenses per passenger";
		menu += "Q:\tQuit program";
		// display the output string to the console
		System.out.println(menu);

	}

}
