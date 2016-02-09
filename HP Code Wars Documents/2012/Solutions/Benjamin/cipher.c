/*
 * Sample solution for HP CodeWars 2012
 * Problem 9
 * "Coderian Cipher"
 */

#include <stdio.h>
#include <string.h>
#include <ctype.h>

char no_spaces[255];
char with_spaces[255];
char key[80];
char shifted_key[80];

void key_shift(int shift);
void print_decoded(int key_shift);

int main(void)
{
    int x;
    int answer;
    char input[255];
    int next_is_key = 0;
    
    no_spaces[0]='\0';
    with_spaces[0]='\0';

    while(scanf("%s", input) == 1)
    {
        /*
         * To search for the key, spaces need to be ignored, but
         * to print out the answer, the spaces must be preserved.
         * This saves two versions of the input, one without spaces, and
         * the other with.
         *
         * When the magic word "KEY" is found, don't save it, but set a flag
         * to save the next word as the key.
         */

        if(next_is_key)
        {
            strcpy(key, input);
            next_is_key = 0;
        }
        else if(strcmp(input, "KEY") == 0)
        {
            next_is_key = 1;
            continue;
        }
        else
        {
            strcat(no_spaces, input);
            strcat(with_spaces, input);
            strcat(with_spaces, " ");
        }
    }

    answer = 99;
    for(x=0;x<26;x++)
    {
        key_shift(x);
        if(strstr(no_spaces, shifted_key) != NULL)
        {
            answer=x;
            break;
        }
    }

    if(answer == 99)
    {
        // Didn't find the offset
        printf("No solution found\n");
        return 0;
    }

    print_decoded(answer);

    return 0;
}

void key_shift(int shift)
{
    int x;
    int limit;
    limit = strlen(key);
    for(x=0;x<limit;x++)
    {
        shifted_key[x] = (((key[x]-'A') + shift)%26)+'A';
    }
}

void print_decoded(int key_shift)
{
    int shift=26-key_shift;
    int y;
    char letter;
        
    /*
     * key_shift is how much the plaintext key rotated to match the
     * encrypted input.  To decrpyt the output, the input needs to shift
     * the other direction.
     */
    shift = 26 - key_shift;

    for(y=0;y<strlen(with_spaces);y++)
    {
        letter = with_spaces[y];
        if(isalpha(letter))
        {
            printf("%c", (((letter-'A') + shift)%26)+'A');
        }
        else
        {
            printf("%c", letter);
        }
    }

    printf("\n\n");
}
