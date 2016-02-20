
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Battle of the Best (best)
//
// Author:        Nick Buelich
// Judge Data:    Tyler Brazill
// C Solution:    Aaron Teetor
// Java Solution: Michael Kirsche
// Verifier:      Skyler Goodell

import java.util.*;
import java.io.*;

public class best 
{
   public static void main(String[] args) throws Exception
   {
      // Open file for reading
      Scanner input = new Scanner(new File("best.in"));

      // Get the number of test cases
      int T = input.nextInt();
      // Read the trailing newline
      input.nextLine(); 
      
      // Loop over the test cases
      for(int t = 0; t<T; t++)
      {
         // Get the two names
         String x = input.nextLine(), y = input.nextLine();
         
         // Read in the first player's attacks
         int n = input.nextInt();
         input.nextLine();
         String[] first = new String[n];
         for(int i = 0; i<n; i++) first[i] = input.nextLine();
         
         // Read in the second player's attacks
         int m = input.nextInt();
         input.nextLine();
         String[] second = new String[m];
         for(int i = 0; i<m; i++) second[i] = input.nextLine();
         
         // Simulate the battle but stop when somebody uses up their attacks
         System.out.printf("Battle #%d! BEGIN!!!\n",t+1);
         for(int i = 0; i<Math.min(n,m)+1; i++)
         {
            // Handle player 1
            if(i<n) System.out.printf("%s uses %s!\n", x, first[i]);
            else
            {
               // Ran out of actions so player 1 loses!
               System.out.printf("%s is defeated by %s's %s!!!\n",
                                 x, y, second[i-1]);
               break;
            }

            // Handle player 2
            if(i<m) System.out.printf("%s uses %s!\n",y,second[i]);
            else
            {
               // Ran out of actions so player 2 loses!
               System.out.printf("%s is defeated by %s's %s!!!\n",
                                 y, x, first[i]);
               break;
            }
         }

         // Blank line after each case
         System.out.println();
      }

      // Close file
      input.close();
   }
}
