import java.util.TreeMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class picture
{
	public static void main(String[] args)
	{
		TreeMap<String,Double> terrainTypes;
		Scanner fin;
		int track = 1;
		
		// Open the input file for reading. Abort if the file can't be found.  
		try{
			fin = new Scanner(new File("picture.in"));
		}
		catch(IOException e)
		{
			return;
		}
		// The program will run until a zero is read as the number of terrain types
		while(true)
		{
			int terrainCount = fin.nextInt();			
			if(terrainCount == 0)
				return;
			
			// Read each terrain type and add it to a map for easy lookup
			terrainTypes = new TreeMap<String,Double>();
			for(int i=0;i<terrainCount;i++)
			{
				terrainTypes.put(fin.next(), fin.nextDouble());
			}
			
			// A key simplifying observation can be made: Since acceleration is
			// not an issue and terrains affect vehicles uniformly, a segment
			// with half speed is no different from a segment of twice the length
			// with normal speed. Thus, we can compute an "adjusted length" for
			// the racetrack by dividing each segment by its terrain factor.
			int segments = fin.nextInt();
			double adjustedLength = 0;
			for(int i=0; i<segments; i++)
			{
				double tfactor = terrainTypes.get(fin.next());
				adjustedLength += fin.nextDouble() / tfactor;
			}
			
			// Another simplifying observation is that the length of time taken
			// for the two vehicles to meet is the amount of time taken for
			// the two vehicles together to cover the adjusted track length at
			// their normal speeds, so we can add their speeds together to
			// simplify our calculations
			// Vehicle 1
			// ------------------------------->
			//                                 <----------------------
			//                                               Vehicle 2
		
			double totalSpeed = fin.nextDouble() + fin.nextDouble();
			
			// Speed is distance/time, so the time is distance / speed			
			double timeTaken = adjustedLength / totalSpeed;
			System.out.printf("Picture %d: %.3f%n", track, timeTaken);
			track++;
		}
	}
}
