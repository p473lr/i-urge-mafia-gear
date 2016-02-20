// Arup Guha
// 4/22/08
// Solution to 2008 UCF High School Programming Contest Problem: Friends
import java.util.*;
import java.io.*;

public class friends {
	
	// Constants used for adjacency matrix.
	
	// Note: This is big enough since there are at most 50 friends total.
	final static int NOT_FRIENDS = 100; 
	final static int NOT_FOUND = -1;
	
	// Here we store the names from the network, the adjacency matrix and
	// a connectivity matrix.
	private String[] names;
	private int[][] adjMatrix;
	private int[][] distMatrix;
	
	public static void main(String[] args) throws IOException {
		
		Scanner fin = new Scanner(new File("friends.in"));
		
		int n = fin.nextInt();
		
		// Process each case.
		for (int i=1; i<=n; i++) {
			
			// Initialize our object.
			friends myNetwork = new friends(fin);
			
			// Calculate connectivity.
			myNetwork.Floyds();
			
			// Get the score of "You".
			int yourScore = myNetwork.score("You");
			
			int rivals = fin.nextInt();
			
			System.out.println("Social Network "+i+":");
			
			// Calculate each rival's score.
			for (int j=0; j<rivals; j++) {
			
				// Get the rival's score.
				String rival = fin.next();
				int rivalScore = myNetwork.score(rival);
				
				
				// Output the difference.
				System.out.print("   "+rival+": Difference of ");
				System.out.println((yourScore-rivalScore)+" point(s).");	
			}
			
			// Blank line after the case.
			System.out.println();
		}
		
		fin.close();
	}
	
	// Pre-condition: fin is referencing the location in the file that stores
	//                the beginning of an individual network.
	// Post-condition: Information from the file for a network initializes this
	//                 object.
	public friends(Scanner fin) {
		
		// Get the number of friends.
		int size = fin.nextInt();
		
		
		// Store their names.
		names = new String[size];
		for (int i=0; i<size; i++)
			names[i] = fin.next();
			
		int connections = fin.nextInt();
		
		// Set up our adjacency matrix.
		adjMatrix = new int[size][size];
		
		// Initially, no one are friends.
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				adjMatrix[i][j] = NOT_FRIENDS;
				
		// This is useful for distance calculations.
		for (int i=0; i<size; i++)
			adjMatrix[i][i] = 0;
		
		// Add in each pair of friends.
		for (int i=0; i<connections; i++) {
			
			// Read in the names.
			String name1 = fin.next();
			String name2 = fin.next();
			
			// Find them in the list.
			int index1 = find(name1);
			int index2 = find(name2);
			
			// 1 indicates these two are friends.
			adjMatrix[index1][index2] = 1;
			adjMatrix[index2][index1] = 1;
		}
		
		// The distance Matrix starts off as a copy of the adjacency matrix.
		distMatrix = new int[size][size];
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				distMatrix[i][j] = adjMatrix[i][j]; 
		
	}
	
	// Pre-condition: none
	// Post-condition: Returns the index that name is stored in this object, if
	//                 it is, and -1 otherwise.
	public int find(String name) {
		
		// Look for name.
		for (int i=0; i<names.length; i++)
			if (name.equals(names[i]))
				return i;
				
		// Never found name.
		return NOT_FOUND;
	}
	
	// Pre-condition: This object had been initialized.
	// Post-condition: the distMatrix will store the shortest distance between
	//                 each pair of nodes in the graph adjMatrix represents.
	public void Floyds() {
		
		// There are other ways to solve this problem, but this way is probably
		// the easiest to type in. It's not the most efficient though. But, since
		// our graph is so small, it doesn't really matter...
		// # ops = 50x50x50 = 125,000, which is tiny, relatively speaking.
		
		// Consult an algorithms textbook for Floyd-Warshall's Algorithm to get
		// an in-depth explanation of what this code is really doing.
		
		for (int k=0; k<distMatrix.length; k++) {
			for (int i=0; i<distMatrix.length; i++) {
				for (int j=0; j<distMatrix.length; j++) {
					if (distMatrix[i][k] + distMatrix[k][j] < distMatrix[i][j])
					
						distMatrix[i][j] = distMatrix[i][k] + distMatrix[k][j];
					
				}
			}
		}
	}
	
	// Pre-condition: Floyd's has already been run on this object.
	// Post-condition: Returns the score of name in this network.
	public int score(String name) {
		
		// Find the name.
		int index = find(name);
		
		// Not in the network, so they get 0.
		if (index == NOT_FOUND) return 0;
		
		// Calculate # of friends
		int score = 0;
		for (int i=0; i<adjMatrix.length; i++)
		
			// 1 represents friendship in the adjacency matrix.
			if (adjMatrix[i][index] == 1)
				score++;
				
		// Calcualte # of friends in extended network.
		for (int i=0; i<adjMatrix.length; i++)
		
			// A value greater than 0 and less than 100 represents someone in
			// your network.
			if (distMatrix[i][index] > 0 && distMatrix[i][index] < NOT_FRIENDS)
				score++;
		
		// This is your score!
		return score;
	}
}