/**
 * University of Central Florida
 * 20th Annual High School Programming Tournament
 * May 11, 2006
 *
 * Judges' Solution: "Inverse Xordroid" (inverse)
 * Language: Java
 */


import java.io.*;
import java.util.*;

/**
 * The class name must be "inverse"
 */
public class inverse {

public static void main( String[] args )throws Exception {

   /*
    * Set up file input from "inverse.in"
    */
   BufferedReader in = new BufferedReader( new FileReader( "inverse.in" ) );
   StringTokenizer token;

   /*
    * The first line of input is the number of challenges
    */
   int numChallenges = Integer.parseInt( in.readLine( ) );

   /*
    * Process each of the challenges one at a time
    */
   for( int i = 1; i <= numChallenges; ++i )
   {
      /*
       * Read the whole line of input, and remove space(s) from the
       * beginning and end, then tokenizes it.
       */

      String line = in.readLine().trim();
	  token = new StringTokenizer(line);

      /*
       * The first integer on the line will be the xor result
       */
      int xorResult = Integer.parseInt( token.nextToken() );

      /*
       * All that should be left in the line is our second integer,
       * which is bit B.
       */
      int bitB = Integer.parseInt( token.nextToken() );

      /*
       * Here's the trick to this problem:
       * if A xor B = result, then A = B xor result.
       * In other words, xor is its own inverse function.
       */
      int bitA = xorResult ^ bitB;

      System.out.println( "Inverse XOR #" + i + ": " + bitA );
   }
}

}

