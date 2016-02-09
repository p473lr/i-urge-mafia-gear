/**
 * This problem works by taking creating the percentages as you read the input.
 *  This can work because no location is referenced after it is defined.  As a result,
 *  the only changes to a location's percentage can only be made before it is defined,
 *  so any routes below it will have half the percentage chance as the defined location.
 * Once the chances are made, simply output as queried.
 */

#include <iostream>
#include <fstream>
#include <iomanip>

using namespace std;

#define epsilon 1e-14

int main()
	{
	ifstream infile;
	infile.open("decisions.in", ios::in);
	
	// percentages stores the likelihood of reaching a location
	// 0 represents the restaurant and 1000 represents the hotel
	double percentages[1001];
	percentages[0] = 100.0;
	
	// start represents location we're at, next array represents where you can go
	int start, next[2];
	
	unsigned int i;
	unsigned int scenNum = 1;
	
	// go through each scenario
	while ( (infile >> next[0] >> next[1]) && (next[0] != -1) && (next[1] != -1) )
		{
		// initialize - start at restaurant and reset everything else
		start = 0;
		for(i = 1; i < 1001; i++)
			percentages[i] = 0.0;
		
		// since no location is referenced after it is defined, we can build the
		// percentage of a location as we go based on the location we came from
		// as the input comes in, up to the hotel
		do	{
			for (i = 0; i < 2; i++)
				{
				// hotel gets non-normal index
				if (next[i] != -1)
					percentages[next[i]] += percentages[start]/2.0;
				else
					percentages[1000] += percentages[start]/2.0;
				}
			} while ( (infile >> start >> next[0] >> next[1]) && (start != -1) );
		
		// now take in queries up to the hotel and output as wanted
		cout << "Scenario #" << scenNum++ << ":" << endl;
		
		// because of the way setprecision works, add a small amount so that .005
		// rounds up (usually rounds down if not followed by anything)
		cout.setf(ios::fixed);
		while (infile >> start && start != -1)
			{
			cout << "Location " << start << ": " << setprecision(2) << (percentages[start] + epsilon) << " %" << endl;
			}
		
		cout << "Hotel: " << setprecision(2) << (percentages[1000] + epsilon) << " %" << endl;
		}
	
	infile.close();
	return 0;
	}