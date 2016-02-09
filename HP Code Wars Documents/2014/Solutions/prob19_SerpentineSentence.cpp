/*
   Read grid of characters and find the longest path of characters
   repeated in the grid.

   Example input:
12 26
IAI my MatC76 Trombones le
 FkeCIDIch bin ein BerliJd
wGiHIJtiw ImpressiverlInI 
aIl IMhNIOI!edarap gib eht
nZ!aQR Invent! my Mat wriS
tITzUIaWX ofZkeYIZIAhIaCI?
 piz.E IJeA Pi.KLtiw MnX!a
I win slicIOPl IQhRSTUtVWz
76 Trombones leXI YZAB piz
Ich bin.ein BIdCIaEI ofXII
ImpressiveHIer JK LIeB Pi.
!edarap gib ehtNIslicI win

   Example Output:
I like my Math with a slice of Pi.
.iP fo ecils a htiw htaM ym ekil I


9 20
time flies like aXYZ
Chocolate Cakes nlas
o!sriurf  .worra una
deWatrj is awesome n
time flies like a ba
aChocolate CakesnSat
!oDaiurf  .worra gna
ddeWtjrj is awesomen
time flies like a ba

time flies like an arrow.  fruit flies like a banana
ananab a ekil seilf tiurf  .worra na ekil seilf emit
*/

//#define DEBUG 1 // For debug print statements

#include <string>
#include <iostream>

using namespace std;

#define MAX_ROWS 25
#define MAX_COLS 50
#define MAX_SENTENCE 80

// Global values

int rows, cols; // Global values for the user-supplied size of the grid

char grid[MAX_ROWS][MAX_COLS]; // Array of characters
char usedGrid[MAX_ROWS][MAX_COLS]; // Array of characters already inspected for sentence.
char longSentence[MAX_SENTENCE];
char workSentence[MAX_SENTENCE];

int longestLengthFound = 0;



#ifdef DEBUG
void printGrid(int rows, int cols)
{
	int row, col;
	cout << endl;
	for (row=0; row<rows; row++)
	{
		for (col=0;col<cols; col++)
		{
			cout << grid[row][col];
		}
		cout << endl;
	}
}
#endif DEBUG


void printSentence(void)
{
	int col;
	//cout << longestLengthFound << ": ";
	for (col=0;col<longestLengthFound; col++)
		cout << longSentence[col];
	cout << endl; // Print end of line
}

void printSentenceBackward(void)
{
	int col;
	//cout << longestLengthFound << ": ";
	for (col=longestLengthFound-1;col>=0; col--)
		cout << longSentence[col];
	cout << endl; // Print end of line
}


