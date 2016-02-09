
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Find X (find)
//
// Author:        Skyler Goodell
// Judge Data:    Matt Fontaine
// C Solution:    SteVen Batten
// Java Solution: Gabe Pita
// Verifier:      Danny Wasserman

#include <stdio.h>

int main() {
   //open the input file
   FILE* in = fopen("find.in", "r");

   //read in number of test cases
   int T;
   fscanf(in, "%d", &T);

   //loop over the cases
   int t;
   for(t = 1; t <= T; t++) {

      //read in number of rows and columns
      int R, C; 
      fscanf(in, "%d", &R);
      fscanf(in, "%d", &C);

      //read in grid
      char grid[R][C];
      int i, j;
      for(i = 0; i < R; i++)
         fscanf(in, "%s", &grid[i]);

      //max stores the answer
      int max = 0;

      //loop over all center points for x
      for(i = 0; i < R; i++) {
         for(j = 0; j < C; j++) {
            //if this isn't an x don't consider it
            if(grid[i][j] != 'X')
               continue;

            //try extending diagonally this center point until
            //you are off the grid or not an x
            int k = 0;
            for(;;) {
               //check if we went off grid
               if(i+k >= R || i-k < 0 || j+k >= C || j-k < 0)
                  break;
               //check if it's not an X
               if(grid[i+k][j+k] != 'X' || grid[i-k][j+k] != 'X' ||
                  grid[i+k][j-k] != 'X' ||grid[i-k][j-k] != 'X')
                  break;

               k++;
            }

            //decrement because we over shot it in our check
            k--;

            //see if this new max is better than the previous one
            if (k*2+1 > max)
               max = k*2+1;
         }
      }

      //output answer for this case
      printf("Page #%d: %d\n", t, max);
   }

   //close file and exit
   fclose(in);
   return 0;
}
