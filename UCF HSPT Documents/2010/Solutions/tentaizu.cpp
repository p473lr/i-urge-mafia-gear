
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

// Blank spaces in our board matrix will be represented by a '-1'
// Bombs will be represented by a '9'
#define BLANK -1
#define BOMB 9

void SolveBoard(int ** boardMatrix);
bool Recurse(int ** boardMatrix, int * boardArray, int * mines,
             int currentMine, int mineCount);
void FillUnknowns(int ** boardMatrix, int unknownCount);
inline int calculateIndex(int row, int col);
void calculateRC(int index, int *row, int *col);

int ** boardMatrix;
int *  boardArray;
int ** keyPositionsByIndex;
int *  keyPositionCounts;

int main()
{
    FILE *   inFile;
    char     currentLine[256];
    int      boardCount;

    // Open the input file for reading
    inFile = fopen("tentaizu.in", "r");

    // Read the first line and scan the number of boards from it
    fgets(currentLine, 16, inFile);
    sscanf(currentLine, "%d", &boardCount);

    // Create the board matrix
    boardMatrix = (int **)malloc(7 * sizeof(int *));
    for (int r = 0; r < 7; r++)
        boardMatrix[r] = (int *)calloc(7, sizeof(int));

    // Process each board
    for (int currentBoard = 1; currentBoard <= boardCount; currentBoard++)
    {
        // Read in the board itself, first by rows and then by columns
        for (int r = 0; r < 7; r++)
        {
            // Read the next line
            fgets(currentLine, 16, inFile);

            // Process each column in this row
            for (int c = 0; c < 7; c++)
            {
                // Check whether the character is a numerical value or the
                // unknown character
                if ((currentLine[c] >= '0') && (currentLine[c] <= '8'))
                {
                    // Convert the character to an integer
                    boardMatrix[r][c] = currentLine[c] - '0';
                }
                else
                {
                    // Place a sentinel value in the board at this position
                    boardMatrix[r][c] = BLANK;
                }
            }
        }

        // Send the data off to a dedicated method to solve the system
        SolveBoard(boardMatrix);

        // Print out the header
        fprintf(stdout, "Tentaizu Board #%d:\n", currentBoard);
        for (int r = 0; r < 7; r++)
        {
            for (int c = 0; c < 7; c++)
            {
                if (boardMatrix[r][c] == BLANK)
                    fprintf(stdout, ".");
                else if (boardMatrix[r][c] == BOMB)
                    fprintf(stdout, "*");
                else
                    fprintf(stdout, "%c", (char)(boardMatrix[r][c] + '0'));
            }

            fprintf(stdout, "\n");
        }

        // Skip the blank line between cases, but only if this wasn't the last
        // case
        if (currentBoard < boardCount)
        {
            fgets(currentLine, 16, inFile);
        }
        fprintf(stdout, "\n");
    }

    // We're done! Clean up and return.
    fclose(inFile);
    return 0;
}

void SolveBoard(int ** boardMatrix)
{
    int r, c;
    int * mineIndices;

    // Initialize a linear board array with the same data as our board matrix.
    // This is primarily for convenience, so we don't have to convert back and
    // forth between position indices and row-column notation as often.
    boardArray = (int *)calloc(49, sizeof(int));
    for (int i = 0; i < 49; i++)
    {
        calculateRC(i, &r, &c);
        boardArray[i] = boardMatrix[r][c];
    }

    // Create one more structure
    keyPositionsByIndex = (int **)malloc(49 * sizeof(int *));
    keyPositionCounts = (int *)calloc(49, sizeof(int));
    for (int i = 0; i < 49; i++)
    {
        keyPositionsByIndex[i] = (int *)calloc(10, sizeof(int));

        // Calculate the things
        calculateRC(i, &r, &c);
        for (int r2 = r - 1; r2 <= r + 1; r2++)
        {
            if ((r2 == -1) || (r2 == 7)) continue;
            for (int c2 = c - 1; c2 <= c + 1; c2++)
            {
                // Bounds check our column value
                if ((c2 == -1) || (c2 == 7))
                    continue;

                // Make sure we're not including the original position
                if ((r == r2) && (c == c2))
                    continue;

                // Check whether the board
                if (boardMatrix[r2][c2] >= 0)
                {
                    // Indicate that the position (r2, c2) influences the
                    // position (r, c), currently stored in i.
                    keyPositionsByIndex[i][keyPositionCounts[i]] =
                        calculateIndex(r2, c2);

                    // Increment the number of key positions associated with
                    // this position.
                    keyPositionCounts[i]++;
                }
            }
        }
    }

    // Initialize the numbers by index helper
    mineIndices = (int *)calloc(10, sizeof(int));

    // Start by trying to fill in 10 mines forcefully.
    for (int mineCount = 10; mineCount >= 0; mineCount--)
    {
        if (Recurse(boardMatrix, boardArray, mineIndices, 0, mineCount))
        {
            FillUnknowns(boardMatrix, 10 - mineCount);
            break;
        }
    }

    // Free memory
    free(mineIndices);
    for (int i = 0; i < 49; i++)
        free(keyPositionsByIndex[i]);
    free(keyPositionsByIndex);
    free(keyPositionCounts);
}

