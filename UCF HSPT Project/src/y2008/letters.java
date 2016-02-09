 import java.io.*;
import java.util.*;

public class letters{

	/*
		the main function is run when "java letters" is run
		constructor for scanner might throw an IOException, so
		instead of having to put it inside a try/catch, I have
		the entire function throw an IOException. This makes
		it easier to program in a contest environment
	*/
	public static void main(String[] args) throws IOException{

		//open a scanner for the file
		Scanner scanner = new Scanner(new File("letters.in"));
		
		//read in the number of words that will have to be read
		int numWords = scanner.nextInt();
		
		/*
			nextLine reads to the end of the current line.
			Since the current line we're on has the number on it,
			we need to end this line to be ready for the next one.
			Therefore, we call scanner.nextLine() to finish off
			this line
		*/
		scanner.nextLine();
		
		//for every word to be read,
		for(int x = 0; x < numWords; x++){
		
			//get the current line with the word on it
			String s = scanner.nextLine();
			
			/*
				replace the string with a version of the string
				where all the l's are replaced with w's
			*/
			s = s.replace('l', 'w');
			
			//output the new string
			System.out.println(s);
			
		}//end for
		
	}//end main
	
}//end letters