// Recursive routine to search for matching characters.  This is only
//   called when the character at A and B match.
void searchSentence(int rowA, int colA, int rowB, int colB, int length)
{
	int i;

	if (length == 0)
	{
		memset (usedGrid, 0, sizeof(usedGrid));  // Clear work grid
		memset (workSentence, 0, sizeof(workSentence)); // Clear current sentence					
	}

	workSentence[length] = grid[rowA][colA];
	length++;
	usedGrid[rowA][colA] = 1;

	// UP
	if ((rowA > 0) && (rowB > 0))
	{
		if ( (usedGrid[rowA-1][colA] == 0) && // Only set/check the A sentence for pre-use. OK A to overlap B.
			 (grid[rowA-1][colA] == grid[rowB-1][colB])) // Characters match
		{
			// Mark neighboring squares so can't return to touch the path
			if (colA>0)      usedGrid[rowA][colA-1]++; // LEFT
			if (rowA<rows-1) usedGrid[rowA+1][colA]++; // DOWN
			if (colA<cols-1) usedGrid[rowA][colA+1]++; // RIGHT
			searchSentence(rowA-1, colA, rowB-1, colB, length);
			if (colA>0)      usedGrid[rowA][colA-1]--; // LEFT
			if (rowA<rows-1) usedGrid[rowA+1][colA]--; // DOWN
			if (colA<cols-1) usedGrid[rowA][colA+1]--; // RIGHT
		}
	}

	// DOWN
	if ((rowA < rows-1) && (rowB < rows-1))
	{
		if ( (usedGrid[rowA+1][colA] == 0) && // Only set/check the A sentence for pre-use. OK A to overlap B.
			 (grid[rowA+1][colA] == grid[rowB+1][colB])) // Characters match
		{
			// Mark neighboring squares so can't return to touch the path
			if (colA>0)      usedGrid[rowA][colA-1]++; // LEFT
			if (rowA>0)      usedGrid[rowA-1][colA]++; // UP
			if (colA<cols-1) usedGrid[rowA][colA+1]++; // RIGHT
			searchSentence(rowA+1, colA, rowB+1, colB, length);
			if (colA>0)      usedGrid[rowA][colA-1]--; // LEFT
			if (rowA>0)      usedGrid[rowA-1][colA]--; // UP
			if (colA<cols-1) usedGrid[rowA][colA+1]--; // RIGHT
		}
	}

	// LEFT
	if ((colA > 0) && (colB > 0))
	{
		if ( (usedGrid[rowA][colA-1] == 0) && // Only set/check the A sentence for pre-use. OK A to overlap B.
			 (grid[rowA][colA-1] == grid[rowB][colB-1])) // Characters match
		{
			// Mark neighboring squares so can't return to touch the path
			if (rowA<rows-1) usedGrid[rowA+1][colA]++; // DOWN
			if (colA<cols-1) usedGrid[rowA][colA+1]++; // RIGHT
			if (rowA>0)      usedGrid[rowA-1][colA]++; // UP
			searchSentence(rowA, colA-1, rowB, colB-1, length);
			if (rowA<rows-1) usedGrid[rowA+1][colA]--; // DOWN
			if (colA<cols-1) usedGrid[rowA][colA+1]--; // RIGHT
			if (rowA>0)      usedGrid[rowA-1][colA]--; // UP
		}
	}

	// RIGHT
	if ((colA < cols-1) && (colB < cols-1))
	{
		if ( (usedGrid[rowA][colA+1] == 0) && // Only set/check the A sentence for pre-use. OK A to overlap B.
			 (grid[rowA][colA+1] == grid[rowB][colB+1])) // Characters match
		{
			// Mark neighboring squares so can't return to touch the path
			if (rowA<rows-1) usedGrid[rowA+1][colA]++; // DOWN
			if (colA>0)      usedGrid[rowA][colA-1]++; // LEFT
			if (rowA>0)      usedGrid[rowA-1][colA]++; // UP
			searchSentence(rowA, colA+1, rowB, colB+1, length);
			if (rowA<rows-1) usedGrid[rowA+1][colA]--; // DOWN
			if (colA>0)      usedGrid[rowA][colA-1]--; // LEFT
			if (rowA>0)      usedGrid[rowA-1][colA]--; // UP
		}
	}

	// Searched every direction and can't go any farther.  See if this is a new longest sentence.
	if (length > longestLengthFound)
	{
		longestLengthFound = length;
		for(i=0; i<length;i++)
		{
			longSentence[i] = workSentence[i]; // Copy the new longest sentence
		}			
#ifdef DEBUG
		cout << length << ": "; printSentence();
#endif
	}

	// Restore working variables
	workSentence[length] = 0;
	usedGrid[rowA][colA] = 0;

	return;
}

int main( int argc, char* argv[] )
{
	int row, col;           // Current grid pointers
	int row_match, col_match;
	char m;

	// Prompt
	cout << endl << "Input the rows and columns, then the grid of letters." << endl;

	memset(grid, 0, sizeof(grid));
	memset(longSentence, 0 , sizeof(longSentence));
	row = col = 0;

	cin >> rows;
	cin >> cols;
	while ((m=getchar()) != '\n') {}  // Read to new line

	while (row<rows) // Read in full grid
	{
		m=getchar();
		if (m=='\n')
		{ 
			row++;  // Advance to next row
			col=0;
		}
		else
		{
			grid[row][col++]=m;
		}
	}

#ifdef DEBUG
	//printGrid(rows,cols);
#endif

	// Loop through entire grid.  Search for matching characters.
	//  For each match, search for a sentence.
	for (row=0; row<rows; row++)
	{
		for (col=0;col<cols;col++)
		{
			row_match=row;
			col_match=col+1; // Start on the next character in the grid
			if (col_match==cols)
			{
				col_match=0;
				row_match++; // Advance row if needed
			}
			while (row_match < rows) // Still in the grid
			{
				if (grid[row][col] == grid[row_match][col_match])
				{
					// Search for the sentence
					searchSentence(row, col, row_match, col_match, 0);
				}

				// Move to next character
				if (++col_match==cols) // Advance column
				{
					col_match=0;
					row_match++; // Advance row if needed
				}
			} // while still searching in grid
		} // column loop
	} // row loop

	cout << endl;
	printSentence();
	printSentenceBackward();
	cout << endl;

	cout << "0 to end." <<endl;
	cin >> col;
}