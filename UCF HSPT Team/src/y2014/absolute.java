
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class absolute {
   public static void main(String[] args) throws IOException
   {
      // Open file for input
      Scanner input = new Scanner(new File("absolute.in"));

      // Read in number of test cases
      int T = input.nextInt();
      for (int t = 0; t < T; t++)
      {
         // Read in the number and then output its negative form
         int n = input.nextInt();
         System.out.printf("Integer #%d: %d%n", t+1, -Math.abs(n));
      }

      // Close file
      input.close();
   }
}
