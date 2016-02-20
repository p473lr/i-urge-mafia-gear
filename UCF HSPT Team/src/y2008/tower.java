import java.io.*;
import java.util.*;

// The class name "tower" (all lowercase) is required, because the .java file
// must have the same base name as the class name, and the file must be called
// "tower.java".
class tower
{
	// the problem specification says that, for this problem, pi will be
	// represented as 3.14159265, so we reserved our own pi as that.
	static final double OUR_PI = 3.14159265;
	
	
   // Note: This boolean is useful to have in a solution-program template.
   // It allows you to put debugging output throughout your code (which is good
   // because it allows you to debug from a printout, away from the computer)
   // and then you can TURN IT OFF all at once, instead of deleting or
   // commenting out individual debug print statements.
   static final boolean debug = false;  // CHANGE TO false BEFORE SUBMITTING


   // The height of the tower does not change, so we can put it in a constant.
   // Note that we are ignoring tower width, so we treat the tower as a line.
   static final double height = 56.0;

   //
   // As with ALL of our floating-point values, we use "double" instead of
   // "float" in order to get the maximum possible accuracy.
   //

   // Here is our main method, modified only slightly from the template.
   // It will open the correct input file, read (parse) the input into separate
   // variables, and solve for the angle on each test case.
   public static void main( String[] args ) throws FileNotFoundException
   {
      Scanner in = new Scanner( new File( "tower.in" ) );

      int numTargets = in.nextInt();
      // Make sure we read the input correctly
      if( debug ) System.out.println( "### Number of tower simulations = " + numTargets );

      for( int t = 1; t <= numTargets; ++t )
      {
         double distance = in.nextDouble();
         // Make sure we read the input correctly
         if( debug ) System.out.println( "### Case " + t + ": distance = " + distance );

         //
         // The cosine of the angle is equal to the distance of the target from
         // the base divided by the tower height.
         //
         // Therefore, the distance divided by the height can be used to find the
         // angle, using arccosine.
         //
         double angleInRadians = Math.acos( distance / height );
         // Check the intermediate result
         if( debug ) System.out.println( "### angleInRadians = " + angleInRadians );

         // Convert radians to degrees
         double angleInDegrees = angleInRadians / OUR_PI * 180.0;
         // Check the result before using a formatted print
         if( debug ) System.out.println( "### angleInDegrees = " + angleInDegrees );

         // Format the output correctly.
         //
         // %d holds the place for our target number, t, an integer from 1 to
         // numTargets.
         //
         // %1.1f holds the place for the angle; the first 1 means to print
         // "at least 1" character, which is the same as "as few characters as
         // possible"; the .1 means to print at most 1 decimal place (which
         // will be rounded), and the f means that it is a floating-point
         // number.
         //
         // %n prints a newline that is appropriate for the locale and platform.
         // (Using \n usually prints a Unix newline, even on a Windows system).
         //
         System.out.printf( "Target #%d: %1.1f degrees%n", t, angleInDegrees );
      }
   }
};

