// Stephen Fulwider
//	Choose Your Own Adventure - 2010 High School Programming Tournament

// This solution uses a technique known as memoization to solve the longest
//	path in Directed Acyclic Graph (DAG). The basic idea is to recursively
//	solve the problem, but when you get the answer for the longest path from
//	a particular node, you store the answer for that node in an array first.
//	Then, in the future, if you ever again see that node, you simply return
//	the already computed answer instead of redoing all the work.

// Since each node will make at most 2 recursive calls, then this solution will
//	run in time linear in the number of nodes in the graph, O(n). Note that if
//	we don't use memoization (i.e. just write the pure recursive function), then
//	the time needed will be O(2^n), which is much too large for graphs with as
//	many as 1000 nodes.

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class adventure
{

	public static void main(String[] args) throws Exception
	{
		// I like to instantiate my classes this way so that I don't have to use static variables or methods
		new adventure();
	}

	// the number of nodes in the graph
	int N;
	
	// the graph represented as an adjacency list
	ArrayList<Integer>[] GE = new ArrayList[1000];
	
	// the memoization table
	int[] memo = new int[1000];
	
	adventure() throws Exception
	{
		// initialize the adjacency list
		for (int i=0; i<GE.length; ++i) GE[i]=new ArrayList<Integer>();
		
		// open file for reading
		Scanner in = new Scanner(new File("adventure.in"));
		
		// open file for output
		PrintWriter out = new PrintWriter(System.out);//"adventurej.out");
		
		
		// read in the number of test cases and solve each of them
		for (int T=in.nextInt(),ds=1; T-->0; ++ds)
		{
			// get the size of the graph
			N=in.nextInt();
			
			// parse the rest of the this line so that future calls to nextLine() get what we expect
			in.nextLine();
			
			// read in the graph
			for (int i=0; i<N; ++i)
			{
				// clear any old adjacencies from this node (from previous test cases)
				GE[i].clear();
				
				// mark the memo table with -1 to indicate we have not yet solved the problem from this node
				memo[i]=-1;
				
				// get the adjacent vertices from this node and add to adj. list as necessary
				String line = in.nextLine();
				if (!line.equals("ENDING"))
				{
					Scanner sc = new Scanner(line);
					while (sc.hasNextInt())
						GE[i].add(sc.nextInt()-1); // subtract 1 from each page for 0-based indexing
				}
			}
			
			// get the longest path starting from page 1 (using 0-based indexing)
			int res = go(0);
			
			// print the result
			out.printf("Book #%d: The longest story is %d pages.%n",ds,res);
		}
		
		// close output file
		out.close();
	}

	// Input: node x of the graph
	// Output: the longest path in the graph starting at node x
	int go(int x)
	{
		// if we've already solved the problem from this node, just return the computed value
		if (memo[x]>-1)
			return memo[x];
		
		// otherwise, we've never seen this node before, so get the longest path from each of its children
		int res=0;
		for (int y : GE[x])
			res=Math.max(res,go(y));
		
		// store the result of this node so that future calls can avoid redoing work
		memo[x]=1+res;
		
		// return the computed result
		return memo[x];
	}

}
