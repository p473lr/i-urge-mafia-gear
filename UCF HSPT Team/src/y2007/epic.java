/* ********************************
 * Problem: The Epic of Aldez
 * 
 * The problem of finding the optimal path for Chain to follow to reach
 * the boss is very challenging. Fortunately, that's a different problem.
 * 
 **********************************/

import java.util.Scanner;
import java.io.*;

public class epic {
	public static void main(String[] args)
	{
		int dungeon = 1;
		
		// Open the input file for reading
		Scanner fin = null;
		try{
			fin = new Scanner(new File("epic.in"));
		} catch(IOException e) {
			return;
		}
		
		// Process each case until end of input
		while(true)
		{
			fin.nextInt(); // The first integer in the input is essentially irrelevant
			int n = fin.nextInt();
			
			// Check the terminating condition
			if(n == 0)
				return;
			
			// Output the dungeon header
			System.out.println("Dungeon "+dungeon+":");
			dungeon++;
			
			// For each line, output a reversed version of the input
			// Just print out the characters from the back of the string to the front
			for(int i=0;i<n;i++)
			{
				String s = fin.next();
				for(int j=s.length() - 1; j >= 0; j--)
					System.out.print(s.charAt(j));
				System.out.println();
			}
			
			// Leave a blank line between dungeons
			System.out.println();
		}
		
		
	}
}
