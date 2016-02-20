import java.io.*;		// Import input and output libraries
import java.util.*;	// Import utility libraries (for Scanner)


class roboto
{

	// Create "infinity" constant to be a very large number, with regard to the 
	// problem. The maximum junction resistance in this problem is 500, so one 
	// billion effectivly acts like infinity
	public static final int INFINITY = 1000000000;

	/****************************************************************************/
	
	public static void main(String[] args) throws IOException
	{
		// Create input file scanner
		Scanner inpt = new Scanner(new File("roboto.in"));
		
		// Get the number of circuits to be tested
		int numDesigns = inpt.nextInt();
		
		for (int i = 0; i < numDesigns; i++)
		{
			// Get the number of junctions in this design
			int numJunctions = inpt.nextInt(); 
			
			// Create a 2D array to hold the junction connection information
			int[][] junctions = new int[numJunctions][numJunctions];
			
			// Get each junction connection resistance
			for (int j = 0; j < numJunctions; j++)
				for (int k = 0; k < numJunctions; k++)
				{
					junctions[j][k] = inpt.nextInt();

					// If there is no connection between j and k, as denoted by -1, there is 
					// essentially an infinite resistance between these junctions, so we set
					// the value of the resistance here to infinity
					if (junctions[j][k] == -1)
						junctions[j][k] = INFINITY;				
				}
				
			// Find the shortest paths between each junction using Floyd's Algorithm
			// (see function for more details)
			int[][] pathLengths = floyd(junctions);
			
			// If the shorest path from junction 0 to junction 1 is zero, then we know
			// that there is some path in the circuit that contains a zero resistance 
			// path between the two terminals, and thus there is a short circuit
			if (pathLengths[0][1] == 0)
				System.out.println("Circuit Design #" + 
				                   (i+1) + ": " +
				                   "Back to the drawing board");
			else
				System.out.println("Circuit Design #" + 
				                   (i+1) + ": " +
				                   "No more hedgehog troubles");
		}
		
		// All done now, so we can close the input file scanner
		inpt.close();
	}
	
	
	/****************************************************************************/
	
	/* 
		Floyd's Algorithm- finds the shortest path from each junction to each other 
	   junction. It does this by trying to find a shorter path from i to j by
		going through junction k. More information can be found at 
		http://en.wikipedia.org/wiki/Floyd%27s_algorithm
		
		The returned matrix is such that adj[i][j] = the length of the shortest path
		from i to j
	*/
	
	public static int[][] floyd (int[][] junctions)
	{
		//Make a copy becuase the matrix is destroyed through Floyd's
		int[][] adj = junctions.clone(); 
				
		//Start finding shortest paths		
		for (int k = 0; k < adj.length; k++)
			for (int i = 0; i < adj.length; i++)
				for (int j = 0; j < adj.length; j++)
					if (adj[i][k] + adj[k][j] < adj[i][j])
						adj[i][j] = adj[i][k] + adj[k][j];	
		
		return adj;		
	}
}
