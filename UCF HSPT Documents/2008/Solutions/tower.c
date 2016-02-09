
#include <stdio.h>
#include <math.h>
#include <sys/types.h>

#define OUR_PI 3.14159265


int main()
{
   FILE *   infile;
   char     line[256];
   unsigned long   numTargets;
   unsigned long   i;
   double   targetDistance;
   double   angle;

   /* Open the input file */
   infile = fopen("tower.in", "r");

   /* Get the number of targets */
   fgets(line, sizeof(line), infile);
   sscanf(line, "%ld", &numTargets);

   /* Loop through each target */
   for (i=0; i < numTargets; i++)
   {
      /* Get this target distance */
      fgets(line, sizeof(line), infile);
      sscanf(line, "%lf", &targetDistance);

      /* Compute the angle needed for this distance.  Note that we
       * are basically creating a triangle with the base being this
       * targetDistance and the hypotenuse being 56 meters.  Taking
       * the arc-cosine of this distance over the hypotenuse gives
       * us our answer, but in radians
       */
      angle = acos(targetDistance / 56.0);

      /* Output the answer angle, but in degrees */
      printf("Target #%ld: %.1f degrees\n", i+1, angle * 180.0 / OUR_PI);
   }

   /* Close the input file */
   fclose(infile);
}

