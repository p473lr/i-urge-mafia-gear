import java.io.*;
import java.util.*;

public class kakuro
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader in = new BufferedReader(new FileReader("kakuro.in"));
		
		// Read in the number of lines in the input.
		int numlines = Integer.parseInt(in.readLine());
		
		// Loop through the number of lines we're supposed to read.
		for(int x = 1; x <= numlines; x++)
		{
			// Read in the line.
			String line = in.readLine();
			
			// Create the string tokenizer so we can separate the line to get
			// the individual integers.
			StringTokenizer st = new StringTokenizer(line);
			
			// Get the four numbers we need.
			int goal = Integer.parseInt(st.nextToken());
			int num1 = Integer.parseInt(st.nextToken());
			int num2 = Integer.parseInt(st.nextToken());
			int num3 = Integer.parseInt(st.nextToken());
			
			// First, see if the numbers sum up correctly.
			if(num1 + num2 + num3 == goal)
			{
				// If that's the case, make sure they're unique.
				if(num1 != num2 && num2 != num3 && num1 != num3)
				{
					// It's a good triplet
					System.out.println("Proper triplet");
				}
				else
				{
					// Not a good triplet
					System.out.println("Not a good triplet");
				}
			}
			else
			{
				// Not a good triplet
				System.out.println("Not a good triplet");
			}
		}
	}
}
			