// Necessary Imports
import java.util.*;
import java.io.*;

public class election {
	
	public static void main (String[] args) throws IOException {
		
		// Make a Scanner object that will read in from election.in
		Scanner in = new Scanner (new File("election.in"));
		
		// Read in the Number of Names.
		int numberOfNames = in.nextInt();
		
		// Loop as many times as there are names.
		for (int index = 1; index <= numberOfNames; index++){
			
			// Read in the winner's name
			String winner = in.next();
			
			// Print out the winner's message
			System.out.println("Election Result " + index + ": " + winner + " wins!");
			
		}//for
		
	} // main
	
} // election