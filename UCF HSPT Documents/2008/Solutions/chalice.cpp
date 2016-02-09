
#include <stdio.h>

int main(void)
{
   FILE *fp;
   int  numTrials;
   int  trial;
   int  witchWeight;
   int  numGeese;
   int  i;
   int  gooseWeight;
   int  geeseWeight;

   // Open the input file
   fp = fopen("chalice.in", "r");

   // Read the number of trials
   fscanf(fp, "%d\n", &numTrials);

   // Conduct the trials
   for (trial = 0; trial < numTrials; trial++)
   {
      // Read the weight of the accused
      fscanf(fp, "%d\n", &witchWeight);

      // Read the number of geese
      fscanf(fp, "%d\n", &numGeese);

      // Read and accumulate the weights of the geese
      geeseWeight = 0;
      for (i = 0; i < numGeese; i++)
      {
         // Read the weight
         fscanf(fp, "%d", &gooseWeight);

         // Add it to the total
         geeseWeight += gooseWeight;
      }

      // Compare the two weights and print the output
      if (witchWeight <= geeseWeight)
         printf("Trial #%d: SHE'S A WITCH! BURN HER!\n", trial+1);
      else
         printf("Trial #%d: She's not a witch. BURN HER ANYWAY!\n", trial+1);
   }

   // Close the input file
   fclose(fp);
}
