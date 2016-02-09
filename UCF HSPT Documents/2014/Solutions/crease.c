
#include <stdio.h>
#include <math.h>

int main(){
   // Open the input file
   FILE * fin = fopen("crease.in", "r");
   
   // Read in the number of creases to process
   int times;
   fscanf(fin, "%d", &times); 
   
   // Iterate over each crease
   int t;
   for (t = 1; t <= times; t++) {
      double a, b;

      // Read in paper side lengths
      fscanf(fin, "%lf %lf", &a, &b); 
      
      // We want to ensure that the height is less than the width in 
      // our solution so swap the two if this is not the case.
      if (a > b) {
         double temp = a;
         a = b;
         b = temp;
      }
      
      // Corresponding angles of congruent triangles are congruent!
      double diagonal = sqrt(a*a + b*b);
      double crease = (a*diagonal)/b;
      
      // Output the answer.
      printf("Crease #%d: %.2f\n", t, crease); 
   }
   
   // Close file and return.
   fclose(fin);
   return 0;
}
