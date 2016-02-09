
import java.io.FileInputStream;
import java.util.Scanner;

public class pedals
{
   public static void main(String args[]) throws Exception
   {
      Scanner   inFile;
      String    line;
      int       numCourses;
      int       numMovements;
      int       i, j;

      // Open the input file and create a Scanner to read from it
      inFile = new Scanner(new FileInputStream("pedals.in"));

      // Read in the number of courses
      numCourses = Integer.parseInt(inFile.nextLine());

      // Process each course
      for (i = 0; i < numCourses; i++)
      {
         System.out.printf("Course %d:%n", i+1);

         // Read in the number of movements on this course
         numMovements = Integer.parseInt(inFile.nextLine());

         // Read in each movement and translate it to the appropriate pedal
         for (j = 0; j < numMovements; j++)
         {
            line = inFile.nextLine().trim();
            if (line.equals("forward"))
               System.out.printf("1%n");
            else if (line.equals("backward"))
               System.out.printf("2%n");
            else if (line.equals("left"))
               System.out.printf("3%n");
            else if (line.equals("right"))
               System.out.printf("4%n");
            else if (line.equals("rotate left"))
               System.out.printf("5%n");
            else if (line.equals("rotate right"))
               System.out.printf("6%n");
         }

         // Print one last blank line
         System.out.printf("%n");
      }
   }
}

