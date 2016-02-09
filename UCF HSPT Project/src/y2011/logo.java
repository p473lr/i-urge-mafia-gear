/***********************************
Michael Galletti
04/02/2011
Programming Logo
***********************************/

import java.util.*;
import java.io.*;

public class logo{
	public static void main(String[] args) throws IOException{
		//Open the input file
		Scanner reader = new Scanner(new File("logo.in"));
		int times = reader.nextInt(); //Scan the number of cases
		
		//Declare and initialize the canvas
		char[][] pad = new char[22][32];
		//Set the four corners of the pad to '+'
		pad[0][0] = '+';
		pad[0][31] = '+';
		pad[21][0] = '+';
		pad[21][31] = '+';
		
		//Draw the top and bottom rows
		for(int i = 1; i < 31; i++){
			pad[0][i] = '-';
			pad[21][i] = '-';
		}
		
		//Draw the left and right columns
		for(int i = 1; i < 21; i++){
			pad[i][0] = '|';
			pad[i][31] = '|';
		}
		
		//Initialize all center cells to ' '
		for(int i = 1; i < 21; i++){
			for(int j = 1; j < 31; j++){
				pad[i][j] = ' ';
			}
		}
		
		//Direction arrays
		int[] dr = new int[]{-1, -1, 0, 1, 1, 1, 0, -1}; //Change in row
		int[] dc = new int[]{0, 1, 1, 1, 0, -1, -1, -1}; //Change in column
		
		/* These direction arrays will make turning the turtle a great deal simpler. You may
		   think of the 8 directions the turtle can face like so, where each number indicates
			an index into dr and dc:
			
				  7 0 1
				  6 T 2
				  5 4 3
					 
			Note that each direction has a unique pair of integers added to T's row and column coordinate. 
			For example, position 7 is equal to (T.r-1, T.c-1). These unique pairs of integers are stored 
			in the direction arrays, so we can store the direction the turtle is facing as a single index. 
			So, if the turtle is facing in a particular direction i (corresponding to an index in dr and dc), 
			when it turns right or left we can increment or decrement i, respectively. Note that we must mod this
			new value by 8, as the array wraps around.
			
			Take, for example, a turtle facing position 7 executing the following command:
			
			RIGHT TURN 90
			
			Perform the following calculation:
			
			(7 + (90/45))%8 = 1
			
			The turtle will now face 1, whose dr and dc are (-1, 1). So, on the next move, the turtle will move up (toward row 0) and right (toward column 31).
		*/
		
		//Begin processing cases
		for(int k = 1; k <= times; k++){
			System.out.println("Program #" + k);
			int line = 0; //Current line of the program
			int r = 1; //Row coordinate of the turtle
			int c = 1; //Column coordinate of the turtle
			int dir = 2; //Turtle begins facing right, which we defined as direction 2 in the above explanation
			boolean down = true; //Whether the pen is down or not
			char draw = '*'; //Character to be drawn on move commands
			
			while(true){
				//Read the next token into in
				String in = reader.next();
				
				//If-tree for each command, except for START, which is simply consumed
				if(in.equals("END")){
					//The program terminates, print the canvas and break
					for(int i = 0; i < 22; i++){
						for(int j = 0; j < 32; j++){
							System.out.print(pad[i][j]);
						}
						System.out.println();
					}
					break;
				}else if(in.equals("PEN")){
					//Read in the next token to decide whether to raise or lower the pen
					in = reader.next();
					if(in.equals("UP")) //UP, the pen is no longer down
						down = false;    
					else                //Otherwise, the pen is down
						down = true;
				}else if(in.equals("CHANGE")){
					//Read the next token, which will be CHARACTER as per the problem specification
					reader.next();
					//The next token is the character the turtle will now draw. We read it into draw.
					draw = reader.next().charAt(0);
				}else if(in.equals("MOVE")){
					//The next token is the distance to move.
					int distance = reader.nextInt();
					
					//Only draw on the current space if it is blank and the pen is down
					if(pad[r][c] == ' ' && down){
						pad[r][c] = draw;
					}
					
					//Check if this move will take the turtle out of bounds
					if(r+(distance*dr[dir]) < 1 || r+(distance*dr[dir]) > 20 || c+(distance*dc[dir]) < 1 || c+(distance*dc[dir]) > 30){
						System.out.println("LINE " + line + ": TURTLE OUT OF BOUNDS ERROR");
						
						//Read the rest of the input up until the next END token, then break
						while(!in.equals("END"))
							in = reader.next();
						break;
					}
					
					//Otherwise, if the pen is down, draw a line beginning from the current space, extending distance units away, in direction dir.
					if(down){
						for(int i = 1; i <= distance; i++){
							pad[r+(i*dr[dir])][c+(i*dc[dir])] = draw; //coordinate + (range i)*(direction modifier[dir])
						}
					}
					
					//Change the turtle's current position to the end point
					r = r+(distance*dr[dir]);
					c = c+(distance*dc[dir]);
				}else if(in.equals("RIGHT")){
					//Consume the TURN token
					reader.next();
					
					//Read in the next token, specifying the degrees to turn
					int degrees = reader.nextInt();
					
					//Rotate the turtle using the method we described above
					dir = (dir+(degrees/45))%8;
				}else if(in.equals("LEFT")){
					//Consume the TURN token
					reader.next();
					
					//Read in the next token, specifying the degrees to turn
					//We mod this value by 8 because it will be subtracted from dir before adding 8 to make the net total positive. 
					//We do this to avoid modding a negative number.
					int degrees = (reader.nextInt()/45)%8;
					
					//Rotate the turtle using the method we described above. A left turn means we will be moving left in the direction arrays.
					dir = ((dir-degrees)+8)%8;
				}
				
				//Increment the line counter
				line++;
			}
			
			//Wipe the canvas clean for the next program
			for(int i = 1; i < 21; i++){
				for(int j = 1; j < 31; j++){
					pad[i][j] = ' ';
				}
			}
			
			//Print a blank line between cases
			System.out.println();
		}
	}
}