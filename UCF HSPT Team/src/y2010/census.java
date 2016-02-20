/**
 * 24th Annual
 * University of Central Florida
 * High School Programming Tournament
 * May 14, 2010
 *
 * Judges' solution for "2010 Census"
 */

// import all of java.io since since input comes from a file, and
// import all of java.util since it contains many helpful classes
import java.io.*;
import java.util.*;

public class census {
    public static void main(String[] args) throws FileNotFoundException {

        // open the input file
        Scanner in = new Scanner(new File("census.in"));

        // n is the number of individual counts
        int n = in.nextInt();

        // t is the grand total of individual counts
        int t = 0;

        // use 'i' to count from zero to n
        for (int i = 0; i < n; ++i)
        {
            // c is an individual count
            int c = in.nextInt();
            
            // add c to the grand total
            t += c;
        }

        // output the grand total, using the exact message
        // format specified in the problem statement
        System.out.printf("Total census for 2010 is %d%n", t);
    }
}
