
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Greatest Cosmic Devourers (cosmic)
//
// Author:        Mike Galletti
// Judge Data:    Matt Fontaine
// C Solution:    Aaron Teetor
// Java Solution: Gabe Pita
// Verifier:      Andrew Harn

import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class cosmic {

   public static void main(String[] args) throws IOException
   {
      //Open file for reading
      Scanner in = new Scanner(new File("cosmic.in"));
      
      //The number of test cases
      int u = in.nextInt();
      
      //Loop over the test cases
      for(int i =1; i <= u; i++)
      {
         //Read in the factors for each monster
         int a = in.nextInt();
         int b = in.nextInt();
         
         //The pattern of stars being devoured repeats every time a star 
         //is divisible by both a and b.  All of these stars are multiples 
         //of the least common denominator of a and b, but all you need to know
         //to solve this question is that a times b is divisible by 
         //both a and b.
         
         //We get the percentage of stars that are devoured from 
         //0 to a times b, and that will be the percentage for the 
         //universe since that pattern found in 0 to a times b will 
         //repeat endlessly
         
         int ate = 0;
         
         //Iterate through the stars till we get to the repeating pattern
         for(int j =0; j < a * b; j++)
         {
            //If the star isn't contested by both monsters
            if(j % a != 0 || j % b !=0)
            {
               //And one of them wishes to eat it, then increment 
               //the number of stars eaten
               if(j % a == 0)
                  ate++;
               if(j % b == 0)
                  ate++;
            }
         }
         
         //The percentage of stars ate is simply the number eaten 
         //over the number of stars considered
         double percentAte = (ate / (double) (a * b)) * 100; 
         
         //The survival percentage is the whole minus the percentage 
         //of eaten stars
         double percentSurvived = 100 - percentAte;
         
         //Print out the answer
         System.out.printf("Universe #%d: There's a %.4f",i,percentSurvived);
         System.out.println("% chance we'll survive!");
      }

      //Done
      in.close();
   }
}
