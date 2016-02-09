//Author: Steven Zielinski
//Date: 4/30/2009

import java.util.*;
import java.io.*;

public class lines 
{
	public static void main(String[] args) throws IOException
	{
		//create a file reader to get the input file
		Scanner in = new Scanner(new File("lines.in"));
		
		//read in the first line of the input
		int partyLine = in.nextInt();//length of party line
		int singleLine = in.nextInt();//length of single rider line
		int capacity = in.nextInt();
		
		int scenario = 1;//keeps track of the number of test cases
		
		//a loop to run through every case until end of input is signaled by three zeros
		while(partyLine != 0 || singleLine != 0 || capacity != 0)
		{
			//declare an array to hold the party sizes in line
			int[] parties = new int[partyLine];
			
			//reads in the size of the parties
			for(int i = 0; i < partyLine; i++)
			{
				parties[i] = in.nextInt();
			}
			
			//finds and prints the answer to the problem
			run(scenario, parties, singleLine, capacity);
			
			//read next input
			partyLine = in.nextInt();
			singleLine = in.nextInt();
			capacity = in.nextInt();
		
			scenario++;
		}
	}
	
	public static void run(int sce, int[] p, int s, int c)
	{
		//keeps track of which coaster MHR will ride
		int coaster = 1;
		
		//a counter to keep track of the position in the party line that the program is at
		int pc = 0;
		
		//run until a break statement
		while(true)
		{
			//resets the temp counter to the capacity of a coaster so that i can fill it
			int temp = c;
			
			//the exit condition for a coaster
			boolean leave  = true;
		
			//loop that fills the coaster with passengers
			while(leave)
			{
				//used to determine that i had a full coaster before but i have no passengers in either line
				int temp2 = temp;
				
				//as long as the next party can fit on the coaster add them
				if(p.length > pc && p[pc] <= temp)
				{
					temp -= p[pc];
					pc++;
				}
				//allows the use of either line when there are still passengers in the single rider line
				else if(p.length == pc && temp <= s)
				{
					break;
				}
				//add single riders until the coaster is full
				else if(s > 0)
				{
					temp--;
					s--;
				}
				
				//if the coaster is full or no new passengers were added break from the loop
				if(temp == 0 || temp2 == temp)
					leave = false;
			}
			
			//determines that either line could be used and sends the data to my print function
			if(s == 0 && p.length == pc && temp != 0)
			{
				print(sce, coaster, 3);
				return;
			}
			//determines that the single rider line should be used and sends the data to my print function
			else if(s == 0 && temp != 0)
			{
				print(sce, coaster, 2);
				return;
			}
			//determines that the group line should be used and sends the data to my print function
			else if(p.length == pc && temp != 0)
			{
				print(sce, coaster, 1);
				return;
			}
			
			coaster++;
		}
	}
	
	//prints out the solution using the data given.
	public static void print(int s, int c, int l)
	{
		if(l == 1)
		{
			System.out.println("Scenario #" + s + ": MHR rides coaster #" + c + ", using the regular line.");
		}
		else if(l == 2)
		{
			System.out.println("Scenario #" + s + ": MHR rides coaster #" + c + ", using the single rider line.");
		}
		else
		{
			System.out.println("Scenario #" + s + ": MHR rides coaster #" + c + ", using either line.");
		}
	}
}
