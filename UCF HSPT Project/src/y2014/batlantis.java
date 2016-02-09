
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class batlantis {
  public static void main(String[] args) throws IOException {
    // Open input file
    Scanner in = new Scanner(new File("batlantis.in"));

    // Loop over the cases
    int cases = in.nextInt();
    for (int caseOn = 1; caseOn <= cases; caseOn++) {
      // Read in the two strings
      String ab = in.next();
      String bc = in.next();
      
      // Try all suffixes for ab
      String c = null;
      for (int i = 0; i <= ab.length(); i++) {
        // Get the suffix starting from index i
        String b = ab.substring(i);

        // Assume the suffix of ab is b and see if b is actually a
        // prefix of bc
        if (bc.startsWith(b)) {
          // If b is a prefix of bc than we have found our answer!
          c = ab + bc.substring(b.length());
          break;
        }
      }
      
      // Output the answer
      System.out.printf("Entry #%d: %s%n", caseOn, c);
    }

    // Close file
    in.close();
  }
}
