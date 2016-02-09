/*
 * Tetra Square
 * Author: Don Brace
 *
 * This basically involves best-fit solution. You are limited to a 4x4 grid
 * to fit shapes that can be up to 3x3.
 *
 * I decided to use bit patterns. I took the 4x4 grid and choose each row
 * to represent 4 bits. I then used this model to determine how the shape
 * is represented.
 *
 * +-------+-------+-------+-------+
 * | | | | | | | | | | | | | | | | |
 * | Row 1 | Row 2 | Row 3 | Row 4 |
 * +-------+-------+-------+-------+
 *
 * T1 looks like: If left justified
 * +-------+-------+-------+-------+     111  or  ---
 * |1|1|1|0|0|1|0|0|0|0|0|0|0|0|0|0|      1        |
 * | Row 1 | Row 2 | Row 3 | Row 4 |
 * +-------+-------+-------+-------+
 *
 * The above bit pattern is: 0XE400
 *
 * +-+-+-+-+
 * |1|1|1|0|
 * +-+-+-+-+
 * |0|1|0|0|
 * +-+-+-+-+
 * |0|0|0|0|
 * +-+-+-+-+
 * |0|0|0|0|
 * +-+-+-+-+
 *
 * The above shape can only be shifted to the right/left by one square.
 * (In its current position, it can only be shifted right once.)
 * But it can be shifted down 2 more rows. So we need to find the bit patterns
 * for each possible shape position.
 * Eg:
 *          0xE400 - Current displayed position.
 *          0x7200 - Shifted right.
 *          0x0E40 - Shifted down 1x and left justified.
 *          0x0720 - Shifted down 1x and shifted right 1x.
 *          0x00E4 - Shifted down 2x and left justified.
 *          0x0072 - Shifted down 2x and shifted right 1x.
 *
 * Using the above bit patterns I generated all possible patterns for each
 * shape then ran through each pattern and ORed the bits together until I 
 * obtained all F's in the result.
 *
 * The trick is to keep track of the patterns so you know which letter to
 * output for the solution.
 *
 * I used recursion to build the solution list.
 *
 * Note: In the shape table, I right-justified them in the top row.
 */

import java.util.*;

/*
 * Class to track and manage the shapes.
 */
class shapes {
	shapes(String n, String l, int g) {
		name = n;
		letter = 0;
		pattern = g;
	}

	/*
	 * Just debug to verify we did the patterns correctly.
	 */
	void PrintPattern(String msg, int p) {
		int a,b,c,d;
		a = (p >> 12) & 0x0F;
		b = (p >> 8) & 0x0F;
		c = (p >> 4) & 0x0F;
		d = p  & 0x0F;

		System.out.println(msg + a + ' ' + b + ' ' + c + ' ' + d);
	}

	/*
	 * Shift the pattern left
	 *
	 * We stop whenever a 1 bit is detected on the leftmost bit.
	 */
	int ShiftLeft(int n) {
		int a,b,c,d;

		a = (n >> 12) & 0x0F;
		b = (n >> 8) & 0x0F;
		c = (n >> 4) & 0x0F;
		d = n  & 0x0F;

		if ((a & 8) > 0)
			return 0;
		if ((b & 8) > 0)
			return 0;
		if ((c & 8) > 0)
			return 0;
		if ((d & 8) > 0)
			return 0;
		a = a << 1;
		b = b << 1;
		c = c << 1;
		d = d << 1;

		return (a << 12) | (b << 8) | (c << 4) | d;
	}

	/*
	 * Shift the pattern down
	 *
	 * We can go down until a 1 bit is detected in the last row.
	 * The calling function is responsible for storing the pattern
	 * and stopping when a 0 is returned.
	 */
	int ShiftDown(int n) {
		int a,b,c,d;
		int a1, b1, c1, d1;

		a = (n >> 12) & 0x0F;
		b = (n >> 8) & 0x0F;
		c = (n >> 4) & 0x0F;
		d = n  & 0x0F;

		/* 
		 * Last row, stop.
		 */
		if (d > 0)
			return 0;

		a1 = 0;
		b1 = a;
		c1 = b;
		d1 = c;
		return (a1 << 12) | (b1 << 8) | (c1 << 4) | d1;
	}

