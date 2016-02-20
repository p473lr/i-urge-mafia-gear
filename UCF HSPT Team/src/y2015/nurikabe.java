/*********************
 * 
 * This problem boils down to following the given rules, there's not much else
 * to it other than knowledge of some search technique (breadth- or depth-first). 
 * Given a completed board, we will perform a series of checks (described in the 
 * problem statement), and finally output whether the board is  valid. The checks 
 * we perform will be the following:
 *
 *    1. Ensure there are no pools in the map (2x2 blocks of black cells)
 *    2. Ensure each numbered cell is disconnected from each other numbered cell
 *    3. Ensure each numbered cell is connected to an appropriate number of white cells
 *    4. Ensure each black cell is connected to each other black cell
 *    5. Ensure each white cell is connected to some numbered cell
 *
 * We can perform check number 1 by simply examining each 2x2 block of cells for the
 * described condition. To perform checks 2-5, we will maintain a "visited" boolean array
 * of size n x m. We will then perform a breadth-first search from each numbered cell in
 * the map to all adjacent white cells, marking each cell visited in this manner. During
 * a breadth-first search, we know that if we encounter a numbered cell that isn't the
 * source of the search, then check number 2 must be in violation. We can also count
 * the number of cells we visit during the search, which will perform check number 3. We
 * partially perform check 4 by selecting a single black cell (if any exist) and performing
 * a breadth-first search, visiting only black cells. Checks 4 and 5 are then performed
 * simultaneously by confirming that each and every cell has been visited by a breadth-
 * first search at the end of our algorithm. Finally, we report the result.
 *
 *********************/

import java.util.*;
import java.io.*;

public class nurikabe{
	//Direction arrays used to arithmetically find the adjacent neighbors of a cell
	public static int[] dx = {-1,0,1,0};
	public static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		
		int times = reader.nextInt();
		for(int t = 1; t <= times; t++){
			//Read input
			int n = reader.nextInt();
			int m = reader.nextInt();
			int k = reader.nextInt();
			
			//map[i][j] will denote whether cell (i,j) is filled or not
			boolean[][] map = new boolean[n][m];
			//numbered[i][j] will be zero for all cells other than number cells, which will have a value > 0
			int[][] numbered = new int[n][m]; //Initialized to 0 by java
			for(int i = 0; i < n; i++){
				String line = reader.next();
				for(int j = 0; j < m; j++)
					map[i][j] = line.charAt(j) == '#'; //true if cell is filled
			}
			
			for(int i = 0; i < k; i++){
				int x = reader.nextInt()-1;
				int y = reader.nextInt()-1;
				int v = reader.nextInt();
				
				//Mark as a numbered cell
				numbered[x][y] = v;
			}
			
			//Assume the board is good until proven otherwise
			boolean good = true;
			
			//Check 1
			//Perform pool checks: no 2x2 block of cells is completely filled in
			for(int i = 0; i < n-1; i++) //Iterate to i < n-1 so we can look ahead one row
				for(int j = 0; j < m-1; j++) //Iterate to j < m-1 so we can look ahead one column
					//Check the 2x2 block of cells!
					good &= !(map[i  ][j] && map[i  ][j+1] &&
							  map[i+1][j] && map[i+1][j+1]);
			
			//Checks 2-4
			boolean blackCheck = false; //Boolean to denote whether we've performed the partial step for check 4
			boolean[][] visited = new boolean[n][m]; //Visited array described above
			//Iterate through each cell, perform BFS when appropriate (break if not good)
			for(int i = 0; i < n && good; i++){
				for(int j = 0; j < m && good; j++){
					
					//If this is a numbered cell, perform the BFS
					//Likewise, if this is a black cell and the check 4 pre-step hasn't been performed, perform the BFS
					if(numbered[i][j] > 0 || (!blackCheck && map[i][j])){
						//Touching will be equal to the number of connected cells of the same color as (i,j)
						//Check 2 is performed in this function
						int touching = countConnectedCells(i, j, map, numbered, visited);
						if(!map[i][j]) //Numbered cell- perform check 3
							good &= touching == numbered[i][j];
						else //We've performed the check 4 pre-step once; mark this down and don't do it again
							blackCheck = true;
					}
				}
			}
			
			//Checks 4 and 5, performed simultaneously. If each cell was visited in a BFS,
			//then everything is connected in a satisfactory faction.
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					good &= visited[i][j];
			
			//Report the final result!
			if(good)
				System.out.println("Puzzle #" + t + ": Correct");
			else
				System.out.println("Puzzle #" + t + ": Incorrect");
		}
	}
	
	//Performs a BFS from source cell (i,j) to cells of identical color, then returns the number of visited cells
	public static int countConnectedCells(int i, int j, boolean[][] map, int[][] numbered, boolean[][] visited){
		//This will keep track of whether a numbered cell is connected to another numbered cell, check 2
		boolean good = true;
		
		//Initialize the queue for the BFS, add (i,j) to it and mark it visited
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(i);
		queue.add(j);
		visited[i][j] = true;
		
		//Initialize the number of cells in this component to zero
		int touching = 0;
		
		//Perform the BFS!
		while(!queue.isEmpty()){
			//Poll a cell from the queue
			int x = queue.remove();
			int y = queue.remove();
			
			//Increment the number of cells (i,j) is indirectly adjacent to
			touching++;
			
			//Check 2
			good &= (x == i && y == j) || numbered[x][y] == 0;
			
			//Iterate through the 4 directions
			for(int z = 0; z < 4; z++){
				//dx and dy are static arrays declared earlier in the global scope
				int nx = x + dx[z];
				int ny = y + dy[z];
				
				//Out of bounds checks, wrong color check, previously visited check
				if(nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length || map[nx][ny] != map[i][j] || visited[nx][ny])
					continue;
				
				//This cell is cleared to move into, add it to the queue!
				visited[nx][ny] = true;
				queue.add(nx);
				queue.add(ny);
			}
		}
		
		//If good is false, we failed check 2 at some point. Return -1 as a dummy value, which will cause the "good" in main to become false.
		if(!good) 
			return -1;
		
		//Otherwise, report the number of adjacent cells.
		return touching;
	}
}
