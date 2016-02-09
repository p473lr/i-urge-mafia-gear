#include <stdio.h>

using namespace std;

// Disjoint set functions:
// Set find returns the parent of the given node.
int setFind(int *parent, int a)
{
    if(parent[a] == a)
        return a;
    int newParent = setFind(parent, parent[a]);
    parent[a] = newParent;
    return parent[a];
}
// Set union sets the parent of a to b.
void setUnion(int *parent, int a, int b)
{
    parent[setFind(parent, a)] = setFind(parent, b);
}

int main()
{
    int tests;
    scanf("%d", &tests);
    for(int t = 1; t<=tests; t++)
    {
        // Read in all the things!
        int n, m, k;
        scanf("%d %d %d", &n, &m, &k);
        bool isWhite[n][m];
        int locR[k];
        int locC[k];
        int number[k];
        char line[m+2];
        for(int r = 0; r<n; r++)
        {
            scanf("%s", line);
            for(int c = 0; c<m; c++)
                isWhite[r][c] = line[c]=='.';
        }
        for(int i = 0; i<k; i++)
            scanf("%d %d %d", locR+i, locC+i, number+i);

        // Initialize all the cells to be disconnected (in their own set).
        int parent[n*m];
        for(int i = 0; i<n*m; i++)
            parent[i] = i;

        // Union two cells if they are the same color and next to each other.
        for(int r = 0; r<n; r++)
            for(int c = 0; c<m; c++)
            {
                if(r+1 < n && isWhite[r][c] == isWhite[r+1][c])
                    setUnion(parent, r*m+c, (r+1)*m+c);
                if(c+1 < m && isWhite[r][c] == isWhite[r][c+1])
                    setUnion(parent, r*m+c, r*m+c+1);
            }

        // initialize some stuffs.
        int setCount[n*m];
        bool taken[n*m];
        for(int i = 0; i<n*m; i++)
        {
            taken[i] = false;
            setCount[i] = 0;
            setFind(parent, i); // make sure all the parents are up to date
        }

        // Get the size of each set.
        for(int i = 0; i<n*m; i++)
            setCount[parent[i]]++;

        bool isCorrect = true;

        // Check for numbers on the same island and island sizes.
        for(int i = 0; i<k; i++)
        {
            int index = (locR[i]-1)*m+(locC[i]-1);
            if(taken[parent[index]])
                isCorrect = false; // We already found a number that's part of this set. They must be on the same island.
            else taken[parent[index]] = true;
            if(setCount[parent[index]] != number[i])
                isCorrect = false; // The size of the island doesn't match the number given.
        }

        // Check for pools.
        for(int i = 0; i<n-1; i++)
            for(int j = 0; j<m-1; j++)
                if(!isWhite[i][j] && !isWhite[i+1][j] && !isWhite[i][j+1] && !isWhite[i+1][j+1])
                    isCorrect = false;

        // Check for lonely islands (an island without a number) and disconnected black cells.
        int aBlackIndex = -1;
        for(int r = 0; r<n; r++)
            for(int c = 0; c<m; c++)
            {
                int index = r*m+c;
                if(isWhite[r][c])
                {
                    if(!taken[parent[index]])
                        isCorrect = false; // None of our k numbers claimed this set of white cells.
                }
                else
                {
                    if(aBlackIndex == -1)
                        aBlackIndex = parent[index]; // YAY we found a black set.
                    if(parent[index] != aBlackIndex)
                        isCorrect = false; // We found a disconnected black set.
                }
            }

        printf("Puzzle #%d: %s\n", t, isCorrect?"Correct":"Incorrect");
    }
    return 0;
}
