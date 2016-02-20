
import java.io.*;
import java.util.*;

class Edge implements Comparable<Edge>{
   // Every edge has a start point, end point, and weight
   public int start, end, weight;
   
   public Edge(int s, int e, int w) {
      start = s; end = e; weight = w;
   }
   
   // To minimize the Minimum Spanning Tree, sort the edges by weight
   public int compareTo(Edge e) {
      return this.weight - e.weight;
   }
}


// Using the Minimum Spanning Tree algorith we employ, we will need a class
// to manage Disjoint Sets (see Wikipedia)
class DisjointSet {
   public int[] p, r;

   public DisjointSet(int v) {
      // Make space for tracking parents and roots for each node
      p = new int[v];
      r = new int[v];

      // In the beginning, all elements are in disjoint sets
      for(int i = 0; i < v; i++) p[i] = i;
   }

   // Union two elements
   public void union(int x, int y) {
      // Find the parents of the elements
      int a = find(x);
      int b = find(y);
      
      // If they have the same parent, the two elements are already 
      // in the same set
      if(a == b) return;

      // Otherwise, choose which to make the root, based on height
      if (r[a] == r[b]) 
         r[p[b] = a]++;
      else 
         p[a] = p[b] = r[a] < r[b] ? b : a;
   }
   
   // Finds the parent / root of the element
   public int find(int x) {
      return p[x] = p[x] == x ? x : find(p[x]);
   }
}


class Kruskal {
   // Stores edges in sorted order as they're added in (to minimize 
   // Minimum Spanning Tree)
   public PriorityQueue<Edge> pq;

   // Stores actual MST
   public ArrayList<Edge> MST;

   // Disjoint set used to union edges in the graph
   public DisjointSet ds;
   public int numE = 0, numV, cost = 0;
   
   public Kruskal(int v) {
      // Initialize everything
      numV = v;
      ds = new DisjointSet(v);
      pq = new PriorityQueue<Edge>();
      MST = new ArrayList<Edge>();
   }
   
   // Add an edge to the graph (original, not MST)
   public void addEdge(int src, int dst, int cst) {
      pq.add(new Edge(src, dst, cst));
      numE++;
   }
   
   // Run Kruskal's algorithm to obtain MST
   public void kruskal() {
      // Can add at most numV-1 edges to the MST
      for (int found = 0; found < numV-1;) {
         // No more edges to add (avoids null pointer if we run 
         // out of edges to add and don't have an MST yet)
         if (pq.isEmpty()) 
            break;
         
         // Remove the best edge we could potentially add
         Edge cur = pq.poll();
         
         // Try to union the start and end nodes of this edge
         if (ds.find(cur.start-1) != ds.find(cur.end-1)) {
            // If they're not already in the same set, they belong to the MST
            ds.union(cur.start-1, cur.end-1);
            MST.add(cur);
            
            // Keep track of the cost needed to add in this edge
            cost += cur.weight;

            // Keep track of how many edges we've added to the MST
            found++;
         }
      }
   }
}


public class ant {
   public static void main(String [] args) throws IOException {
      // Open the input file
      Scanner in = new Scanner(new File("ant.in"));
 
      // Read in number of cases (campuses)
      int numCampus = in.nextInt();
      
      // Loop over the cases
      for (int nc = 1; nc <= numCampus; nc++) {
         // Read in the number of hills and number of tunnels
         int numHills = in.nextInt(); 
         int numTunnels = in.nextInt(); 
         
         // Create class that will be used to run Kruskal's algorithm 
         // to build the Minimum Spanning Tree (MST)
         Kruskal k = new Kruskal(numHills);
         
         // Read all the edges and store them
         for (int i = 0; i < numTunnels; i++)
            k.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
         
         // Now run Kruskal's
         k.kruskal();
         
         // All hills are connected iff the number of edges in 
         // the MST + 1 equals the original number of hills (nodes)
         /* Ex: 1 --> 2   numHill = 4
                      |   size of MST = 3 (1 --> 2 is one edge using two nodes, 
                      |                    so the last node never gets counted;
                      V                    hence + 1)
                4 <-- 3
          
         */
         System.out.println("Campus #" + nc + ": " + 
            (k.MST.size()+1 == numHills ? k.cost : 
               "I'm a programmer, not a miracle worker!"));
      }

      // Close file
      in.close();
   }
}

