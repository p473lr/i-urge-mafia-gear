/*
 * Sample solution for HP CodeWars 2012
 * Problem 8
 * "Letter Distribution"
 */
#include <stdio.h>
#include <ctype.h>
#include <string.h>

int find_max(void);

int counts[26] = {0};

int main(void)
{

    int x, y, z;
    int input_char;
    int max;
    char input[100];

    // Read one line at a time, until there is no more input
    while(fgets(input, sizeof(input), stdin) != NULL)
    {

        for(y=0;y<strlen(input);y++)
        {
            input_char=input[y];

            // Skip any character that's not a letter
            if(isalpha(input_char))
            {
                // Count upper and lower case for each letter
                counts[toupper(input_char)-'A'] += 1;
            }
        }
    }

    for(x=0;x<26;x++)
    {
        max = find_max();
        for(y=0;y<26;y++)
        {
            if(counts[y] == max)
            {
                /*
                 * Each time the highest count is found, set that letter's count to -1, so it
                 * won't get checked again, instead the next highest count is returned next time
                 */
                counts[y] = -1; 
                printf("%c ", 'A'+y);
                for(z=0;z<max;z++)
                {
                    printf("* ");
                }
                printf("\n");
            }
        }
    }

    return 0;
}

// Helper function to find letter with the highest count
int find_max(void)
{
    int x;
    int max;
    
    max = 0;
    for(x=0;x<26;x++)
    {
        if(counts[x] > max)
        {
            max = counts[x];
        }
    }

    return max;
}
