/* 
Sapphire Search
The following note was recovered from the newly uncovered ruins. 
It’s unfortunate the archaeologist didn’t record where the note was found, 
since it refers to a treasure located elsewhere in the walls.

    I buried my sapphire then started walking. 
	I always walked in a straight line following a compass direction (N, S, E, W). 
	When I stopped, I made a right turn and continued walking. 
	I might have crossed my path, but I don’t remember. 
	Below are the number of meters I travelled in each direction. 
	I’m now lost and must abandon this record while I search for a way out. 
	I’m placing this note under a rock at my final location. 
	Perhaps some lucky adventurer will decode my note and retrace my steps to earn the treasure.
	
Input
The first line contains two integers X Y, representing the number of rows and columns in the ruins. Maximum of 20 rows and 50 columns.
The next X lines show a grid map of the space. A period “.” is an empty square. A hash “#” is a large boulder, marking a square that cannot be entered.
The next line has an integer N, the count of the straight paths walked. Maximum of 20 paths.
The last line contains N integers, showing the successive path-lengths.

Output
Your program must print the same map, with the location of both the Sapphire (S) and the final location of the message (F) marked. 
There is only one route which follows the path-lengths in the list.

Example Input:
5 10
##########
#........#
#.#...##.#
#...#....#
##########
8
2 4 2 2 2 5 2 1

Example Output:
##########
#........#
#.#...##.#
#...#S.F.#
##########

##########
#b.e.a..f#
#.#...##.#
#c.d#S.Fg#
##########


20 41
#########################################
#.......................................#
#....####.....####....#####....######...#
#...#....#...#....#...#....#...#........#
#...#........#....#...#....#...#........#
#...#........#....#...#....#...####.....#
#...#........#....#...#....#...#........#
#...#....#...#........#........#........#
#....####.....####....#####....######...#
#.......................................#
#.......................................#
#...#....#.....##.....#####.....#####...#
#...#....#....#..#....#....#...#........#
#...#....#...#........#........#........#
#...#....#...######...#####.....####....#
#...#.##.#...#....#...#....#........#...#
#...##..##...#....#...#....#........#...#
#...#....#...#....#...#....#...#####....#
#.......................................#
#########################################
20
8 8 6 4 3 3 3 5 6 4 1 1 1 5 5 19 12 3 3 2

#########################################
#.......................................#
#....####.....####....#####....######...#
#...#.F..#...#....#...#....#...#........#
#...#........#....#...#....#...#........#
#...#........#....#...#....#...####.....#
#...#........#....#...#....#...#........#
#...#....#...#........#........#........#
#....####.....####....#####....######...#
#.....................................S.#
#.......................................#
#...#....#.....##.....#####.....#####...#
#...#....#....#..#....#....#...#........#
#...#....#...#........#........#........#
#...#....#...######...#####.....####....#
#...#.##.#...#....#...#....#........#...#
#...##..##...#....#...#....#........#...#
#...#....#...#....#...#....#...#####....#
#.......................................#
#########################################

#########################################
#.............................b.......a.#
#....####.....####....#####....######...#
#...#.F.s#...#....#...#....#...#........#
#...#........#....#...#f..e#...#........#
#...#........#....#...#....#...####.....#
#...#...r..q.#....#...#....#...#........#
#...#....#...#........#g..d.h.c#........#
#....####.....####....#####....######...#
#.....................................S.#
#.......................................#
#...#....#.....##.....#####.....#####...#
#...#....#....#..#....#.kl.#...#........#
#...#....#...#........#.jm..i.n#........#
#...#....#...######...#####.....####....#
#...#.##.#...#....#...#....#........#...#
#...##..##...#....#...#....#........#...#
#...#....#...#....#...#....#...#####....#
#..........p..................o.........#
#########################################

20 50
##################################################
#................................................#
#.....#.........#.........#.........#.........#..#
#.....#.........#.........#.........#.........#..#
#.....#..#####..#..#####..#..#####..#..#####..#..#
#.....#.........#.........#.........#.........#..#
#.....#....#....#....#....#....#....#....#....#..#
#.....#....#....#....#....#....#....#....#....#..#
#..........#.........#.........#.........#.......#
#...#####..#..#####..#..#####..#..#####..#..######
#..........#.........#.........#.........#.......#
#.....#....#....#....#....#....#....#....#....#..#
#.....#.........#.........#.........#.........#..#
#.....#..#####..#..#####..#..#####..#..#####..#..#
#.....#.........#.........#.........#.........#..#
#.....#....#....#....#....#....#....#....#....#..#
#..........#.........#.........#.........#.......#
#...#####..#..#####..#..#####..#..#####..#..######
#..........#.........#.........#.........#.......#
##################################################
20
3 4 5 4 5 5 5 4 20 4 5 5 5 4 5 4 6 6 1 7

##################################################
#................................................#
#.....#.........#.........#.........#.........#..#
#.....#.........#.........#.........#.........#..#
#.....#..#####..#..#####..#..#####..#..#####..#..#
#.F...#.........#.........#.........#.........#..#
#.....#....#....#....#....#....#....#....#....#..#
#.....#....#....#....#....#....#....#....#....#..#
#..........#.........#.........#.........#.......#
#...#####..#..#####..#..#####..#..#####..#..######
#..........#.........#.........#.........#.......#
#.....#....#....#....#....#....#....#....#....#..#
#.....#.........#.........#.........#.........#..#
#.....#..#####..#..#####..#..#####..#..#####..#..#
#.....#.........#.........#.........#.........#..#
#.....#....#....#....#....#....#....#....#....#..#
#..........#.........#.........#.........#.......#
#...#####..#..#####..#..#####..#..#####..#..######
#..........#.........#....S....#.........#.......#
##################################################

##################################################
#.......................i...................h....#
#.....#.........#.........#.........#.........#..#
#.....#.........#.........#.........#.........#..#
#.....#..#####..#..#####..#..#####..#..#####..#..#
#.F...#.........#..k....j.#.........#..f....g.#..#
#.....#....#....#....#....#....#....#....#....#..#
#.....#....#....#....#....#....#....#....#....#..#
#..........#.........#.........#.........#.......#
#...#####..#..#####..#..#####..#..#####..#..######
#..........#..m....l.#.........#..d....e.#.......#
#.....#....#....#....#....#....#....#....#....#..#
#.sr..#.........#.........#.........#.........#..#
#.....#..#####..#..#####..#..#####..#..#####..#..#
#.....#..o....n.#.........#..b....c.#.........#..#
#.....#....#....#....#....#....#....#....#....#..#
#..........#.........#.........#.........#.......#
#...#####..#..#####..#..#####..#..#####..#..######
#..q.....p.#.........#....S..a.#.........#.......#
##################################################
*/


