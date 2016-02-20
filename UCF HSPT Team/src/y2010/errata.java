
import java.io.*;
import java.util.Scanner;


public class errata
{
   public static void main(String args[]) throws Exception
   {
      Scanner   fp;
      int       contestNum;
      int       numContests;
      int       numErrata;

      // Open the input file
      fp = new Scanner(new File("errata.in"));

      // Read in the number of contests
      numContests = fp.nextInt();

      // Check each contest in the file
      for (contestNum = 1; contestNum <= numContests; contestNum++)
      {
         // Read in the number of errata in this contest
         numErrata = fp.nextInt();

         // Print the appropriate output
         if (numErrata == 0)
            System.out.println("Contest #" + contestNum + ": No errata!");
         else
            System.out.println(
               "Contest #" + contestNum + ": Not perfect, errata issued!");
      }
   }
}

