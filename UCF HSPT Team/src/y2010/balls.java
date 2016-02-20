/* Problem: balls
 * Problem Solver: Michael Do
 * Date: April 10, 2010
 */

import java.io.*;
import java.util.*;

public class balls{

	public static void main(String[] args) throws Exception{
	
		Scanner In = new Scanner(new File("balls.in"));
		
		int t = In.nextInt(); // read in the number of test cases.
		
		for(int i = 1;i <= t;i++){
		
			int n = In.nextInt();      //Read in the number of bins
			double p = 1;		   //Initialize the starting probability
			
			// For each ball, calculate the odds of making a successful bin throw
			// assuming all bin throws were successful
			for(int j = n;j > 0;j--)
				p *= j*1.0/n;
			
			// Print output. Printf will take care of rounding issues.
			System.out.printf("Balls and Bins Game #%d: %.6f%%%n%n",i,p*100);

		}
	
	}

}