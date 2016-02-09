
#include <stdio.h>

void main() {
   int times, t;

   // Open the input file
   FILE* fin = fopen("sponge.in", "r");
   
   // Read in the number of sponges to process and iterate over them
   fscanf(fin, "%d", &times); 
   for(t = 1; t <= times; t++) {
      int n, s, j;

      // Read in the sponge-level and side-length
      fscanf(fin, "%d %d", &n, &s); 
      
      // Level-0 sponge volume
      double volume = s*s*s; 
      
      // We make the observation that a sponge of level (i+1) 
      // has 20/27s the volume of a sponge of level i.
      // This is because each sub-cube in the level (i+1) sponge
      // is divided into 27 smaller cubes, of which 7 are discarded.
      
      // So, we need merely reduce the level 0 sponge n times to find 
      // the final volume. 
      double ratio = 20.0/27.0; 
      for (j = 0; j < n; j++)
         volume *= ratio;
      
      // Output our answer, rounded to 3 decimal places
      printf("Sponge #%d: %.3f\n", t,volume);
   }

   // Close file and return
   fclose(fin);
   return 0;
}
