/*
 * Letter Scramble
 * Author: Don Brace
 *
 * Builds a grid containing word multipliers and determines the score
 * Tricks to solve:
 *          1. Java Arrays are zero based, word row and cols are 1 based.
 *          2. Helps to build searchable Vector of letter scores.
 *          3. Use NxN array to your advantage.
 */

import java.util.*;

/*
 * Simple little class to store letter scores
 */
class letter_value {
	letter_value(String s, int v) {
		letter = s;
		value = v;
	}
	String letter;
	int value;
};

class letter_scramble {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String	word;
		int	gridsize = 0;
		int	i;
		int	j;
		int	row;
		int	col;
		int	numWords;
		String	orient;
		Vector lv = new Vector();

		/* Input gridsize */
		gridsize = scan.nextInt();

		/* Create NxN Array */
		String[][] scramble = new String[gridsize][gridsize];

		/* Read in grid */
		for (i = 0; i < gridsize; i++) {
			for (j = 0; j < gridsize; j++) {
				String s = scan.next();
				scramble[i][j] = s;
			} /* inner for loop */
		} /* outer for loop */
		//PrintGrid(scramble);

		/* Build letter value table */
		lv.add(new letter_value("A", 1));
		lv.add(new letter_value("B", 3));
		lv.add(new letter_value("C", 3));
		lv.add(new letter_value("D", 2));
		lv.add(new letter_value("E", 1));
		lv.add(new letter_value("F", 4));
		lv.add(new letter_value("G", 2));
		lv.add(new letter_value("H", 4));
		lv.add(new letter_value("I", 1));
		lv.add(new letter_value("J", 8));
		lv.add(new letter_value("K", 5));
		lv.add(new letter_value("L", 1));
		lv.add(new letter_value("M", 3));
		lv.add(new letter_value("N", 1));
		lv.add(new letter_value("O", 1));
		lv.add(new letter_value("P", 3));
		lv.add(new letter_value("Q", 10));
		lv.add(new letter_value("R", 1));
		lv.add(new letter_value("S", 1));
		lv.add(new letter_value("T", 1));
		lv.add(new letter_value("U", 1));
		lv.add(new letter_value("V", 4));
		lv.add(new letter_value("W", 4));
		lv.add(new letter_value("X", 8));
		lv.add(new letter_value("Y", 4));
		lv.add(new letter_value("Z", 10));

		/* Read in words */
		numWords = scan.nextInt();
		for (i = 0; i < numWords; i++) {
			word = scan.next();
			row = scan.nextInt();
			col = scan.nextInt();
			orient = scan.next();

			/*
			 * Remember arrays are zero based!!!!
			 * And the  coordinates are 1 based.
			 */
			j = CalculateWordScore(scramble, word, lv,
						row-1, col-1, orient);
			System.out.println(word + " " + j);
		} /* for */
	} /* main */

	/*
	 * Find letter in letter value Vector
	 * Could have used Iterator class and Comparator but this is a
	 * small vector to search.
	 */
	static int LetterValue(Vector lv, String s) {
		int i;
		for (i = 0; i < lv.size(); i++) {
			letter_value l = (letter_value)lv.get(i);
			if (l.letter.equals(s))
				return l.value;
		}
		return 0; /* Should always find a letter */
	} /* LetterValue

	/*
	 * Calculate the word score 
	 *
	 * Start word in row = r, col = c. Follow orientation.
	 * if orientation is H (horiz.) increase row.
	 * if orientation is V (vert.) increase col.
	 * Note: This assumes you have converted row and col to 0-based.
	 */
	static int CalculateWordScore(String[][] s, String w, Vector lv, 
					int r, int c, String o) {
		int len = 0;
		int score = 0;
		int lscore = 0;
		int word_multiplier = 1;

		while (len < w.length()) {
			String l = w.substring(len,len+1);
			String g = s[r][c];

			lscore = LetterValue(lv, l);

			if (!g.equals("[]")) {
				//System.out.println("g:" + g + 
						//" " + g.substring(0,1));
				Integer I = new Integer(g.substring(0,1));
				if (g.contains("W"))
					word_multiplier *= I.intValue();
				else
					lscore *= I.intValue();
			}
			score += lscore;
			//System.out.println("let=" + l + " " + 
						//lscore + " " + score + " " +
						//word_multiplier);

			len ++;

			if (o.equals("H")) /* Horizontal: increase column */
				++c;
			else		/*Vertical: increase row */
				++r;
		} /* while */

		return score * word_multiplier;

	} /* CalculateWordScore */

	/* 
	 * For debug
	 */
	static void PrintGrid(String[][] s) {
		int i;
		int j;
		for (i = 0; i < s.length; i++) {
			for (j = 0; j < s.length; j++)
				System.out.print(s[i][j] + ' ');
			System.out.println();
		}
	} /* PrintGrid */
}; /* letter_scramble */
