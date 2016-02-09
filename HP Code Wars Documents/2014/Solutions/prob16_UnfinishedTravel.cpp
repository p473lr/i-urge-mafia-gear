/*
   Your program must replace all of the digits in the input with appropriate letters 
   so that all of the provided words can be found in the grid in a word-search fashion 
   (horizontally or vertically, in any direction, but NOT diagonally.) 
   Letters may be used in more than one word, but missing letters are used in 
   exactly one word, and no word is missing more than one letter. Careful! 
   Each digit could represent different letters to complete different words, 
   but only one set of replacements will result in all words being found. 
   Each word will be found exactly once in the final grid.

Input
The first line contains two integers X Y, representing the number of rows and columns in the data. 
Maximum of 10 rows and 20 columns.
The next X lines include the grid of letters, Y characters at a time. 
It consists of only capital letters (A-Z) and numbers (1-9) marking the location 
of missing letters. After the grid is a line with a single integer N (less than 20), 
then N lines with the words to be found in the search grid.

5 13
SLH8UROW3LIDE
92OGAMEANDERL
UJPCRA5LUGCO4
R7TNUASK6PALM
CGXY1LIMBERLA
14
JOG
RUN
HOP
WALK
RUSH
RACE
CLIMB
AMBLE
SLIDE
SAUNTER
MEANDER
CRAWL
SKIP
ROLL

Output
Print in numerical order one line for each digit, its replacement letter, 
and the word it is in, each separated by one space.
1 C CLIMB
2 J JOG
3 S SLIDE
4 B AMBLE
5 W CRAWL
6 I SKIP
7 E SAUNTER
8 S RUSH
9 N RUN
*/

//#define DEBUG 1 // For debug print statements

#include <string>
#include <iostream>

using namespace std;

#define MAX_ROWS 10
#define MAX_COLS 20
#define MAX_WORDS 20
#define MAX_WORDLENGTH 20

// Directions to search
#define NORTH 1
#define EAST 2
#define SOUTH 3
#define WEST 4
#define NORTHEAST 5
#define SOUTHEAST 6
#define SOUTHWEST 7
#define NORTHWEST 8

// Not using diagonals for this problem
#define MAX_DIRECTION WEST

typedef struct
{
	int row;
	int col;
	int wordIndex;  // Which word uses this
	char letter;
} digit_entry;

typedef struct
{
	int digit;
	int length;
	char word[MAX_WORDLENGTH];
} word_entry;

// Global values

int rows, cols, words; // Global values for the user-supplied size of the grid

char grid[MAX_ROWS][MAX_COLS]; // Array of characters

word_entry wordlist[MAX_WORDS];
digit_entry digitlist[10];

void printGrid(void)
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

void printDigit(int digit)
{
	int wordIndex = digitlist[digit].wordIndex;

	if (digitlist[digit].letter > 0)
	{
		cout << digit <<" "<< digitlist[digit].letter <<" "; 
		cout << wordlist[wordIndex].word;		
		cout << endl;
	}
}

