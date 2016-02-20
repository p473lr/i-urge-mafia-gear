import java.io.*;
import java.util.*;

public class pizza{

	static double pi = 3.14159265;

	public static void main(String[] args) throws Exception{

		// Sets up the file input and reads in the number of orders Perry ordered
		Scanner In = new Scanner(new File("pizza.in"));
		int numOrders = In.nextInt();
	
		for(int currOrder = 1;currOrder <= numOrders;currOrder++){

			/* Reads in p, t, c. The perimeter p is equal to 2*r*pi, where r is the radius of the pie.
			 * The area of a circular pie, a, is equal to r^2*pi. So if you rewrite the equation in relation to the
			 * perimeter, the radius, r, is equal to p/(2*pi) and the area, a, is equal to p^2/(4*pi). 
			 * The number of calories in a pie is a * 10. Perry ate c out of t slices from the pie. 
			 * Thus the total number of calories Perry consumed is equal to (10*p^2*c)/(4*t*pi). 
			 * Round it up with Math.ceil or whatever way you prefer and print it out.
			 */
			double p = In.nextDouble(), t = In.nextDouble(), c = In.nextDouble();
			int caloriesConsumed = (int) Math.ceil(10*p*p/pi/4*c/t);
			System.out.println("Perfectly Popular Pizza "+currOrder+
					": Perry consumed "+caloriesConsumed+" calories.");

		}


	}

}