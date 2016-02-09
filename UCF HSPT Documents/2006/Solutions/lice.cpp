#include <stdio.h>
#include <string.h>
#include <limits.h>

char registered[256][16];

// This is a simple function that returns the minimum of the three integers
// given
int min3(int a, int b, int c)
{
    if (a < b)
    {
        if (a < c)
            return a;
        else
            return c;
    }
    else
    {
        if (b < c)
            return b;
        else
            return c;
    }
}

// This is a recursive method for computing the edit distance of two strings.
// It recursively pulls characters off the end of each string until one or
// both strings are empty.  This allows an exact edit distance to be computed.
// There are more efficient algorithms using the dynamic programming
// technique, but this method works fine for short strings. 
int editDistance(char *s1, char *s2)
{
   int length1, length2;
   char ch1, ch2;
   char t1[16], t2[16];
   int d1, d2, d3;

   // Find the length of the two strings
   length1 = strlen(s1);
   length2 = strlen(s2);

   // If both strings are empty, the edit distance is zero
   if ((length1 == 0) && (length2 == 0))
   {
       return 0; 
   }
   // If one of the strings is empty, the edit distance is the length of
   // the other string
   else if (length1 == 0)
   {
       return length2;
   }
   else if (length2 == 0)
   {
       return length1;
   }
   else
   {
       // Initialize two new strings to all nulls.  This will give us a
       // "scratch pad" for putting new strings together
       memset(t1, 0, sizeof(t1));
       memset(t2, 0, sizeof(t2));

       // If both strings are non-empty, then there are three possibilities.
       // First shorten s1 and s2 by one character each, and remember
       // the character we strip off
       strcpy(t1, s1);
       ch1 = t1[length1-1];
       t1[length1-1] = 0;
       strcpy(t2, s2);
       ch2 = t2[length2-1];
       t2[length2-1] = 0;

       // The first possibility is the edit distance of the two shorter
       // strings, plus the edit distance of the two characters we just
       // stripped. If the two characters match, the edit distance for those
       // two charcters is zero, otherwise it is one.  We'll compute this
       // value and remember it.
       d1 = editDistance(t1, t2);
       if (ch1 != ch2)
           d1 += 1;

       // The second possibility is to delete ch1 from s1, which gives this:
       d2 = editDistance(t1, s2) + 1;

       // Likewise, we could delete ch2 from s2, which gives this:
       d3 = editDistance(s1, t2) + 1;

       // We want the minimum edit distance, so we return the minimum of the
       // three quantities we computed
       return min3(d1, d2, d3);
   }
}

int main(void)
{
    FILE *fp;
    char line[80], *nl;
    int cases, caseNum;
    int plates, plateNum;
    int regs, regNum;
    int minEditDist, editDist;

    // Open the input file
    fp = fopen("lice.in", "r");

    // Read the first line, and parse out the number of cases
    fgets(line, sizeof(line), fp);
    sscanf(line, "%d", &cases);

    // Iterate for each case in the input
    for (caseNum = 1; caseNum <= cases; caseNum++)
    {
        // Print the output header:
        printf("Registration %d:\n", caseNum);

        // Read in the number of existing plates
        fgets(line, sizeof(line), fp);
        sscanf(line, "%d", &plates);

        // Read in the plates
        for (plateNum = 0; plateNum < plates; plateNum++)
        {
            // Read the next plate
            fgets(line, sizeof(line), fp);
            nl = strchr(line, '\n');
            if (nl != NULL)
                *nl = 0;
 
            // Copy the plate to the registration list
            strcpy(registered[plateNum], line);
        }

        // Read in the number of registration applications
        fgets(line, sizeof(line), fp);
        sscanf(line, "%d", &regs);

        // Read in the registrations
        for (regNum = 0; regNum < regs; regNum++)
        {
            // Read the next plate
            fgets(line, sizeof(line), fp);
            nl = strchr(line, '\n');
            if (nl != NULL)
                *nl = 0;

            // Initialize the minimum edit distance
            minEditDist = INT_MAX;

            // Iterate over the existing plates and find the smallest
            // edit distance
            plateNum = 0;
            while ((plateNum < plates) && (minEditDist > 1))
            {
                // Compute the edit distance of this plate with the
                // existing registered plates
                editDist = editDistance(line, registered[plateNum]);

                // If this distance is less than the smallest distance
                // we've found so far, remember it
                if (editDist < minEditDist)
                    minEditDist = editDist;

                // Check the next plate
                plateNum++;
            }
 
            // Print the output
            if (minEditDist <= 1)
            {
                printf("%s is NOT registered.\n", line);
            }
            else
            {
                printf("%s is registered.\n", line);

                // Copy the plate to the registration list
                strcpy(registered[plateNum], line);
                plates++;
            }
        }

        // Leave a blank line
        printf("\n");
    }
    
    printf("press enter to continue...");
    char c;
    scanf("%c", &c);

    return 0;
}
