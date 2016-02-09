
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Squirrel Terrirory (squirrels)
//
// Author:        Matt Fontaine
// Judge Data:    Mike Galletti
// C Solution:    Patrick Fenelon
// Java Solution: John Boswell
// Verifier:      SteVen Batten

#include <stdio.h>
#include <stdlib.h>

//no uniform constant in all C math libraries, thus, define it here
#define PI 3.14159265358979 

int main()
{
   int c,t;
   int *x,*y;
   int testCase, i, j;

   int min;

   //open file to read from
   FILE *in = fopen("squirrels.in", "rt");

   //read number of test cases (C)
   fscanf(in, " %d", &c);

   //iterate over test cases (campuses)
   for(testCase = 0; testCase < c; testCase++){

      //read number of trees in this test case (T)
      fscanf(in, " %d", &t);

      //allocate space for arrays to store coordinates
      x = (int*) malloc(t * sizeof(int));
      y = (int*) malloc(t * sizeof(int));

      //read in coordinates
      for(i = 0; i < t; i++){
         fscanf(in, " %d", &(x[i]));
         fscanf(in, " %d", &(y[i]));
      }

      //set to arbitrarily large value to start with
      min = 99999999; 

      //find smallest distance between trees
      for(i = 0; i < t; i++){
         for(j = 0; j < t; j++){
            if(i!=j){
               int dx = x[i] - x[j];
               int dy = y[i] - y[j];
               int distSq = dx*dx+dy*dy;
               
               //only the square of the distance is needed, 
               //this will avoid error in later calculations
               if(min > distSq) min = distSq;
            }
         }
      }

      //free memory, we're done with x and y now
      free(x);
      free(y);

      //compute answer, keep in mind we have the 2*r^2 stored in min, 
      //to account for this multiply by PI and divide by 4 to get the area
      double answer = PI / 4.0 * min; 

      //print answer
      printf("Campus #%d:\n", testCase+1);
      printf("Maximum territory area = %.3f\n\n", answer);

   }   

   //no error happened so return 0
   fclose(in);
   return 0;
}

