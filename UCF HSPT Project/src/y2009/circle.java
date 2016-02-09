import java.io.*;
import java.util.*;

public class circle {
	
	public static void main(String[] args) throws Exception{
		
		Scanner In = new Scanner(new File("circle.in"));
		int numCases = In.nextInt();
		
		for(int currCase = 1;currCase <= numCases;currCase++){
			
			int r = In.nextInt(), s = In.nextInt(), rs = r*s;
			/* According to the pythagorean theorem a^2 + b^2 = c^2. So the x distance between
			 * the centers of the two centers of circle r and and circle s (d) is d^2 = (r+s)^2 - (r-s)^2.
			 * This simplifies to d = 2(rs)^.5. This can be then made for r and the tangential circle t, as
			 * well as s and t. The lateral distance from r to t (d1) plus the lateral distance t to s 
			 * equals d. So d1+d2 = d. So 2(rs)^.5 = 2(rt)^.5 + 2(ts)^.5 which can be simplified to
			 * rs/(r+s+2(rs)^.5)
			 */
			double ans = rs/(r+s+2*Math.sqrt(rs));
			
			System.out.printf("Circle Problem #%d: Radius of the small circle = %.2f%n", currCase, ans);
		}
	}
}
