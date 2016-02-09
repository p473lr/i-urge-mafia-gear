/*
UCF's 2007 High School Programming Contest
Organize
Solution Prepared by: Nick Beato

The problem: given a box and a cylinder, does the cylinder fit
entirely within the box?  The cylinder must be placed "flat".

There are 2 ways that I thought about solving this problem.

1) Find the smallest 2 dimensions of the box that are greater
than the diameter of the CDs.  Then you can compare the remaining
value to the height of the CD stack.  If done this way, we would
need to make sure that at least 2 dimensions are greater than
the diameter of the box and find the largest dimension that
satisfies this.

2) Find a box that fits around the CDs that is as small as possible
and see if this box fits in the box from the input. If done this
way, all we have to do is sort the dimensions of both boxes
(independantly) and make sure that each pair satisfies the
inequality.

Both are fairly straight forward to code.  I opted for the 2nd
approach since there is far less chance of a bug coming up on me.

*/

import java.util.*;
import java.io.*;

public class organize {
	public static void main(String []args) throws IOException {
		Scanner in = new Scanner(new File("organize.in"));

		// labels the output
		int boxNumber = 1;

		// the box from the input
		int [] box = new int[3];

		// the smallest box that can wrap the cds
		int [] cds = new int[3];

		// the number of cds
		int cdCount;

		// since all input is positive, we only need
		// to find the first 0 in the file
		box[0] = in.nextInt();

		while(box[0] > 0) {
			box[1] = in.nextInt();
			box[2] = in.nextInt();

			cdCount = in.nextInt();

			// make the box
			// it is 2*cdCount by 120 by 120
			cds[0] = cdCount * 2;
			cds[1] = cds[2] = 120;

			// independantly sort the dimensions of the 2 boxes
			Arrays.sort(box);
			Arrays.sort(cds);

			// if each pair satisfies the inequality, then the
			// cd box fits in the dimensions give, so the cds must fit in there
			if(box[0] >= cds[0] && box[1] >= cds[1] && box[2] >= cds[2])
				System.out.println("Box " + boxNumber + ": Stack of " + cdCount + " discs fits!");
			else
				System.out.println("Box " + boxNumber + ": Stack of " + cdCount + " discs does not fit.");

			box[0] = in.nextInt();
			boxNumber++;
		}

		in.close();
	}
}
