/**
 * 24th Annual
 * University of Central Florida
 * High School Programming Tournament
 * May 14, 2010
 *
 * Judges' solution for "2010 Census"
 */

// include 'iostream' for the "console output" object (cout)
// and include 'fstream' for the "input file stream" class,
// which can be used to make a "file input" object (fin)
#include <iostream>
#include <fstream>

int main()
{
    // open the input file
    std::ifstream fin("census.in");

    // n is the number of individual counts
    unsigned int n;
    fin >> n;

    // t is the grand total of individual counts
    unsigned int t = 0;

    // use 'i' to count from zero to n
    for (unsigned int i = 0; i < n; ++i)
    {
        // c is an individual count
        unsigned int c;
        fin >> c;
        
        // add c to the grand total
        t += c;
    }

    // output the grand total, using the exact message
    // format specified in the problem statement
    std::cout << "Total census for 2010 is " << t << std::endl;

    return 0;
}
