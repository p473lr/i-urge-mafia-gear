import java.io.*;
import java.util.*;
public class lava {
	public static boolean[][] visited;
	public static boolean[][] lava;
	public static void main(String[] Args) throws IOException {
		Scanner input = new Scanner(new File("lava.in"));
		
		//read in the number of rooms
		int num_rooms = input.nextInt();
		
		//loop through all cases
		for(int count = 1; count <= num_rooms; count++) {
			
			//read in number of rows, columns, and steps taken 
			int r = input.nextInt();
			int c = input.nextInt();
			int num_steps = input.nextInt();
			
			//variables used to locate starting location
			int st_row = 0;
			int st_col = 0;
			
			//visited[i][j] tells me if I have visited the furniture in the ith row and jth column
			visited = new boolean[r][c];

			//lava[i][j] tells me if there is lava in the ith row and jth column
			lava = new boolean[r][c];
			
			//read in the room layout and initialize variables in a loop
			for(int row_count = 0; row_count < r; row_count++) {
				
				//row is the row we are currently reading in
				String row = input.next();
				
				//loop through the row
				for(int col_count = 0; col_count < c; col_count++) {
					
					//set visit to false
					visited[row_count][col_count] = false; 
					
					//if we don't see lava set the lava to false else there is lava
					if(row.charAt(col_count) != 'L') {
						
						lava[row_count][col_count] = false; 
					}
					else{

						lava[row_count][col_count] = true; 
					} 
					
					//if we started here then set this square as our starting location and say we have visited
					if(row.charAt(col_count) == 'S') {

						st_row = row_count;
						st_col = col_count;
						visited[row_count][col_count] = true; 
					}
				}
			}

			// cols and rows will keep track of the cols and rows we can leave from
			Queue<Integer> cols = new LinkedList<Integer>();
			Queue<Integer> rows = new LinkedList<Integer>();
			
			//since we start at st_row, st_col we can leave from it provided we can take at least 1 step
			cols.add(st_col);
			rows.add(st_row);
			
			//loop through the number of steps taken until we can't take anymore
			for(int steps_taken = 1; steps_taken <= num_steps; steps_taken++) {

				//these will be the new columns and rows we can leave from
				Queue<Integer> new_cols = new LinkedList<Integer>();
				Queue<Integer> new_rows = new LinkedList<Integer>();
				
				//this will tell me if I have visited a new square with the new step
				boolean found_new_square = false;
				
				//while there is still a square we can step from we loop
				while(!cols.isEmpty()&&!rows.isEmpty()) {
					
					//examine the current spot we can leave from
					int curr_col = cols.poll();
					int curr_row = rows.poll();
					
					//case 1 if there is a square to the left of us check it
					if(curr_col > 0) {
						
						//if we have not visited it and it is not lava step on it
						if(!visited[curr_row][curr_col - 1]&&!lava[curr_row][curr_col - 1]) {
							
							//we have now visited the square to the left we can leave from it if there is remaining steps left
							visited[curr_row][curr_col - 1] = true;
							new_cols.add(curr_col - 1);
							new_rows.add(curr_row);
							
							//we have found a new square to step from
							found_new_square = true;
						}
					}
					
					//case 2 similar to case 1 except we are going up instead
					if(curr_row > 0) {
						
						//check
						if(!visited[curr_row - 1][curr_col]&&!lava[curr_row - 1][curr_col]) {
							
							//set to true and queue the new square
							visited[curr_row - 1][curr_col] = true;
							new_cols.add(curr_col);
							new_rows.add(curr_row - 1);
							
							// we found a new square
							found_new_square = true;
						}
					}
					
					//case 3 is the same as case 1 and 2 except we are going right
					if(curr_col + 1 < c) {
						
						//check
						if(!visited[curr_row][curr_col + 1]&&!lava[curr_row][curr_col + 1]) {
							
							//set to true and queue
							visited[curr_row][curr_col + 1] = true;
							new_cols.add(curr_col + 1);
							new_rows.add(curr_row);
							
							//found one
							found_new_square = true;
						}
					}
					
					//case 4 same as 1, 2, and 3
					if(curr_row + 1 < r) {
						
						//check
						if(!visited[curr_row + 1][curr_col]&&!lava[curr_row + 1][curr_col]) {
							
							//change variables
							visited[curr_row + 1][curr_col] = true;
							new_cols.add(curr_col);
							new_rows.add(curr_row + 1);
							
							//found one
							found_new_square = true;
						}
					}
				}
				
				//we now have a new set of squares to use so change the old cols and old rows to new cols and new rows
				cols = new_cols;
				rows = new_rows;
				
				//if we did not find anything new stop trying to step
				if(!found_new_square)
					break;
			}
			
			//this will tell me how many squares I was able to visit
			int visited_count = 0;

			//loop through the entire room if we visited a square add 1 to the number I visited
			for(int row_count = 0; row_count < r; row_count++) {
				for(int col_count = 0; col_count < c; col_count++) {
					if(visited[row_count][col_count])
						visited_count++;
				}
			}
			
			//check if I only visited 1 square else print the number of squares I visisted
			if(visited_count == 1)
				System.out.printf("Room #%d: They can reach 1 piece of furniture.%n",count);
			else
				System.out.printf("Room #%d: They can reach %d pieces of furniture.%n",count, visited_count);
			
			// new line if we are not done
			if(count!=num_rooms)
				System.out.printf("%n");
					
		}
	}
}
