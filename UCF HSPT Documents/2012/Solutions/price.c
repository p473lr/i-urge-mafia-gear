/*
  Jason Jones
  filename: price.c
  file input, standard output
*/

#include<stdio.h>
#include<stdlib.h>

int main()
{
   int i, j, p; // loop variables
   int n; // number of items to price 
   int k; // number of days to track price for an item
   int price;
   int prices[100]; // contains the prices for the item
   int priceCount[100]; // number of times each price occurs
   int numPrices; // number of unique prices; also, size of prices and priceCount arrays
   int found;
   int mode; // most common price 
   int modeCount; // frequency of the mode
   FILE *fpIn;

   fpIn = fopen("price.in", "r");
   fscanf(fpIn, "%d", &n);
   for(i = 0; i < n; i++) {
      // initialize prices and priceCount for the new item
      for(j = 0; j < 100; j++) {
         prices[j] = 0;
         priceCount[j] = 0;
      }
      numPrices = 0;
      fscanf(fpIn, "%d", &k);
      for(j = 0; j < k; j++) {
         fscanf(fpIn, "%d", &price);
         // check if the price is already in prices[].  if it is, increment priceCount for that price.
         found = 0;
         for(p = 0; p < numPrices; p++) {
            if(prices[p] == price) {
               found = 1;
               priceCount[p]++;
            }
         }
         // if you didn't find the price, add it to prices[].
         if(!found) {
            prices[numPrices] = price;
            priceCount[numPrices] = 1;
            numPrices++;
         }
      }
      // find the mode
      mode = 0;
      modeCount = 0;
      for(j = 0; j < numPrices; j++) {
         if(priceCount[j] > modeCount) {
            mode = prices[j];
            modeCount = priceCount[j];
         }
      }
      printf("Item #%d: Most likely price is %d cents.\n\n", i+1, mode);
   }
   fclose(fpIn);
   return 0;
}
