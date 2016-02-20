import java.util.*;
import java.io.*;


public class ryan
{

	//Our two constant strings to look out for.
	String sol = "Greeks! Go home!";
	String don = "Am I done yet?";

	public ryan(Scanner in) throws Exception
	{
		//Number of times we need to write the phrase.
		int times;
		
		//Keep looping until we see a zero
		while((times = in.nextInt()) != 0)
		{
			//The number of times we have already written
			int writ = 0;
		
			//An infinite loop, we break out when we find the end phrase
			for(;;)
			{
				String lin = in.nextLine();
				
				//If it is right, add one. == works here too.
				if(sol.equals(lin))
					writ++;
				else if(don.equals(lin))	//Else if it is the end string, quit
					break;
			}
			
			//If we didn't write enough times, print the difference
			if(writ < times)
				System.out.println("You have " + (times - writ) + " left to go.");
			else	//Else print done message.
				System.out.println("You're done.  Now don't do it again.");
		
		
		}
	
	}

	public static void main(String[] args) throws Exception
	{
		new ryan(new Scanner(new File("ryan.in")));	//I like using this trick instead
											// of making everything static.
	}
}

