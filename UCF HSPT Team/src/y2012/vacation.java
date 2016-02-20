/*
Vacation
Mike Galletti
---
The problem boils down to this: we have N places we'd like to visit, and we'd like to do it as quickly
as possible. Since, regardless of how we go about things, we'll have to visit all N places at some point,
one approach is to simply try visiting the N places in every possible order, or every permutation of the
N places. This is the approach this solution will demonstrate.
*/

import java.util.*;
import java.io.*;

public class vacation{
   public static boolean[][] blocked; //blocked[i][j] tells whether the path from i to j is blocked
   public static double[] x, y; //The x and y coordinates of each ride
   public static int N; //Number of rides in the park
   public static double bestAnswer; //Our best answer, updated with the method permute()
   public static final double INFINITY = 10000000000.0; //We'll choose a very large number to represent infinity
   
   public static void main(String[] args) throws FileNotFoundException{
      Scanner reader = new Scanner(new File("vacation.in"));
      
      int times = reader.nextInt();
      for(int park = 1; park <= times; park++){
         int n = reader.nextInt(); //Read the number of rides in this park
         N = n;
         int B = reader.nextInt(); //Read the number of blocked paths
         
         //Declare the arrays we'll be using to solve the problem
         x = new double[n];
         y = new double[n];
         blocked = new boolean[n][n];
         
         //Read in x and y coordinates for each ride
         for(int i = 0; i < n; i++){
            x[i] = reader.nextDouble();
            y[i] = reader.nextDouble();
         }
         
         //Read in blocked pathways
         for(int i = 0; i < B; i++){
            int a = reader.nextInt()-1;
            int b = reader.nextInt()-1;
            blocked[a][b] = true;
            blocked[b][a] = true;
         }
         
         //We'll set our best answer to infinity to start with, and see if we can do better.
         bestAnswer = INFINITY;
         permute(0, new int[n], new boolean[n]);
         
         //If our answer is still infinity, then we know that there was no way to visit every ride.
         System.out.println("Vacation #"+park+":");
         if(bestAnswer == INFINITY){
            System.out.println("Jimmy should plan this vacation a different day.");
         }else{
            //Since Jimmy has to ride all rides once, and each ride takes 120 seconds to ride, we add 120*n to our best answer (this flat amount will always be in our answer).
            System.out.printf("Jimmy can finish all of the rides in %.3f seconds.\n", bestAnswer + 120*n);
         }
         System.out.println();
      }
   }
   
   /*
   This is the permutation method we'll be using. It's a recursive method which takes 3 parameters, explained below:
   
   n: the current position we're filling
   ordering: the permutation we're building up
   used: the list of places we've already used in our ordering
   
   For each n, we'll try placing every possible value we have not yet used at position n (ordering[n]), mark that
   value as used, then recurse to position n+1. Once that branch has finished running, we'll unmark that value for
   future branches.
   
   Try to trace through the code for N = 3 if you're having trouble understanding.
   */
   public static void permute(int n, int[] ordering, boolean[] used){
      if(n == N){
         double currentX = 0, currentY = 0;
         double sum = 0;
         int pos = 0;
         boolean flag = false;
         
         for(int i = 0; i < N; i++){
            if(i > 0){
               //Check whether the path from i-1 to i is blocked, update flag if so.
               flag = flag || blocked[ordering[i-1]][ordering[i]];
            }
            
            double dx = x[ordering[i]]-currentX; //Change in x from our current position to the next
            double dy = y[ordering[i]]-currentY; //Change in y
            sum += Math.sqrt(dx*dx + dy*dy); //Add the distance we've travelled to our sum
            
            //Update our current x and y coordinates
            currentX = x[ordering[i]]; 
            currentY = y[ordering[i]];
         }
         
         //If there were no blocked paths in this ordering, update our best answer!
         if(!flag && sum < bestAnswer)
            bestAnswer = sum;
         
         //We're done with this permutation, so there's no need to recurse again.
         return;
      }
      
      //Iterate over all possible rides we can take at this step
      for(int i = 0; i < N; i++){
         //If we have not used ride i yet, try it out!
         if(!used[i]){
            used[i] = true; //Mark it used for now
            ordering[n] = i; //Remember that we picked ride i to be visited as our nth ride
            permute(n+1, ordering, used); //Recurse to n+1
            used[i] = false; //Once we're done recursing into n+1, we can free up i.
         }
      }
   }
}
