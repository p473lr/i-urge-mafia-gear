// Necessary namespace and includes
#include <iostream>
#include <fstream>
#include <string>
#include <math.h>

using namespace std;

int main(){
	
	// variable to hold the number of names in the file
	int numberOfPurchases;
	
	// open the file ucfbucks.in
	ifstream fin("ucfbucks.in");
	 
	// Read in the Number of Names.
	fin >> numberOfPurchases;
	
	// Loop as many times as there are names.
	for (int index = 1; index <= numberOfPurchases; index++) {
		
		// declare some variables to use later
		int cashOnHand;
		double cost;
		
		// Read in the cashOnHand and the cost
		fin >> cashOnHand >> cost;
		
        // Print out the difference of the ceiling of the cost and the cost casted as an integer
		cout << (int)((ceil(cost)-cost)*100) << endl;
		
	}// for
	
	// finish up
	return 0;
	
}// Main
