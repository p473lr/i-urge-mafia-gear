// Arup Guha
// 3/27/2012
// Solution to HS Contest Problem: Shell

import java.util.*;
import java.io.*;

public class shell {
   public static void main(String[] args) throws Exception {
      
      Scanner fin = new Scanner(new File("shell.in"));
      
      // Use this array to store the answers to all the possible questions that might be posed.
      // allAnswers[i] = area of shell i.
      double[] allAnswers = new double[51];
      
      // These answers are easy.
      allAnswers[0] = 0;
      allAnswers[1] = 1;
      
      // Calculate the first seven, where new squares don't overlap with any of the originals.
      double lastSquare = 1;
      for (int i=2; i<8; i++) {
         
         // To get the next one, add the previous area to 1.5 times the last square.
         // This is because the new square added has twice the area of the last 
         // square, but there is some overlap with the last square itself. This
         // overlap is exactly 1/2 of the last square. So, 2 - .5 = 1.5.
         allAnswers[i] = allAnswers[i-1] + 1.5*lastSquare;
         
         // Now, the area of the last square has doubled, in preparation for the next iteration.
         lastSquare = 2*lastSquare;
      }
         
      int numCases = fin.nextInt();
      
      // Go through each case, and output the stored answer for it.
      for (int loop=1; loop<=numCases; loop++) {
         
         int numShells = fin.nextInt();
         
         // These answers are explicitly stored.
         if (numShells < 8)
            System.out.printf("Shell #%d: %.3f\n", loop, allAnswers[numShells]);
         
         // For each of these answers, just multiply the last design by the appropriate power of 2,
         // since each subsequent design is a "double" of the previous.
         else
            System.out.printf("Shell #%d: %.3f\n", loop, allAnswers[7]*Math.pow(2,numShells-7));
      }
      
      fin.close();
   }
}
