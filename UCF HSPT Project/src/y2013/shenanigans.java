
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Rock Paper Shenanigans (shenanigans)
//
// Author:        Kyle Urquhart
// Judge Data:    Gabe Pita
// C Solution:    Andrew Harn
// Java Solution: Marcos Arribas
// Verifier:      Stephen Royal

import java.util.*;
import java.io.*;

public class shenanigans {
   
   public static void main(String[] args) throws IOException
   {
      // Open input file.
      Scanner in = new Scanner(new File("shenanigans.in"));
      
      // Number of test cases.
      int T = in .nextInt();
      
      // Create a grid that gives information on what move beats what move.
      int[][] game = new int[3][3];
      
      // Fills out the grid with information that R > S > P > R.
      for (int i = 0; i < game.length; i++)
      {
         game[i][i] = 0;
         game[i][(i + 1) % game.length] = -1;
         game[i][(i + 2) % game.length] = 1;
      }
      
      // Iterates through every test case.
      for (int I = 1; I <= T; I++)
      {
         // Number of moves played in this game.
         int n = in.nextInt();
         int aAdvantage = 0;
         int won = 0;
         
         // Iterates through every move.
         for (int i = 0; i < n; i++)
         {
            // Gets the move that Andrew and Gabe play.
            char aMove = in.next().charAt(0);
            char gMove = in.next().charAt(0);
            
            // If someone already won, then there's no need to do anything else.
            if (won > 0)
               continue;
            
            // Hashes the moves to find their index in the grid.
            int aIn = hashChar(aMove);
            int gIn = hashChar(gMove);
            
            // If Gabe wins he gets advantage.
            if (game[aIn][gIn] == -1)
               aAdvantage = -1;
            // If Andrew wins he gets advatange,
            else if (game[aIn][gIn] == 1)
               aAdvantage = 1;
            // If it's a tie, then if someone had advantage, that person wins.
            else
            {
               if (aAdvantage == 1)
                  won = 1;
               if (aAdvantage == -1)
                  won = 2;
            }
         }
         // Print out result of the test case.
         System.out.print("Game #" + I + ": ");
         if (won == 1)
            System.out.println("Looks like Andrew won again.");
         else
            System.out.println("Oh snap! Gabe beat Andrew!");
      }

      // Close file
      in.close();
   }
   
   // Hashes a character to their index in the grid.
   static int hashChar(char c)
   {
      switch (c)
      {
      case 'R':
         return 0;
      case 'P':
         return 1;
      case 'S':
         return 2;
      default:
         return -1;
      }
   }
}

