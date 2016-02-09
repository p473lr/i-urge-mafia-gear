
#include <stdio.h>

// This is a problem where we must convert cups to tablespoons.
// We can do this by multiplying the number of cups given by the
// conversion factor (16) of cups to tablespoons. Remember kids
// baking is science for hungry people!
int main()
{
   // This is how we open a file for reading in c/c++
   FILE * fptr = fopen("pizza.in", "r");

   // Gather the number of ingredients from the file, 
   // ingredients can include (but not limited to)
   // pepperonis, green peppers, mushrooms, olives and chives
   int N; fscanf(fptr, "%d", &N);

   // Go through each test case until our counter reaches 0
   while (N--)
   {
      int ingredient;
      fscanf(fptr, "%d", &ingredient);
      printf("%d\n", ingredient*16);
   }

   return 0;
}
