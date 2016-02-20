/**
 * CodeWars 2007
 * Problem #1 -- Powers of Two
 * NOVICE TEAMS ONLY -- 2 Points
 * 
 * Task Description:
 * Read a nonnegative interger N (range 0 to 20) and print the powers of 2 from 1 to 2^N.
 * You do not need to validate that the integer is in range.
 */

/**
 * @author jasonw
 */
import java.util.Scanner;

public class Prob01 {

	/**
	 * @param args no args used in this program
	 */
	public static void main(String[] args) {
		// output introduction and instructions
		System.out.println("Problem #1 -- Powers of Two\n This Program will output the powers of 2 from 1 to 2^2.");
		System.out.println("Enter a non-negative integer N (range 0 to 20): ");
		
		//class used to read keyboard input
		Scanner keyboard =  new Scanner(System.in); 
		
		//initialize input value
		int inputInteger=0;

		//Read in an int and check the parameters
		while (keyboard.hasNextInt()){
			inputInteger = keyboard.nextInt();
            if ( inputInteger < 0) {
            	System.out.println("number is too small!\n");
            	System.out.println("Enter a non-negative integer N (range 0 to 20): ");
            } else if (inputInteger > 20) {
            	System.out.println("number is too large!\n");
            	System.out.println("Enter a non-negative integer N (range 0 to 20): ");
            } else {
            	// we have a good value so break out of the while loop
            	break;
            }
        }
		
		// Loop through 0 to the input number and output the power of twos
		for (int i = 0; i <= inputInteger; i++) {
			System.out.println("\t2^"+inputInteger+" = "+(int)Math.pow(2, i));
		}

	}

}
