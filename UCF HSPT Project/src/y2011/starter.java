/* STARTER
 * 
 * This problem boils down to converting from rectangular to polar coordinates.
 * 		* The angle has to be formatted as a time
 * 		* The radius (distance) has to be formatted as a date
 */

import java.io.*;
import java.util.*;

public class starter {
	public static void main (String[] args) throws Exception {
		Scanner in = new Scanner(new File("starter.in"));
		
		int t = in.nextInt();		//The number of cases in the input file
		
		for (int i=0; i<t; i++) {
			double x = in.nextDouble(), y = in.nextDouble();	//Tango coordinates
			
			double angle = Math.atan2(y, x);	//The angle relative to the x-axis (positive angle is counter-clockwise)
			angle = -angle;						//Make the angle go clockwise
			if (angle < 0)						//Angle is in the range (-PI, PI], make it [0, 2*PI)
				angle += 2 * Math.PI;
			angle += Math.PI / 2;				//Forward is +x-axis, rotate 90 degrees to +y-axis
			angle %= 2*Math.PI;					//Last step could make the angle > 2*PI, normalize it back to [0, 2*PI)
			
			//Now angle is positive, clockwise, and relative to +y-axis
			//For distance we use Pythagorean Theorem
			
			System.out.println("Tango at " + angleToTime(angle) + ", " + distToDate(Math.sqrt(x*x + y*y))+ "!");
		}
		
		//Program end output
		System.out.println("The enemy's gate is down!");
	}
	
	//Convert an angle to a formatted time
	public static String angleToTime (double angle) {
		double radsPerSecond = 2*Math.PI / (12 * 60 * 60);		//The amount of radians corresponding to a second on the clock
		
		int SS = (int)Math.round(angle / radsPerSecond);		//The (rounded) total number of seconds for this angle
		
		int mm = SS / 60;		//The total number of whole minutes in this angle
		SS %= 60;				//Drop all seconds that are now counted in minutes
		
		int H = mm / 60;		//The number of whole hours in this angle
		mm %= 60;				//Drop all minutes that are now counted in hours
		
		if (H == 0)				//Hours are 1-based, but hour 0 corresponds to straight ahead (12 o'clock)
			H = 12;
		
		//String formatting
		String ret = H + ":";
		
		if (mm < 10)
			ret += "0";
		ret += mm + ":";
		
		if (SS < 10)
			ret += "0";
		ret += SS;
		
		return ret;
	}
	
	//Convert a distance to a formatted date
	public static String distToDate (double dist) {
		int DD = (int)Math.round(dist * 500);	//The (rounded) total number of days in this distance (500 days per meter)
		
		int MM = DD / 30;	//The total number of whole months in this distance
		DD %= 30;			//Drop all days that are now counted in months
		
		int YYYY = MM / 12;	//The total number of years in this distance
		MM %= 12;			//Drop all months that are now counted in years
		
		DD++;				//Days and months are 1-based
		MM++;
		
		//String formatting
		String ret = "" + YYYY;
		while (ret.length() < 4)
			ret = "0" + ret;
		
		ret = DD + "/" + ret;
		if (DD < 10)
			ret = "0" + ret;
		
		ret = MM + "/" + ret;
		if (MM < 10)
			ret = "0" + ret;
		
		return ret;
	}
}