	/*
	 * Generate all possible combination of the shape patterns
	 */
	void BuildPatterns() {
		int i;
		int j;
		int k;
		int newpat = pattern;
		patterns = new int[16];
		num_patterns = 0;

		/*
		 * Do not forget the first pattern (from constructor)
		 */
		patterns[num_patterns] = pattern;
		++num_patterns;

		/*
		 * Now start shifting the pattern to the left.
		 */
		for(i = 0; i < 4; i++) {
			newpat = ShiftLeft(newpat);
			if (newpat == 0)
				break;
			patterns[num_patterns] = newpat;
			++num_patterns;
			//PrintPattern("   Lt:", newpat);
		} /* for */

		/*
		 * Now shift pattern down and then shift it left.
		 */
		k = num_patterns;
		for (i = 0; i < k; i++)  {
			newpat = patterns[i];
			for (j = i; j < 4; j++) {
				newpat = ShiftDown(newpat);
				if (newpat == 0)
					continue;
				patterns[num_patterns] = newpat;
				++num_patterns;
				//PrintPattern("   Dn:", newpat);
			}
		}

	} /* BuildPatterns */

	String name;
	int letter;
	int pattern;
	int[] patterns;
	int final_pattern_index;
	int num_patterns;
}; /* shapes */

/*
 * I used this to help print the proper letters in the tetras grid
 */
class output {
	output(int l, int v) {
		Integer I = new Integer(l);
		value = v;
		letter = I.toString();
	}
	output(String l, int v) {
		letter = l;
		value = v;
	}
	String	letter;
	int	value;
};

public class tetra_square {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Vector<shapes> shapes = new Vector<shapes>();
		Vector<String> in_shapes = new Vector<String>();
		Vector<shapes> tetra_shapes = new Vector<shapes>();
		Vector<Integer> patterns = new Vector<Integer>();
		Vector<shapes> final_shapes = new Vector<shapes>();
		String tetra_shape;
		int numShapes = 0;
		int i = 0;

		/*
		 * Build up list of shapes so we know the shape's patterns 
		 * we are attempting to fit in the 4x4 grid.
		 *
		 * These come from the problem description.
		 */
		BuildShapeList(shapes);

		/*
		 * Read in number of shapes
		 */
		numShapes = scan.nextInt();
		
		/*
		 * Read in shapes and make a list of them.
		 */
		for (i = 0; i < numShapes; i++) {
			tetra_shape = scan.next();
			in_shapes.add(tetra_shape);
		} /* for */

		/*
		 * Generate all possible combinations of patterns for each
		 * inputted shape.
		 */
		BuildShapePatterns(in_shapes, shapes, tetra_shapes);

		/*
		 * Find each shape pattern that solves the problem.
		 */
		BuildTetraPatterns(tetra_shapes, patterns, final_shapes, 0);