bool Recurse(int ** boardMatrix, int * boardArray, int * mines,
             int currentMine, int mineCount)
{
    int currentPosition;
    int r, c;

    // See whether our recursive search has placed the desired number of mines.
    if (currentMine == mineCount)
    {
        // Check the global solution to confirm that all of the numbers are
        // satisfied by this solution.
        for (int i = 0; i < 49; i++)
        {
            // All numbers should have been reduced to zero (or still be blank)
            if (boardArray[i] > 0)
            {
                // We've found a position with an insufficient number of mines.
                // Indicate that we have to continue searching.
                return false;
            }
        }

        // All of the numbers present on the board have been satisfied. We've
        // found a working configuration of mines using the desired number.
        return true;
    }

    // See which position we should begin searching at.
    if (currentMine == 0)
    {
        // This is our first mine. Start at the first position.
        currentPosition = 0;
    }
    else
    {
        // We've placed one or more mines already. Start after the last one
        // that was placed.
        currentPosition = mines[currentMine - 1] + 1;
    }

    // Keep trying placements until we run off the end of the board.
    while (currentPosition < 49)
    {
        // Try a mine at the current position. Make sure each of the key
        // positions for this position can withstand the placement.
        calculateRC(currentPosition, &r, &c);
        if (boardMatrix[r][c] == BLANK)
        {
            // Begin assuming this placement will work. We will usually prove
            // otherwise.
            bool validated = true;

            // If the position isn't influenced by any numbers, then we don't
            // have any idea what it might contain. These pure unknown spaces
            // are filled in a later method call, so we will avoid them for
            // now.
            if (keyPositionCounts[currentPosition] == 0)
                validated = false;

            // Now for any key positions that do exist, make sure they can
            // withstand placement of another mine in this position.
            for (int i = 0; i < keyPositionCounts[currentPosition]; i++)
            {
                if (boardArray[keyPositionsByIndex[currentPosition][i]] == 0)
                {
                    // The position has already been reduced to zero. We can't
                    // place a mine at this location.
                    validated = false;
                }
            }

            // Check whether our position passed validation.
            if (validated)
            {
                // Set the mine.
                mines[currentMine] = currentPosition;

                // Decrement the remaining capacity of each number adjacent to
                // our new placement.
                for (int i = 0; i < keyPositionCounts[currentPosition]; i++)
                    boardArray[keyPositionsByIndex[currentPosition][i]]--;

                // Recurse, testing whether this configuration has produced an
                // acceptable result.
                if (Recurse(boardMatrix, boardArray, mines, currentMine + 1, mineCount))
                {
                    // We've found a valid configuration. Modify the board
                    // matrix to include the new bomb
                    calculateRC(currentPosition, &r, &c);
                    boardMatrix[r][c] = BOMB;
                    return true;
                }
                
                // This configuration wasn't successful, so we need to place
                // the key position capacities back to their previous levels.
                for (int i = 0; i < keyPositionCounts[currentPosition]; i++)
                    boardArray[keyPositionsByIndex[currentPosition][i]]++;
            }
        }

        // Move on to the next candidate position.
        currentPosition++;
    }

    // We didn't find a valid configuration. Indicate this.
    return false;
}

void FillUnknowns(int ** boardMatrix, int unknownCount)
{
    int currentIndex = 0;
    int r, c;
    while (unknownCount > 0)
    {
        // Calculate the row and column values for this board position.
        calculateRC(currentIndex, &r, &c);

        // Confirm first that the position is currently empty.
        if (boardMatrix[r][c] == BLANK)
        {
            // Now make sure the board position is truly unknown (possesses no
            // influencing or 'key' positions).
            if (keyPositionCounts[currentIndex] == 0)
            {
                boardMatrix[r][c] = BOMB;
                unknownCount--;
            }
        }

        // Move to the next candidate position.
        currentIndex++;
    }
}

inline int calculateIndex(int row, int col)
{
    return ((row * 7) + col);
}

void calculateRC(int index, int * row, int * col)
{
    *col = (index % 7);
    *row = ((index - *col) / 7);
}

