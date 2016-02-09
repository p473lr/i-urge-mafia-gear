
#include <stdio.h>
#include <stdlib.h>

int main()
{
   // Open the input file.
   FILE* ifp;
   ifp = fopen("awesome.in", "r");

   // Read number of test cases.
   int t;
   fscanf(ifp, "%d", &t);

   // Iterate over test cases.
   int i;
   for (i = 0; i < t; i++)
   {
      // Read input string.
      char s[50];
      fscanf(ifp, "%s", &s);

      // Output formatted string as per problem.
      printf("Item #%d: %s! It's awesome!\n", i + 1, s);
   }

   // Close file and return.
   fclose(ifp);
   return 0;
}
