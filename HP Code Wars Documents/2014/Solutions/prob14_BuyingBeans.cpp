/*
   A friend of mine is a grocer and has a strange offer for buying beans. 
   I can put as many beans as I want on one side of a balance scale. 
   If he CANNOT make the scale balance using his weights, he will buy my 
   beans for a dollar each (he thinks they're magic). I only get one weighing 
   each day, so obviously I want to put as many beans as possible on the scale. 
   But he changes his base weights every day, so I always have to work out 
   my best deal. Each bean weighs one gram, and his weights are also in units of grams.

For example, if he has a supply of weights with values of 5 and 7 grams, 
he can’t balance 1, 2, 3, 4, 6, 8, 9, 11, 13, 16. 18. or 19 beans. 
And the largest number he cannot balance is 23 beans.

If the next day he uses weights of 6, 7 and 11 grams, 
the largest number he cannot balance is 16 beans.

Input 
The first line will be a number N: 2, 3, or 4, representing the number 
of different weights the grocer uses.
The next line will be N different integers, the value in grams of each weight. 
The maximum weight is 100 grams.

Output
Print the maximum number of beans which cannot be balanced using any 
combination of multiples of the N weights. [In cases when all the weights 
share a common factor F, the maximum is infinite; the weights cannot combine 
to any value equal to 1 plus a multiple of F. You may assume 
the input will never include these cases.]

Example Input 1
2
5 7
Example Output 1
23

Example Input 2
3
6 7 11
Example Output 2
16

Example Input 3
4
11 12 13 14

*/

//#define DEBUG 1 // For debug print statements

#include <string>
#include <iostream>

using namespace std;
	
int weights[4];
int weightCount;

int findMaxGrams(void)
{
	int maxSearchValue;
	int i[4];  // Array of indeces
	int newGuess[4];  // Array of smaller values as weights are subtracted
	int currentGuess;
	int thisGuessIsDivisible;

	// For 2 weights, the maximum number of grams can be generated with
	//    the formula below.  For more than 2 weights, the max will be
	//    equal or less.

	maxSearchValue = (weights[0]-1) * (weights[1]-1) - 1;
	if (weightCount == 2)
		return maxSearchValue;

	for (currentGuess = maxSearchValue; currentGuess>0; currentGuess--)
	{
		thisGuessIsDivisible = 0;
		// Nested loops each subtract multiples of the weights
		for(i[0]=0; !thisGuessIsDivisible && ( (newGuess[0]=currentGuess-i[0]*weights[0])>0); i[0]++)
		{
			for(i[1]=0; !thisGuessIsDivisible && ( (newGuess[1]=newGuess[0]-i[1]*weights[1])>0); i[1]++)
			{
				for(i[2]=0; !thisGuessIsDivisible && ( (newGuess[2]=newGuess[1]-i[2]*weights[2])>0); i[2]++)
				{
					for(i[3]=0; !thisGuessIsDivisible && ( (newGuess[3]=newGuess[2]-i[3]*weights[3])>0); i[3]++)
					{
						if ( (newGuess[3] % weights[0] == 0) ||
							 (newGuess[3] % weights[1] == 0) ||
							 (newGuess[3] % weights[2] == 0) ||
							 (newGuess[3] % weights[3] == 0) )
							 thisGuessIsDivisible = 1;
					} 
				}
			}
		}
		if (!thisGuessIsDivisible)  // All possible combinations were checked and still not divisible
			return currentGuess;
	}
	return 0;
}

int main( int argc, char* argv[] )
{
	int i, maxGrams;

	// Prompt
	cout << endl << "Input the number of weights, then the individual values in grams. 0 to end." << endl;
	cin >> weightCount;
	while (weightCount>=2 && weightCount<=4)
	{
		memset(weights, 10000, sizeof(weights)); // Initialize to LARGE values to shorten search

		for (i=0; i<weightCount; i++)
			cin >> weights[i];

		maxGrams = findMaxGrams();

		cout << maxGrams << endl;

//		cout << endl << "Input the number of weights, then the individual values in grams. 0 to end." << endl;
		cin >> weightCount;
//		weightCount = 0;  // to end the loop
	}

	cout << endl;
}