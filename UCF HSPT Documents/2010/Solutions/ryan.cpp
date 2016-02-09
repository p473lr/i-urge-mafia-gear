
#include <stdio.h>
#include <string.h>


int main()
{
   FILE *   infile;
   char     line[256];
   int      numTimes;
   char *   ch;
   int      numProper;

   // Open the file
   infile = fopen("ryan.in", "r");

   // Read the number of times Ryan should write the message
   fgets(line, sizeof(line), infile);
   sscanf(line, "%d", &numTimes);

   // Loop until we get a zero
   while (numTimes != 0)
   {
      // Get the first attempt
      fgets(line, sizeof(line), infile);
      if ( (ch = strchr(line, '\n')) != NULL)
         *ch = '\0';

      // Loop until we're done
      numProper = 0;
      while (strcmp(line, "Am I done yet?") != 0)
      {
         // See if this attempt is written correctly
         if (strcmp(line, "Greeks! Go home!") == 0)
             numProper++;

         // Get the next attempt
         fgets(line, sizeof(line), infile);
         if ( (ch = strchr(line, '\n')) != NULL)
            *ch = '\0';
      }

      // Output as appropriate
      if (numProper < numTimes)
         printf("You have %d left to go.\n", numTimes - numProper);
      else
         printf("You're done.  Now don't do it again.\n");

      // Read the number of times Ryan should write the message for the next
      // session
      fgets(line, sizeof(line), infile);
      sscanf(line, "%d", &numTimes);
   }

   // Close the file
   fclose(infile);
}

