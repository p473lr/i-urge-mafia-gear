//This is SPARTA judge solution
//Shehan Sirigampola

import java.io.*;
import java.util.*;

public class sparta {
	public static void main(String[] args) throws IOException, FileNotFoundException {
		
		//File reading mechanism
		FileReader file = new FileReader("sparta.in");
		BufferedReader reader = new BufferedReader(file);
		
		int n = Integer.parseInt(reader.readLine());						//Take in the number of cases for the program and store in n
		
		int p = 0;															//Stores the troop count of the persian army
		
		for(int i = 0; i < n; i++) {										//Loop through all the cases
		    int m = Integer.parseInt(reader.readLine());					//Input the number of shouts in this case and store it in m
		    
		    for(int j = 0; j < m; j++) {									//Loop trough all the shouts
				StringTokenizer tokens = new StringTokenizer(reader.readLine());			//Read in a line of the case and tokenize it
				tokens.nextToken();															//Take in first token of the line ("MADNESS") and ignore it
				p += Integer.parseInt(tokens.nextToken());									//Take in number of persian troops from shout
		    }
		    
		    System.out.println("Dispute " + (i+1) + ":");					//Output dispute number
		    System.out.println("Persian " + p + ", Spartan 300");		//Output troop numbers(result)
		    System.out.println("This is SPARTA!\n"); 
		    p = 0;															//Reset Persian troop count for next case
		}
	}
}