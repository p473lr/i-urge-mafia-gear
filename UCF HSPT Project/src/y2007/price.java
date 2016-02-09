// Arup Guha
// 4/30/07
// Solution to 2007 UCF High School Programming Contest Practice Problem
// Price List.

import java.io.*;
import java.util.*;
import java.text.*;

public class price {
	
	public static void main(String[] args) throws IOException {
		
		// Open the input file.
		Scanner fin = new Scanner(new File("price.in"));
		
		// Get the number of cases.
		int numcases = fin.nextInt();
		
		// Process each case.
		for (int i=1; i<=numcases; i++) {
		
			// Get the number of cards for this case.
			int numcards = fin.nextInt();
			
			// Set up initial values.
			int maxcard = 1;
			double maxvalue = 0;
			
			// Go through each card.
			for (int cardnum=1; cardnum<=numcards; cardnum++) {
				
				double cardval = fin.nextDouble();
				
				// If this card is the best, reset our values.
				if (cardval > maxvalue) {
					maxcard = cardnum;
					maxvalue = cardval;
				}
			}
			
			// Output the answer for this case.
			DecimalFormat twoD = new DecimalFormat("0.00");
			System.out.println("Collection "+i+": "+maxcard+" "+twoD.format(maxvalue));
			//System.out.printf("Case %d:   %d %.2f\n",i,maxcard,maxvalue);
		}	
		
		fin.close();
	}
}