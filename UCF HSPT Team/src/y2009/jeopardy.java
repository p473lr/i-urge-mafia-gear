import java.io.*;
import java.util.*;

public class jeopardy {

	public static void main(String[] args) throws Exception{
		
		// Sets up file i/o and reads in the first input.
		Scanner In = new Scanner(new File("jeopardy.in"));
		int i = In.nextInt(), j = In.nextInt();
		
		// Continues running until terminating conditions are met (the -1 -1 case),
		while(i != -1 && j != -1){

			/* We know two numbers (we'll call them a & b) form the sum i and the difference j
			 * In other words, a+b=i and a-b=j. So 2a = i+j. If i+j is odd, that means that
			 * a would have to be a fraction. And if i was less than j, that means that the
			 * sum is smaller than the difference which is only possible if b is negative.
			 * Thus we print it as an invalid case.
			 */
			if((i+j)%2 == 1 || i < j)
				System.out.println("NOT SO FAST, TREBEKK!");
			else{
				// Solve for a, then use a to solve for b.
				int a = (i+j)/2, b = i-a;
				// Since the difference is non-negative, we know a will always be greater than
				// or equal to b in a valid case.
				System.out.println("What are the sum and difference of "+a+" and "+b+"?");
			}
			// Take in the next input
			i = In.nextInt();
			j = In.nextInt();
		}
	}
}
