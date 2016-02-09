/*
 * Sample solution for HP CodeWars 2012
 * Problem 4
 * "Return of Spell Binder"
 */
#include <stdio.h>
#include <string.h>

int main(void)
{
    char input[255];
    char word[100];
    char target[100];
    char replacement[100];
    char new_word[100];

    char *location;
    char *current;

    int x;

    // Read one line at a time, until there is no more input
    while(fgets(input, sizeof(input), stdin) != NULL)
    {
        
        memset(new_word, '\0', sizeof(new_word));

        sscanf(input, "%s %s %s", word, target, replacement);

        // Find where in the first word the bad part is
        location = strstr(word, target);
    
        if(location == NULL)
        {
            // Sanity check
            printf("not found\n");
            return;
        }

        current = &word[0];
        x=0;

        // Copy the first part of the word up to where the bad part is
        while(current != location)
        {
            new_word[x] = *current;
            x++;
            current++;
        }

        // Copy the fix into place
        strcpy(&new_word[x], replacement);
        x+=strlen(target);
        current = &word[x];

        x = strlen(new_word);

        // Copy the rest of the word, after the fix and skipping the bad part
        while(*current != '\0')
        {
            new_word[x] = *current;
            x++;
            current++;
        }

        printf("%s\n", new_word);
    }

    return 0;
}
