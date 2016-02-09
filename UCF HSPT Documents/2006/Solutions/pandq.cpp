/**
 * University of Central Florida
 * 20th Annual High School Programming Tournament
 *
 * Judges' Solution: "Mind Your P's And Q's" (pandq)
 * Language: C/C++
 */

#include <stdio.h>

int main()
{
   /*
    * Open the input file. We know it's named pandq.in and
    * it's in the current directory. Use a stdio file handle
    * fin for the input file.
    */
   FILE* fin = fopen( "pandq.in", "r" );

   /*
    * Loop through the input until we reach the end of input,
    * as specified in the input specification.
    */
   while( !feof( fin ) )
   {
      /*
       * Declare a memory buffer for input characters and
       * call it 'line' since we'll be reading the file one
       * line at a time. 
       * Note the buffer is big enough to hold the longest
       * possible line of input (70 characters).
       */
      char line[1000];

      /*
       * Read a line from the input file into our 'line' memory
       * location. The fscanf function from the stdio library will
       * read to end-of-line or to end-of-file.
       */
      fscanf( fin, "%s", line );

      /*
       * Declare two integers. These will be used to count the
       * P's and Q's, respectively.
       */
      int numP = 0;
      int numQ = 0;

      /*
       * Step through each character position in the line, starting
       * at position 0, until we reach a  memory location containing
       * zero. For each character position, if it is a P or Q,
       * increment the counter.
       */
      int i = 0; 
      while( line[i] != '\0' )
      {
         if( 'P' == line[i] ) ++numP;
         if( 'Q' == line[i] ) ++numQ;
         ++i;
      }

      /*
       * At this point we've reached the end of the line, so
       * we're done counting. Output the results in the format
       * that was requested.
       */
      printf( "P's: %d, ", numP );
      printf( "Q's: %d\n", numQ );

   }

   return 0;
}


