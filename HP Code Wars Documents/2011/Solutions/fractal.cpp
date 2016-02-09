
#include <math.h>
#include <stdio.h>
#include <string>
#include <iostream>

using namespace std;

double a1, b1, a2, b2, m, b;

double f( double x )
{
   return a1 * cos( b1 * x ) + a2 * sin( b2 * x );
}

double g( double x )
{
   return m * x + b;
}

/*
3.22 1.41 1.07 5.39
0.24 4.11 0.29 4.104
9
*/

int main( int argc, char* argv[] )
{
   cin >> a1 >> b1 >> a2 >> b2;

   double xv, yv, xp, yp;
   cin >> xv >> yv >> xp >> yp;

   double d;
   cin >> d;

   m = ( yv - yp ) / ( xv - xp );
   b = yv - m * xv;

//   cout << "m: " << m << endl;
//   cout << "b: " << b << endl;

   double xd = xp + d;
   double dx = 3.14159265 / ( 8 * max( b1, b2 ) );

   double almostZero = 1e-16;
   double marginOfError = 0.0000001;
   double x, lastDiff = -1;
   for( x=xp; x<=xd; x+=dx )
   {
      double diff = f(x) - g(x);
      // printf( "%10.4lf : %10.4lf - %10.4lf = %10.4lf\n", x, f(x), g(x), diff );
      // maybe we get lucky and hit the intersection
      if( diff*diff < almostZero )
         break;
      // if the signs of the diff has changed, then we
      // have found the interval over which the line
      // intersects the formula
      if( lastDiff / diff  < 0 )
      {
         double xLeft = x - dx;
         double xRight = x;
         double diffX, diffL, diffR;

         // cout << "binary search" << endl;

         while( xRight - xLeft > marginOfError )
         {
            x = (xLeft + xRight) / 2;
            diffX = f(x) - g(x);
            diffL = f(xLeft) - g(xLeft);
//            diffR = f(xRight) - g(xRight);
//
//            printf( "%.4lf(%.4lf) : %.4lf(%.4lf) : %.4lf(%.4lf)\n",
//                     xLeft, diffL,
//                     x, diffX,
//                     xRight, diffR );

            if( diffX / diffL < 0 )
               xRight = x;
            else
               xLeft = x;
         }
         break;
      }
      lastDiff = diff;
   }
   if( x > xd )
      cout << "NONE" << endl;
   else
   {
      cout << x << endl;
   }
}
