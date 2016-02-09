/* Making a Brute Force ABC Solver
	... to solve Product Distribution/"Easy as ABC" puzzles of up to 6x6

Input
The input is first an integer N representing the size of the square grid.  
The next line shows the first product seen in each column from the top of the 
grid separated by spaces (X means no camera is present.)  The next N lines show 
the first product seen on each row from the left and right of the grid.  
The last line shows the first product seen in each column from the bottom of the grid.

3
A X X
X X
X X
X C
X X X


4
X X X X
X C
X X
A X
A X
X X B B

5
X X X A A
X B
X X
X C
X X
X X
A X C B B
 
Output
Print the NxN grid, with space between each square in a row.  Use a period for empty squares.
Example 1 
A C B
C B A
B A C

Example 2
B . A C
C B . A
. A C B
A C B .

Example 3
C A B . .
B C . . A
. B . A C
. . A C B
A . C B .

*/

//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>

#include <string>
#include <iostream>

using namespace std;

#define MAX_SOLVES 1 // Just to assure we only find one solution.  Datasets must lead to unique solution

// Square size
#define DIMENSION 6

int reqs [DIMENSION][DIMENSION]=
	{ {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0} };

int grid [DIMENSION][DIMENSION]=
	{ {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0},
	  {0,0,0,0,0,0} };

int size;
int topReq [DIMENSION] = {0,0,0,0,0,0};
int botReq [DIMENSION] = {0,0,0,0,0,0};
int leftReq [DIMENSION] = {0,0,0,0,0,0};
int rightReq [DIMENSION] = {0,0,0,0,0,0};

/**********************************************
 * checkElem: Check end conditions or interim
 *            conditions of elements assigned.
 *
 * Return: -1 if current assignments fail.
 *          1 if current assignment is final and correct
 *          0 otherwise (still can't tell).
 **********************************************/
int checkElem(int row, int col)
{
	int i, val, req;
	int newVal;
	int retVal=0;

	val = grid[row][col];

	// Requirements
	if (((req=reqs[row][col]) != 0) && (req != val))
		{retVal = -1; return retVal;}

	// Check Horizontal, Vertical, Row, Col
	//Horizontal
	for (i=0;i<col;i++)
	{
		 if (grid[row][i] == val)
			 {retVal = -1; return retVal;}
	}

	//Vertical
	for (i=0;i<row;i++)
	{
		 if (grid[i][col] == val)
			 {retVal = -1; return retVal;}
	}

	// Placed a letter (not space).  Check leftReq and topReq requirements.
	if (val <= 3) 
	{
		// column/topReq
		if ((topReq[col]>0) && (row<=size-2)) // Requirement exists to check
		{
			for (i=0;(newVal=grid[i][col])>3;i++){} // Find first non-space value
			if (topReq[col] != newVal)
				 {retVal = -1; return retVal;}
		}
		// row/leftReq
		if ((leftReq[row]>0) && (col<=size-2))
		{
			for (i=0;(newVal=grid[row][i])>3;i++){} // Find first non-space value
			if (leftReq[row] != newVal)
				 {retVal = -1; return retVal;}
		}
	}

	//End of Row
	if (col == (size-1))
	{
		// row/rightReq
		if(rightReq[row]>0)
		{
			for (i=size-1;(newVal=grid[row][i])>3;i--){} // Find first non-space value
			if (rightReq[row] != newVal)
				 {retVal = -1; return retVal;}
		}
	}

	// End of Column
	if (row == (size-1))
	{
		// column/bottomReq
		if(botReq[col]>0)
		{
			for (i=size-1;(newVal=grid[i][col])>3;i--){} // Find first non-space value
			if (botReq[col] != newVal)
				 {retVal = -1; return retVal;}
		}
	}

	// Check end
	if ((row==col) && (row==(size-1)) )
		retVal = 1;

	return retVal;
}

/*********************************************
 * solve: Recursive function to assign values
 *        and test for success/failure
 *
 * Parameters: local copies of the used array
 *             and the elements array.
 *             Which element to assign now.
 *
 * Returns: 1 if successful completion, 0 if not.
 *          -1 if bad data.
 *********************************************/
int solve(int row, int col)
{
	int i, checkSuccess,x,y, val;  /* loop count, success variable */
	static int solves=MAX_SOLVES;
	char c;

	if (col >= size)           /* Update row/column */
		{
			row++;
			col=0;
			if (row >= size)
				return -1;  /* Too big */
		}

	for (i=1; i<=size; i++) // Loop through the values
	{
		grid[row][col]=i;

			/* Check here for success.  Return 1 if done.
				Return -1 if fail.  Return 0 if not sure. */
			checkSuccess = checkElem(row,col);
			if (checkSuccess == 1)
			{
				/* Print results here */
				printf ("Solution %2d: \n",
						MAX_SOLVES+1-solves);
				for (x=0;x<size;x++)
				{
					for (y=0;y<size;y++)
					{
						if ((val=grid[x][y])<4)
							c =  ('A' + val - 1);
						else
							c = '.';
						cout << c << ' ';
					}
					printf("\n");
				}
            printf("\n");

				if (--solves <=0) return 1;
			}
			else if (checkSuccess == 0)
				/* Get next value in list, recursively */
				if (solve(row,col+1) == 1)
					return 1;
		 grid[row][col]=0;

	} /* Continue loop to end of values */

	return 0;
}

void fillSpecialReqs()
{
	return; // To be filled in if it's needed
}

int clue(char letter)
{
	if (letter == 'A') return 1;
	if (letter == 'B') return 2;
	if (letter == 'C') return 3;
	return 0;
}

int main()
{
	int i;
	char c;
	cin >> size;
	if (size > DIMENSION) return -1;
	for (i=0;i<size;i++) { cin >> c; topReq[i]=clue(c);}
	for (i=0;i<size;i++) { cin >> c; leftReq[i]=clue(c); cin >> c; rightReq[i]=clue(c);}
	for (i=0;i<size;i++) { cin >> c; botReq[i]=clue(c);}
	fillSpecialReqs(); // Use clues to mark some squares before starting brute force

	solve(0,0);
//	cout<<endl<<"0 to end."<<endl;
//	cin>>i;
	return 0;
}