bool wordExists(int wordIndex, int row, int col, int direction)
{
	int i, length, rowEnd, colEnd;
	int currentRow, currentCol;
	int digit;
	char wordChar, gridChar;
	int rowDelta = 0;
	int colDelta = 0;

	switch (direction)
	{
		case NORTH: rowDelta = -1; break;
		case SOUTH: rowDelta =  1; break;
		case EAST : colDelta =  1; break;
		case WEST : colDelta = -1; break;
		case NORTHEAST: rowDelta = -1; colDelta =  1; break;
		case SOUTHEAST: rowDelta =  1; colDelta =  1; break;
		case SOUTHWEST: rowDelta =  1; colDelta = -1; break;
		case NORTHWEST: rowDelta = -1; colDelta = -1; break;
		default: break;
	}

	// First test word fits in grid
	length = wordlist[wordIndex].length;
	rowEnd = row + (length-1)*rowDelta;
	colEnd = col + (length-1)*colDelta;
	if ((rowEnd < 0) || (rowEnd >= rows) ||
		(colEnd < 0) || (colEnd >= cols) )
		return false;

	// Test each letter for a match
	for (i=0;i<length;i++)
	{
		currentRow = row + (i*rowDelta);
		currentCol = col + (i*colDelta);
		wordChar = wordlist[wordIndex].word[i];
		gridChar = grid[currentRow][currentCol];
		if (wordChar == gridChar)
			continue;  // We've a match, go to next letter

		// Not a match.  See if we have found a digit we could replace
		if ( !((gridChar >= '1') && (gridChar <= '9')) ) // If NOT a digit, leave
			return false;

		// We found a digit.  First make sure we haven't already replaced a digit for this word
		if (wordlist[wordIndex].digit > 0)
			return false;

		//Replace with desired letter and keep searching
		digit = gridChar - '0';
		grid[currentRow][currentCol] = wordChar;
		wordlist[wordIndex].digit = digit;
		digitlist[digit].letter = wordChar;
		digitlist[digit].wordIndex = wordIndex;
	}

	// If we made it this far, we found the entire word
	return true;
}

void restoreWordAndGrid(int wordIndex)
{
	int digit = wordlist[wordIndex].digit;
	if (digit > 0)
	{
		digitlist[digit].letter=0;
		grid[digitlist[digit].row][digitlist[digit].col] = ('0'+digit);
		wordlist[wordIndex].digit = 0;
	}
}

int findWord(int wordIndex)
{
	int row, col, direction;

	if (wordIndex >= words) // Found all words
		return 1;

	// Loop through entire grid to pick a start square.  
	for (row=0; row<rows; row++)
	{
		for (col=0;col<cols;col++)
		{
			for (direction = 1; direction<=MAX_DIRECTION; direction++)
			{
				// See if word exists at this location
				if (wordExists(wordIndex, row, col, direction))
				{
					if (findWord(wordIndex+1))  // Move to next word
						return 1;
				}
				restoreWordAndGrid(wordIndex);
			} // direction loop
		} // column loop
	} // row loop
	return 0;
}


int main( int argc, char* argv[] )
{
	int row, col, wordCount, letterIndex;           // Current grid pointers
	int digit;
	char m;

	// Prompt
	cout << endl << "Input the rows and columns, then the grid of letters." << endl;

	memset(grid, 0, sizeof(grid));
	memset(wordlist, 0 , sizeof(wordlist));
	memset(digitlist, 0 , sizeof(digitlist));
	row = col = 0;

	cin >> rows;
	cin >> cols;
	while ((m=getchar()) != '\n') {}  // Read to new line

	// Read in full grid
	while (row<rows) 
	{
		m=getchar();
		if (m=='\n')
		{ 
			row++;  // Advance to next row
			col=0;
		}
		else
		{
			if ( (m >= '1') && (m <= '9')) // Read in a digit.  Record its location
			{
				digit = m-'0';
				digitlist[digit].row = row;
				digitlist[digit].col = col;
			}
			grid[row][col++]=m;
		}
	}

	// Read in all words
	cin >> words;
	while ((m=getchar()) != '\n') {}  // Read to new line
	// Read in each word
	wordCount=0;
	while (wordCount<words) 
	{
		letterIndex=0;
		m=getchar();
		while (m!='\n')
		{ 
			wordlist[wordCount].word[letterIndex] = m;
			letterIndex++;  // Advance to next row
			m=getchar();
		}
		wordlist[wordCount].length = letterIndex;
		wordCount++;
	}

	cout << endl<<"Initial Grid:";
	printGrid();

	// Recursive call to find all words in the grid, replacing digits as needed.
	findWord(0);

#ifdef DEBUG
	cout << "Final Grid:"<<endl;
	printGrid();
#endif

	cout << endl;
	for (digit=1;digit<=9;digit++)
		printDigit(digit);
	cout << endl;

	// Only needed for debug
	//cout << "0 to end." <<endl;
	//cin >> col;
}