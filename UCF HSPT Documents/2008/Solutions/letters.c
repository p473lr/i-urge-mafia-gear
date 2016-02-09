/*
	Judge solution for letters
	C programming language
	UCF High School Programming Contest, 2008
	Solution written by Jeremy Elbourn
*/

#include <stdio.h>

int main()
{
	char word[11];
	int numWords, i, j;
	FILE * inpt;
	
	// Open the input file for reading
	inpt = fopen("letters.in","r");
	
	// Read the number of words from the input file
	fscanf(inpt, "%d\n", &numWords);
	
	// For each word
	for (i = 0; i < numWords; i++)
	{
		// Read the unmodified word from the input file
		fscanf(inpt, "%s", word);

		// Check each letter in the word to see if it is a lower case 'l',
		// and if it is, replace it with a lower case 'w'
		for (j = 0; word[j] != '\0'; j++)
			if (word[j] == 'l')
				word[j] = 'w';

		// Print the modified word
		printf("%s\n", word);
	}

	return 0;
}
