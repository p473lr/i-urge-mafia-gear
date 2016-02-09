/*
 * Minelayer
 * Author: Don Brace
 *
 * Inputs a 15x30 grid and generates a minsweeper grid. 
 *
 * What is adjacent mean?
 * Example Grid.
 * ...........   ---> .1221....
 * ..**.......   ---> .1**1....
 *
 * So like a checkerboard, diagonals are adjacent. 
 * What are the cases?
 * 1 - Upper Outer edge. 
 * 2 - Upper left corner
 * 3 - Upper right corner
 * ...
 */

import java.util.*;

public class minelayer {
	static final int NUMROWS = 15;
	static final int NUMCOLS = 30;

	public static void main(String[] args) {
		StringBuffer	minelayer[] = new StringBuffer[NUMROWS];
		Scanner scan = new Scanner(System.in);
		int i;
		int j;

		/* 
		 * Read in the minelayer board
		 */
		for (i = 0; i < NUMROWS; i++) {
			minelayer[i] = new StringBuffer();
			minelayer[i].append(scan.next());
		}

		/*
		 * Build up the minesweeper input template
		 */

		for (i = 0; i < NUMROWS; i++) {
			for (j = 0; j < NUMCOLS; j++) {
				char square = minelayer[i].charAt(j);
				if (square == '*') {
					continue;
				}

				SetNumberOfMines(i, j, minelayer);
			}
		}
		for (i = 0; i < NUMROWS; i++)
			System.out.println(minelayer[i]);
	} /* main */

	/*
	 * This could be better optimized, but I leave it up to you :) */
	static void SetNumberOfMines(int row, int col, StringBuffer[] ml) {
		int i, j;
		int count = 0;

		/* Case 1 - Upper row, nothing adjacent above */
		if (row == 0) {
			if (col == 0) { /* Upper left hand corner */
				if (ml[row].charAt(col+1) == '*')
					++count;
				if (ml[row+1].charAt(col) == '*')
					++count;
				if (ml[row+1].charAt(col+1) == '*')
					++count;
			} else if (col < (NUMCOLS-1)) {
				if (ml[row].charAt(col+1) == '*')
					++count;
				if (ml[row].charAt(col-1) == '*')
					++count;
				if (ml[row+1].charAt(col) == '*')
					++count;
				if (ml[row+1].charAt(col+1) == '*')
					++count;
				if (ml[row+1].charAt(col-1) == '*')
					++count;
			} else { /* upper right hand corner */
				if (ml[row].charAt(col-1) == '*')
					++count;
				if (ml[row+1].charAt(col) == '*')
					++count;
				if (ml[row+1].charAt(col-1) == '*')
					++count;
			}
		} else if ((row < NUMROWS-1)) {
			if (col == 0) { /* Left hand side */
				if (ml[row-1].charAt(col) == '*')
					++count;
				if (ml[row-1].charAt(col+1) == '*')
					++count;
				if (ml[row].charAt(col+1) == '*')
					++count;
				if (ml[row+1].charAt(col) == '*')
					++count;
				if (ml[row+1].charAt(col+1) == '*')
					++count;
			} else if (col < (NUMCOLS-1)) {
				if (ml[row-1].charAt(col) == '*')
					++count;
				if (ml[row-1].charAt(col-1) == '*')
					++count;
				if (ml[row-1].charAt(col+1) == '*')
					++count;
				if (ml[row].charAt(col-1) == '*')
					++count;
				if (ml[row].charAt(col+1) == '*')
					++count;
				if (ml[row+1].charAt(col) == '*')
					++count;
				if (ml[row+1].charAt(col-1) == '*')
					++count;
				if (ml[row+1].charAt(col+1) == '*')
					++count;
			} else {
				if (ml[row-1].charAt(col) == '*')
					++count;
				if (ml[row-1].charAt(col-1) == '*')
					++count;
				if (ml[row].charAt(col-1) == '*')
					++count;
				if (ml[row+1].charAt(col) == '*')
					++count;
				if (ml[row+1].charAt(col-1) == '*')
					++count;
			}
		} else { /* Bottom row */
			if (col == 0) { /* Bottom left hand corner */
				if (ml[row-1].charAt(col) == '*')
					++count;
				if (ml[row-1].charAt(col+1) == '*')
					++count;
				if (ml[row].charAt(col+1) == '*')
					++count;
			} else if (col < (NUMCOLS-1)) {
				if (ml[row].charAt(col-1) == '*')
					++count;
				if (ml[row].charAt(col+1) == '*')
					++count;
				if (ml[row-1].charAt(col-1) == '*')
					++count;
				if (ml[row-1].charAt(col+1) == '*')
					++count;
				if (ml[row-1].charAt(col) == '*')
					++count;
			} else {
				if (ml[row-1].charAt(col) == '*')
					++count;
				if (ml[row-1].charAt(col-1) == '*')
					++count;
				if (ml[row].charAt(col-1) == '*')
					++count;
			}
		}

		if (count > 0)
			ml[row].setCharAt(col, (char)(count + '0'));

		return;
	} /* SetNumberOfMines */
}; /* minelayer */
