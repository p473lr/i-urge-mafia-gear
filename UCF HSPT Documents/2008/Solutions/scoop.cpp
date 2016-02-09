// Stephen Fulwider
//  UCF High School Programming Contest 2008
//  Problem: Scoop

#include <iostream>
#include <fstream>
#include <string>

using namespace std;

int main()
{
    int numModifier;
    string modifier;

    // open the input file for reading
    ifstream fin("scoop.in",ios::in);

    // get num test cases
    int n;
    fin >> n;

    // solve each test case
    for (int testCase = 1; testCase <= n; testCase++)
    {
        // we always start with one scoop
        //  note that it's okay to use an integer to store this because the answer
        //  is guaranteed to be no more than 1,000,000.  If this were not the true,
        //  then the max case would have 4^19 scoops, which is equal to 2^38, which
        //  does not fit in a 32-bit integer, and so a long would need to be used.
        int numScoops = 1;

        // continue reading in and multiplying until the word "Scoop" is reached
        while (true)
        {
            fin >> modifier;
            if (modifier == "Scoop")
                break;

            // get which modifier used, set numModifier accordingly
            //  note that because we are guaranteed the modifiers will be one of the
            //  three modifiers given, we don't need to do any error checking to make
            //  sure that a valid modifier was input -- normally, it is good practice
            //  to make sure the input is what you're expecting; never assume!
            if (modifier == "Double")
                numModifier = 2;
            else if (modifier == "Triple")
                numModifier = 3;
            else
                numModifier = 4;
            
            // apply the modifier to the current number of scoops
            numScoops *= numModifier;
        }
        
        // output per problem spec
        cout << "Order #" << testCase << ": " << numScoops << endl;
    }

    return 0;
}