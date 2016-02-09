//imports the files needed
import java.io.*;
import java.util.*;

public class scoop 
{    
	public static void main(String[] args)throws IOException
	{
		//opens the input file
		Scanner in = new Scanner(new File("scoop.in"));
		
		int n = in.nextInt();
		
		//for loop to run through all the test cases in the input file
		for(int i = 1; i <= n; i++)
		{
			//sets the initial amount and reads in the different qualifiers
			int amt = 1;
			String get = in.next();
			
			//when the qualifier equals scoop the while loop stops
			while(!get.equals("Scoop"))
			{
				//determines the qualifier and multiplies the amount by the necessary amount
				if(get.equals("Double"))
				{
					amt *= 2;
				}
				else if(get.equals("Triple"))
				{
					amt *= 3;
				}
				else
				{
					amt *= 4;
				}
				
				//gets the next qualifier
				get = in.next();
			}
			
			//outputs the result of the test case
			System.out.println("Order #" + i + ": " + amt);
		}
	}    
}