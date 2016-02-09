
#include <stdio.h>
#include <stdlib.h>

// Prototypes
void processTile(char map[21][21], int r, int c);
void fireOne(char map[21][21], int r, int c, int dr, int dc);
void fireTwo(char map[21][21], int r, int c, int dr, int dc, int oneDR, int oneDC);

int main(void)
{
   int cases, caseNum = 0;
   int rows, cols, r, c;

   // Open input file and read in number of cases to process
   FILE* fp = fopen("fire.in", "r");
   fscanf(fp, "%d", &cases);
   
   // Loop over each test case
   char map[21][21];
   while (caseNum++ < cases) {
      // Read in the map size and the map itself
      fscanf(fp, "%d %d", &rows, &cols);
      for (r = 0; r < rows; r++) {
         fscanf(fp, "%s", map[r]);
      }
      
      // This stops us from going off the map in the downward direction
      // as this will be read as a blocked tile.  Going off in the right
      // direction is also blocked because all character arrays end with
      // a '\0'.
      for (c = 0; c <= cols; c++) {
         map[rows][c] = '\0';
      }
      
      for (r = 0; r < rows; r++) {
         for (c = 0; c < cols; c++) {
            // For every tile, check if there is a dragon, if so do work.
            processTile(map, r, c);
         }
      }
      
      // Print the final answer for this case
      printf("Map #%d:\n", caseNum);
      for (r = 0; r < rows; r++) {
         printf("%s\n", map[r]);
      }
      
      // Blank line after the output for each case
      printf("\n");
   }
   
   // Close file and return
   fclose(fp);
   return 0;
}

// Runs the fire functions if there is a dragon at this spot.
void processTile(char map[21][21], int r, int c) {
   int dr = 0, dc = 0;

   // Get the direction the dragon is facing as
   // the deltas of the rows and columns of the map
   // if you travel the direction they are looking.
   if (map[r][c] == 'V') {
      dr = 1;
      dc = 0;
   } else if (map[r][c] == '>') {
      dr = 0;
      dc = 1;
   } else if (map[r][c] == '<') {
      dr = 0;
      dc = -1;
   } else if (map[r][c] == '^') {
      dr = -1;
      dc = 0;
   }
   
   // If this isn't true there is no dragon here.
   if (dr + dc != 0) {
      // Fires of type one go in the same direction the dragon faces.
      fireOne(map, r+dr, c+dc, dr, dc);
      
      // Fires of type two go in the same direction, but with the 0 delta as
      // a 1 or -1 (left or right rather than the 0 that is straight).
      //
      // Also pass them the direction of the dragon so we can spawn
      // type one fires from the type two fires.
      if (dr != 0) {
         fireTwo(map, r+dr, c-1, dr, -1, dr, dc);
         fireTwo(map, r+dr, c+1, dr, 1, dr, dc);
      } else {
         fireTwo(map, r+1, c+dc, 1, dc, dr, dc);
         fireTwo(map, r-1, c+dc, -1, dc, dr, dc);
      }
   }
}

// Places fire of type one and spreads it.
void fireOne(char map[21][21], int r, int c, int dr, int dc) {
   // Make sure we are on the map and that the tile isn't blocked.
   if (r >= 0 && c >=0 && (map[r][c] == '.' || map[r][c] == 'F')) {
      // Mark it as fire and call recursively to spread the fire
      map[r][c] = 'F';
      fireOne(map, r+dr, c+dc, dr, dc);
   }
}

// Places fire of type two and spreads it.
void fireTwo(char map[21][21], int r, int c, int dr, int dc, int oneDR, int oneDC) {
   // Make sure we are on the map and that the tile isn't blocked.
   if (r >= 0 && c >=0 && (map[r][c] == '.' || map[r][c] == 'F')) {
      // Mark it as fire (note that we spread through 
      // tiles already on fire as well!)
      map[r][c] = 'F';
      fireTwo(map, r + dr, c + dc, dr, dc, oneDR, oneDC);
      fireOne(map, r+oneDR, c+oneDC, oneDR, oneDC);
   }
}

