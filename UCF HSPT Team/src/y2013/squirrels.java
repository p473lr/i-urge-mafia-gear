
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Squirrel Terrirory (squirrels)
//
// Author:        Matt Fontaine
// Judge Data:    Mike Galletti
// C Solution:    Patrick Fenelon
// Java Solution: John Boswell
// Verifier:      SteVen Batten

import java.util.*;
import java.io.*;

public class squirrels {

   //Infinity (a number larger than we will be using).
   static final int oo = (int)1e9;
   
   //Calculate the magnitude of x/y differences (simplifies the code).
   static double magnitude(int x, int y) {
      return Math.sqrt(x*x+y*y);
   }

   public static void main(String[] args) throws IOException {
      //Initialize scanner.
      Scanner in = new Scanner(new File("squirrels.in"));

      //Read in the number of campuses.
      int campuses = in.nextInt();
      
      //For each campus...
      for (int c=0; c<campuses; c++) {

         //Read in the number of trees.
         int trees = in.nextInt();

         //Initialize arrays for X/Y values.
         int[] X = new int[trees], Y = new int[trees];

         //For each tree, read in its location.
         for (int t=0; t<trees; t++) {
            //Read in the X and Y values of that tree.
            X[t] = in.nextInt();
            Y[t] = in.nextInt();
         }

         //Initialize the max radius so something huge, so that it is 
         //guaranteed larger than the actual minimum.
         double radius = oo;

         //For each pair of trees...
         for (int a=0; a<trees; a++)
            for (int b=a+1; b<trees; b++)
               //Calculate the distance between the trees. This divided by two
               // is the most equal (greatest for both) split of circle radii.
               // The best radius must be less than or equal to this value.
               radius = Math.min(radius,magnitude(X[a]-X[b],Y[a]-Y[b])/2.0);

         //Output the answer.
         System.out.printf("Campus #%d:\n",c+1);
         System.out.printf("Maximum territory area = %.3f\n",
                           radius*radius*Math.PI);
         System.out.println();
      }

      // Close file
      in.close();
   }
}
