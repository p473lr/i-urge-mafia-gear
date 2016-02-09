#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(void)
{
   FILE  *fp;
   char  line[64];
   int   numProducts;
   int   i;
   char  productName[64];
   char  *ch;

   // Open the input file
   fp = fopen("upractice.in", "r");

   // Read the number of products
   fgets(line, sizeof(line), fp);
   numProducts = atoi(line);

   // Process the product names
   for (i = 0; i < numProducts; i++)
   {
      // Read the product name (get rid of the newline character at the
      // end)
      fgets(productName, sizeof(productName), fp);
      ch = strchr(productName, '\n');
      if (ch != NULL)
        *ch = 0;
      
      // Print out the new product name
      printf("u%s\n", productName);
   }

   fclose(fp);
}
