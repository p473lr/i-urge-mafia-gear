// Arup Guha
// 5/2/06
// Solution for 2006 UCF High School Programming Contest Problem Neptune

import java.io.*;
import java.util.*;

public class neptune {


  public static void main(String[] args) throws IOException {

    Scanner fin = new Scanner(new File("neptune.in"));

    int n;

    n = fin.nextInt();

    // Process each case.
    for (int i=1; i<=n; i++) {

      // Read in the data.
      int x1, y1, x2, y2, angle1, angle2;
      x1 = fin.nextInt();
      y1 = fin.nextInt();
      angle1 = fin.nextInt();
      x2 = fin.nextInt();
      y2 = fin.nextInt();
      angle2 = fin.nextInt();

      // Convert angles to radians.
      double a1 = angle1*Math.PI/180;
      double a2 = angle2*Math.PI/180;

      // The math below will be explained in a long comment at the end of this file. 
      // Basically a system of two equations is solved for one variable, which then
      // allows us to find the desired x and y coordinates.

      // The denominator for Cramer's Rule in solving the system of two equations.
      double dendet = det(Math.cos(Math.PI/2-a1),-Math.cos(Math.PI/2-a2),
                          Math.sin(Math.PI/2-a1),-Math.sin(Math.PI/2-a2));

      // The numerator for Cramer's Rule in solving the system of two equations for 
      // lambda1, which will be explained below.
      double lambda1 = det(x2-x1,-Math.cos(Math.PI/2-a2),
                           y2-y1,-Math.sin(Math.PI/2-a2));

      // Divide the numerator by the denominator.
      lambda1 = lambda1/dendet;

      // With lambda, we can solve for the intersection point.
      double xsol = x1 + lambda1*Math.cos(Math.PI/2-a1);
      double ysol = y1 + lambda1*Math.sin(Math.PI/2-a1);

      // If the values are out of range, this maps them back in range.      
      xsol = mapx(xsol);
      ysol = mapy(ysol);

      // Output this point in the required format.
      System.out.printf("Rescue #%d:  Neptune's last known position is %.2f, %.2f\n\n",i,xsol,ysol);
    }

    fin.close();
    
  }

  // Returns the determinant of a matrix with the entries x11, x12, x21, and x22 in their
  // usual locations.
  public static double det(double x11, double x12, double x21, double x22) {
    return x11*x22-x21*x12;
  }

  // Takes a longitude value out of range and maps it to the corresponding value in range.
  public static double mapx(double x) {

    // Subtract out the full revolutions around the globe.
    if (Math.abs(x) > 360)
      x = x - 360*((int)x/360);

    // Rule to map coordinates that fall of the right side.
    if (x > 180)
      x = x - 360;

    // Rule to map coordinates that fall of the left side.
    if (x < -180)
      x = x + 360;

    return x; // Everything else is okay.
  }

  // Takes a latitude value out of range and maps it to the corresponding value in range.
  public static double mapy(double y) {

    // Subtract out the full revolutions around the globe.
    if (Math.abs(y) > 360)
      y = y - 360*((int)y/360);

    // For cases where we've wrapped all the way up, then down, and the started back up again.
    if (y > 270)
      return y-360;

    // We've wrapped all the way up, but haven't reached the south pole yet.
    if (y > 90)
      return 180-y;

    // We've wrapped all the way down, then all the way up and have started back down again.
    if (y < -270)
      return 360+y;

    // We've wrapped all the way down, but haven't reached the north pole yet.
    if (y < -90)
      return -180-y;
  
    return y; // Everything else is cool.
  }

}

/*********************************************************************************

Math to solve the problem:

Let the input values be x1, y1, a1 and x2, y2 and a2, respectively, where
a1 and a2 are converted to radians for convenience.

The line described by the first transmission has the following parametric equation:

x = x1 +cos(pi/2-a1)*lambda1
y = y1 +sin(pi/2-a1)*lambda1, where lambda1 is the parameter in the equations.

Basically, we need to convert the angle based on the specifications. The angle in
the specifications starts at pi/2 and "goes" backwards. So, the correct conversion
into usual angles is pi/2-a1. Then, if we are moving from a point in that direction,
our x-coordinate is changing by some multiple of the cosine of that angle while 
the y-coordinate is changing by the same multiple of the sine of that angle. This 
is because of how cosine and sine are define on the unit circle.

Now, set up the same equation for the second distress call:

x = x2 + cos(pi/2-a2)*lambda2
y = y2 + sin(pi/2-a2)*lambda2, where lambda2 is the parameter of the equations.

It's important to note that lambda2 and lambda1 can be different because each
distress call could be traveling different distances.

These two lines are known to intersect at a single point based on the problem 
specification. Our goal is to find that point (x,y). Basically, we know that
both the x's and the y's in the parametric equations must be equal. So, we set
them equal:

x1 +cos(pi/2-a1)*lambda1 = x2 + cos(pi/2-a2)*lambda2
y1 +sin(pi/2-a1)*lambda1 = y2 + sin(pi/2-a2)*lambda2

We can do some algebra and rewrite these equations as follows:

cos(pi/2-a1)*lambda1 - cos(pi/2-a2)*lambda2 = x2 - x1
sin(pi/2-a1)*lambda1 - sin(pi/2-a2)*lambda2 = y2 - y1

Now this is set up so we can use Cramer's Rule to solve for lambda1. (We could 
have just as easily chose lambda2, but I just arbitrarily chose lambda1.)

          |x2-x1          -cos(pi/2-a2)|
          |y2-y1          -sin(pi/2-a2)|
lambda1 = ------------------------------ 
          |cos(pi/2-a1)   -cos(pi/2-a2)|
          |sin(pi/2-a1)   -sin(pi/2-a2)|

Once we know lambda1, we can plug that value back into the original parametric 
equation for the first boat and solve for both x and y.

************************************************************************************/