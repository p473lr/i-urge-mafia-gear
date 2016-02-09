
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Greatest Cosmic Devourers (cosmic)
//
// Author:        Mike Galletti
// Judge Data:    Matt Fontaine
// C Solution:    Aaron Teetor
// Java Solution: Gabe Pita
// Verifier:      Andrew Harn

#include <stdio.h>
#include <stdlib.h>

int main(void) {
   //Open the file
   FILE* fp;
   fp = fopen("cosmic.in", "r");

   int numScenarios;
   int i, j, k;

   //Read in the number of scenarios (cases)
   fscanf(fp, "%d", &numScenarios);

   //Loop over the scenarios
   for (i = 0; i < numScenarios; i++)
   {
      //Read in a and b
      int a;
      int b;
      fscanf(fp, "%d", &a);
      fscanf(fp, "%d", &b);

      //Planet 0 always survives, start on planet 1
      int planetsSurviving = 1;
      int planetsChecked = 1;

      //Loop through all planets until we get to a contested one.
      //Note that the sequence repeats after that so enough planets 
      //will have been checked once we get to a contested one!
      while (planetsChecked % a != 0 || planetsChecked % b != 0) {
         //If a nor b wants to eat the planet it counts as surviving
         if (planetsChecked % a != 0 && planetsChecked % b != 0) {
            planetsSurviving++;
         }

         //Add one to number of planets checked
         planetsChecked++;
      }

      //Take the percent of planets that survived
      printf("Universe #%d: There's a %.4f%% chance we'll survive!\n", i + 1,
             100 * ((double)(planetsSurviving) / (double)(planetsChecked)));
   }

   //Exit
   fclose(fp);
   return EXIT_SUCCESS;
}
