package y2010;

/*
 * Program to solve TraceRoute problem
 * Author: Don Brace
 * Input:
 *       <Number of Routes>
 *       <Node Name> <Node Name> <cost>
 *       . . .
 *       <Desired Route>
 * Output:
 *       Cost to travel from Node0 to desired node
 *
 * Uses adjacency matrix and Dijkstra's method.
 *
 * Requires JDK 5 or greater for Vector support.
 */
import java.util.*;

/*
 * node pair of the route list
 * Models a connection from one node to another and the cost.
 */

class bnode {
	int	weight;
};

/*
 *
 * Java program to find the cheapest route from one node to another.
 *
 */
public class TraceRoute {
	public static void main(String[] args) {

		String N;		/* Name of node 1 */
		String E;		/* Name of node 2 */
		String Start;		/* Name of first node in the path */
		String End;		/* Name of the last node in the path */
		int cost;		/* Cost to get from N to E */
		int i;			/* loop index variable */
		int j;			/* loop index variable */
		int s;			/* Represents index of starting node */
		int e;			/* Index of ending node */
		Scanner scan = new Scanner(System.in);
		int numConnections;

		numConnections = scan.nextInt();

		/* Trick, computer nodes are all uppercase English
		 * letters. A-Z.
		 * weight matrix must be 26x26 letters.
		 * Shamelessly hard-coding the value :<
		 */
		int[][] weight = new int[26][26];

		/* Initialize weights to a value larger than the problem spec. states. */
		for (i = 0; i < 26; i++)
			for (j = 0; j < 26; j++)
				weight[i][j] = 99999;

		/* Read in all path entries. */
		for (i = 0; i < numConnections; i++) {
			int x, y;

			N = scan.next();
			E = scan.next();
			cost = scan.nextInt();

			/*
 			 * Translate letters to numbers, A=0, B=1...
 			 */
			//System.out.println(N.charAt(0));
			x = N.charAt(0) - 'A';
			y = E.charAt(0) - 'A';

			weight[x][y] = cost;
			weight[y][x] = cost;
			//System.out.println("x=" + x + " y=" + y + ' ' + cost);
		}
		/* Read in desired path */
		Start = scan.next();
		End = scan.next();
		s = Start.charAt(0) - 'A';
		e = End.charAt(0) - 'A';

		/*
		 * Use Dijkstra's method.
		 * Again shamelessly using hard-coded 26
		 */
		cost = shortpath(weight, s, e, 26);

		if (cost == -1)
			System.out.println("NoRoute");
		else
			System.out.println(cost);
	} // main

	/*
	 * Find the shortest distance from one node to another
	 */
	static int shortpath(int[][] w, int s, int t, int num)
	{
		int[] distance = new int[num];
		int[] perm = new int[num];
		int current, i, k = 0, dc;
		int smalldist, newdist;
		int loopcounter = 0;

		/* Initialization */
		for (i = 0; i < num; i++) {
			perm[i] = 0;
			distance[i] = 99999;
		}

		perm[s] = 1;
		distance[s] = 0;
		current = s;

		while (current != t) {
			smalldist = 99999;
			dc = distance[current];
			for (i = 0; i < num; i++) {
				if (perm[i] == 0) {
					newdist = dc + w[current][i];
					if (newdist < distance[i]) {
						distance[i] = newdist;
					} // if
					/* smallest distance? */
					if (distance[i] < smalldist) {
						smalldist = distance[i];
						k = i;
					}
				} // if
			} // for
			current = k;
			perm[current] = 1;
			/*
			 * There are only 26 nodes to traverse
			 */
			++loopcounter;
			/*
			 * 26 letters, can be no more than 26 routes.
			 * Handles circular routes.
			 */
			if (loopcounter > 28)
				return -1;
		} // while
		//System.out.println("distance = " + distance[t]);
		return distance[t];
	} // shortpath
};
