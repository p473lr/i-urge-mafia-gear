
#include <stdio.h>
#include <string.h>


// For this problem all we do is read in the input and count the number
// of exact matches for the letter p and the letter q
int main()
{
   FILE * fptr = fopen("pandq.in", "r");

   // We read in 70 character strings exactly per line until there are
   // no more lines in the input, we need 71 characters at least in the 
   // buffer to account for the '/0' character which indicates end of
   // a string, this is known as the null character, we will be careful 
   // and read in 100 characters
   char buffer[100];

   // We will make use of fgets which gets the next line of input and
   // returns null if an end of file is encountered
   while (fgets(buffer, 100, fptr))
   {
      // Here we make use of the fancy strlen function!
      // strlen is in string.h so we must include it
      int len = strlen(buffer);
      int nP = 0; int nQ = 0;

      // For loops are wonderful for iterating over arrays
      for (int i=0; i<len; i++)
      {
         // Check to see if the character matches what we got
         if (buffer[i] == 'P') nP++;
         if (buffer[i] == 'Q') nQ++;
      }

      // Do the case output!
      printf("%d P's, %d Q's\n", nP, nQ);
   }

   return 0;
}
