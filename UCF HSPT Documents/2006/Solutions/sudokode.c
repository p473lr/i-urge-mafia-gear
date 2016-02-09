/**
 * The idea is to take each board in as input and make sure that each of the rows,
 *  columns, and 3x3 squares individually used each number only once.  Basically,
 *  just do what the problem says.
 */

#include <stdio.h>

#define FALSE 0
#define TRUE 1

/* Function that makes sure row i only uses each number once.  Expects 0-8 for
 *  the values in the board.  Returns TRUE if so and FALSE if not.
 */
int checkRow(int board[9][9], int i)
	{
	int used[9];
	int j;
	
	for (j = 0; j < 9; j++)
		used[j] = FALSE;
	
	/* Go through the board values.  If the board value has been used, break
	   so that j won't equal 9.  If j equals 9, all board values work */
	for (j = 0; j < 9; j++)
		{
		if (used[board[i][j]])
			break;
		
		used[board[i][j]] = TRUE;
		}
	
	return (j == 9);
	}

/* Function that makes sure column j only uses each number once.  Expects 0-8 for
 *  the values in the board.  Returns TRUE if so and FALSE if not.
 */
int checkCol(int board[9][9], int j)
	{
	int used[9];
	int i;
	
	for (i = 0; i < 9; i++)
		used[i] = FALSE;
	
	/* Go through the board values.  If the board value has been used, break
	   so that i won't equal 9.  If i equals 9, all board values work */
	for (i = 0; i < 9; i++)
		{
		if (used[board[i][j]])
			break;
		
		used[board[i][j]] = TRUE;
		}
	
	return (i == 9);
	}

/* Function that makes sure that the 3x3 subsquare starting at row i and column j
 *  only uses each number once.  Expects 0-8 for the values in the board.  Returns
 *  TRUE if so and FALSE if not.
 */
int checkSquare(int board[9][9], int i, int j)
	{
	int used[9];
	int k, l;
	
	for (k = 0; k < 9; k++)
		used[k] = FALSE;

	/* Go through the board values.  If the board value has been used, break
	   so that k won't equal 3.  If k equals 3, all board values work */
	for (k = 0; k < 3; k++)
		{
		for (l = 0; l < 3; l++)
			{
			if (used[board[i+k][j+l]])
				break;
			
			used[board[i+k][j+l]] = TRUE;
			}
		}
	
	return (k == 3);
	}

int main()
	{
	int numBoards;
	int i, j;
	int check;
	int sudoku[9][9];
	char c;
	int caseNum;
	
	FILE *in = fopen("sudokode.in", "r");
	
	/* get number of boards and then process each board */
	fscanf(in, "%d ", &numBoards);
	for (caseNum = 1; caseNum <= numBoards; caseNum++)
		{
		/* get input */
		for (i = 0; i < 9; i++)
			{
			for (j = 0; j < 9; j++)
				{
				/* convert values to 0-8 to make indexing easier */
				fscanf(in, "%c ", &c);
				sudoku[i][j] = c - '1';
				}
			}
		
		/* test all rows */
		check = TRUE;
		for (i = 0; i < 9 && check == TRUE; i++)
			check = checkRow(sudoku, i);
		
		/* test all columns */
		for (j = 0; j < 9 && check == TRUE; j++)
			check = checkCol(sudoku, j);
		
		/* test all 3x3 subsquares */
		for (i = 0; i < 9 && check == TRUE; i += 3)
			{
			for (j = 0; j < 9 && check == TRUE; j += 3)
				{
				check = checkSquare(sudoku, i, j);
				}
			}
		
		/* print output */
		printf("Sudoku #%d:  ", caseNum);
		if (check)
			printf("Dave's the man!\n\n");
		else
			printf("Try again, Dave!\n\n");
		}
	
	fclose(in);
	return 0;
	}