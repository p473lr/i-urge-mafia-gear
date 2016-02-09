
#include <stdio.h>
#include <stdlib.h>

int main()
{
   // Open the input file.
   FILE * ifp = stdin;

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
      printf("Word #%d: How do you %s?\n", i + 1, s);
   }

   // Return.
   return 0;
}
