// Necessary namespace and includes
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main(){
	
	// variable to hold the number of names in the file
	int numberOfNames;
	
	// open the file election.in
	ifstream fin("election.in");
	
	// Read in the Number of Names.
	fin >> numberOfNames;
	
	// Loop as many times as there are names.
	for (int index = 1; index <= numberOfNames; index++) {
		
		// Make a string to hold the name of the winner.
		string winner;
		
		// Read in the winner
		fin >> winner;
		
		// Print out the winner's message
		cout << "Election Result " << index << ": " << winner << " wins!" << endl;
		
	}// for
	
	// finish up
	return 0;
	
}// Main
