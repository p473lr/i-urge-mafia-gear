/* Problem: Egg Drop
 * Problem Solver: Michael Do
 * Date: April 10, 2010
 */
 
import java.io.*;
import java.util.*;

public class eggdrop{

	public static void main(String[] args) throws Exception{
	
		Scanner In = new Scanner(new File("eggdrop.in"));
	
		// Read in the input	
		int n = In.nextInt();
		
		// Loop through each input
		for(int k = 1;k <= n;k++){
		
			int v = In.nextInt();       // Read in the velocity
			double t = v/9.8;	    // Solve for the time using velocity equation
			int d = (int) (t*t*4.9);    // Solve for distance using time, converting to integer to round down
			
			// Print output
			System.out.println("Egg Drop #"+k+": The maximum height is "+d+" meter(s).");
			
		}
	
	}

}
