import java.util.*;
import java.io.*;


public class dogsled {

	
	public dogsled(Scanner in)
	{
		int sets = in.nextInt();
		
		
		for(int s = 1; s <= sets; s++)
		{
			//I chose to have the starting position at -500,0
			// so that my destination was 0.0
			double x = -500.0;
			double y = 0.0;
			
			double TOLERANCE = 10e-5;
			double MINESIZE = 15.0; //This solution treats each arm from the
									// center separately, don't get confused
									// with width/2 and width!
			
			double turn_amount_radians = 10.0 * 3.1415926535/180.0;
			
			double DOGSPEED = 50.0;
			
			//Heading is in radians
			// 0.0 = east
			// Turn left = increase
			// Turn right = decrease
			double heading = 0.0;
			
			String line = "";
			
			do
			{
				do
				{
					line = in.nextLine().trim();
				}while(line.length() == 0);
				
				
				if(line.equals("Haw!"))
					heading += turn_amount_radians;
				else if(line.equals("Gee!"))
					heading -= turn_amount_radians;
				else if(line.equals("Hike!"))
				{
					x += Math.cos(heading)* DOGSPEED;
					y += Math.sin(heading)* DOGSPEED;
				}			
			}while(!line.equals("Whoa!"));
			
			//Check our x and y to see if we made it to the
			// mine and then report
			
			System.out.print("Simulation #" + s + ": ");
			
			//System.out.println("X: " + x + " y: " + y);
			
			if(Math.abs(x) - MINESIZE <= TOLERANCE && Math.abs(y) - MINESIZE <= TOLERANCE)
				System.out.println("Made it!");
			else
				System.out.println("We're lost, eh?");
			
		}
	}
	
	
	//I like to instantiate my solution class to avoid
	// non-static variable issues. The 'test' scanner
	// is to allow for both file reading and standard in
	// from the same solution.
	public static void main(String[] args) {
		Scanner test;
		
		try
		{
			test = new Scanner(new File("dogsled.in"));
		}
		catch (FileNotFoundException e)
		{
			test = new Scanner(System.in);
		}
		
		new dogsled(test);
	}
		
	
}
