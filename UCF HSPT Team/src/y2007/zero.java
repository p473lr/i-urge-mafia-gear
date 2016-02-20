import java.io.*;

/*
 * 2007 UCF High School Programming Contest
 * Java Solution for Guitar Zero
 */

public class zero
{
	public static void main(String args[]) throws Exception
	{
		// Create the input file.
		BufferedReader in = new BufferedReader(new FileReader("zero.in"));
		
		int count = 1;
		while(true)
		{
			// Read in the length of the song.
			int length = Integer.parseInt(in.readLine());
			
			// If it's zero, we've reached the end of the input file, so break out of the loop.
			if(length == 0)
			{
				break;
			}
			
			// Read in the song string.
			String input = in.readLine();
			
			// The score starts off at zero.
			int score = 0;
			
			// Loop through each character in the input string, and increment
			// or decrement the score depending on the character.
			for(int i = 0; i < input.length(); i++)
			{
				if(input.charAt(i) == '+')
				{
					score++;
				}
				else
				{
					score--;
				}
			}
			
			// If the score is greater than zero, they're "shreddin", otherwise
			// they're just a guitar zero.
			if(score > 0)
			{
				System.out.println("Song "+count+": Shreddin");	
			}
			else
			{
				System.out.println("Song "+count+": Guitar Zero");
			}
			
			// Increment the count for the next song.
			count++; 
		}
	}
}