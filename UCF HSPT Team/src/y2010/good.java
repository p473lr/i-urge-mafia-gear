
import java.io.*;
import java.util.Scanner;


public class good
{
   public void solve() throws Exception
   {
      int   numObjects;
      int   obj;
      int   x1, y1, x2, y2;

      // Open the input file
      Scanner s = new Scanner(new File("good.in"));

      // Read the number of objects
      numObjects = s.nextInt();

      // Process the objects
      for (obj = 0; obj < numObjects; obj++)
      {
         // Read the coordinates of this object
         x1 = s.nextInt();
         y1 = s.nextInt();
         x2 = s.nextInt();
         y2 = s.nextInt();

         // Examine for horizontality or verticalness
         if ((x1 == x2) || (y1 == y2))
            System.out.println("It's all good");
         else
            System.out.println("We need to fix this");
      }
   }

   public static void main(String args[]) throws Exception
   {
      good solution;

      // Create the class and solve the problem
      solution = new good();
      solution.solve();
   }
}

