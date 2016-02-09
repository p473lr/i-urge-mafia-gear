#include <stdio.h>
#include <math.h>

double RoundDouble(double d)
{
   int intValue = (d * 1000) + .00000001;
   if (intValue % 10 >= 5)
   {
      intValue /= 10;  
      intValue++;
   }
   else
   {
      intValue /= 10;  
   }
   return (double)intValue / 100.0;
}

int main ()
{
   //With some math, the answer should be 1/sqrt(r) + 1/sqrt(s) = 1/sqrt(answer)
   //Solve for the answer, and you'll get:
   //answer = (1 / (1 / (sqrt(r)  + 1 / sqrt(s)))^2
   
   int numCases = 0, i = 0;
   double r = 0, s = 0;
   FILE *fp = fopen("circle.in", "r");
   
   fscanf(fp, "%d", &numCases);
   for (i = 0; i < numCases; i++)
   {
      fscanf(fp, "%lf %lf", &r, &s);
      double answer = pow(1.0 / (1.0 / sqrt(r) + 1.0 / sqrt(s)), 2);
      printf("Circle Problem #%d: Radius of the small circle = %.02lf\n", i+1, RoundDouble(answer));
   }
}
