// Arup Guha
// 4/29/09
// Solution to 2009 UCF High School Programming Contest Problem: Family

import java.io.*;
import java.util.*;

public class family {
	
	private Hashtable people;
	private int[][] adjMatrix;
	
	// Sets up a default family tree object.
	public family() {
		people = new Hashtable();
		
		// This is an inefficient representation for this problem, but I am being lazy!
		adjMatrix = new int[300][300];
		
		// We'll never have more than 300 nodes in this graph.
		for (int i=0; i<300; i++)
			for (int j=0; j<300; j++)
				adjMatrix[i][j] = 0;
	}
	
	public void addRelation(String parent, String child) {
		
		// Get the index of the parent in our graph.
		Integer index1;
		if (people.containsKey(parent))
			index1 = (Integer)people.get(parent);
			
		// If it's not there, add it to our list of nodes.
		else {
			index1 = people.size();
			people.put(parent, new Integer(index1));
		}
		
		// Do the same with the child.
		Integer index2;
		if (people.containsKey(child))
			index2 = (Integer)people.get(child);
		else {
			index2 = people.size();
			people.put(child, new Integer(index2));
		}
		
		// connect the child to the parent and parent to the child
		adjMatrix[index1][index2] = 1;
		adjMatrix[index2][index1] = 1;
	}
	
	public boolean related(String person1, String person2) {
		
		// If either person is not on the list, automatically there is no relation.
		if (!people.containsKey(person1))
			return false;
		if (!people.containsKey(person2))
			return false;
			
		// Find the indexes of these two people.
		Integer index1 = (Integer)people.get(person1);
		Integer index2 = (Integer)people.get(person2);
		
		// Set up our DFS.
		boolean[] visited = new boolean[people.size()];
		for (int j=0; j<visited.length; j++)
			visited[j] = false;
			
		// Run a DFS from person i.
		dfs(index1, visited);
		return visited[index2];
	}
	
	
	// This does a DFS on the graph stored in the object. Visited marks
	// where we've already been.
	public void dfs(int index, boolean[] visited) {
		visited[index] = true;
		for (int i=0; i<adjMatrix.length; i++)
			if (adjMatrix[index][i] == 1 && !visited[i])
				dfs(i, visited);
	}
	
	public static void main(String[] args) throws Exception {
		
		Scanner fin = new Scanner(new File("family.in"));
		
		int numRelations = fin.nextInt();
		int caseNum = 1;
		
		// Read through all the cases.
		while (numRelations != 0) {
			
			// Let's start a new family =)
			String mom, dad, child;
			family tree = new family();
			
			// Read in each relation and add it to our tree.
			for (int i=0; i<numRelations; i++) {
				
				mom = fin.next();
				dad = fin.next();
				child = fin.next();
				tree.addRelation(mom, child);
				tree.addRelation(dad, child);	
			}
			
			// Get the two people for our query.
			String person1 = fin.next();
			String person2 = fin.next();
			
			// Output the answer!
			if (tree.related(person1, person2))
				System.out.println("Family #"+caseNum+": Related.");
			else
				System.out.println("Family #"+caseNum+": Not related.");
			
			numRelations = fin.nextInt();
			caseNum++;
		}
	
		fin.close();
	}
}
