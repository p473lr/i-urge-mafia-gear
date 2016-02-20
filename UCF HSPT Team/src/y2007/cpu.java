import java.util.*;
import java.io.*;

/* *****************************************
 * Problem: Cheaper processing units
 * 
 * This problem is what is known as the minimum spanning tree problem.
 * A graph is a collection of vertices (i.e. processor components) and
 * edges which are the interconnections between those vertices. The minimum
 * spanning tree problem is that of finding a collection of edges in a graph
 * that allow every vertex to be reachable from any other vertex, but 
 * minimizes the total weight of all edges. There are many algorithms for
 * solving this problem. I just happened to choose Kruskal's algorithm
 * because it's highly efficient, and I personally find it comparatively easy
 * to code.  
 *
 * (Note that the terms 'vertex' and 'node' are used interchangably in the comments) 
 *******************************************/


public class cpu {
	public static void main(String[] args) throws IOException
	{
		// Open the input file for reading
		Scanner fin = new Scanner(new File("cpu.in"));
		
		int designs = fin.nextInt();
		
		// Loop through each data case		
		for(int design = 1; design <= designs; design++)
		{
			int nodes = fin.nextInt();
			
			// Get all of the edges out of the input file
			// Storing them in a PriorityQueue makes it easy to find the
			// smallest edge efficiently
			PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
			for(int i=0; i<nodes; i++)
			{
				for(int j=0; j<nodes; j++)
				{
					int weight = fin.nextInt();
					if(i != j)
						pq.add(new Edge(i,j,weight));
				}
			}
			
			// I have Kruskal's algorithm set up to only return the total
			// distance 
			int answer = kruskal(pq, nodes);
			System.out.println("Design "+design+": "+answer+" micrometers");
		}
	}
	
	/* *****************************************
	 * 
	 * Kruskal's algorithm in brief:
	 * 
	 * 1) Choose the smallest edge that connects two vertices that were
	 * 		previously unreachable from each other and add it to the
	 * 		minimum spanning tree we're building.
	 * 2) Repeat step 1 until every vertex is reachable from any other.
	 * 
	 *******************************************/
	public static int kruskal(PriorityQueue<Edge> pq, int nodes)
	{
		int components = nodes;
		int cost = 0;
		
		// I make use of a Disjoint Sets data structure using the union-find 
		// algorithm to determine whether two nodes are reachable from one
		// another
		DisjointSets sets = new DisjointSets(nodes);
		
		// Repeat until every node can reach every other node
		while(components > 1)
		{
			Edge e = pq.poll();
			
			// If the smallest remaining edge connects two vertices that can
			// already get to each other, just ignore that edge and move on
			if(sets.find(e.end1) == sets.find(e.end2))
				continue;
			
			// Combine the components containing the endpoints of the edge into
			// a single component
			sets.union(e.end1, e.end2);
			components--;
			cost += e.weight;
		}
		return cost;
	}
}

/* ***************************************
 * The purpose of the union-find algorithm:
 * 
 * Union-find operates on disjoint sets (i.e. clusters of processor units) and
 * consists of two operations, union and find. The union operation combines
 * two disjoint sets into one. The find operation gives a "canonical element"
 * of a set. What this means is that if two vertices belong to the same set,
 * find is guaranteed to return the same thing when called on each of them.
 * 
 * Union-find has very limited applications, but it is blindingly fast when
 * implemented correctly.
 *****************************************/
class DisjointSets
{
	// For each node, store a link to another node which may or may not be
	// the canonical element of the set. Every node that points to itself is
	// a canonical element. All other nodes can find their canonical element
	// by following the (typically very short) chain of links
	private int[] parent;
	public DisjointSets(int size)
	{
		// Initially, every vertex is in a set by itself.
		parent = new int[size];
		for(int i=0;i<size;i++)
			parent[i] = i;
	}
	
	// The find algorithm, in addition to returning the canonical element of
	// the set containing node x, also makes all nodes along x's path to the
	// canonical element point at the canonical element. That way, the next
	// time find is called on any of those nodes, it will be really fast.
	public int find(int x)
	{
		if(parent[x] == x)
			return x;
		parent[x] = find(parent[x]);
		return parent[x];
	}
	
	// Union combines two sets by making the canonical element of one point at
	// the canonical element of the other.
	public void union(int x, int y)
	{
		parent[find(x)] = find(y);
	}
}

/* ********************************
 * This class represents an edge in a graph. I've defined edges to be 
 * comparable to each other in such a way that shorter edges come first when
 * sorting them. 
 **********************************/
class Edge implements Comparable<Edge>
{
	public int weight;
	public int end1;
	public int end2;
	
	// Constructor. Direction doesn't matter for these edges, so for 
	// simplicity, we just make sure that end1 is the smaller endpoint.
	public Edge(int e1, int e2, int w)
	{
		weight = w;
		if(e1<e2)
		{
			end1 = e1;
			end2 = e2;
		}
		else
		{
			end1 = e2;
			end2 = e1;
		}
	}
	
	// This compareTo method looks at the weight of the edge first,
	// then uses arbitrary criteria to distinguish them if they have
	// the same weight. A negative return value indicates that this edge
	// comes before rhs, positive means that this comes after, and 0 means
	// that they're the same edge.
	public int compareTo(Edge rhs)
	{
		if(weight != rhs.weight)
			return weight - rhs.weight;
		if(end1 != rhs.end1)
			return end1 - rhs.end1;
		return end2 - rhs.end2;
	}
}
