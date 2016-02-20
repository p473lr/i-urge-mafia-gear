// Arup Guha
// 3/26/2011
// Solution to UCF 2011 High School Programming Contest Problem: Circle
// The problem is to determine the winner in a game of tic-tac-toe.

import java.util.*;
import java.io.*;

public class circle {
	
	final static int SIZE = 3;
	final static boolean DEBUG = true;
	
	private char[][] board;
	
	// Pre-condition: fin is a Scanner ready to read in 9 tokens in
	//                top to bottom left to right order of the contents
	// 				  of a Tic-Tac-Toe board as specified in the problem.
	public circle(Scanner fin) {
		
		board = new char[SIZE][SIZE];
		
		// Copy in each character into the approriate place in the board.
		for (int row=0; row<SIZE; row++)
			for (int col=0; col<SIZE; col++)
				board[row][col] = fin.next().charAt(0);
	}
	
	// Returns the winner of the current object. In particlar, if 'X' is
	// returned 'X' wins, if 'O' is returned 'O' wins, if '#' is returned
	// the game is a cats game.
	public char winner() {
		
		// Check for X on all rows and columns.
		for (int i=0; i<SIZE; i++)
			if (sameRow('X', i) || sameCol('X', i))
				return 'X';
				
		// Check for X on both diagonals.
		if (sameForwardDiag('X') || sameBackDiag('X'))
			return 'X';
			
		// Check for O on all rows and columns
		for (int i=0; i<SIZE; i++)
			if (sameRow('O', i) || sameCol('O', i))
				return 'O';
				
		// Check for O on both diagonals.
		if (sameForwardDiag('O') || sameBackDiag('O'))
			return 'O';
			
		// No one won if we got here, so it's a CATS game!
		return '#';		
	}
	
	// This is only written to verify the input. Here, we will check to
	// see if O won before checking if X won. By calling both this method
	// and winner, we can see if any board has both 3 in a row for X and
	// 3 in a row for O...
	public char winner2() {
		
		// Check for O on all rows and columns.
		for (int i=0; i<SIZE; i++)
			if (sameRow('O', i) || sameCol('O', i))
				return 'O';
				
		// Check for O on both diagonals.
		if (sameForwardDiag('O') || sameBackDiag('O'))
			return 'O';
			
		// Check for X on all rows and columns
		for (int i=0; i<SIZE; i++)
			if (sameRow('X', i) || sameCol('X', i))
				return 'X';
				
		// Check for X on both diagonals.
		if (sameForwardDiag('X') || sameBackDiag('X'))
			return 'X';
			
		// No one won if we got here, so it's a CATS game!
		return '#';				
	}
	
	// Returns true iff the row row in this object has 3 of the character ch.
	public boolean sameRow(char ch, int row) {
		
		// Return false if we see a different character here.
		for (int col=0; col<SIZE; col++)
			if (board[row][col] != ch)
				return false;
				
		// All three must have been ch.
		return true;
	}
	
	// Returns true iff the column col in this object has 3 of the character ch.
	public boolean sameCol(char ch, int col) {
		
		// Return false if we see a different character here.
		for (int row=0; row<SIZE; row++)
			if (board[row][col] != ch)
				return false;
				
		// All three must have been ch.
		return true;
	}
	
	// Returns true iff the forward diagonal in this object has 3 of the character ch.
	public boolean sameForwardDiag(char ch) {
		
		// Return false if we see a different character here.
		for (int row=0; row<SIZE; row++)
			if (board[row][row] != ch)
				return false;
		
		// All three must have been ch.		
		return true;
		
	}
	
	// Returns true iff the backward diagonal in this object has 3 of the character ch.
	public boolean sameBackDiag(char ch) {
		
		// To check these, start at the first row and the last column and for each
		// subsequence place, adjust row and col accordingly.
		for (int row=0,col=SIZE-1; row<SIZE; row++,col--)
			if (board[row][col] != ch)
				return false;
				
		// All three must have been ch.
		return true;
	}
	
	// Method to help verify the input file. It returns true iff a board has an
	// unplayed square.
	public boolean hasBlank() {
		
		// Look for the blank, a #.
		for (int row=0; row<SIZE; row++)
			for (int col=0; col<SIZE; col++)
				if (board[row][col] == '#')
					return true;
						
		// Didn't find any.
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		
		// Open the input file.
		Scanner fin = new Scanner(new File("circle.in"));
		
		int numGames = fin.nextInt();
		
		// Solve each game.
		for (int loop=1; loop<=numGames; loop++) {
			
			// Header.
			System.out.println("Game #"+loop+":");
			
			// Read in this game.
			circle thisGame = new circle(fin);
			
			// Find who won.
			char outcome = thisGame.winner();
			
			// Validating input, to make sure no cases occur where
			// both teams win.
			if (DEBUG) {
				char altOutcome = thisGame.winner2();
				
				if (outcome != altOutcome)
					System.out.println("INVALID: BOTH TEAMS WIN");
			}
			
			// Output appropriately.
			if (outcome == 'X')
				System.out.println("Eureka! X wins!");
			else if (outcome == 'O')
				System.out.println("Gadzooks! O wins!");
			else {
				System.out.println("Lolcat's game!");
				
				// Just for validating the input.
				if (DEBUG && thisGame.hasBlank())
					System.out.println("INVALID INPUT CASE!!!");
			}
			System.out.println();
		}
		
	}
}