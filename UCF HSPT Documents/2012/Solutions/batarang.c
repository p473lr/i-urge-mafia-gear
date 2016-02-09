/*
  Jason Jones
  filename: batarang.c
  file input, standard output
*/

#include<stdio.h>
#include<stdlib.h>

struct thug { // location of a thug
   int xLoc;
   int yLoc;
};
int signOf(int); // prototype

int main()
{
   int i, j, k; // loop variables
   int x, y;
   int R; // number of rooms
   int n; // number of thugs in the room
   int numBatarangs;
   int found;
   FILE *fpIn;
   struct thug thugs[100]; // keep adding thugs to this array if they aren't on the same line, 
                       // and in the same quadrant, of a thug that is already in this array.

   fpIn = fopen("batarang.in", "r");

   fscanf(fpIn, "%d", &R);
   for(i = 0; i < R; i++) {
      numBatarangs = 0; // initialize number of batarangs for each room
      fscanf(fpIn, "%d", &n);
      for(j = 0; j < n; j++) {
         fscanf(fpIn, "%d%d", &x, &y);
         // check if this thug is on the same line, and in the same quadrant, as a 
         // thug that is already in thugs[]
         found = 0;
         for(k = 0; k < numBatarangs; k++) {
            // check quadrant/axis and cross multiply the slopes to see if they are the same
            if(signOf(x) == signOf(thugs[k].xLoc) && signOf(y) == signOf(thugs[k].yLoc) && x*thugs[k].yLoc == y*thugs[k].xLoc)
               found = 1;
         }
         if(!found) {
            thugs[numBatarangs].xLoc = x;
            thugs[numBatarangs].yLoc = y;
            numBatarangs++;
         }
      }
      printf("Room #%d: ", i+1);
      if(numBatarangs == 1)
         printf("Leaping lizards, %d birds with one stone!\n", n);
      else
         printf("Batman will only need %d batarangs!\n", numBatarangs);
      printf("\n"); //formatting
   }
   fclose(fpIn);
   return 0;
}

// function to check if the thugs are in the same quadrant, or lie on the same half-axis
int signOf(int num)
{
   if(num > 0)
      return 1; // return 1 for positive
   if(num < 0)
      return -1; // return -1 for negative
   return 0; // return 0 for 0
}
