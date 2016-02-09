
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: The 3,155,760,000 Seconds of This Century (parody)
//
// Author:        Matt Fontaine & Glenn Martin
// Judge Data:    Glenn Martin
// C Solution:    Glenn Martin
// Java Solution: Glenn Martin
// Verifier:      Glenn Martin

#include <stdio.h>


int main()
{
   FILE *   infile;
   int      numSongs;
   int      stanza;
   int      i;
   int      j;

   // Open the input file
   infile = fopen("parody.in", "r");

   // Get number of songs to sing
   fscanf(infile, "%d", &numSongs);

   // Loop over the songs
   for (i=0; i < numSongs; i++)
   {
      // Get the most recent stanza sung
      fscanf(infile, "%d", &stanza);

      // Sing the rest by counting down to 1
      printf("Song #%d: ", i+1);
      for (j=stanza-1; j > 0; j--)
         printf("%d ", j);
      printf("\n");
   }

   // Done
   fclose(infile);
   return 0;
}

