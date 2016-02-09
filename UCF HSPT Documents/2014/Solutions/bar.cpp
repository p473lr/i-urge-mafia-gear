#include <iostream>
#include <fstream>
using namespace std;

/**
 * UCF HSPT 2013-2014
 * Progress Bar - C++ Solution
 * Author: Andrew Harn
 **/

 int main() {

    // Open the input file
    ifstream in;
    in.open("bar.in", ifstream::in);

    // Read the number of test cases
    int testCases;
    in >> testCases;

    // Process each case
    for (int caseNumber = 1; caseNumber <= testCases; caseNumber++) {

        // Read the number of checkpoints
        int checkpoints;
        in >> checkpoints;

        // Read in the times for each checkpoint for each player
        int gabeTimes[checkpoints];
        int aaronTimes[checkpoints];
        for (int i = 0; i < checkpoints; i++) {
            in >> gabeTimes[i];
        }
        for (int i = 0; i < checkpoints; i++){
            in >> aaronTimes[i];
        }

        // The excitment factor
        int excitementFactor = 0;

        // The last difference we've seen
        int last = 0;

        // Run through each checkpoint
        for (int i = 0; i < checkpoints; i++) {
            int difference = gabeTimes[i] - aaronTimes[i];

            // If we have a comeback, increment the excitement factor
            if (last * difference < 0) {
                excitementFactor++;
            }

            // Update the last if it's not a tie
            if (difference != 0) {
                last = difference;
            }
        }

        // Print out the result
        printf("Video #%d: %d\n", caseNumber, excitementFactor);

    }

    return 0;
 }
