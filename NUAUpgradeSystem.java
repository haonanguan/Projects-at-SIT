//NAME: Haonan Guan, for M4.B4: Module 4 Priority Queues and Heaps Application Programming Assignment

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class NUAUpgradeSystem {
	
	//inner class to save flyer's information
	private static class Flyer {
		public int ID;
		public int priority;
		public String name;
		public int time;
		
		//Constructor to create a new node by giving data and priority
		public Flyer(String name, int id, int time, String priorityStatus) {
			if (name == null) {
				System.out.println("The name is null, please try again.");
			}
			else {
				this.ID = id;
				this.name = name;
				this.time = time;
				
				switch (priorityStatus) {
					case "silver":
						priority = 1;
						break;
					
					case "gold":
						priority = 2;
						break;
						
					case "platinum":
						priority = 3;
						break;
					
					case "super":
						priority = 4;
						break;
				}

			}
		}
		
		//method to return flyer's information
		@Override
		public String toString() {
			String priorityStatus = "";
			switch (this.priority) {
			case 1:
				priorityStatus = "Silver";
				break;
			case 2:
				priorityStatus = "Gold";
				break;
			case 3:
				priorityStatus = "Platinum";
				break;
			case 4:
				priorityStatus = "Super";
				break;
			}
			
			String print = "(" + this.name + ", " + this.ID + ", ";
			print += this.time + ", " + priorityStatus +")\n";
			
			return print;
		}
	}
	
	private ArrayList <Flyer> flyerList;
	public ArrayList <Flyer> record;
	public ArrayList <Flyer> assigned;
	
	public NUAUpgradeSystem() {
		flyerList = new ArrayList<>();
		record = new ArrayList<>();
		assigned = new ArrayList<>();
	}
	
	//Methods to add a flyer based on given name and priority
	public boolean add(String name, int id, int time, String priorityStatus) {
		Flyer newNode = new Flyer(name, id, time, priorityStatus);
		if (flyerList.size() == 0) {
			flyerList.add(newNode);
			record.add(newNode);
			
			return true;
		}
		else {
			//from the root to the bottom, find the correct leaf
			//position for newNode based on key value 
			flyerList.add(newNode);
			record.add(newNode);
				
			int i = flyerList.size();
			while(i > 1 && flyerList.get(i - 1).priority >= flyerList.get(i/2 - 1).priority) {
				if (flyerList.get(i - 1).priority == flyerList.get(i/2 - 1).priority) {
					if (flyerList.get(i - 1).time > flyerList.get(i/2 - 1).time){
						Flyer tempNode = flyerList.get(i/2 - 1);
						flyerList.set(i/2 - 1, flyerList.get(i - 1));
						flyerList.set(i - 1, tempNode);
						i = i / 2;
					}//end inner if
					
					return false;
				}//end outer if
				else {
					Flyer tempNode = flyerList.get(i/2 - 1);
					flyerList.set(i/2 - 1, flyerList.get(i - 1));
					flyerList.set(i - 1, tempNode);
					i = i / 2;
				}//end inner else
			}//end while
			
			return true;
		}//end outer else
	}
		
	public void printTotal() {
		
		if (flyerList.size() == 0) {
			System.out.println("No upgrade request on the list.");
		}
		else {
			for (int i = 0; i < flyerList.size(); i++) {
				System.out.println(flyerList.get(i).toString());
			}
		}
	}
		
	public static void printMenu() {

		System.out.println("MENU\na - Add new request\nc - Cancel an existing request\no - Output the existing list\n"
				+ "r - Ready to departure\nq - Quit\n");
	}
	
	public static void execute(char userChoice, NUAUpgradeSystem system, Scanner scnr, int times) {
		if (userChoice == 'r') {
			readyToDeparture(system, times);
			return;
		}
		for (int i = 0; i < times; ++i) {
			executeMenu(userChoice, system, scnr);
		}
		return;
	}
	
	//method to excecute the action based on userChoice
	public static void executeMenu(char userChoice, NUAUpgradeSystem system, Scanner scnr) {
		boolean inputCorrect = false;
		int numUpgrade = 0;
		int cancelId = 0;
		
		if (userChoice == 'c') {
				boolean idCheck = false;
				while(!idCheck) {
					//System.out.println("Please enter the id to cancel: ");
					try {
						cancelId = scnr.nextInt();
						scnr.nextLine();
						idCheck = cancelIntCheck(cancelId, system);
					}
					catch (NumberFormatException e) {
						System.out.println("Invalid id!");
					}
					catch (StringIndexOutOfBoundsException e) {
						System.out.println("Invalid input.");
					}
				}
				cancelRequest(cancelId, system).toString();

		}
		else if (userChoice == 'a') {
			inputCorrect = false;
			String flyerName = "";
			String status = "";
			String[] priorityList = {"silver", "gold", "platinum", "super"};
			int time = 0;
			int id = 0;
			try {
				//System.out.println("Please enter the flyers information: \nFlyer name:");
				flyerName = scnr.next();
				
				//System.out.println("Flyer id: ");
				id = scnr.nextInt();
				
				//System.out.println("time: ");
				time = scnr.nextInt();
				if (time < 0) {
					System.out.println("Invalid time input, positive number only.");
					System.exit(-1);
				}
				
				//System.out.println("status: ");
				status = scnr.next();
				status = status.toLowerCase();
				for (int i = 0; i < priorityList.length; ++i) {
					if (status.compareTo(priorityList[i]) == 0) {
						inputCorrect = true;
					}
				}
				
				if (!inputCorrect) {
					System.out.println("Invalid status input.");
					System.exit(-1);
				}
			}
			catch(StringIndexOutOfBoundsException e) {
				System.out.println("Invalid information input.");
				System.exit(-1);
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid information input.");
				System.exit(-1);
			}
			catch(NumberFormatException e) {
				System.out.println("Invalid information input.");
				System.exit(-1);
			}
			
			scnr.nextLine();
			system.add(flyerName, id, time, status);
			
			return;
		}
		else if (userChoice == 'r') {
			inputCorrect = false;
			while(!inputCorrect) {
				//System.out.println("Please enter the number of available seats: ");
				try {
					numUpgrade = Integer.parseInt(scnr.nextLine());
					inputCorrect = readyToDeparture(system, numUpgrade);
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input, integer only please!");
				}
			}
			
			return;
		} 
	}//end executeMenu()
	
	//method to remove the a node at certain position and reheap
	public Flyer remove(int index) {
		
		Flyer remove = flyerList.get(index);
		
		if (flyerList.size() == 1) {
			flyerList.remove(0);
			
			return remove;
		}
		else {
			flyerList.set(index,flyerList.get(flyerList.size() - 1));
			flyerList.remove(flyerList.size() - 1);
			
			int i = index + 1;
			while(i < flyerList.size()) { 
				Flyer current = flyerList.get(i - 1);
				Flyer tempNode = current;
				if ((i * 2 + 1) <= flyerList.size()) {
					Flyer left = flyerList.get(2*i - 1);
					Flyer right = flyerList.get(2*i);
					
					if (current.priority == left.priority) {
						if (current.priority == right.priority) {
							if (current.time < left.time) {
								if (current.time < right.time) {
									if (left.time < right.time) {
										flyerList.set(i - 1, right);
										flyerList.set(2 * i, tempNode);
										i = 2 * i + 1;
									}//end if (currentP=left: currentP = right: current time < left: current time < right: left time < right)
									else {
										flyerList.set(i - 1, left);
										flyerList.set(2 * i - 1, tempNode);
										i = 2 * i;
									}//end if (currentP=left: currentP = right: current time < left: current time < right: left time > right)
								}//end if (currentP=left: currentP = right: current time < left : current time < right)
								else {
									flyerList.set(i - 1, left);
									flyerList.set(2 * i - 1, tempNode);
									i = 2 * i;
								}//end else (currentP=left: currentP = right: current time < left: current time > right)
							}//end if (currentP=left: currentP = right: current time < left)
							else if (current.time < right.time) {
								flyerList.set(i - 1, right);
								flyerList.set(2 * i, tempNode);
								i = 2 * i + 1;
							}//end else if (currentP=left: currentP = right: current time > left: current time < right)
							else {
								
								return remove;
							}//end else (currentP=left: currentP = right: current time > left: current time > right)
						}//end if (currentP=left: currentP = right)
						else if (current.priority < right.priority) {
							flyerList.set(i - 1, right);
							flyerList.set(2 * i, tempNode);
							i = 2 * i + 1;
						}// end else if (current P = left: currentP < right)
						else {
							if (current.time < left.time) {
								flyerList.set(i - 1, left);
								flyerList.set(2 * i - 1, tempNode);
								i = 2 * i;
							}// end if (current P = left: currentP > right: current time < left)
							else {
								
								return remove;
							}// end else (current P = left: currentP > right: current time > left)
						}// end else (current P = left: currentP > right)
					}//end if (current P = left)
					else if (current.priority < left.priority) {
						if (current.priority < right.priority) {
							if (left.priority == right.priority) {
								if (left.time < right.time) {
									flyerList.set(i - 1, right);
									flyerList.set(2 * i, tempNode);
									i = 2 * i + 1;
								}//end if (current P < left: current P < right: left P = right: left time < right)
								else {
									flyerList.set(i - 1, left);
									flyerList.set(2 * i - 1, tempNode);
									i = 2 * i;
								}//end else (current P < left: current P < right: left P = right: left time > right)
							}//end if (current P < left: current P < right: left P = right)
							else if (left.priority < right.priority) {
								flyerList.set(i - 1, right);
								flyerList.set(2 * i, tempNode);
								i = 2 * i + 1;
							}//end if (current P < left: current P < right: left P < right)
							else {
								flyerList.set(i - 1, left);
								flyerList.set(2 * i - 1, tempNode);
								i = 2 * i;
							}//end else (current P < left: current P < right: left P > right)
						}//end else if (current P < left: current P < right)
						else {
							flyerList.set(i - 1, left);
							flyerList.set(2 * i - 1, tempNode);
							i = 2 * i;
						}//end else (current P < left: current >= right)
					}//end else if (current P < left)
					else {
						if (current.priority == right.priority) {
							if (current.time < right.time) {
								flyerList.set(i - 1, right);
								flyerList.set(2 * i, tempNode);
								i = 2 * i + 1;
							}//end if (current P > left: current P = right: current time < right)
							else {
								
								return remove;
							}//end else //end if (current P > left: current P = right: current time > right)
						}//end if (current P > left: current P = right)
						else if (current.priority < right.priority) {
							flyerList.set(i - 1, right);
							flyerList.set(2 * i, tempNode);
							i = 2 * i + 1;
						}//end else if (current P > left: current P < right)
						else {
							
							return remove;
						}//end else if (current P > left: current P > right)
					}//end else (current P > left)
				}// end if current node has two children
				else {
					if ((2 * i) == flyerList.size()) {
						Flyer left = flyerList.get(2*i - 1);
						if (current.priority == left.priority) {
							if (current.time < left.time) {
								flyerList.set(i - 1, left);
								flyerList.set(2 * i - 1, tempNode);
								i = 2 * i;
							}//end if (current P = left: current time < left)
							else {
								
								return remove;
							}//end else (current P = left: current time > left)
						}//end if (current P = left)
						else if (current.priority < left.priority) {
							flyerList.set(i - 1, left);
							flyerList.set(2 * i - 1, tempNode);
							i = 2 * i;
						}//end if (current P < left)
						else {
							
							return remove;
						}
					}//end if current node only has left children
					
					return remove;
				}//end else (current node has zero or only left children)
			}//end while
			
			return remove;
		}//end else (size >1)
	}//end removeTop()
	
	//method to cancel an existing request
	public static Flyer cancelRequest(int id, NUAUpgradeSystem system) {
		int cancelIndex = 0;
		
		for (cancelIndex = 0; cancelIndex < system.flyerList.size(); ++cancelIndex) {
			if (system.flyerList.get(cancelIndex).ID == id) {
				break;
			}
		}
		Flyer cancelFlyer = system.flyerList.get(cancelIndex);
		system.remove(cancelIndex);
		
		return cancelFlyer;
	}//end of cancelRequest()
	
	//method to return num of flyer's information to assign seats
	public static boolean readyToDeparture(NUAUpgradeSystem system, int num) {
		int i = 0;
		if (num == 0) {
			System.out.println("No seat assigned, back to menu now.");
			return true;
		}
		else if (system.flyerList.size() == 0) {
			System.out.println("The upgrade waiting list is empty.");
			return false;
		}
		else if (num > system.flyerList.size()) {
			//System.out.printf("Available seats are more than requests, here are %d flyers to be assigned to seats: \n", system.flyerList.size());
			int originalSize = system.flyerList.size();
			for (i = 0; i < originalSize; ++i) {
				system.assigned.add(system.flyerList.get(0));
				System.out.println(system.remove(0).toString());
			}
			return true;
		}
		else {
			//System.out.printf("Here are %d flyers to be assigned to seats: \n", num);
			for (i = 0; i < num; ++i) {
				system.assigned.add(system.flyerList.get(0));
				System.out.println(system.remove(0).toString());
			}
			return true;
		}
	}// end readyToDeparture()
	
	//method to check if the ID for the cancel is valid
	public static boolean cancelIntCheck(int userInputInt, NUAUpgradeSystem system) {
		for (int j = 0; j < system.record.size(); ++j) {
			if (userInputInt == system.record.get(j).ID) {
				for (int i = 0; i < system.flyerList.size(); ++i) {
					if (system.flyerList.get(i).ID == system.record.get(j).ID) {
						return true;
					}
				}
				System.out.printf("The request(id:%d) already got canceled, try another id: \n", userInputInt);
				return false;
			}
		}
		System.out.printf("The request(id: %d) doesn't exist, try another id: \n", userInputInt);
		return false;
	}//end cancelIntCheck
}