
import java.util.*;
import java.io.*;

public class tingling {
   private static final double PI = 3.141592653589793;

   public static void main(String[] args) throws IOException {
      // Open input file
      Scanner br = new Scanner(new File("tingling.in"));

      // Read number of cases and loop over them
      int testCases = br.nextInt();
      for (int caseOn = 1; caseOn <= testCases; caseOn++) {
         // Read enemy name
         String name = br.next();

         // You are given the square root of the area of a circle and asked
         // to find its radius so A = r*r*pi -> sqrt(A) = r*sqrt(pi) and
         // this leads to r = sqrt(A)/sqrt(pi) 
         double squareRootArea = br.nextDouble();
         double radius = squareRootArea/Math.sqrt(PI);

         // Output the answer
         System.out.printf("%s is %.3f feet away.%n", name, radius);
      }

      // Close file
      br.close();
   }
}
