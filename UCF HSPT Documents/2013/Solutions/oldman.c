
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Old Man of the Mountain (oldman)
//
// Author:        Glenn Martin & Matt Fontaine
// Judge Data:    Glenn Martin
// C Solution:    Glenn Martin
// Java Solution: Glenn Martin
// Verifier:      Glenn Martin

#include <stdio.h>


int main()
{
   FILE *   infile;
   int      numSites;
   int      i;
   int      m1, m2, m3;

   // Open the input file
   infile = fopen("oldman.in", "r");

   // Get number of sites to test
   fscanf(infile, "%d", &numSites);

   // Loop over the sites
   for (i=0; i < numSites; i++)
   {
      // Get the three measurements
      fscanf(infile, "%d %d %d", &m1, &m2, &m3);

      // Test to see if they match
      printf("Set #%d: ", i+1);
      if ( (m1 == m2) && (m1 == m3) && (m2 == m3) )
         printf("Sorry, too flat.\n");
      else
         printf("Shows potential!\n");
   }

   // Done
   fclose(infile);
   return 0;
}

