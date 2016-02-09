#include <algorithm>
#include <iostream>
#include <fstream>
using namespace std;

/**
 * UCF HSPT 2013-2014
 * Brood - C++ Solution
 * Author: Andrew Harn
 **/

 int main() {

    // Open the input file
    ifstream in;
    in.open("brood.in", ifstream::in);

    // Read the number of test cases
    int testCases;
    in >> testCases;

    // Process each case
    for (int caseNumber = 1; caseNumber <= testCases; caseNumber++) {

        // Read in the number of young dragons
        int dragons;
        in >> dragons;

        // Special case
        if (dragons == 0) {
            printf("Class #%d: YES\n", caseNumber);
            continue;
        }

        // Get the groups of the dragons
        int freq[dragons];
        for (int i = 0; i < dragons; i++) {
            freq[i] = 0;
        }
        for (int i = 0; i < dragons; i++) {
            int groupNumber;
            in >> groupNumber;
            freq[groupNumber - 1]++;
        }

        // Sort the frequencies
        sort(freq, freq + dragons);

        // Get the largest group
        int largest = freq[dragons - 1];

        // Get size of the halves on the sides of the mother
        int smallHalf = dragons >> 1;
        int largeHalf = dragons - smallHalf;

        // Get the number of dragons in each group who are part of the each halves
        int smallLargest = largest >> 1;
        int largeLargest = largest - smallLargest;

        // If the number of dragons that aren't of the most common group in a half are at least
        // as frequent as the number of dragons who are minus one,
        // then we can fit them between the largest group.
        // Otherwise, it's impossible
        // We must apply this for each half (as the mother is splitting them up in the middle)
        if (smallHalf - smallLargest >= smallLargest - 1 && largeHalf - largeLargest >= largeLargest - 1) {
            printf("Class #%d: YES\n", caseNumber);
        }
        else {
            printf("Class #%d: NO\n", caseNumber);
        }

    }

    return 0;
}
