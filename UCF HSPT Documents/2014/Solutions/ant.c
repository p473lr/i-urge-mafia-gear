
/*
Solution Sketch:
This problem is simply asking to calculate the minimum
spanning tree of the network of tunnels. There are many
well studied algorithms on achieving this, this solution
uses Prim's algorithm.

In essence, Prim's algorithm begins with a tree of a single
node. One by one, the cheapest node to add to the tree
is then added (while maintaining a tree structure), 
until the tree contains all N nodes.
*/

#include <stdio.h>
#include <stdlib.h>

#define TRUE 1
#define FALSE 0

// Since the maximum answer is 1000 * 99 = 99 thousand,
// 1 million is an appropriate infinity value.
// Also note: the maximum possible cost the algorithm can
// produce is infinity * h-1, which does not exceed the
// bounds of an integer.
#define infinity 1000000

int main() {
   // Open the input file for reading
   freopen("ant.in", "r", stdin);

   // Read in the number of cases to process
   int numCases, curCase;
   scanf("%d", &numCases);

   // Iterate over each case
   for (curCase = 1; curCase <= numCases; curCase++) {
      int h, t, i, j, answer = 0;

      // Read the number of tunnels
      scanf("%d %d", &h, &t);

      // Initially, we will assume all tunnels are present
      // with an infinite cost. If the final cost is at least
      // infinity, this corresponds to an infeasible solution.
      int cost[h][h];
      for (i = 0; i < h; i++)
         for (j = 0; j < h; j++)
            cost[i][j] = infinity;

      // Read in each tunnel and store it in the adjacency matrix
      for (i = 0; i < t; i++) {
         int x, y, d;
         scanf("%d %d %d", &x, &y, &d);

         // Subtract 1 off each node to get zero-based and then store it
         x--;
         y--;
         cost[x][y] = cost[y][x] = d;
      }

      int visited[h];
      int costToAdd[h];

      // At the beginning of Prim's algorithm, no nodes are added
      // to the set of nodes, and all nodes have an infinite cost
      // to add to the set, except for a single starting node,
      // which has a cost of zero.
      for (i = 0; i < h; i++) {
         visited[i] = FALSE;
         costToAdd[i] = infinity;
      }
      costToAdd[0] = 0;

      // For 'h' iterations, we will add in the cheapest new node
      // to our spanning tree, and update the adding costs of all
      // other nodes.
      for (i = 0; i < h; i++) {
         // Find the cheapest node to add
         int bestNode = -1;
         for (j = 0; j < h; j++)
            if (!visited[j] && 
                (bestNode == -1 || costToAdd[j] < costToAdd[bestNode]))
               bestNode = j;

         // Mark it as visited and add it to the answer
         visited[bestNode] = TRUE;
         answer += costToAdd[bestNode];

         // Update the costs of all nodes adjacent to the new node
         for (j = 0; j < h; j++)
            if(costToAdd[j] > cost[bestNode][j])
               costToAdd[j] = cost[bestNode][j];
      }

      // If the answer is at least infinity, an invalid edge was
      // added. Thus, there is no spanning tree.
      if (answer >= infinity)
         printf("Campus #%d: I'm a programmer, not a miracle worker!\n", 
                curCase);
      else
         printf("Campus #%d: %d\n", curCase, answer);
   }
}

