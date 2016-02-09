#include <stdio.h>

int main()
{

   FILE *in;
   int cases, i, x1, y1, x2, y2;
   in = fopen("good.in", "r");

   // Read in the cases
   fscanf(in, "%d", &cases);

   for (i=0; i<cases; i++)
   {
      fscanf(in, "%d %d %d %d", &x1, &y1, &x2, &y2);

      // Determine if there are any vertical or horizontal lines
      // we do this by checking of the x's or y's are equal
      if ((x1 == x2)||(y1 == y2))
         printf("It's all good\n");
      else
         printf("We need to fix this\n");
   }
}
