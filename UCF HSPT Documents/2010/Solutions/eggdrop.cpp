
#include <stdio.h>
 
int main()
{
   FILE* fp;
   int cases;

   fp = fopen("eggdrop.in", "r");
   fscanf(fp, "%d", &cases);

   // haha c++ get it =P
   for (int c=1; c<=cases; c++)
   {
      int v;

      // First solve for t using the velocity given 
      fscanf(fp, "%d", &v);
      double t = v/9.8;

      // Now plug t into the other equation
      // First calculate t^2
      double t2 = t*t;
      // mutliply t^2 by 4.9 to obtain d
      double d = 4.9 * t2;

      // Now truncate the zero result
      int res = (int)d;

      // Print the result using the formatting provided
      printf("Egg Drop #%d: The maximum height is %d meter(s).\n", c, res);
   }

   fclose(fp);
}
