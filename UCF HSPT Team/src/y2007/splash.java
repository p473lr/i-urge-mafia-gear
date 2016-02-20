/* Spirit Splash
 * input: splash.in
 * Solution: Michael Do
 */

import java.io.*;
import java.util.*;

public class splash{
	
	public static void main(String[] args) throws Exception{

// Sets up I/O and takes in the number of cases		
		Scanner In = new Scanner(new File("splash.in"));
		int numCases = In.nextInt();
		
// Loops through the cases to compute them and print them to screen		
		for(int x = 0;x < numCases;x++){
			
// Takes in the length and width. Area of a rectangle = length * width, 
// so take length*width and print it to screen.			
			int length = In.nextInt();
			int width = In.nextInt();
			System.out.println(length*width);
			
		}
		
	}
	
}