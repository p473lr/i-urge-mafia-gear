/* *****************************************
 * Problem: QUEE MAR
 * 
 * This problem is pretty simple: just rotate the string for one full
 * rotation!
 *******************************************/
import java.util.*;
import java.io.*;


public class marquee {
	public static void main(String[] args) throws IOException
	{
		// Open the input file for reading
		Scanner fin = new Scanner(new File("marquee.in"));
		int signs = fin.nextInt();
		
		// Process each sign
		for(int sign = 1; sign <= signs; sign++)
		{
			fin.nextLine(); // Consume the line separator
			String s = fin.nextLine(); // Read the string to rotate
			int m = fin.nextInt(); // Read the length of the marquee
			
			System.out.println("Sign #"+sign+":"); // Print the sign header
			
			// If the message is short enough to fit on the sign, just print
			// it out
			if(m >= s.length())
			{
			        // If the sign is bigger than the message, we
				// need to add spaces to fill the sign
				while (m > s.length())
					s += " ";

				System.out.println("["+s+"]");
				System.out.println();
				continue;
			}
			
			s = s+" ";
			// The number of lines of output is equal to the number of 
			// characters in the string we're rotating
			for(int offset = 0; offset < s.length(); offset++)
			{
				System.out.print("[");
				// Print out enough characters to fill the marquee, beginning
				// with character at position 'offset' and wrapping around if
				// need be.
				for(int i = 0; i < m; i++)
				{
					System.out.print(s.charAt((i+offset)%s.length()));
				}
				System.out.println("]"); // Wrap up the string
			}
			System.out.println();
		}
	}
}
