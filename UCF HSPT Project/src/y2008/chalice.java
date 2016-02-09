//Montgomery Anaconda and the Sacred Chalice judge solution
//Shehan Sirigampola

import java.io.*;
import java.util.*;

public class chalice {
	public static void main(String[] args) throws IOException, FileNotFoundException {
		FileReader file = new FileReader("chalice.in");
		BufferedReader reader = new BufferedReader(file);
		
		int cases = Integer.parseInt(reader.readLine());				//Input the number of cases and store it in cases
		for(int i = 0; i < cases; i++) {								//Loop through all the cases
			int weight = Integer.parseInt(reader.readLine());			//Input the weight of the accused and store it in weight
			
			int geese = Integer.parseInt(reader.readLine());			//Input the number of geese and store it in geese
			int sum = 0;												//Stores the total weight of the flock of geese
			StringTokenizer tokens = new StringTokenizer(reader.readLine());		//Read in the line with the weight of the geese and tokenize it
			for(int j = 0; j < geese; j++) {							//Loop through all the geese
				sum += Integer.parseInt(tokens.nextToken());			//Add the weight of one goose to the weight of the flock
			}
			
			System.out.print("Trial #" + (i+1) + ": ");					//Output trial number
			
			//Based on weight comparison output whether she is or isn't a witch
			if(weight <= sum)
				System.out.println("SHE'S A WITCH! BURN HER!");
			else
				System.out.println("She's not a witch. BURN HER ANYWAY!");
		}
	}
}
		
