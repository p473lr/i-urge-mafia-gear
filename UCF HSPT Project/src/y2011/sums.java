/* DIGIT SUMS
 * 
 * Given a number find the sum of all of its digits, and determine whether the sum is evenly divisible by 25 or not.
 */

import java.io.*;
import java.util.*;

public class sums {
	public static void main (String[] args) throws Exception {
		Scanner in = new Scanner(new File("sums.in"));
		
		int n = in.nextInt();	//The number of cases in the input file
		
		for (int i=0; i<n; i++) {
			int x = in.nextInt();	//The current number to check
			int sum = 0;			//The running sum of the digits of x
			
			//This loop runs 1 iteration for each digit in x, quitting when all digits have been processed 
			//or only 0's remain (in both cases x == 0)
			while (x > 0) {
				sum += x%10;		//Add the rightmost (least significant) digit to the running sum
				x /= 10;			//We don't need this digit any more, so drop it
			}
			
			//Now "sum" contains the sum of all the digits of x
			
			if (sum % 25 == 0)		//The sum is divisible by 25
				System.out.println("Yes, it's 25!!!");
			else					//The sum is not divisible by 25
				System.out.println("Bummer, no 25.");
		}
	}
}
