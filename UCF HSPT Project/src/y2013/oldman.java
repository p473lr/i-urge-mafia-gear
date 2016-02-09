
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Old Man of the Mountain (oldman)
//
// Author:        Glenn Martin & Matt Fontaine
// Judge Data:    Glenn Martin
// C Solution:    Glenn Martin
// Java Solution: Glenn Martin
// Verifier:      Glenn Martin

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class oldman 
{
   public static void main(String[] args) throws IOException 
   {
      // Open file for reading
      Scanner in = new Scanner(new File("oldman.in"));

      // Get in the number of sites
      int numSites = in.nextInt();

      // Loop over the sites
      for (int i=0; i < numSites; i++)
      {
         // Get the three measurements
         int m1 = in.nextInt();
         int m2 = in.nextInt();
         int m3 = in.nextInt();

         // Test them
         System.out.printf("Set #%d: ", i+1);
         if ( (m1 == m2) && (m1 == m3) && (m2 == m3) )
            System.out.printf("Sorry, too flat.%n");
         else
            System.out.printf("Shows potential!%n");
      }

      // Done
      in.close();
   }
}
