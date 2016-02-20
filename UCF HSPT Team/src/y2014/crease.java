
import java.util.*;
import java.io.*;

public class crease {
   public static void main(String[] args) throws Exception {
      // Open the input file
      Scanner in = new Scanner(new File("crease.in"));
      
      // Iterate over each case
      int numCases = in.nextInt();
      for (int curCase = 1; curCase <= numCases; curCase++) {
         // Read in the size of paper
         int a = in.nextInt();
         int b = in.nextInt();

         // In order to regulate the problem a bit, always make 'a'
         // the smaller of the two input values and 'b' the larger.
         if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
         }
         
         // The length of the main diagonal (from one corner to another)
         // is found using the Pythagorean theorem
         double mainDiagonal = Math.sqrt(a*a + b*b);
         
         // Here, there is a similar triangle relation. Notice that the
         // large half rectangle defined by sides 'a', 'b', and the main 
         // diagonal, once rotated and scaled, is similar to the triangle 
         // made by side 'a' (slid appropriately), some length along the 
         // bottom of the rectangle, and the crease diagonal we are trying 
         // to find.
         
         // By the laws of similar triangles, the ratio of any two 
         // sides in these triangles must be equal. This yields:
         // (mainDiagonal / b) == (crease / a)
         // When rearranged, we achieve the following result:
         double crease = a * mainDiagonal / b;

         // Output the answer
         System.out.printf("Crease #%d: %.2f%n", curCase, crease);
      }

      // Close file
      in.close();
   }
}
