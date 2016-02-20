import java.io.*;
import java.util.*;

public class hands{

	public static void main(String[] args) throws Exception{

		// Setup file input and read in the number of test cases
		Scanner In = new Scanner(new File("hands.in"));
		int numClocks = In.nextInt();
		
		for(int currClock = 1;currClock <= numClocks;currClock++){

			double[] handsOne = new double[3];
			double[] handsTwo = new double[3];

			// Reads in each clock hand
			for(int i = 0;i < 3;i++)
				handsOne[i] = In.nextDouble();
			for(int i = 0;i < 3;i++)
				handsTwo[i] = In.nextDouble();

			// Sorts the sides in order for ease of calculations
			Arrays.sort(handsOne);
			Arrays.sort(handsTwo);

			// find the length of each side
			double lengthOne = Calculate(handsOne);
			double lengthTwo = Calculate(handsTwo);
			
			// Take the maximum length to represent how large the clock needs
			// to be at a minimum and multiply it by 2 in order to get the diameter.
			// Round it upwards and cast as an integer, as per the specification of 
			// the problem to find the smallest integer possible for the clock.
			System.out.println("Pair #"+currClock+" of hands requires a clock face at least "
					+(int)Math.ceil(2*Math.max(lengthOne, lengthTwo))+" meters wide.");

		}	

	}

	public static double Calculate(double[] hands){

		/* The problem is based on the law of cosines. 
		 * The law of cosines states that given a triangle with sides
		 * a, b, c, then c^2 = a^2 + b^2 - 2ab*cos(theta) where theta is
		 * the angle across from side c.
		 * Thus we can find the cosine of theta from side c, which we make the largest
		 * side for our calculations like so: cos(theta) = (a^2 + b^2 - c^2)/(2ab)
		 */
		double theta = (hands[0]*hands[0]+hands[1]*hands[1]-
				hands[2]*hands[2])/(2*hands[0]*hands[1]);
		
		/* Now when attaching the clock hand, what we realize is that by splitting the
		 * clock hand, we have basically created a second triangle. Now just like the
		 * law of cosines can be used to find an angle when we have 3 sides, we can find a
		 * side if we have 2 sides and an angle. In this case we solve for the angle
		 * in the previous line of code, and we have 2 sides. Now realize, when attaching
		 * the clock to the center, the short side, a, has been split in half. So the two
		 * sides of the triangle are a/2 and b. Thus to find the 3rd side which we'll call
		 * c', we use the law of cosines again c'^2 = a^2/4 + b^2 - 2ab/2*cos(theta), 
		 * which can be simplified into c' = Sqrt(a^2/4 + b^2 - ab*cos(theta).
		 */
		double length = Math.sqrt(hands[0]*hands[0]/4+
				hands[1]*hands[1]-hands[0]*hands[1]*theta);
		
		/* After we're finished, we now have the length of the clock hand. We
		 * add 0.01 as the tolerance, in case the hand is too close to the edge and
		 * needs to be bumped up and return it.
		 */
		return length+0.01;
		
	}
}