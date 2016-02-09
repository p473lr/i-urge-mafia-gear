
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Global variables
int junctions[512][512];
int numJunctions;

// A number big enough to represent infinity for this problem
#define INFINITY 10000


// To solve this problem, we use a wonderful algorithm known as 
// Floyd-Warshall's All Shortest Paths.  This lets us find the
// shortest path (the path of least resistance) from any junction to any
// other.  Remember that we're looking for short circuits in the design.
// Since a short circuit has a resistance of zero, it will always be the
// path of least resistance between any two junctions, and will always
// show up when we use Floyd-Warshall's algorithm.
//
// We start with the original circuit design matrix.  Each entry (i,j) in
// this matrix indicates the length (or resistance in this case) of the
// direct connection between junctions i and j.
// After running Floyd-Warshall's on this matrix, it will contain the
// length of the shortest path (or the path of least resistance in this
// case) between junctions i and j.  This path may still go directly
// from i to j, or it may go through several other junctions to get
// from i to j.
//
// Note that this algorithm is a bit of overkill for this problem, since
// it gives us the shortest path for every pair of junctions, and we only
// care about the path from junction 0 to junction 1.  However, 
// Floyd-Warshall's is the simplest shortest paths algorithm to implement
// in code (which makes it good to use in contest programming), and it works
// quite well, as long as the problem isn't too large (with only 500 junctions
// maximum, this problem is plenty small enough).
//
void floyd()
{
   int i, j, k;

   // We have to do a simple preprocessing step here.  The -1's in the
   // circuit design matrix will cause this algorithm to fail without a
   // lot of messy code to handle this special case. To fix this, we'll
   // convert the -1's to a large number.  You can think of this number as
   // representing infinite resistance, which is consistent with the lack
   // of a connection between two junctions.
   for (i = 0; i < numJunctions; i++)
      for (j = 0; j < numJunctions; j++)
         if (junctions[i][j] < 0)
            junctions[i][j] = INFINITY;

   // This outer loop (k) will test paths going from junction i to junction
   // j that go through junction k.
   for (k = 0; k < numJunctions; k++)
   {
      // The inner loops simply iterate over all junctions, testing for
      // less resistant paths from i to j
      for (i = 0; i < numJunctions; i++)
      {
         for (j = 0; j < numJunctions; j++)
         {
            // If there is a path from i to j going through k that is
            // less resistant than any path we've found so far, remember
            // the resistance along this new path
            if (junctions[i][k] + junctions[k][j] < junctions[i][j])
               junctions[i][j] = junctions[i][k] + junctions[k][j];
         }
      }
   }
}


int main(void)
{
   FILE *fp;
   char line[256];
   char *token;
   int  numDesigns;
   int  designNum;
   int  i, j;

   // Open the input file
   fp = fopen("roboto.in", "r");

   // Read the number of designs in the input
   fgets(line, sizeof(line), fp);
   sscanf(line, "%d", &numDesigns);

   // Process each design
   for (designNum = 1; designNum <= numDesigns; designNum++)
   {
      // Read in the design, first getting the number of junctions
      fgets(line, sizeof(line), fp);
      sscanf(line, "%d", &numJunctions);
      for (i = 0; i < numJunctions; i++)
      {
         // Get the next line of resistance values
         fgets(line, sizeof(line), fp);

         // Read the first resistance value from this line
         token = strtok(line, " \n");
         junctions[i][0] = atoi(token);

         // Read the rest of the resistances off of this line
         for (j = 1; j < numJunctions; j++)
         {
            token = strtok(NULL, " \n");
            junctions[i][j] = atoi(token);
         }
      }

      // Compute all paths of least resistance for this design, using
      // Floyd-Warshall's algorithm
      floyd();

      // Now, check the resistance on the path from the first junction
      // to the second, and print the results
      if (junctions[0][1] == 0)
         printf("Circuit Design #%d: Back to the drawing board\n", designNum);
      else
         printf("Circuit Design #%d: No more hedgehog troubles\n", designNum);
   }
}
