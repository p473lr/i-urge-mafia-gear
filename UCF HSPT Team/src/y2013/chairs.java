
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Musical Chairs (chairs)
//
// Author:        Matt Fontaine
// Judge Data:    Marcos Arribas
// C Solution:    Nick Buelich
// Java Solution: Danny Wasserman
// Verifier:      Mike Galletti

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class chairs {
  public static void main(String[] args) throws IOException {
    // Open file for reading
    Scanner in = new Scanner(new File("chairs.in"));

    // Get in the number of test cases
    int cases = in.nextInt();

    // Loop over the test cases
    for(int caseOn = 1; caseOn <= cases; caseOn++) {
      // Read in number of children and number of chairs
      int children = in.nextInt();
      int chairs = in.nextInt();

      // Since chairs <= children, can simply subtract.
      System.out.printf("Round #%d: %d children eliminated\n",
                        caseOn, children-chairs);
    }

    // Close
    in.close();
  }
}
