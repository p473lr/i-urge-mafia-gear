
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Show Through the Heart... (heart)
//
// Author:        Glenn Martin & Matt Fontaine
// Judge Data:    John Boswell
// C Solution:    Mike Galletti
// Java Solution: Gabe Pita
// Verifier:      Matt Fontaine

#include <stdio.h>
#include <math.h>

/*
Detecting whether a point lies within the given equation can be done using
a small observation. If we take an equation, such as y = mx + b or 
x^2 + y^2 = r^2, and rearrange it such that the right hand side is 0 
(mx + b - y = 0, x^2 + y^2 - r^2 = 0), we can evaluate the left hand side 
at any point to determine whether that point lies to the "left" or "right" 
(inside or outside) of the equation using the resulting inequality. 
Namely, evaluating the left hand side (f(x,y)) at point (a,b) will yield the
following results:

f(a,b) < 0, the point is on the "left" or "inside" of the curve
f(a,b) = 0, the point is precisely on the curve 
f(a,b) > 0, the point is on the "right" or "outside" of the curve

Try this with a few points around a straight line, a parabola, or even a 
circle to see the behavior.

So, to determine if John Bon Joswell can let it rock, all we have to do
is evaluate the given function at the given point and check the sign on 
the result!
*/

int main() {
   //Open up that file.
   FILE* fin = fopen("heart.in","r");

   //Number of points to test
   int times,t;

   //Read 'em in
   fscanf(fin,"%d", &times);

   //Loop over the points
   for(t = 1; t <= times; t++){
      //x and y coordinates of the point to test
      double x,y;
      //Read 'em in!
      fscanf(fin,"%lf %lf", &x,&y);
      
      //Compute the left hand side of the heart equation with the given point
      double inequality = pow(x*x + y*y - 1, 3.) - x*x*y*y*y;
      
      if(inequality < 0){
         //If the equation evaluated to a negative number, the point 
         //lies within the banner
         printf("Point #%d: You give love a bad name.\n",t);
      }else{
         //Otherwise, the point was on the boundary or outside. Since we're 
         //guaranteed that it cannot be on the border, there's only one 
         //other possibility!
         printf("Point #%d: Let it rock!\n",t);
      }
   }

   //Exit
   fclose(fin);
   return 0;
}
