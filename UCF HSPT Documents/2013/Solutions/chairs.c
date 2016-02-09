
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Musical Chairs (chairs)
//
// Author:        Matt Fontaine
// Judge Data:    Marcos Arribas
// C Solution:    Nick Buelich
// Java Solution: Danny Wasserman
// Verifier:      Mike Galletti

#include <stdio.h>

main()
{
   // Open the input file
   FILE* fp = fopen("chairs.in", "r");

   // Read in the number of cases
   int cases = 0;
   fscanf(fp, "%d", &cases);

   // Loop over the cases
   int i = 0;
   for(i = 1; i <= cases; i++) {
      // Read in the number of children and chairs
      int a=0,b=0;
      fscanf(fp, "%d %d", &a, &b);  

      // Output how many children are eliminated during the round
      printf("Round #%d: %d children eliminated\n", i, a-b);
   }

   // Exit
   fclose(fp);
   return 0;
}

