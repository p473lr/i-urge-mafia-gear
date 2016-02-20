
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class sponge {
   public static void main(String[] args) throws FileNotFoundException {
      // Think of each sponge as 27 smaller sponges. When we make the fractal
      // we cut away 7 of them and leave 20. When we make a level two fractal
      // we do the same thing for each cube. Each cube has 20/27ths the
      // volume. Each time we go a level deeper the size gets multiplied by
      // this ratio. Note that "20d" and "27d" are written with a d so they
      // get treated as doubles.
      final double ratio = 20d / 27d;

      // Open file and read in number of cases
      Scanner in = new Scanner(new File("sponge.in"));
      int cases = in.nextInt();
      int caseNum = 0;

      // Loop over the cases
      while (caseNum++ < cases) {
         // Get input case (level of sponge and the original level-0 size)
         int levels = in.nextInt();
         int originalSize = in.nextInt();

         // The original volume is the original length cubed. We multiply
         // that by ratio once for each level. This can be expressed as one
         // multiplication by ratio to the power of the number of levels.
         double newSize = Math.pow(ratio, levels)
               * Math.pow(originalSize, 3);

         // Output the answer
         System.out.printf("Sponge #%d: %.3f%n", caseNum, newSize);
      }

      // Close input file
      in.close();
   }
}
