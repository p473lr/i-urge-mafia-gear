#include <iostream>
#include <fstream>

using namespace std;

// checks if there are repeating elements in puzzle in between rows rowLow to rowHigh
//  and columns colLow and colHigh.  Only checks values between 0 through 9.
bool checkNine(int puzzle[9][9], int rowLow, int rowHigh, int colLow, int colHigh)
	{
	int r, c;
	bool used[10];
	
	// set used table to false
	for (r = 0; r < 10; r++)
		used[r] = false;
	
	// check passed blocks for values... if a value is used twice, then say wrong answer
	for (r = rowLow; r <= rowHigh; r++)
		{
		for (c = colLow; c <= colHigh; c++)
			{
			if (used[puzzle[r][c]])
				return false;
			else
				used[puzzle[r][c]] = true;
			}
		}
	
	// no values were reused so return true
	return true;
	}

int main()
	{
	ifstream infile;
	infile.open("sudokode.in", ios::in);
	
	int board[9][9];
	char c;
	int i, j;
	bool correct;
	
	// get number of puzzles
	int n;
	infile >> n;
	
	// get every puzzle and evaluate it
	for (int sudokuNum = 1; sudokuNum <= n; sudokuNum++)
		{
		for (i = 0; i < 9; i++)
			{
			for (j = 0; j < 9; j++)
				{
				infile >> c;
				board[i][j] = c - '0';
				}
			}
		
		// use correct to see if puzzle is solved
		correct = true;
		
		// check rows (row - same row, 9 elements in the column)
		for (i = 0; i < 9 && correct; i++)
			correct = checkNine(board, i, i, 0, 8);
		
		// check columns (column - 9 elements in row, same column)
		for (j = 0; j < 9 && correct; j++)
			correct = checkNine(board, 0, 8, j, j);
		
		// check 3x3 subsquares (use 3 elements in row, 3 elements in column)
		for (i = 0; i < 9 && correct; i += 3)
			{
			for (j = 0; j < 9 && correct; j += 3)
				{
				correct = checkNine(board, i, i+2, j, j+2);
				}
			}
		
		// output whether or not correct made it through all the checks
		cout << "Sudoku #" << sudokuNum << ":  ";
		if (correct)
			cout << "Dave's the man!" << endl << endl;
		else
			cout << "Try again, Dave!" << endl << endl;
		}
	
	infile.close();
	return 0;
	}