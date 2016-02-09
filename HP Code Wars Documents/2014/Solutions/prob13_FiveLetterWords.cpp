/*
Five Letter Words

Input: 
N (an integer from 1 to 10)
N capital letters. They may not be unique, nor in alphabetical order.
K1 (an integer less than 1 million)
K2
K3
...
0

Arrange the N letters into all possible 5-letter words. You may use each letter only as often as it is shows up in the input. Arrange these strings in alphabetical order, eliminating duplicate words.

Output:
Print the K’th word in the alphabetical list. If K is larger than the number of list entries, print the last entry in the list.

Example Input 1:
8
B C E D E A E A
3
0

Example Output 1:
AABDC


Example Input 2:
10
B A A A A B B B B A
40
0
Example Output 2:
BBBBB

Example Input 3:
10
R E F F O P E T S S
2596
3281
4251
0
Example Output 3:
OFFER
PETER
ROSES

*/

//#define DEBUG 1 // For debug print statements

#include <string>
#include <iostream>

using namespace std;


typedef struct
{
	char letter;
	int count;
} letterStruct;

letterStruct letterArray[10];
int numLetters, listLocation;
char wordArray[5];
int wordFound=0;
int wordCount=0;

void printWord(void)
{
	cout << wordArray[0] << wordArray[1] << wordArray[2] << wordArray[3] << wordArray[4] <<endl;
}

void printLetters(void)
{
	int i=0;
	while (letterArray[i].letter != '\0')
	{
		cout << letterArray[i].letter << " " << letterArray[i].count << endl;
		i++;
	}
}

void sortLetters(void) // Put letter array in alphabetical order
{
	letterStruct tmpLetter;
	int i,j;

	// Inefficient, but effective sort
	for (i=0;i<numLetters-1 && letterArray[i].letter > 0;i++)
		for (j=i+1; j<numLetters && letterArray[j].letter > 0; j++)
		{
			if (letterArray[i].letter > letterArray[j].letter)
			{
				// Swap letters
				tmpLetter = letterArray[i];
				letterArray[i] = letterArray[j];
				letterArray[j] = tmpLetter;
			}
		}
}

void setLetter(int letterIndex)
{
	int i;
	for (i=0;  (wordFound==0) && i<numLetters && letterArray[i].letter > 0; i++)
	{
		if (letterArray[i].count > 0)
		{
			// Use this letter
			letterArray[i].count--;
			wordArray[letterIndex] = letterArray[i].letter;
			if (letterIndex < 4)
			{
				setLetter(letterIndex+1);
			}
			else // Last letter.  Count the word.  Exit if done.
			{
				wordCount++;
#ifdef DEBUG
				cout << wordCount<<": "; printWord();
#endif
				if (wordCount == listLocation)
				{
					wordFound = 1;
				}
			}
			letterArray[i].count++;
		}
	}
}

int main( int argc, char* argv[] )
{
	int inCount;
	int i;
	char charIn;

	// Prompt
	cout << endl << "Input the number of letters, then the letters, then the list location. 0 to end." << endl;

	memset(letterArray, 0, sizeof(letterArray));

	cin >> numLetters;
	if (numLetters < 5)
	{
		cout << "Not enough letters." <<endl;
		return 0;
	}

	for (inCount=0; inCount<numLetters; inCount++)
	{
		cin >> charIn;
		// Store letter in array
		for (i=0;i<10;i++) 
		{
			if (letterArray[i].letter == '\0')  // NULL, so add a letter
			{
				letterArray[i].letter = charIn;
				letterArray[i].count++;
				break; // stop FOR loop for storing
			}
			else if (letterArray[i].letter == charIn) // Duplicate.  Just increase count
			{
				letterArray[i].count++;
				break; // stop FOR loop for storing
			}
			// else continue the loop to the next location
		}
		//cin >> charIn;  // Read in the separating space
	}

//	printLetters();
//	cout<<endl;
	sortLetters();
//	printLetters();

	cin >> listLocation;
	while (listLocation > 0)
	{	
		wordCount=0;
		wordFound=0;
		setLetter(0);  // Start assigning letters.
		
		// Print word
		cout << wordCount<<": "; printWord();
//		cout << endl;

//		cout << "0 to end." <<endl;
		cin >> listLocation;
	}

	return 0;
}