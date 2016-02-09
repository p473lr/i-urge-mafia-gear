
#include <stdio.h>

#define debug 0
#define debug_print if( debug ) printf

void showGrid( unsigned w, unsigned h, bool grid[30][30], unsigned x, unsigned y );

int main()
{
   /*
    * Open the input file. We know it's named zombies.in and
    * it's in the current directory. Use a stdio file handle
    * fin for the input file.
    */
   FILE* fin = fopen( "zombies.in", "r" );

   /*
    * Read in the first line, the number of game levels.
    */
   unsigned numLevels = 0; 
   fscanf( fin, "%u\n", &numLevels );
   debug_print( "### numLevels=%u\n", numLevels );

   /*
    * Use a loop to read each level, and process as we go.
    */
   for( unsigned level = 1; level <= numLevels; ++level )
   {
      /*
       * Read in the width and height of the playing area for this level
       */
      unsigned width = 0;
      unsigned height = 0;
      fscanf( fin, "%u %u\n", &width, &height );
      debug_print( "### width=%u, height=%u\n", width, height );

      /*
       * We'll use a 2-dimensional array of boolean values to store which
       * locations on the playing field have zombies. The limit on width
       * and height is 25, but we'll make ours a little bigger just to be
       * on the safe side. (This will also help using a coordinate system
       * starting at 1 instead of 0.)
       */
      bool zombiePresence[30][30];

      /*
       * Even though C++ is supposed to zero out memory for us, we'll be
       * safe and set all of our positions to 'false' (ie. no zombie
       * presence at that location).
       */
      for( unsigned xInit = 0; xInit <= width; ++xInit )
      {
         for( unsigned yInit = 0; yInit <= height; ++yInit )
         {
            zombiePresence[xInit][yInit] = false;
         }
      }
      showGrid( width, height, zombiePresence, 0, 0 );

      /*
       * Read in the number of zombies for this level.
       */
      unsigned numZombies = 0;
      fscanf( fin, "%u\n", &numZombies );
      debug_print( "### numZombies=%u\n", numZombies );

      /*
       * Read in the zombie locations, and add them to the grid as we go.
       */
      for( unsigned zombie = 0; zombie < numZombies; ++zombie )
      {
         /*
          * Read a zombie x and y position.
          */
         unsigned xZombie = 0;
         unsigned yZombie = 0;
         fscanf( fin, "%u %u\n", &xZombie, &yZombie );
         debug_print( "### zombie #%u: x=%u, y=%u\n", zombie, xZombie, yZombie );

         /*
          * Set the zombie presence at that location to 'true'.
          */
         zombiePresence[xZombie][yZombie] = true;
         showGrid( width, height, zombiePresence, 0, 0 );
      }

      /*
       * Read in the number of steps Crystal will take on this level.
       */
      unsigned numSteps = 0;
      fscanf( fin, "%u\n", &numSteps );
      debug_print( "### numSteps=%u\n", numSteps );

      /*
       * Crystal starts in the southwest corner, with x and y positions both 1.
       * She also starts with zero zombies vanquished for each level.
       */
      unsigned xCrystal = 1;
      unsigned yCrystal = 1;
      unsigned numVanquished = 0;

      showGrid( width, height, zombiePresence, xCrystal, yCrystal );

      /*
       * Is there a zombie at Crystal's starting position? Shoot it!
       */
      if( zombiePresence[xCrystal][yCrystal] )
      {
         /* KA-BLAM!! Vanquish the zombie */
         zombiePresence[xCrystal][yCrystal] = false;
         /* record the kill! */
         ++numVanquished;
      }

      /*
       * Use a loop to read and process each of Crystal's steps on this level.
       */
      for( unsigned step = 0; step < numSteps; ++step )
      {
         /*
          * Read in the direction of the step.
          */
         char direction = 'X';
         fscanf( fin, "%c\n", &direction );
         debug_print( "### direction=%c\n", direction );

         /*
          * Change Crystal's x or y position based on which direction she steps.
          */
         if( 'N' == direction ) ++yCrystal;
         if( 'S' == direction ) --yCrystal;
         if( 'E' == direction ) ++xCrystal;
         if( 'W' == direction ) --xCrystal;

         showGrid( width, height, zombiePresence, xCrystal, yCrystal );

         /*
          * Is there a zombie at Crystal's new position? Shoot it!
          */
         if( zombiePresence[xCrystal][yCrystal] )
         {
            /* KA-BLAM!! Vanquish the zombie */
            zombiePresence[xCrystal][yCrystal] = false;
            /* record the kill! */
            ++numVanquished;
         }
      }

      /*
       * Crystal is done with the level! Output the number of kills.
       */
      printf( "Level %u: Crystel vanquished %u zombies.\n", level, numVanquished );
   }
   fclose( fin );

   return 0;
}


/*
 * This function is not needed to solve the problem, but can be helpful
 * when debugging the program.
 */
void showGrid( unsigned w, unsigned h, bool grid[30][30], unsigned x, unsigned y )
{
   if( !debug ) return;

   for( unsigned j = h; j >= 1; --j )
   {
      printf( "###" );
      for( unsigned i = 1; i <= w; ++i )
      {
         if( x > 0 && y > 0 && x == i && y == j )
         {
            printf( "{%c}", grid[i][j] ? 'Z' : 'c' );
         }
         else
         {
            printf( " %c ", grid[i][j] ? 'Z' : '-' );
         }
      }
      printf( "\n" );
   }
}

