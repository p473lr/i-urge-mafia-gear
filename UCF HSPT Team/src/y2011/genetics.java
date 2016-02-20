/*
Program: genetics
Language: Java
*/

import java.util.*;
import java.io.*;

public class genetics {
	public static void main(String[] args) throws Exception{
		//Create a Scanner for the file input
		Scanner in = new Scanner(new File("genetics.in"));

		//The input begins with an integer denoting the number of tests
		int numCases = in.nextInt();

		//loop across each test case
		for(int iter=1; iter<=numCases; iter++) {
			//each output must begin with the following header
			System.out.printf("Sequence #%d: ", iter);

			//acquire the input for the given test case
			String input = in.next();

			//if the first character in the input string is a digit, we will
			//perform conversion from base 10 to base 4
			//otherwise, skip to the else statement for base 4 to base 10 conversion
			if(Character.isDigit(input.charAt(0))) {
				//parse the integer representation of the input string
				int intVal = Integer.parseInt(input);

				//pass the parsed value to the 'convert' method, written below
				//output is printed within the convert method
				convert(intVal);
			}
			else {
				//start the integer value at 0
				int intVal = 0;

				//To perform conversion from base 4 to base 10, begin with a value
				//of zero. For each character in the string, beginning with the
				//first character, multiply the current number by 4, then add the
				//value of the current character being inspected
				for(int i=0; i<input.length(); i++) {
					intVal *= 4;
					switch(input.charAt(i)) {
						case 'A':
							break;
						case 'C':
							intVal += 1;
							break;
						case 'G':
							intVal += 2;
							break;
						case 'T':
							intVal += 3;
							break;
					}
				}
				//output the final value
				System.out.print(intVal);
			}
			//end each test case with a new line
			System.out.println();
		}
	}
	//to convert a number from base 10 to base 4, take the current value
	//and divide it by 4. The remainder is added to the beginning of the
	//base 4 representation, and the process is repeated on the quotient.
	//In order to add the new character to the beginning of the string,
	//it is printed after the recursive call
	public static void convert(int intVal) {
		//if the value reaches 0, the process is complete
		if(intVal == 0) return;

		convert(intVal/4);

		switch(intVal%4) {
			case 0: 
				System.out.print("A");
				break;
			case 1:
				System.out.print("C");
				break;
			case 2:
				System.out.print("G");
				break;
			case 3:
				System.out.print("T");
				break;
		}
	}
}
