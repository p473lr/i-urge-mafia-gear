
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


// There are potenially several ways to solve this problem.  This solution
// simply fills a two dimensional boolean array representing whether or
// not a commercial is on for each minute of each station's programming.
// Then, we traverse the array, checking each minute to see if two or more
// stations are playing commercials at that time.
//
// This is probably not the most efficient solution in terms of time or
// memory used, but it is a simple, straightforward way to solve the
// problem.


int main(void)
{
   FILE *fp;
   char line[256];
   int numSessions, numStations;
   bool commercials[16][384];
   int i, j;
   int commercialGap, commercialLength;
   int minute;
   int commercialCount;
   int sessionNum;
   int pureCramming;
   
   // Open the input file
   fp = fopen("radio.in", "r");

   // Get the number of sessions to check
   fgets(line, sizeof(line), fp);
   sscanf(line, "%d", &numSessions);

   // For each session...
   for (sessionNum = 1; sessionNum <= numSessions; sessionNum++)
   {
      // Clear the commercials array to all zeroes (all false values)
      memset(commercials, 0, sizeof(commercials));

      // Get the number of stations
      fgets(line, sizeof(line), fp);
      sscanf(line, "%d", &numStations);

      // For each station...
      for (i = 0; i < numStations; i++)
      {
         // Read the station's schedule and set up the commercial
         // schedule in our array
         fgets(line, sizeof(line), fp);
         commercialGap = atoi(strtok(line, " \n"));
         commercialLength = atoi(strtok(NULL, " \n"));

         // Start at minute zero (6:00 PM)
         minute = 0;

         // Update the schedule all the way through midnight
         while (minute < 360)
         {
            // Skip the commercial gap (the amount of time between
            // commercials)
            minute += commercialGap;

            // Now, iterate through the array for commercialLength
            // minutes, and change the entries to true (there is a
            // commercial on at this time).  Keep track of the overall
            // minute as we go, so we don't overrun our array.
            j = 0;
            while ((j < commercialLength) && (minute < 360))
            {
               // Change this minute's entry to true
               commercials[i][minute] = true;

               // Increment the commercial time counter as well as the
               // overall minute counter
               minute++;
               j++;
            }
         }
      }

      // Now that the array is set up, scan it minute by minute to see
      // how many minutes of pure cramming we have
      pureCramming = 0;
      for (minute = 0; minute < 360; minute++)
      {
         // Start with zero commercial on for this minute
         commercialCount = 0;

         // Check each station for commercials
         for (i = 0; i < numStations; i++)
         {
            // If there's a commercial on this station, increment the
            // commercial count
            if (commercials[i][minute])
               commercialCount++;
         }

         // If there are two or more commercials on at this time, the
         // radio is off and pure cramming ensues
         if (commercialCount >= 2)
            pureCramming++;
      }

      // Now, we can print the output
      printf("Study session #%d has %d minute(s) of pure cramming."
         "  Excellent!\n", sessionNum, pureCramming);
   }
}