#include <string>
#include <iostream>

using namespace std;

#define WALL 10001
#define PATH 10002

#define NO_DIRECTION 0
#define NORTH 1
#define EAST 2
#define SOUTH 3
#define WEST 4

// Global Variables
int maze[20][50];     // Maze for solving
int totalPaths;
int paths[20];
int rows, columns;
int startRow, startColumn, finishRow, finishColumn;

void printMaze(void)
{
	int i,j;	

    for (i=0; i<rows; i++)
	{
	   	cout << endl;
		for (j=0; j<columns; j++)
	    {
			if ((i==startRow) && (j==startColumn))
				cout << "S";
			else if ((i==finishRow) && (j==finishColumn))
				cout << "F";
			else if (maze[i][j] == WALL)
				cout << "#";
			else if (maze[i][j] != PATH)   // Debug only
				cout << (char)maze[i][j];  // Debug only
			else
				cout << ".";
	    }
	}
	//cout << endl;
}

// From the provided Row and Column, analyze the maze recursively until the finish is found
// Return 1 when finish is reached, else 0
int analyzeMaze(int row, int col, int pathNumber, int entryDirection)
{
	int i;
	int pathLength;
	int originalValue;
	bool wallFound;

	
	if (pathNumber == totalPaths) // Check for end condition
	{
		finishRow = row;
		finishColumn = col;
		printMaze();  // Print all solutions (should be only one)
		return 0;     // return 1 to only show 1 solution 0 to show all
	}
	else if (pathNumber == 0) // Store Start Location
	{
		startRow = row;
		startColumn = col;
	}
	pathLength = paths[pathNumber];

	// Store location - used in debug section of printMaze()
	originalValue = maze[row][col];
	maze[row][col] ='a'+pathNumber-1;

	if ((entryDirection != NORTH) && (entryDirection != SOUTH))
	{
		// Look NORTH
		if (row-pathLength >= 0) // Don't go beyond array
		{
			wallFound = false;
			for (i=1;!wallFound && i<=pathLength;i++)
				wallFound = (maze[row-i][col] == WALL);
			if (!wallFound)
				if (analyzeMaze(row-pathLength, col, pathNumber+1, NORTH)) 
					return 1;
		}
		// Look SOUTH
		if (row+pathLength < rows) // Don't go beyond array
		{
			wallFound = false;
			for (i=1;!wallFound && i<=pathLength;i++)
				wallFound = (maze[row+i][col] == WALL);				
			if (!wallFound)
				if (analyzeMaze(row+pathLength, col, pathNumber+1, SOUTH))
					return 1;
		}
	} // NORTH and SOUTH
	
	if ((entryDirection != EAST) && (entryDirection != WEST))
	{
		// Look WEST
		if (col-pathLength >= 0) // Don't go beyond array
		{
			wallFound = false;
			for (i=1;!wallFound && i<=pathLength;i++)
				wallFound = (maze[row][col-i] == WALL);				
			if (!wallFound)
				if (analyzeMaze(row, col-pathLength, pathNumber+1, WEST))
					return 1;
		}
		// Look EAST
		if (col+pathLength < columns) // Don't go beyond array
		{
			wallFound = false;
			for (i=1;!wallFound && i<=pathLength;i++)
				wallFound = (maze[row][col+i] == WALL);
			if (!wallFound)
				if (analyzeMaze(row, col+pathLength, pathNumber+1, EAST))
					return 1;
		}
	} // EAST and WEST

	// Restore location
	maze[row][col] = originalValue;

	return 0;
}

int main( int argc, char* argv[] )
{
	int i,j, done;
	string lineIn;

	cout << "Enter Rows, Columns and Maze. Then Number of Paths and the Path Lengths." << endl;
	cin >> rows;
	cin >> columns;

	// Make a numerical grid of the maze
	for (i=0; i<rows; i++)
	{
		cin >> lineIn;
		for (j=0; j<columns; j++)
		{
			if (lineIn[j] == '#')
				maze[i][j] = WALL;
			else
				maze[i][j] = PATH;
		}
	}
	// Now retrieve the number of paths and their path lengths
	cin >> totalPaths;
	for (i=0;i<totalPaths;i++)
	{
		cin >> paths[i];
	}

	// Print Maze to verify input ok.
    //cout << endl << "Input Maze:";
	//printMaze();
   
	// Loop through starting locations
	done = 0;
	for (i=0; !done && i<rows; i++)
		for(j=0; !done && j<columns; j++)
		{
			if (maze[i][j] != WALL)
				done = analyzeMaze(i, j, 0, NO_DIRECTION);
		}
	//printMaze();	

	// Prompt to Exit (Only  needed for debugger)
	cout << endl << "0 to End: ";
	cin >> lineIn;
}