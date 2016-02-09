// Arup Guha
// 3/26/2011
// Solution to 2011 UCF High School Programming Contest Problem: Mice

// Given a circular pond with a circular part cut out, determine the
// largest 2x by x rectangle that can be allocated within the pond
// without intersecting the the circular part.

import java.util.*;
import java.io.*;

public class mice {

    public static void main(String[] args) throws IOException {

        // Open the input file.
        Scanner fin = new Scanner(new File("mice.in"));

        int numPonds = fin.nextInt();

        // Go through each pond.
        for (int loop=1; loop<=numPonds; loop++) {

            int rPond, rHole, xHole, yHole;

            // Get all the input for this case.
            rPond = fin.nextInt();
            rHole = fin.nextInt();
            xHole = fin.nextInt();
            yHole = fin.nextInt();

            // Calculate the distance from the center of the hole to the center of the pond.
            double dToCenter = Math.sqrt(xHole*xHole + yHole*yHole);

            // Output header.
            System.out.printf("Pond #%d:\n", loop);

            // The hole doesn't interfere here. The optimal rectangle is centered at (0,0)
            // with dimensions 2r/sqrt(5) by 4r/sqrt(5). Basically, just draw a rectangle
            // centered at the middle with a ratio of sides 2 to 1. You'll find that the
            // distance from the center of the circle to the center of either short side
            // is r/sqrt(5). If our circle is completely outside of that, we're good!!!
            if (dToCenter >= rPond/Math.sqrt(5) + rHole)
                System.out.printf("ICE CLEAR!!! %.2f\n", 8.0*rPond*rPond/5);

            // This is a difficult case to draw out. In fact, there are two drawings, one
            // for when x > 0 and another for x < 0. Though the drawings are different,
            // they lead to the same exact quadratic equation. The positive root of that
            // equation is the length of the shorter side of the rectangle. Thus, the area
            // is 2 times that side squared.
            else {
                double x = dToCenter - rHole;
                double y = quadBigRoot(2, -2*x, x*x-rPond*rPond);
                System.out.printf("OBSTRUCTION! %.2f\n", 2*y*y);
            }

            System.out.printf("\n");

        }
    }

    // Pre-condition: b^2 - 4ac >= 0, ie. the roots of the quadratic are real.
    // Post-condition: Returns the larger of the two roots.
    public static double quadBigRoot(double a, double b, double c) {
        return (-b + Math.sqrt(b*b-4*a*c))/(2*a);
    }
}
