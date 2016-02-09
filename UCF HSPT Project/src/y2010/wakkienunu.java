import java.util.*;
import java.io.*;

public class wakkienunu {

	public wakkienunu(Scanner in)
	{
		//First we read in the number of appliances to be checked
		int n = in.nextInt();
		
		// A standard for loop from 0 to n-1 (a total of n times)
		for(int i = 0; i < n; i++)
		{
			//Read in the name of the appliance
			String app = in.next();
			
			//Get the cost of the appliance
			int cost = in.nextInt();
			
			//Get m, the number of checks to make
			int m = in.nextInt();
			
			//Print out the header required by the problem
			System.out.println("Appliance " + app + ":");
			
			//Now loop over the m checks
			for(int j = 0; j < m; j++)
			{
				//Get the retail vale from input
				int retail = in.nextInt();
				
				//If we paid more than the base cost
				if(cost < retail)
					System.out.println("You paid too much!"); 
				else if(cost > retail) //else the retail was less than the base cost
					System.out.println("I love appliances!");
				else //If it's not greater, and not less, it's equal!
					System.out.println("Wakkie Nu Nu!");
				//I have no idea what Wakkie Nu Nu means
			}
			//Print out what extra blank line.
			System.out.println();
		}
	}
	
	//I prefer to create an instance of my sourcecode
	// and do all my logic in the constructor.
	public static void main(String[] args) throws Exception
	{
		new wakkienunu(new Scanner(new File("wakkienunu.in")));
	}
}
