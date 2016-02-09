// Arup Guha
// 5/4/09
// Solution to 2009 UCF HS Programming Contest Problem: Smart.

import java.util.*;
import java.io.*;

public class smart
{
	public static void main(String[] args) throws Exception
	{
		Scanner in;
		
		// Open the input file. If it's not there, read from the keyboard.
		try
		{
			in = new Scanner(new File("smart.in"));
		}
		catch(FileNotFoundException e)
		{
			in = new Scanner(System.in);
		}
		
		// Read in the number of data sets.
		int sets = in.nextInt();
		
		// Read through each set and output the appropriate message.
		for(int s = 1; s <= sets; s++)
			if(in.nextInt() == in.nextInt())
				System.out.println("The Old Knife-Hits-the-Target Trick");
			else
				System.out.println("Missed it by THAT much");
	
	
	}
}