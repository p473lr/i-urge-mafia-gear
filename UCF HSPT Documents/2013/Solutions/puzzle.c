
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Nine-Piece Puzzle (puzzle)
//
// Author:        Skyler Goodell
// Judge Data:    Danny Wasserman
// C Solution:    Antony Stabile
// Java Solution: Kyle Urquhart
// Verifier:      Tyler Brazill

/*
Strategy:

For each test case, do the following
1) Try all permutations of the digits 0 through 8
2) Use this to construct all possible arrangements of the tiles
3) Check each arrangement (verify the border rows and border columns)

*/

#include <stdio.h>

#define MAXSIZE 10
#define WIDTH 3
#define NUMTILES WIDTH*WIDTH

#define TRUE 1
#define FALSE 0

void findSolutions(int pos);
void build();
int verify();


// the board of letters, which will be verified
char board[MAXSIZE*WIDTH][MAXSIZE*WIDTH];

// the individual tiles
char tiles[NUMTILES][MAXSIZE][MAXSIZE];
int tileSize, solutionsFound;

// arrays used for permutations
int used[NUMTILES] = {FALSE};
int perm[NUMTILES];

int main() {
    // open the input file
    FILE * fp;
    fp = fopen("puzzle.in", "r");

    int numCases, curCase, i, j;

    // read in the number of cases
    fscanf(fp, "%d", &numCases);

    // loop over the cases
    for(curCase = 1; curCase <= numCases; curCase++) {
        // read in this cases size, and tiles
        fscanf(fp, "%d", &tileSize);
        for(i=0; i<NUMTILES; i++)
            for(j=0; j<tileSize; j++)
                fscanf(fp, "%s ", tiles[i][j]);

        // find the number of valid configurations
        solutionsFound = 0;
        findSolutions(0);

        // print the answer
        printf("Puzzle #%d: ", curCase);
        if(solutionsFound == 0) printf("NO");
        else if(solutionsFound == 1) printf("YES");
        else printf("MULTIPLE");

        printf("\n");
    }

    // All done
    fclose(fp);
    return 0;
}

void findSolutions(int pos) {
    int i;
    // if we placed all the values already, check feasibility
    if(pos == NUMTILES) {
        if(verify())
            solutionsFound++;
        return;
    }

    // try placing each unused value at this position
    for(i=0; i<NUMTILES; i++) {
        if(!used[i]) {
            used[i] = TRUE;
            perm[pos] = i;
            findSolutions(pos+1);
            used[i] = FALSE;
        }
    }
}

// arranges the tiles into the board configuration for verification
void build() {
    int i,j,ii,jj;
    for(i=0; i<WIDTH; i++)
        for(j=0; j<WIDTH; j++)
            for(ii=0; ii<tileSize; ii++)
                for(jj=0; jj<tileSize; jj++)
                    board[i*tileSize + ii][j*tileSize + jj] = 
                       tiles[perm[i*WIDTH + j]][ii][jj];
}

int verify() {
    int i,j;
    build();

    // check the vertical borders
    for(i=1; i<WIDTH; i++)
        for(j=0; j<tileSize*WIDTH; j++)
            if(board[i*tileSize-1][j] != board[i*tileSize][j])
                return FALSE;

    // and now the horizontal borders
    for(i=1; i<WIDTH; i++)
        for(j=0; j<tileSize*WIDTH; j++)
            if(board[j][i*tileSize-1] != board[j][i*tileSize])
                return FALSE;

    return TRUE;
}
