/* Take as input the moves of a Reversi Game, and output the state of the game.

Example Program Run:
1 ........         1 ........         1 ........         1 ........        1 ........       
2 ........         2 ........         2 ........         2 ........        2 ........       
3 ........         3 ........         3 ........         3 ........        3 ........       
4 ...WB...         4 ...WWW..         4 ...WWW..         4 ...WWW..        4 ...WWW..       
5 ...BW...         5 ...BW...         5 ...BBB..         5 ...BWW..        5 ...BBBB.       
6 ........         6 ........         6 ........         6 .....W..        6 .....W..       
7 ........         7 ........         7 ........         7 ........        7 ........       
8 ........         8 ........         8 ........         8 ........        8 ........       
  abcdefgh           abcdefgh           abcdefgh           abcdefgh          abcdefgh       
White move 1: f4   Black move 2: f5   White move 3: f6   Black move 4: g5  White move 5: END
*/


#include <string>
#include <iostream>

using namespace std;

int grid[8][8];
#define EMPTY 0
#define WHITE 1
#define BLACK 2

// Macro to quickly check range
#define isValid(x) ((x<8) && (x>=0))

void printGrid(void)
{
	int i,j;	

    for (i=0; i<8; i++)
	{
	   	cout << endl << i+1 << " ";  // Row Numbers
		for (j=0; j<8; j++)
	    {
			if (grid[i][j] == WHITE)
				cout << "W";
			else if (grid[i][j] == BLACK)
				cout << "B";
			else
				cout << ".";
	    }
	}
	cout << endl << "  abcdefgh" << endl;  // Column Letters
}

void analyzeMoveDir(int row, int col, int currentMove, int rowDir, int colDir)
{
	int opponent = (currentMove == WHITE? BLACK : WHITE);
	int checkRow, checkCol;
	int i, j;

	// Have to be at least 2 away from edge to be worth checking
	checkRow = row + 2*rowDir; 
	checkCol = col + 2*colDir;

	if (isValid(checkRow) && isValid(checkCol)) // 2 away from edge
		if (grid[row+rowDir][col+colDir] == opponent) 
		{   // First square is opponent. Possibly could have to swap.
			for (i=checkRow, j=checkCol; isValid(i) && isValid(j); i+=rowDir, j+=colDir)
			{
				if (grid[i][j] == EMPTY) // Found empty square - quit
					return;
		        else if (grid[i][j] == currentMove) // Found our color. Swap from here back
				{
					i-=rowDir;
					j-=colDir;
					do {
						grid[i][j] = currentMove;   // Assign current color
					    i-=rowDir;
					    j-=colDir;
					} while (! ((i==row) && (j==col))); // Back to start square
					return;
				}
			}// reached edge - no swap was needed.
		} 
}

void analyzeMove(int row, int col, int currentMove)
{
	// Assign new color.  Then scan grid for any that need to swap to new color.
	grid[row][col] = currentMove;
	analyzeMoveDir(row, col, currentMove, -1,  0); // North
	analyzeMoveDir(row, col, currentMove, -1,  1); // NorthEast
	analyzeMoveDir(row, col, currentMove,  0,  1); // East
	analyzeMoveDir(row, col, currentMove,  1,  1); // SouthEast
	analyzeMoveDir(row, col, currentMove,  1,  0); // South
	analyzeMoveDir(row, col, currentMove,  1, -1); // SouthWest
	analyzeMoveDir(row, col, currentMove,  0, -1); // West
	analyzeMoveDir(row, col, currentMove, -1, -1); // NorthWest
}

int main( int argc, char* argv[] )
{
   int currentMove = WHITE;
   int moveCount = 1;
   int row,col;
   string newMove;

   // Initialize grid starting position
   for (row=0; row<8; row++)
	   for (col=0; col<8; col++)
		   grid[row][col] = EMPTY;
   grid[3][3] = grid[4][4] = WHITE;
   grid[3][4] = grid[4][3] = BLACK;

   while( true )
   {
	   printGrid();
	   if (currentMove == WHITE) cout << "White";
	   else cout << "Black";
	   cout << " move " << moveCount++ << ": ";

       cin >> newMove;
       if( newMove == "END" )
          break;
       row=newMove[1] - '1';
	   col=newMove[0] - 'a'; 
	   analyzeMove(row, col, currentMove);
	   currentMove = (currentMove == WHITE? BLACK : WHITE);
   }

}