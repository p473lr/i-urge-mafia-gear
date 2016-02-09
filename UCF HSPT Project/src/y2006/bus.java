import java.io.*;
import java.lang.*;
import java.util.*;

public class bus{

	public static void main(String[] args)throws Exception{
	
		BufferedReader In = new BufferedReader(new FileReader("bus.in"));
		String input;
		StringTokenizer token;
		int lengthA, lengthB, speedV, speedW, shortBusLength, differenceLength, newSpeed, set = 1;
		double correlation, changeSpeed;
		

// Reads in the input
		input = In.readLine();

		
		while(input.compareTo("0 0 0 0 0") != 0){

// tokenizes and assigns the values accordingly
		   token = new StringTokenizer(input);
		   lengthA = Integer.parseInt(token.nextToken());
		   speedV = Integer.parseInt(token.nextToken());
		   lengthB = Integer.parseInt(token.nextToken());
		   speedW = Integer.parseInt(token.nextToken());
		   shortBusLength = Integer.parseInt(token.nextToken());

// Find how fast the bus must go, to decrease by 1 meter
// The correlation is multiplied by -1 to represent that its a negative correlation.		
		   correlation = -1.0 * (speedV - speedW)/(lengthA - lengthB);

// Finds the difference in length between the short bus and one of the bus lengths.
// It finds the change in speed needed to meet the change in length.
		   differenceLength = lengthA - shortBusLength;
		   changeSpeed = differenceLength * correlation;
			
// Rounds the  number up if the number is a fraction.
		   newSpeed = (int) Math.ceil(speedV + changeSpeed);

// If the speed is negative, set the speed to 0
		   if(newSpeed < 0)
		     newSpeed = 0;
			  

		   System.out.printf("Ali's Bus Trip #%d: The bus must travel at least %d kph.\n", set, newSpeed);

// Increments the set and reads in the next line.			  
		   set++;
		   input = In.readLine();

		
		}
	
	}

}
