
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: You're Too Slow! (slow)
//
// Author:        Aaron Teetor
// Judge Data:    Antony Stabile
// C Solution:    Glenn Martin
// Java Solution: Danny Wasserman
// Verifier:      Patrick Fenelon

#include <stdio.h>
#include <math.h>


#define DEBUG   0

#define TRUE    1
#define FALSE   0


typedef struct
{
   int   x;
   int   y;
   int   enemies;
} Room;


int episodeLength(int episodeNum, int totalEpisodes)
{
   // Season finale has 30 minutes; others have 22
   if (episodeNum == totalEpisodes)
      return 30;
   else
      return 22;
}


double distance(Room r1, Room r2)
{
   double   d1;
   double   d2;

   // Compute the distance between the rooms and return it
   d1 = r1.x - r2.x;
   d2 = r1.y - r2.y;
   return sqrt(d1*d1 + d2*d2);
}


int main()
{
   FILE *   infile;
   int      numSims;
   int      i;
   int      numEpisodes;
   int      numRooms;
   int      j;
   Room     rooms[1000];
   double   timeInEpisode;
   int      location;
   int      episode;
   int      ok;
   int      episodeLen;
   double   travelTime;
   int      numEnemies;
   double   dist;

   // Open the input file for reading
   infile = fopen("slow.in", "r");

   // Read in the number of simulations to process
   fscanf(infile, "%d", &numSims);

   // Loop over each simulation
   for (i=0; i < numSims; i++)
   {
      // Read in the number of episodes left and number of rooms that exist
      fscanf(infile, "%d %d", &numEpisodes, &numRooms);

      // Read in the room data
      for (j=0; j < numRooms; j++)
      {
         fscanf(infile, "%d %d %d", &rooms[j].x, &rooms[j].y, 
                &rooms[j].enemies);
      }

      // Initialing starting state (time 0 into episode, first room, 
      // first episode)
      timeInEpisode = 0;
      location = 0;
      episode = 1;

      // Assume we're okay until we find that we aren't or we finished
      ok = TRUE;
      while ( (ok == TRUE) && (location < numRooms) )
      {
         if (DEBUG)
            printf("AT ROOM %d\n", location+1);

         // Initialize how long the current episode is
         episodeLen = episodeLength(episode, numEpisodes);

         // First, defeat enemies in this room (if we have shows left)
         numEnemies = rooms[location].enemies;
         while ( (numEnemies > 0) && (episode <= numEpisodes) )
         {
            // Do we have time to defeat the enemy?
            if (episodeLen - timeInEpisode < 3)
            {
               // No, we do not so we have to wait for an episode where
               // we have time
               while ( (episodeLen - timeInEpisode < 3) && 
                       (episode <= numEpisodes) )
               {
                  // Go to start of next episode
                  timeInEpisode = 0;
                  episode++;
                  episodeLen = episodeLength(episode, numEpisodes);
               }
            }
               
            // Now, defeat the enemy!
            if (DEBUG)
               printf("Defeated enemy from %f to %f\n", timeInEpisode,
                      timeInEpisode + 3);
            timeInEpisode += 3;
            numEnemies--;
         }

         // We defeated everybody in the room so it's time to go to the
         // next room (if it exists)
         if (location < numRooms - 1)
         {
            // Get distance to next room
            dist = distance(rooms[location], rooms[location+1]);
            if (DEBUG)
               printf("Need to travel %f km\n", dist);

            // Determine how long it will take to get there (account for
            // acceleration time)
            if (dist > 10)
            {
               // Sonic will accelerate to his constant maximum speed
               travelTime = 1.0 + (dist - 10) / 20;
            }
            else
            {
               // Sonic arrives while he's still accelerating
               travelTime = dist / 10;
            }
            if (DEBUG)
               printf("It will take %f minutes\n", travelTime);

            // Now, that we now how long it will take to get to the next
            // room, use time in the episodes to do the travel
            while ( (episodeLen - timeInEpisode < travelTime) && 
                    (episode <= numEpisodes) )
            {
               // Use up what's left of this episode
               if (DEBUG)
                  printf("Traveling for %f minutes\n", 
                         (episodeLen - timeInEpisode));
               travelTime -= (episodeLen - timeInEpisode);
               timeInEpisode = 0;
               episode++;
               episodeLen = episodeLength(episode, numEpisodes);
            }

            // And finish leftover travel in this episode if there is any left
            if (travelTime > 0)
            {
               // Finish the travel in the time we need
               if (DEBUG)
                  printf("Traveling for %f minutes\n", travelTime);
               timeInEpisode += travelTime;
               travelTime = 0;
            }
         }

         // Either way, we need to advance to the next room
         location++;

         // Check to see if we ran out of episodes
         if (DEBUG)
             printf("Finished at %f minutes into episode %d\n", timeInEpisode,
                    episode);
         if (episode > numEpisodes)
         {
            // We did, uh oh!
            ok = FALSE;
         }
      }

      // Output accordingly
      if (ok == TRUE)
         printf("Simulation #%d: Abort fortress!\n", i+1);
      else
         printf("Simulation #%d: You're too slow!\n", i+1);
   }

   // Return that we're ending okay
   fclose(infile);
   return 0;
}

