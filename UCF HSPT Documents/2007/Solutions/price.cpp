// Arup Guha
// 4/30/07
// Solution to 2007 UCF High School Programming Contest Practice Problem
// Price List.

#include <iostream>
#include <fstream>

using namespace std;


int main() {
		
    // Open the input file.
    ifstream fin("price.in");
		
    // Get the number of cases.
    int numcases;
	fin >>	numcases;
		
	// Process each case.
	for (int i=1; i<=numcases; i++) {
		
		// Get the number of cards for this case.
		int numcards;
        fin >> numcards;
			
		// Set up initial values.
		int maxcard = 1;
		double maxvalue = 0;
			
		// Go through each card.
		for (int cardnum=1; cardnum<=numcards; cardnum++) {
				
		    double cardval;
            fin >> cardval;
				
		    // If this card is the best, reset our values.
		    if (cardval > maxvalue) {
				maxcard = cardnum;
				maxvalue = cardval;
		    }
		}
		
		// Set up cout so that values are printed to 2 decimal places.
		cout.setf(ios::fixed);
		cout.setf(ios::showpoint);
		cout.precision(2);
		
		// Output the answer for this case.
		cout << "Collection " << i << ": " << maxcard << " " << maxvalue << endl;
		
	}	
		
	fin.close();
	
}

