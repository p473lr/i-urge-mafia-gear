// Arup Guha
// 4/29/09
// Solution to 2009 UCF High School Programming Contest Problem: gnome

// Note: Don't run this with the contest data on JCreator. I had a problem with the 
//       output buffer filling up and crashing JCreator. When I just piped the 
//       output to a file from the command line, I was fine.

import java.io.*;
import java.util.*;

public class gnome {
	
	public static void main(String[] args) throws Exception {
		
		// Open the input file.
		Scanner fin = new Scanner(new File("gnome.in"));
		
		// Read in the number of pots for the first case.
		int numPots = fin.nextInt();
		
		// Keep track of which case it is.
		int caseNum = 1;
		
		// Process each case until we get 0 pots.
		while (numPots != 0) {
			
			// Read in all the pot sizes for this case.
			int[] numbers = new int[numPots];
			for (int i=0; i<numPots; i++)
				numbers[i] = fin.nextInt();
		
			// Print out the header for this case.
			System.out.println("Garden "+caseNum+":");
					
			// Do the sort!
			gnomeSort(numbers);
			
			// Blank line between cases.
			System.out.println();
			
			// Get the number of pots for the next case.
			numPots = fin.nextInt();
			caseNum++;
		}
		
		// Close the input file.
		fin.close();
	}
	
	// Runs gnome sort and prints out the associated output for the problem.
	public static void gnomeSort(int[] array) {
		
		int marker = 0;
		
		// Algorithm ends when we get past the right-end of the array.
		while (marker < array.length) {
			
			// Rule 1: If you are at the left end, move right.
			if (marker == 0)
				marker++;
				
			// Rule 2: If this pot isn't smaller than the one to the left, move right.
			else if (array[marker] >= array[marker-1])
				marker++;
				
			// Rule 3: This pot is smaller than the one to the left. Swap and move left.
			else {
				
				// Swap these two pots.
				int temp = array[marker];
				array[marker] = array[marker-1];
				array[marker-1] = temp;
				
				// Output information about the swap.
				System.out.println("The gnome swaps the pots at positions "+(marker)+" and "+(marker+1)+".");
				
				// Gotta move left now.
				marker--;
			}
		}
		System.out.println("Sorted!");
	}
}