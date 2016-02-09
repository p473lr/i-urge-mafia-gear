#include <stdio.h>
#include <math.h>

int main ()
{
   //In this problem, we have two unknowns, lets call them A and B
   //if our two inputs are X, and Y, then we have...
   //A + B = X
   //A - B = Y
   //With a little math, we can solve for A and B, getting
   //B = (X - Y) / 2
   //A = Y + (X - Y) / 2
   //A problem is insolvable if A or B are below zero, or X - Y is not even
   //since that would result in a fraction
   
   int X = 0, Y = 0;
   FILE *fp = fopen("jeopardy.in", "r");
   
   fscanf(fp, "%d %d", &X, &Y);
   
   //Read until both inputs are zero
   while (X != -1 || Y != -1)
   {
      //Find B
      int B = (X - Y) / 2;
      
      //Find A
      int A = Y + (X - Y) / 2;
      
      //If X - Y are even and A is at least 0 and B is at least zero,
      //We have a valid solution
      if ((((abs(X - Y)) % 2) == 1)  || A < 0 || B < 0)
         printf("NOT SO FAST, TREBEKK!\n");
      else
         printf("What are the sum and difference of %d and %d?\n", A, B);
         
      fscanf(fp, "%d %d", &X, &Y);
   }
}
