#include <iostream>
#include <string>

//Define the number of rows and columns, for organization
#define ROWS 20
#define COLS 30
//Define space, for organization
#define SPACE ' '

using namespace std;

int main() {
	
	//Input the number of programs to run
	int T;
	cin >> T;
	
	//Loop through each program
	for (int t=1; t<=T; t++) {
	
		//Initialize program variables
		bool penDown = true; //The pen is down
		char curChar = '*'; //The current character is '*'
		int turtleOutOfBoundsError = -1; //There is not currently a TOOB error
		int curR = 0; //The current row is 0
		int curC = 0; //The current column is 0
		int curDR = 0; //The current change in column (per step) is 0
		int curDC = 1; //The current change in row (per step) is 1
		int curD = 90; //The current movement directions is 90
		int curLine = 1; //The current program line number is 1
		
		char grid[ROWS][COLS]; //Initialize the grid
		//Fill the grid with spaces
		for (int r=0; r<ROWS; r++)
			for (int c=0; c<COLS; c++)
				grid[r][c] = SPACE;
	
		while(turtleOutOfBoundsError == -1) {
		//Loop through the program as long as there is no error
			
			//Input the command
			string command;
			cin >> command;
			
			if (command == "START") 
			//If the command is START, continue inputting commands
				continue;
			else if (command == "END") 
			//If the command is END, this program is done
				break;
			else if (command == "PEN") { 
			//If the command is PEN, set the pen position (UP/DOWN)
			
				string position;
				cin >> position; //Input the pen status
				
				if (position == "DOWN")
					penDown = true; //If the pen is now down
				else
					penDown = false; //If the pen is now up
					
			} else if (command == "CHANGE") {
			//If the command is CHANGE CHARACTER, change the current character
			
				string tmp;
				cin >> tmp >> curChar; //Input "CHARACTER" and the new first character
				
			} else if (command == "MOVE") { 
			//If the command is MOVE, move X number of steps in the current direction
			
				int steps;
				cin >> steps; //Input the number of steps to take
				
				for (int s=0; s<=steps; s++) { 
				//Loop through each step
				
					int newR = curR + s * curDR; //Find the new row
					int newC = curC + s * curDC; //Find the new column
					
					if (newR >= ROWS || newR < 0 || newC >= COLS || newC < 0) { 
					//If there is a TOOB error
						turtleOutOfBoundsError = curLine; //remember the error line...
						break; //...and break of out this loop, the program has failed
					}
					
					if (penDown && (grid[newR][newC] == SPACE || s>0)) 
					//If the pen is down and the current grid space is empty or the current grid space is after this one
						grid[newR][newC] = curChar; //Fill it with the current character
						
				}
				
				if (turtleOutOfBoundsError != -1) 
				//If there was a TOOB error...
					break; //...stop running the program, it has failed
					
				curR += steps * curDR; //Set the new row
				curC += steps * curDC; //Set the new column
				
			} else if (command == "RIGHT") { 
			//If the command is RIGHT, rotate right R degrees
			
				string turn;
				int turnAmount;
				cin >> turn >> turnAmount; //Input "TURN" and the number of degrees to turn right
				
				curD = (curD + turnAmount)%360; //Change the current angle, keeping it circular in 360
				
			} else if (command == "LEFT") { 
			//If the command is LEFT, rotate left R degrees
			
				string turn;
				int turnAmount;
				cin >> turn >> turnAmount; //Input "TURN" and the number of degrees to turn left
				
				curD = ((curD - turnAmount)%360 + 360)%360; //Change the current angle, keeping it circular in 360

			}
			
			//Change the directions of motion in case the movement angle has been changed
			switch(curD) {
				case 0: //Up
					curDR = -1;
					curDC = 0;
					break;
				case 45: //Up right
					curDR = -1;
					curDC = 1;
					break;
				case 90: //Right
					curDR = 0;
					curDC = 1;
					break;
				case 135: //Down right
					curDR = 1;
					curDC = 1;
					break;
				case 180: //Down
					curDR = 1;
					curDC = 0;
					break;
				case 225: //Down left
					curDR = 1;
					curDC = -1;
					break;
				case 270: //Left
					curDR = 0;
					curDC = -1;
					break;
				case 315: //Up left
					curDR = -1;
					curDC = -1;
					break;
			}
			
			//Increment the current line number, we are done with this line
			curLine++;
			
		} //End of program processing for this program
		
		//Output the results of the program
		
		cout << "Program #" << t << endl;
		
		if (turtleOutOfBoundsError != -1) { 
		//If there was a TOOB error
		
			//Output TOOB error, and the line number it occured on
			cout << "LINE " << turtleOutOfBoundsError << ": TURTLE OUT OF BOUNDS ERROR" << endl;
			
			//IMPORTANT! Don't forget to input all remaining commands, so as not to interfere with
			// the next program, since we decided to break out of the program early due to the error.
			string tmp = "";
			while(tmp != "END")
				cin >> tmp;
				
		} else { 
		//Otherwise, if the program did not throw an error, output the grid (with bounding boxes)
		
			//Output upper bound
			cout << '+';
			for (int c=0; c<COLS; c++)
				cout << '-';
			cout << '+' << endl;
			
			//Output grid with side bounds and values
			for (int r=0; r<ROWS; r++) {
				cout << '|';
				for (int c=0; c<COLS; c++)
					cout << grid[r][c];
				cout << '|' << endl;
			}
			
			//Output lower bound
			cout << '+';
			for (int c=0; c<COLS; c++)
				cout << '-';
			cout << '+' << endl;
			
		}
		//Line break at the end of the program output
		cout << endl;
	
	}

	return 0;
	
}
