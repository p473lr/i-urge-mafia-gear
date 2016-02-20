
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Find X (find)
//
// Author:        Skyler Goodell
// Judge Data:    Matt Fontaine
// C Solution:    SteVen Batten
// Java Solution: Gabe Pita
// Verifier:      Danny Wasserman

import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class find {
   static char[][] page;
   public static void main(String[] args) throws IOException
   {
      //open input file
      Scanner in=new Scanner(new File("find.in"));
      //the number of test cases
      int n = in.nextInt();
      
      //loop over the test cases
      for(int i =1; i <= n; i++)
      {
         //read in the size of page
         int l = in.nextInt();
         int w = in.nextInt();
         
         //this will hold the characters on the page
         page = new char[l][w];
         
         //read in the page
         for(int j = 0; j < l; j++)
         {
            String nextline = in.next();
            page[j] = nextline.toCharArray();
         }
         
         //the largest size x we have seen so far
         int best = 0;
         
         //loop over the page looking of X's
         for(int row =0; row < l; row++)
            for(int col =0; col < w; col++)
            {
               if(page[row][col] =='X')
               {
                  //getting the distance the X's span in each diagonal
                  //with [row][col] being the center
                  int topleft = countDiag(row,col,-1,-1);
                  int topright = countDiag(row,col,-1,1);
                  int botleft = countDiag(row,col,1,-1);
                  int botright = countDiag(row,col,1,1);
                  
                  //getting the minimum of all these values
                  int minimum = Math.min(topleft, topright);
                  minimum = Math.min(minimum, botleft);
                  minimum = Math.min(minimum, botright);
                  
                  //this gives the value for the x at this spot.
                  minimum = minimum*2+1;

                  //for example if minimum's value was 2, then that 
                  //means all diagonals extend at least two X away from 
                  //the center. The size of the diagonal is then 1 
                  //(for the center) + 2 (for each extension from the center)
                  //giving 5
                  
                  //keep this one if it's better
                  best = Math.max(best, minimum);
               }
            }
         
         //output answer
         System.out.println("Page #"+i+": " + best);
      }

      //close file
      in.close();
   }
   
   //returns the number of consecutive X's in page starting 
   //at [startrow][startcol] in the direction of dr dc;
   //if dr = -1 and dc = -1, this goes to the top-left
   //dr = -1, dc = 1 this goes top-right 
   //dr = 1, dc = -1 this goes bottom-left
   //dr = 1, dc = 1 this goes bottom-right 
   static int countDiag(int startrow, int startcol, int dr, int dc)
   {
      int row = startrow;
      int col = startcol;
      
      //count how many we see
      int count = 0;
      
      //go up this diagonal as long as we see X's
      while( withinpage(row,col) && page[row][col] == 'X')
      {
         count++;
         row += dr;
         col += dc;
      }
      
      //the minus one is to not count the center
      return count-1;
   }
   
   //returns true if [row][col] is on the page
   static boolean withinpage(int row, int col)
   {
      return (row < page.length && col < page[0].length && row>=0 && col >=0);
   }
}
