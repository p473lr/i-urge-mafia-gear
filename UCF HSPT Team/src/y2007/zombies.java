/* Home of the Living Impaired
 * input: zombies.in
 * Solution: Michael Do
 */
 
import java.io.*;
import java.util.*;

public class zombies{
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader stdin = new BufferedReader(new FileReader("zombies.in"));
		StringTokenizer token;
		String input;
		
		// Takes in the total number of cases run
		input = stdin.readLine();
		int numLevels = Integer.parseInt(input);
		
		// Loops through and processes each case until all are complete
		for(int currLevel = 1;currLevel <= numLevels;currLevel++ ){
			
			input = stdin.readLine();
			token = new StringTokenizer(input);
			int width = Integer.parseInt(token.nextToken());  // height of the playing field
			int height = Integer.parseInt(token.nextToken()); // width of the playing field
			
			// sets up playing field, increments the borders by 1 since the
			// field starts at 1,1 and this will avoid having to decrement
			// input coordinates in order to have it correctly correspond to
			// its proper position in the array. 
			boolean[][] playingField = new boolean[height+1][width+1];
			
			// initializes the playing field to be empty of zombies (ie false)
			for(int y = 1;y <= height;y++)
				for(int x = 1;x <= width;x++)
					playingField[y][x] = false;
			
			// takes in how many zombies there are as well as initialize the
			// zombie kill counter.
			input = stdin.readLine();		
			int numZombies = Integer.parseInt(input), zombiesKilled = 0;
			
			// for each zombie, it takes in the x and y coordinate of that zombie
			// and marks their position in the 2d array (by changing false to true) 
			for(int currZombie = 0;currZombie < numZombies;currZombie++){
				
				input = stdin.readLine();
				token = new StringTokenizer(input);
				int x = Integer.parseInt(token.nextToken());
				int y = Integer.parseInt(token.nextToken());
				playingField[y][x] = true;
				
			}
			
			// Afterwards we take in directions to see if we come within proximity
			// of a zombie in order to kill it. Whenever we come into contact with
			// a zombie, we change the marker back to false, since the zombies don't
			// respawn, so if we backtrack to a previous position, we won't re-kill
			// a zombie.
			
			// checks if there is a zombie in the initial position
			if(playingField[1][1]){
				playingField[1][1] = false;
				zombiesKilled++;
			}
			
			// takes in the number of moves made and set our initial position
			input = stdin.readLine();
			int numMoves = Integer.parseInt(input);
			int x = 1, y = 1;
			
			// Checks each move:
			//	 N means incrementing y
			//	 S means decrementing y
			//	 E means incrementing x
			//	 W means decrementing x
			for(int currMove = 0;currMove < numMoves;currMove++){
				
				String dir = stdin.readLine();
				if(dir.compareTo("N") == 0) y++;
				if(dir.compareTo("S") == 0) y--;
				if(dir.compareTo("E") == 0) x++;
				if(dir.compareTo("W") == 0) x--;
				
				// checks if a zombie is in current position
				// if so, mark it dead and increment the kill counter
				if(playingField[y][x]){
					zombiesKilled++;
					playingField[y][x] = false;
				}
				
			}
			
			// Prints out kill count for the current input case
			System.out.printf("Level %d: Crystel vanquished %d zombies.\n", currLevel, zombiesKilled);
			
		}
		
		
	}
	
}