/*
UCF Highschool Programming Contest, 2010
C/C++ Judge solution for "Automata"
Programmed by Joshua Michalczak

Solution overview:
   This solution is rather straight-forward; while
the concepts in the algorithm are not difficult,
you still need to implement the rules within the
problem correctly.  Make sure you read the problem
carefully!
   We'll represent the state of the automata as a
boolean array which we can feed into a function that
simulates one progression.  Then, we just need to
loop that function `g` times.
*/

import java.io.File;
import java.util.*;

public class automata {
	public static void main(String args[]) throws Exception {
		Scanner in = new Scanner(new File("automata.in"));
		
		//read in the number of automata
		int automataCount = in.nextInt();
		//since the size of the rules array never changes, we'll make it once
		boolean rules[] = new boolean[8];
		int cur = 0;
		
		//for each automata...
		for(int currentAutomata = 1; currentAutomata <= automataCount; ++currentAutomata) {
			//get the number of generations and cells
			int numGenerations = in.nextInt();
			int numCells = in.nextInt();
			//set the current state array to 0
			cur = 0;
			
			//go ahead an print out which automata this is
			System.out.printf("Automata #%d:%n", currentAutomata);
			
			//make two new boolean arrays for the state 
			boolean state[][] = new boolean[2][numCells];
			//read in the initial state string
			String initialStr = in.next();
			
			//for each character in the string...
			for(int c = 0; c < initialStr.length(); ++c) {
				//set the state to TRUE if the character was '1', FALSE otherwise
				state[cur][c] = (initialStr.charAt(c) == '1');
				//also print out the initial state
				System.out.print((state[cur][c] ? '#' : '.'));
			}
			System.out.println();
			
			//read in the rules string
			String rulesStr = in.next();
			//for each character in the string...
			for(int c = 0; c < rulesStr.length(); ++c) {
				//set the rule to TRUE if the character was '1', FALSE otherwise
				rules[c] = (rulesStr.charAt(c) == '1');
			}
			
			//for each generation we need to simulate
			for(int generation = 0; generation < numGenerations; ++generation) {
				//run the step function on the current state (cur) and the
				//next state ((cur + 1) % 2), using the given rules.
				step(state[cur], state[(cur + 1) % 2], rules);
				//switch the current and next state
				cur = (cur + 1) % 2;
				//print out the newly made generation
				for(int c = 0; c < numCells; ++c) {
					System.out.print((state[cur][c] ? '#' : '.'));
				}
				System.out.println();
			}
			
			System.out.println();
		}
	}
	
	//This is our step array, which assigns the cells of end based on the cells
	//of start using the rules given.
	public static void step(boolean start[], boolean end[], boolean rules[]) {
		//for each cell in the state...
		for(int c = 0; c < start.length; ++c) {
			//set the next state
			end[c] = rule(get(start, c-1), get(start, c), get(start, c+1), rules);
		}
	}
	
	//determines which rule the 3 given cells belong to and returns the value
	//those the value those cells create.
	public static boolean rule(boolean left, boolean mid, boolean right, boolean rules[]) {
		if(left)
			if(mid)
				if(right)
					return rules[0];
				else
					return rules[1];
			else
				if(right)
					return rules[2];
				else
					return rules[3];
		else
			if(mid)
				if(right)
					return rules[4];
				else
					return rules[5];
			else
				if(right)
					return rules[6];
				else
					return rules[7];
	}
	
	//This is just a convenience function that wraps the index around
	//so that it is within bounds of the array and returns the value
	//at that index.
	public static boolean get(boolean array[], int index) {
		while(index < 0) { index += array.length; }
		index %= array.length;
		
		return array[index];
	}
}
