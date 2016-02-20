
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Nine-Piece Puzzle (puzzle)
//
// Author:        Skyler Goodell
// Judge Data:    Danny Wasserman
// C Solution:    Antony Stabile
// Java Solution: Kyle Urquhart
// Verifier:      Tyler Brazill

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Piece{
   public char[][] item;
   public String top;
   public String left;
   public String right;
   public String bot;

   //class to represent a puzzle piece
   public Piece(String[] in){
      //create space for the characters themselves
      item = new char[in.length][in.length];

      //store the characters
      for (int i=0; i < in.length; i++){
         item[i] = in[i].toCharArray();
      }

      //keep track of the sides for easy access
      this.top = in[0];
      this.bot = in[in.length-1];
      this.left = "";
      this.right = "";
      for (int i=0; i < in.length; i++){
         this.left += item[i][0];
         this.right += item[i][in.length-1];
      }
   }

   public Piece(Piece o, int x, int y){
      this.top = o.top;
      this.bot = o.bot;
      this.left = o.left;
      this.right = o.right;
      this.item = o.item;
   }

   //debug method to print piece
   public String toString(){
      String str = "";
      for (int i=0; i < this.item.length; i++){
         for (int j=0; j < this.item.length; j++){
            str += item[i][j];
         }
         str += "\n";
      }
      return str;
   }


   //tries to see if this piece object matches the piece passed in
   //orientation is where the variable 'o' is reletive to this piece
   public boolean match(Piece o, int orientation){

      //if o is above this
      if (orientation == 1){
         if (this.top.equals(o.bot))
            return true;
      }
      //to the left
      if (orientation == 2){
         if (this.left.equals(o.right)){
            return true;
         }
      }
      //to the right
      if (orientation == 3){
         if (this.right.equals(o.left))
            return true;
      }
      //below
      if (orientation == 4){
         if (this.bot.equals(o.top))
            return true;
      }
      return false;
   }
}


public class puzzle {
   public static ArrayList<String> sol = new ArrayList<String>();

   public static void main(String[] args) throws IOException {
      //open input file
      Scanner scan = new Scanner(new File("puzzle.in"));

      //precompute all possible positions the pieces can be in
      sol("", new boolean[9]);

      //read in number of cases and loop over them
      int runs = scan.nextInt();
      for (int i=0; i < runs; i++){
         //stores the number of solutions
         int sols = 0;

         //store the pieces
         Piece[] items = new Piece[9];

         //output header for puzzle
         System.out.print("Puzzle #" + (i+1) + ": ");

         //get the size of the pieces
         int size = scan.nextInt();
         scan.nextLine();

         //read in the pieces
         for (int j=0; j < 9; j++){
            String[] input = new String[size];
            for (int k=0; k < size; k++)
               input[k] = scan.next();
            items[j] = new Piece(input);
         }

         //try the place the pieces in every possible way and 
         //see which ones work
         for (int j=0; j < sol.size(); j++){
            Piece[][] board = new Piece[3][3];

            //place the pieces on the board
            int ind = 0;
            for (int k=0; k < 3; k++){
               for (int m=0; m < 3; m++){
                  board[k][m] = 
                     items[Integer.parseInt(sol.get(j).charAt(ind) + "")];
                  ind++;
               }
            }

            //if its a solution, increment the number of solutions
            if (valid(board))
               sols++;
         }

         //output answer as appropriate
         if (sols == 1)
            System.out.println("YES");
         if (sols == 0)
            System.out.println("NO");
         if (sols > 1)
            System.out.println("MULTIPLE");
      }

      // Close file
      scan.close();
   }

   //checks to see if the board is valid
   public static boolean valid(Piece[][] board){
      //loop over all the pieces
      for (int i=0; i < 3; i++){
         for (int j=0; j < 3; j++){
            //check above, below, to the left and right of the current 
            //piece im looking at, unless there isn't a piece their
            if (j < 2)
               if (!board[i][j].match(board[i][j+1], 3)){
                  return false;
               }

            if (j > 0)
               if (!board[i][j].match(board[i][j-1], 2)){
                  return false;
               }

            if (i < 2)
               if (!board[i][j].match(board[i+1][j],4)){
                  return false;
               }

            if (i > 0)
               if (!board[i][j].match(board[i-1][j],1)){
                  return false;
               }
         }
      }

      //everything looks good
      return true;
   }

   //generates all possible permutations of the string "012345678"
   public static void sol(String str, boolean[] used){
      if (str.length() == 9){
         //have a complete arrangement so store it and return
         sol.add(str);
         return;
      }

      //otherwise, we put this piece here and generate all boards with the
      //piece here; then we don't put this piece here and generate all
      //boards without the piece here
      for (int i=0; i < 9; i++){
         if (!used[i]){
            //put piece here and generate all rest of boards
            used[i] = true;
            sol(str + i, used);

            //do not put piece here and find a different piece to put here
            //instead
            used[i] = false;
         }
      }
   }
}
