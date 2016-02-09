
#include <stdio.h>

int main()
{
   FILE* file;

   int cases;
   
   file = fopen("balls.in", "r");
   if(file == NULL){
   	printf("NO FILE?\n");
   	return 0;
   }

   fscanf(file, "%d", &cases);

   for (int i=1; i<=cases; i++)
   {
      int balls;
      fscanf(file,"%d", &balls);

      // you need to do every throw possible over
      // every throw that gets a distint number 
      // of wins for each turn, this decreases
      // each turn that you hit a correct bin
      // multiply the probability for each throw
      double res=1.0;
      for (int j=balls; j>0; j--)
      {
         // Don't forget to do double division
         res *= (1.0*j)/balls;
      }

      // Multiply res by 100 so we get a percent value
      res *= 100;

      // Format the fancy printing to 6 decimal places
      printf("Balls and Bins Game #%d: %.6lf%%\n\n", i, res);
   }
}
