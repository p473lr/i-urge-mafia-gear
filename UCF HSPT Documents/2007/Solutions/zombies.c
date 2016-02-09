
#include <stdio.h>

#define debug 0
#define debug_print if( debug ) printf


typedef enum {
   FALSE = 0,
   TRUE = 1
} boolean;


void showGrid( unsigned w, unsigned h, boolean grid[30][30], unsigned x, unsigned y );


int main()
{
   /*
    * Open the input file. We know it's named zombie.in and
    * it's in the current directory. Use a stdio file handle
    * fin for the input file.
    */
   FILE* fin = fopen( "zombies.in", "r" );

   /*
    * The number of levels to process.
    */
   unsigned numLevels;

   /*
    * Indicates the current game level.
    */
   unsigned level;

   /*
    * Stores the height and width of the level.
    */
   unsigned width;
   unsigned height;

   /*
    * Stores the number of zombies that have been vanquished.
    */
   unsigned numZombies;

   /*
    * We'll use a 2-dimensional array of boolean values to store which
    * locations on the playing field have zombies. The limit on width
    * and height is 25, but we'll make ours a little bigger just to be
    * on the safe side. (This will also help using a coordinate system
    * starting at 1 instead of 0.)
    */
   boolean zombiePresence[30][30];

   /*
    * All x,y coordinates are stored here.  This is used to initialize
    * the zombiePresence and then move Crystel around.
    */
   unsigned x, y;

   /*
    * For-loop indices.  They are used to read the zombiePresence matrix,
    * the zombies, and the movements.
    */
   unsigned i, j;

   /*
    * Stores the number of steps Crystel will take.
    */
   unsigned numSteps;

   /*
    * The direction of a step.
    */
   char direction;

   /*
    * Read in the first line, the number of game levels.
    */
   fscanf( fin, "%u\n", &numLevels );
   debug_print( "### numLevels=%u\n", numLevels );

   /*
    * Use a loop to read each level, and process as we go.
    */
   for( level = 1; level <= numLevels; ++level )
   {
      /*
       * Read in the width and height of the playing area for this level
       */
      width = 0;
      height = 0;
      fscanf( fin, "%u %u\n", &width, &height );
      debug_print( "### width=%u, height=%u\n", width, height );

      /*
       * Start by setting all of our positions to 'false' (ie. no zombie
       * presence at each location).
       */
      for( i = 0; i <= width; ++i )
      {
         for( j = 0; j <= height; ++j )
         {
            zombiePresence[i][j] = FALSE;
         }
      }
      showGrid( width, height, zombiePresence, 0, 0 );

      /*
       * Read in the number of zombies for this level.
       */
      numZombies = 0;
      fscanf( fin, "%u\n", &numZombies );
      debug_print( "### numZombies=%u\n", numZombies );

      /*
       * Read in the zombie locations, and add them to the grid as we go.
       */
      for( i = 0; i < numZombies; ++i )
      {
         /*
          * Read a zombie x and y position.
          */
         x = 0;
         y = 0;
         fscanf( fin, "%u %u\n", &x, &y );
         debug_print( "### zombie #%u: x=%u, y=%u\n", i, x, y );

         // added by Nick to verify data
         if(x > width || x < 1 || y > height || y < 1 || zombiePresence[x][y] == TRUE)
            fprintf(stderr, "BAD DATA!\n");

         /*
          * Set the zombie presence at that location to 'true'.
          */
         zombiePresence[x][y] = TRUE;
         showGrid( width, height, zombiePresence, 0, 0 );
      }

      /*
       * Read in the number of steps Crystel will take on this level.
       */
      fscanf( fin, "%u\n", &numSteps );
      debug_print( "### numSteps=%u\n", numSteps );

      /*
       * Crystel starts in the southwest corner, with x and y positions both 1.
       * She also starts with zero zombies vanquished for each level.
       */
      x = 1;
      y = 1;
      numZombies = 0;

      showGrid( width, height, zombiePresence, x, y );

      /*
       * Is there a zombie at Crystel's starting position? Shoot it!
       */
      if( zombiePresence[x][y] )
      {
         /* KA-BLAM!! Vanquish the zombie */
         zombiePresence[x][y] = FALSE;
         /* record the kill! */
         ++numZombies;
      }

      /*
       * Use a loop to read and process each of Crystel's steps on this level.
       */
      for( i = 0; i < numSteps; ++i)
      {
         /*
          * Read in the direction of the step.
          */
         char direction = 'X';
         fscanf( fin, "%c\n", &direction );
         debug_print( "### direction=%c\n", direction );

         /*
          * Change Crystel's x or y position based on which direction she steps.
          */
         if( 'N' == direction ) ++y;
         else if( 'S' == direction ) --y;
         else if( 'E' == direction ) ++x;
         else if( 'W' == direction ) --x;
         else fprintf(stderr, "BAD DATA!\n");

         // added by Nick to verify data
         if(x > width || x < 1 || y > height || y < 1)
            fprintf(stderr, "BAD DATA!\n");

         showGrid( width, height, zombiePresence, x, y );

         /*
          * Is there a zombie at Crystel's new position? Shoot it!
          */
         if( zombiePresence[x][y] )
         {
            /* KA-BLAM!! Vanquish the zombie */
            zombiePresence[x][y] = FALSE;
            /* record the kill! */
            ++numZombies;
         }
      }

      /*
       * Crystel is done with the level! Output the number of kills.
       */
      printf( "Level %u: Crystel vanquished %u zombies.\n", level, numZombies );
   }
   fclose( fin );

   return 0;
}


/*
 * This function is not needed to solve the problem, but can be helpful
 * when debugging the program.
 */
void showGrid( unsigned w, unsigned h, boolean grid[30][30], unsigned x, unsigned y )
{
   unsigned j, i;

   if( !debug ) return;

   for( j = h; j >= 1; --j )
   {
      printf( "###" );
      for( i = 1; i <= w; ++i )
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