		/*
		 * Output the 4x4 grid.
		 */
		PrintResultingBoard(tetra_shapes);

	} /* main */

	/*
	 * Prints the 4x4 grid using some simple tricks about bit patterns
	 *
	 * The list of patterns comes out in inputed shape order.
	 * If you printout the hex values for each 4 bits the answer is
	 * down the column, not across the row.
	 * 
	 * Looks like:
	 * Shape 'A' solution pattern = 1100
	 * Shape 'B' solution pattern = 04c8
	 * Shape 'C' solution pattern = 0006
	 * Shape 'D' solution pattern = 0231
	 * Shape 'E' solution pattern = e800
	 *                              ^  ^
	 *                              |  |
	 *                              |  +--> Bottom row of 4x4 grid
	 *                              +--> Top row of 4x4 grid
	 *
	 * Need to emit highest hex value first.
	 *
	 * Answer is:
	 *           EEEA    0XE001 (0xE = 1110)
	 *           EBDA    0x8421 (0x8 = 1000)
	 *           BBDD    0xC011 (0xC = 1100)
	 *           BCCD    0x8601 (0x6 = 0110)
	 */
	static void PrintResultingBoard(Vector<shapes> v) {
		int i; /* loop index */
		int j; /* loop index */
		int a,b,c,d; /* Need to hold each nibble in the pattern */
		int n;
		int letter = 'A'; /* First shape is 'A' (from problem desc.) */

		/* 
		 * I use this 4x16 array because there are 4 rows in the 
		 * grid and only 4 bits for each row. 2^4 = 16.
		 */
		output[][] tet_out = new output[4][16];

		/*
		 * By now each shape has one pattern that solves the 
		 * problem. But we need to place the shape's letter in
		 * each row (if necessary). The 4x16 array above was the 
		 * fastest way I could think to add the shapes letter in
		 * the proper location. If the shapes bit pattern for a 
		 * row is 0x0E the then letter goes into tet_out[row][0x0E]
		 */
		letter = 'A';
		for (i = 0; i < v.size(); i++) {

			shapes s = v.get(i);
			n = s.patterns[s.final_pattern_index];

			/*
			 * Need to decompise the shapes pattern so we can
			 * place the shape's letter in each row of the
			 * 4x4 grid.
			 */
		 
			a = (n >> 12) & 0x0F;
			b = (n >> 8) & 0x0F;
			c = (n >> 4) & 0x0F;
			d = n  & 0x0F;

			/*
			 * This creates a sparse array.
			 */
			tet_out[0][a] = new output(letter, a);
			tet_out[1][b] = new output(letter, b);
			tet_out[2][c] = new output(letter, c);
			tet_out[3][d] = new output(letter, d);
			//System.out.printf("1-->%02x %02x %02x %02x\n",
					//a,b,c,d);
			++letter;
		}

		System.out.println("====================");

		/*
		 * The way I did the bit patterns I need to emit the high
		 * order bits first.
		 */
		for (i = 0; i < 4; i++) {
			for (j = 15; j > -1; j--) {
				PrintCharacter(tet_out[i][j]);
			}
			System.out.println();
		}
	} /* PrintResultingBoard */

	/*
	 * This function looks at each shapes bit pattern and emits the 
	 * shapes associated letter if a 1 bit is found.
	 *
	 * 0x0E = 1100. So two of the shape's letters are emitted.
	 */
	static void PrintCharacter(output o) {
		int i;

		if (o == null)
			return;

		if (o.value == 0)
			return;

		for (i = 0; i < 16; i++) {
			if ( ((o.value >> i) & 0x1) > 0) {
				Integer k = new Integer(o.letter);
				System.out.printf("%c", k.intValue());
			}
		}
	} /* PrintCharacter */

	/*
	 * Look through the static table of shapes and return the shape 
	 * if found.
	 */
	static shapes FindShape(Vector<shapes> s, String name) {
		int i;

		//System.out.println("Looking for:" + name);
		for (i = 0; i < s.size(); i++) {
			shapes sh = s.get(i);
			if (sh.name.equals(name))
				return sh;
		}
		return null;
	} /* FindShape */

	/*
	 * Go through the list of patterns and OR the bits together. At the
	 * end, all F's result in a solution. You can even use XOR.
	 * 
	 * Overlap does not really matter as there will be 0's in other
	 * positions.
	 */
	static int OR_square(Vector<Integer> v) {
		int a,b,c,d;
		int n;
		int i;
		int tot = 0;
		int tot_a = 0;
		int tot_b = 0;
		int tot_c = 0;
		int tot_d = 0;

		for (i = 0; i < v.size(); i++) {
			n = v.get(i);

			a = (n >> 12) & 0x0F;
			b = (n >> 8) & 0x0F;
			c = (n >> 4) & 0x0F;
			d = n  & 0x0F;
			
			/*
			 * Can use OR because we are completely filling
			 * the grid.
			 * If overlap, the grid will not be filled.
			 */
			tot_a |= a;
			tot_b |= b;
			tot_c |= c;
			tot_d |= d;
		}

		return (tot_a << 12) | (tot_b << 8) | (tot_c << 4) | tot_d;
	}

	/* 
	 * This function looks for the patterns that results in 0xFFFF
	 * for the 4x4 grid.
	 * 
	 * It uses a brute force approach to find the resulting pattern
	 * Stops on the first successful pattern found.
	 */
	static int BuildTetraPatterns(Vector<shapes> vs, Vector<Integer> patt, 
				Vector<shapes> final_shapes, int index) {
	
		int i;
		int j;
		int k;
		int pat = 0;
		int found = 0;
		Integer I;
		shapes s;

		/* 
		 * This will not happen but with recursion you need some
		 * checks just to be sure you stop.
		 * 
		 * Left over from debug phase. :<
		 */
		if (index > vs.size()) {
			//System.out.println("BuildTetraPatterns: vs.size = " +
						//vs.size());
			return 0;
		}

		/*
		 * We are at the last shape in the input list.
		 * patt is the list of patterns from each shape that we
		 * used along the way.
		 *
		 * OR in each pattern to see if we get all F's
		 */
		if (index == vs.size()) {
			pat = OR_square(patt);
			if (pat == 0xffff) {
				//System.out.printf("PAT: 0x%x index=%d " +
							//"patt_size=%d\n",pat, 
							//patt.size(), index);
				return 1; /* found a solution */
			}
			return 0; /* Keep looking */
		}

		/*
		 * get the shape from the list of inputed shapes.
		 */
		s = vs.get(index);

		/* 
		 * Brute Force approach, find correct combination of 
		 * patterns that make all rows contain F's.
		 * Look at all possible shape patterns and OR them together
		 * until we get all F's.
		 */
		//System.out.println("BuildTetraPatterns: num_patterns = " + 
					//s.num_patterns);
		for (i = 0; i < s.num_patterns; i++) {
			patt.add(new Integer(s.patterns[i]));
			/*
			System.out.printf("letter=%c index=%d i=%d " +
				"patt=%x\n", s.letter, index, i, s.patterns[i]);
			*/

			/*
			 * Recursively call but use the next shape in the 
			 * list.
			 */
			found = BuildTetraPatterns(vs, patt, final_shapes, 
							index+1);
			/*
			 * If we find all F's in the pattern we have solved
			 * the problem. Use the system stack to set the 
			 * index of the shape's pattern.
			 */
			if (found > 0) {
				s.final_pattern_index = i;
				return 1;
			}

			/*
			 * We did not get a solution using this shape's 
			 * pattern. Remove it and try another.
			 */
			patt.remove(index);
		} /* for */

		return 0;
	}

	/*
	 * The shapes to be fitted come from the input stream. We need to 
	 * lookup the shape from the shape list and then generate all
	 * combination of possible patterns. Really great that there are
	 * no rotations to handle!
	 *
	 * I could have generated all possible patterns in the shape list
	 * itself but figured that just doing the ones to be used would be
	 * faster.
	 */
	static void BuildShapePatterns(Vector<String> input, 
					Vector<shapes> s, Vector<shapes> v) {
		shapes shape;
		int letter = 'A';
		int i;
		int found = 0;

		/*
 		 * Convert input to bit patterns, set letter
		 * The letter starts with a and increases.
 		 */
		for (i = 0; i < input.size(); i++) {
			shape = FindShape(s, input.get(i));
			shape.letter = letter;
			shape.BuildPatterns();
			v.add(shape);
			/*
			System.out.printf("%s(%c) - %X (patterns=%d) " +
					"size=%d\n", shape.name,
					shape.letter, shape.pattern, 
					shape.num_patterns, v.size());
			*/
			++letter;
		}


	} /* BuildShapePatterns */

	/*
	 * This function build the static list of known game shapes.
	 * The shapes come from the problem description.
	 * This function adds them to the top row right justified.
	 */
	static void BuildShapeList(Vector<shapes> v) {
		int g;

		/*
 		 * A = 10, B = 11, C = 12, D = 13, E = 14, F = 15
 		 *
 		 * Example J1:
 		 * +-+-+-+-+
 		 * |0|0|0|1|
 		 * +-+-+-+-+
 		 * |0|0|0|1|
 		 * +-+-+-+-+
 		 * |0|0|1|1|
 		 * +-+-+-+-+
 		 * |0|0|0|0|
 		 * +-+-+-+-+
		 */
		g = 0x1130; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("J1", " ", g));
		g = 0x4700; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("J2", " ", g));
		g = 0x3220; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("J3", " ", g));
		g = 0x7100; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("J4", " ", g));
		g = 0x7200; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("T1", " ", g));
		g = 0x1310; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("T2", " ", g));
		g = 0x2700; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("T3", " ", g));
		g = 0x2320; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("T4", " ", g));
		g = 0x1100; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("I1", " ", g));
		g = 0x3000; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("I2", " ", g));
		g = 0x2230; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("L1", " ", g));
		g = 0x7400; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("L2", " ", g));
		g = 0x3110; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("L3", " ", g));
		g = 0x1700; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("L4", " ", g));
		g = 0x6300; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("Z1", " ", g));
		g = 0x1320; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("Z2", " ", g));
		g = 0x3600; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("S1", " ", g));
		g = 0x2310; /* Each Hex digit represents 4 squares.*/
		v.add(new shapes("S2", " ", g));
	} /* BuildShapeList */

}; /* tetra_square */
