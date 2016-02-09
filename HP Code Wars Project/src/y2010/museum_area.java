/*
 * museum-area
 * Author: Don Brace
 *
 * Find the area of the space enclosed by a series of points.
 * Trick: Need to find the area of all triangles enclosed by the polygon.
 * 
 * Another trick, there is one more vertex to consider. It however has
 * the same (x,y) coordinates as the first vertex.
 */

import java.util.*;

/* 
 * Just a simple class to keep track of each vertex
 */
class vertex {
	double x;
	double y;
};

class museum_area {

	static int numXY = 2; /* Number of coord in each Vertex */

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numVertices = 0;
		int i;
		
		/* Read in number of vertices */
		numVertices = scan.nextInt();

		/* Store vertices in 2D array */
		vertex[] vertices = new vertex[numVertices+1];
		for (i = 0; i < numVertices; i++) {
			vertices[i] = new vertex();
			vertices[i].x = scan.nextDouble();
			vertices[i].y = scan.nextDouble();

			//System.out.println("(" + vertices[i].x + "," +
						//vertices[i].y + ")");
		} /* for */
		/*
		 * Need to add in the final vertex
		 */
		vertices[numVertices] = new vertex();
		vertices[numVertices].x = vertices[0].x;
		vertices[numVertices].y = vertices[0].y;
		++ numVertices; /* Last vertex == first vertex */

		System.out.println(CalcArea(vertices, numVertices));
		
	} /* main */

	/*
 	 * Calculate the area of a polygon.
 	 * Formula:
 	 *        A = 1/2 * SUM[(x(i)y(i+1) - (x(i+1)y(i)] for all i.
 	 *        If the nodes are defined clockwise, then the area
 	 *        will be negative but still correct.
 	 */
	public static double CalcArea(vertex[] vertices, int num) {
		int i;
		double d = 0.0d;
		double d1 = 0.0d;

		for (i = 0; i < num-1; i++) {
			d = ((vertices[i].x * vertices[i+1].y) -
				(vertices[i+1].x * vertices[i].y));

			d1 += d;
			//System.out.println("i=" + i + " i+1=" + (i+1) +
					//" " + d + " " + d1);
		}

		return Math.abs(0.5 * d1);
	} /* CalcArea */
}; /* museum_area */
