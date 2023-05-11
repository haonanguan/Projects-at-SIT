//NAME: Haonan Guan, for M4.B4: Module 4 Priority Queues and Heaps Application Programming Assignment
import java.util.InputMismatchException;
import java.util.Scanner;

public class test {
	public static void main(String[] args) {
		// variables

		Scanner scnr = new Scanner(System.in);
		int n;
		int k;
		int c;
		
		NUAUpgradeSystem upgradeSystem = new NUAUpgradeSystem();
		
		try {
			System.out.println("Enter the number for n k c separated by space: ");
			n = scnr.nextInt();
			k = scnr.nextInt();
			c = scnr.nextInt();
			scnr.nextLine();
			
			if (n < 0 || k<0 || c < 0) {
				System.out.println("All the numbers can't be negative, try again!");
				System.exit(-1);
			}
			NUAUpgradeSystem.execute('a', upgradeSystem, scnr, n);
			NUAUpgradeSystem.execute('c', upgradeSystem, scnr, c);
			NUAUpgradeSystem.execute('r', upgradeSystem, scnr, k);
			
		}
		catch(NumberFormatException e) {
			System.out.println("Invalid input, try the program again!");
			System.exit(-1);
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input, try the program again!");
			System.exit(-1);
		}
		scnr.close();
	}//end main()
}
