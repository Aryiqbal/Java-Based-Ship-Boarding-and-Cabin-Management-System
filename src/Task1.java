import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Task1 {
	// will represent ship cabins
	public static String[] shipCabins = new String[12];
	// will read input from console
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
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
			case "Q":
				break;
			default:
				System.out.println("invalid option selected");
			}
		} while (!choice.equals("Q"));

		sc.close();
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
			if (shipCabins[cabin - 1] == null) {
				System.out.println("No customer at cabin #" + cabin);
			} else {
				// remove the customer at that cabin
				shipCabins[cabin - 1] = null;
				// show confirmation message
				System.out.println("Customer deleted from cabin # " + cabin);
			}
		}
	}

	private static void addCustomer() {
		//ask for customer name
		System.out.print("Enter customer name: ");
		String cust = sc.nextLine();
		//ask for customer cabin number
		System.out.print("Enter cabin number: ");
		int cabin = sc.nextInt();
		sc.nextLine();
		//check if the given cabin is available or empty
		boolean isCabinAvailable = isCabinEmpty(cabin - 1);
		if (!isCabinAvailable) {
			System.out.println("Cabin not available!");
		} else {
			shipCabins[cabin - 1] = cust;
			System.out.println("Customer added at cabin #" + cabin);
		}
	}

	public static boolean isCabinEmpty(int cabin) {
		//if cabin number is not valid, return false
		if (cabin < 0 || cabin >= 12) {
			return false;
		}
		//return true if cabin is null, otherwise return false
		return shipCabins[cabin] == null;
	}

	public static void sort() {
		//iterate over cabins
		for (int i = 0; i < shipCabins.length; i++) {
			//iterate over inner loop
			for (int j = 0; j < shipCabins.length; j++) {
				
				if (shipCabins[i] != null) {
					if (shipCabins[j] == null || shipCabins[i].compareTo(shipCabins[j]) < 0) {
						//replace the two indexes to sort the data
						String temp = shipCabins[i];
						shipCabins[i] = shipCabins[j];
						shipCabins[j] = temp;
					}
				}
			}
		}
	}

	public static void loadData() {
		try {
			// open file to read data
			Scanner fileReader = new Scanner(new File("data.txt"));
			// will read the index
			int index = 0;
			// iterate over file until end is reached
			while (fileReader.hasNextLine()) {
				// read the line
				String line = fileReader.nextLine();
				// if it is null, the cabin is null
				if (line.equals("null")) {
					shipCabins[index++] = null;
				} else {
					//read the data to the cabin
					shipCabins[index++] = line;
				}
			}
			//close the file
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveData() {
		try {
			//read the file to write
			FileWriter fileWriter = new FileWriter(new File("data.txt"));
			//will hold all the data to be written to file
			String data = "";
			//iterate over all the cabins and note the customer names
			for (int i = 0; i < shipCabins.length; i++) {
				data += shipCabins[i] + "\n";
			}
			//write the data to the file
			fileWriter.write(data.trim());
			//close the file
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int findCabinByName(String custName) {
		for (int i = 0; i < shipCabins.length; i++) {
			// if this cabin contains given customer name, return the index
			if (shipCabins[i] != null && shipCabins[i].equals(custName)) {
				return i;
			}
		}
		// customer name not found in any cabin, so return -1
		return -1;
	}

	public static void displayAllCabins() {
		// iterate over all cabins
		for (int i = 0; i < shipCabins.length; i++) {
			// if cabins is null, display empty
			if (shipCabins[i] == null) {
				System.out.println("Cabin#" + (i + 1) + " is Empty.");
			} else {
				// if cabin is not empty display customer name in that cabin
				System.out.println("Cabin#" + (i + 1) + " : " + shipCabins[i]);
			}
		}
	}

	public static void displayEmptyCabins() {
		// iterate over all cabins
		for (int i = 0; i < shipCabins.length; i++) {
			// only show if cabin is null
			if (shipCabins[i] == null) {
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
		menu += "O:\tView Passengers Ordered Alphabetically by name\n";
		menu += "Q:\tQuit program";
		// display the output string to the console
		System.out.println(menu);

	}

}
