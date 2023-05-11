import java.util.InputMismatchException;
import java.util.Scanner;

public class test1 {
	public static void main(String[] args) {
		// variables
		Scanner scnr = new Scanner(System.in);
		int numCenters;
		int numEdges;
		String vName1;
		String vName2;
		int weight;
        
		MST graph = new MST();

		try {
			//System.out.println("Please Enter number for n e: ");
			numCenters = scnr.nextInt();
			numEdges = scnr.nextInt();
			scnr.nextLine();
			
			if (numCenters < 2 || numEdges < 1) {
				//System.out.println("Number of centers must be > 2, and number of edges must be > 1");
				System.exit(-1);
			}
			for (int i = 0; i < numEdges; ++i) {
				//System.out.println("Please Enter Edge information: ");
				vName1 = scnr.next();
				vName2 = scnr.next();
				weight = scnr.nextInt();
				scnr.nextLine();
				graph.addEdge(vName1, vName2, weight);
			}
			//System.out.println("Please Enter source and destination: ");
			vName1 = scnr.next();
			vName2 = scnr.next();
			scnr.nextLine();

			System.out.println(graph.findMinBandwidth(vName1, vName2));
			
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
