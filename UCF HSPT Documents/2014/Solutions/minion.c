
#include <stdio.h>
#include <stdlib.h>

int main()
{
   int x, i; 
   double m1, t1, m2;

   // Open file for reading input.
   FILE* ifp = fopen("minion.in", "r");
 
   // Read number of test cases.
   fscanf(ifp, "%d", &x);

   // Iterate over test cases.
   for (i = 0; i < x; i++)
   {
      // Read m1, t1, and m2.
      fscanf(ifp, "%lf %lf %lf", &m1, &t1, &m2);

      // Take the two equations, re-arrange terms to get answer.
      // t1 = k / m1, k = t1 * m1
      // t2 = k / m2, t2 = t1 * m1 / m2 
      printf("Scenario #%d: %.2f\n", i + 1, (m1 * t1) / m2);
   }

   // Close file and return.
   fclose(ifp);
   return 0;
}
