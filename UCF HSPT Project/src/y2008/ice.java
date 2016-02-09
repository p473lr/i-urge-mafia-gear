// Stephen Fulwider
//	UCF High School Programming Contest
//	Problem: Ice

import java.io.*;
import java.util.*;

public class ice
{
	public static void main(String[] args) throws Exception
	{
		// open input file for reading
		Scanner fin = new Scanner(new File("ice.in"));
		
		// get number of test cases to process
		int s = fin.nextInt();
		
		// process each test case
		for (int session = 1; session <= s; session++)
		{
			int b = fin.nextInt();
			
			// set the the starting point for the simulation
			int cupNum = 1;
			int numIceCubes = 0;
			
			System.out.printf("Session #%d:%n", session);
			
			// process each burst of ice
			for (int i = 0; i < b; i++)
			{
				// add this burst of ice to the current cup
				numIceCubes += fin.nextInt();
				
				// three possiblities here
				//	1) We are still at less than 7 cubes
				//	2) We are at 7 cubes
				//	3) We are at more than 7 cubes
				
				// we only need to worry about (1) if there are no more ice bursts coming,
				//	so that comes after the last ice burst has been handled
				
				// handle case (2) and (3)
				if (numIceCubes >= 7)
				{
					// get the appropriate message to print
					String outMsg = "";
					if (numIceCubes == 7)
						outMsg = "Perfect!";
					else
						outMsg = (numIceCubes - 7) + " cubes too many!";
					
					// print the message for this cup
					System.out.printf("   Cup #%d: %s%n", cupNum, outMsg);
					
					// go to the next cup, reset ice cubes
					cupNum++;
					numIceCubes = 0;
				}
			}
			
			// handle case (1) from above
			//	notice that we only need to check if there are any ice cubes here.  The reason for
			//	this is that we are guaranteed that if there were 7 or more ice cubes after the last
			//	burst, the for loop would have taken care of it and reset numIceCubes to 0.  Thus if
			//	there is any ice in the current cup, it is less than 7 cubes and we need more cubes
			//	to fill this cup.
			if (numIceCubes > 0)
				System.out.printf("   Cup #%d: Need %d more cubes!%n", cupNum, 7 - numIceCubes);
				
			System.out.println();
		}
	}
}