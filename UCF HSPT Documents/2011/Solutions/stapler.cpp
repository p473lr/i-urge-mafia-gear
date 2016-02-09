// Include the file input classes
#include <iostream>
#include <fstream>

// Allows the use of cout without having to state the class we are using
using namespace std;

int main()
{
	// Get the input file
	ifstream inputFile("stapler.in");

	// Will store the number of cases based on the input file
	int numberOfCases;

	// Store the number of cases that the input file has
	inputFile >> numberOfCases;

	// Run through each input case
	for(int i = 1; i <= numberOfCases; i++)
	{
		// Print the title for the input case
		cout << "Battle #" << i << ":\n";

		// This will store the number of piles of paper in the input case
		int numberOfPiles;

		// Store the number of piles
		inputFile >> numberOfPiles;

		// Will store the sum of the piles of paper
		int total = 0;
		
		// Run through all the piles 
		for(int j = 0; j < numberOfPiles; j++)
		{
			// Stores the number of pages for this pile
			int numberOfPages;

			// Get the number of pages
			inputFile >> numberOfPages;

			// Add this to the total number of pages
			total += numberOfPages;
		}

		// Print the number of pages
		cout << "The Stapler must face " << total << " pages\n";

		// Determine if the stapler will be able to save the day
		if(total < 31)
		{
			cout << "The Stapler saves the day!\n\n";
		}
		else
		{
			cout << "Foiled again!\n\n";
		}
	}

	return 0;
}