
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Crafty Trolling (craft)
//
// Author:        Skyler Goodell
// Judge Data:    Nick Buelich
// C Solution:    Antony Stabile
// Java Solution: Andrew Harn
// Verifier:      Aaron Teetor


/*
Strategy:

For each test case, do the following
1) Read in the grid, and locate the lava source
2) Perform a flood fill using a Depth First Search (DFS)
3) Count the number of squares with lava
*/


#include <stdio.h>
#include <stdlib.h>

#define MAXGRID 60

#define EMPTY '.'
#define LAVA '*'
#define WALL '#'

int isOk(int x, int y, int z);
void fill(int x, int y, int z);

// the map and dimensions
char board[MAXGRID][MAXGRID][MAXGRID];
int h,l,w;

int main() {
    FILE * infile;
    int numCases, curCase, i, j, k;
    int sx, sy, sz;

    // open input file
    infile = fopen("craft.in", "r");

    // read in the number of cases
    fscanf(infile, "%d ", &numCases);

    // loop through each case
    for(curCase = 1; curCase <= numCases; curCase++) {
        // read in the dimensions
        fscanf(infile, "%d %d %d ", &l, &w, &h);

        // read in h slices of l lines
        for(i=0; i<h; i++)
            for(j=0; j<l; j++)
                fscanf(infile, "%s ", board[i][j]);

        // locate the source and fill from there
        for(i=0; i<h; i++)
            for(j=0; j<l; j++)
                for(k=0; k<w; k++)
                    if(board[i][j][k] == LAVA) {
                        sx = i;
                        sy = j;
                        sz = k;
                    }

        // fill the board
        board[sx][sy][sz] = EMPTY;
        fill(sx,sy,sz);

        // count the number of lava squares
        int count = 0;
        for(i=0; i<h; i++)
            for(j=0; j<l; j++)
                for(k=0; k<w; k++)
                    if(board[i][j][k] == LAVA)
                        count++;

        // print the answer
        printf("Map #%d: %d\n", curCase, count);
    }

    // exit
    fclose(infile);
    return 0;
}

// performs a recursive dfs from (x,y,z)
// if possible, lava flows down
// otherwise, it flows in the other 4 directions
void fill(int x, int y, int z) {
    // check if we exited the board dimensions
    if(!isOk(x,y,z)) return;

    // if we already filled this square or hit a wall, return
    if(board[x][y][z] != EMPTY) return;

    // fill this square with lava
    board[x][y][z] = LAVA;

    if(!isOk(x+1,y,z) || board[x+1][y][z] == WALL) {
        // we can't go down so spread in the other four directions
        fill(x,y-1,z);
        fill(x,y+1,z);
        fill(x,y,z-1);
        fill(x,y,z+1);
    }
    else {
        // it seems okay to flow down, so do it
        fill(x+1,y,z);
    }
}


// checks if (x,y,z) lies within the board
int isOk(int x, int y, int z) {
    return x>=0 && y>=0 && z>=0 && x<h && y<l && z<w;
}

