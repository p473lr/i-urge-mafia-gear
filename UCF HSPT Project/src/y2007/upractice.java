/* Michael Do
 * Problem: upractice
 * Takes in a string and outputs it with a 'u' in front of it.
 */

import java.io.*;
import java.util.*;

public class upractice{

	public static void main(String[] args) throws Exception{
	
		Scanner In = new Scanner(new File("upractice.in"));
		int numCases = In.nextInt(); // Takes in the number of cases
		
		// Runs through the cases 
		for(int currCase = 1;currCase <= numCases;currCase++)
// for each case, prints u, reads in the String, then prints it following the u
			System.out.println("u"+In.next());
			
	}

}