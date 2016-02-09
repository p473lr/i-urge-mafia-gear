
#include <stdio.h>

int main()
{
   // Read the number of integers
   int tests;
   scanf("%d", &tests);

   // Loop through each integer
   int max;
   max = 0;
   for (int t = 1; t <= tests; t++)
   {
      // Read in the integer
      int x;
      scanf("%d", &x);

      // If this is the largest integer so far, output it and
      // mark it as the new max
      if (x > max)
      {
         printf("%d!\n", x);
         max = x;
      }
   }

   return 0;
}
