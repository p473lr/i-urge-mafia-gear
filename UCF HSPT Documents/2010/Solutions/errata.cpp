
#include <stdio.h>


int main(void)
{
   FILE *   fp;
   int      contestNum;
   int      numContests;
   int      numErrata;


   // Open the input file
   fp = fopen("errata.in", "r");

   // Read the number of contests
   fscanf(fp, "%d", &numContests);

   // Check each contest in the file
   for (contestNum = 1; contestNum <= numContests; contestNum++)
   {
      // Read the number of errata
      fscanf(fp, "%d", &numErrata);

      // Print the appropriate output
      if (numErrata == 0)
         printf("Contest #%d: No errata!\n", contestNum);
      else
         printf("Contest #%d: Not perfect, errata issued!\n", contestNum);
   }

   // Close the file before we go
   fclose(fp);

   // Return
   return 0;
}

