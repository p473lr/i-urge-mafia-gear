
import java.util.Scanner;
import java.io.File;

public class dinner { 
   public static void main(String[] args) throws Exception {
      // We're going to precompute the answers beforehand
      long[] ways = new long[110];
   
      // There is one way to pay a bill of $0
      ways[0] = 1;
   
      // Then we build up our answers
      for (int i = 0; i < 100; i++) {
         // From our current amount, we can lay down a $2, $5, or $10
         ways[i + 2] += ways[i];
         ways[i + 5] += ways[i];            
         ways[i + 10] += ways[i];      
      }

      // Now we read from the input to get the cases
      Scanner in = new Scanner(new File("dinner.in"));
      int testCases = in.nextInt();
   
      // Process each test case   
      for (int caseNumber = 1; caseNumber <= testCases; caseNumber++) {
         // Get the bill amount
         int amount = in.nextInt();
      
         // Print out the ways to reach that amount
         System.out.printf("Dinner #%d: %d%n", caseNumber, ways[amount]);
      }

      // Close file
      in.close();
   }
}
