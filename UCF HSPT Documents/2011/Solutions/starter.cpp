
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


#define PI   3.141592653589793


int main(void)
{
   FILE *   fp;
   char     line[80];
   char *      token;
   int         numEnemies;
   int         i;
   int         x, y;
   double      r, theta;
   int         seconds, minutes, hours;
   int         days, months, years;


   // Open the input file and read the number of enemies
   fp = fopen("starter.in", "r");
   fgets(line, sizeof(line), fp);
   token = strtok(line, " \n\r");
   numEnemies = atoi(token);

   // Process each enemy location
   for (i = 0; i < numEnemies; i++)
   {
      // Read the coordinates
      fgets(line, sizeof(line), fp);
      sscanf(line, "%d %d", &x, &y);

      // Convert to standard polar coordinates
      r = sqrt( (x*x) + (y*y) );
      theta = atan2(y, x);

      // Convert the angle into "Navy" degrees, where 0 is north and 90
      // is east
      theta = 450.0 - (theta * 180 / PI);
      theta = fmod(theta, 360.0);

      // Convert the degree number into seconds in a day (not seconds as in
      // degrees/minutes/seconds)
      seconds = (int) ((theta * 12 * 60 * 60 / 360.0) + 0.5);
      minutes = seconds / 60;
      hours = minutes / 60;
      minutes -= hours * 60;
      seconds -= hours * 60 * 60;
      seconds -= minutes * 60;

      // An hour of 0 should read 12
      if (hours == 0)
         hours = 12;

      // Convert the distance into the date format specified
      days = (int)((r * 500.0) + 0.5);
      months = days / 30;
      years = months / 12;
      months -= years * 12;
      days -= years * 12 * 30;
      days -= months * 30;

      // Months and days are numbered beginning at 1
      months += 1;
      days += 1;

      // Output the bad guy's encoded direction and distance
      printf("Tango at %d:%02d:%02d, %02d/%02d/%04d!\n",
             hours, minutes, seconds, months, days, years);
   }

   // Print the last line of output
   printf("The enemy's gate is down!\n");

   // Close the file
   fclose(fp);
}

