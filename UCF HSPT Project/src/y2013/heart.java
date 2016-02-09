
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Show Through the Heart... (heart)
//
// Author:        Glenn Martin & Matt Fontaine
// Judge Data:    John Boswell
// C Solution:    Mike Galletti
// Java Solution: Gabe Pita
// Verifier:      Matt Fontaine

import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class heart {
   public static void main(String[] args) throws IOException
   {
      //open input file
      Scanner in=new Scanner(new File("heart.in"));

      //read the number of points
      int n = in.nextInt();
      
      //loop over the points
      for(int i = 1; i <= n; i++)
      {
         //read in the point
         double x = in.nextDouble();
         double y = in.nextDouble();
         
         //the point will be on the heart banner if the value of the 
         //equation is less than zero, and outside the heart banner 
         //if the value of the equation is greater than zero
         double partInParen = x*x+y*y-1;
         double equationVal = partInParen*partInParen*partInParen - x*x*y*y*y;
         
         //check result and output appropriately
         if(equationVal < 0)
            System.out.println("Point #" + i + ": You give love a bad name.");
         else 
            System.out.println("Point #" + i + ": Let it rock!");
      }

      // Close file
      in.close();
   }
}
