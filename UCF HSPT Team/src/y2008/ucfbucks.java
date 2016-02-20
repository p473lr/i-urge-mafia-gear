// Necessary Imports
import java.util.*;
import java.io.*;

public class ucfbucks {
	
	public static void main (String[] args) throws IOException {
		
		// Make a Scanner object that will read in from ucfbucks.in
		Scanner in = new Scanner (new File("ucfbucks.in"));
		
		// Read in the Number of Purchases.
		int numberOfPurchases = in.nextInt();
		
		// Loop as many times as there are purchases.
		for (int index = 1; index <= numberOfPurchases; index++){
			
			// Read in the amount of ucf dollars you have
			int cashOnHand = in.nextInt();
			
			// Read in the cost of the item
			double cost = in.nextDouble();
			
			// Print out the difference of the ceiling of the cost and the cost casted as an integer
			System.out.println((int)((Math.ceil(cost)-cost)*100));
			
		}//for
		
	} // main
	
} // ucfbucks