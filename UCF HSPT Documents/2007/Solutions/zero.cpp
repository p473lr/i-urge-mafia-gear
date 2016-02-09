// Guitar Zero solution
// Written in C++ by Jobby Johns
// UCF 2007 High School Programming Contest

/* The solution is very simple.  Get the number of score changes.  Start the
 * score at 0.  Then, get each score change one at a time and adjust the score
 * accordingly.  Once all the score changes are done, output the result
 * ("Shreddin" if score is positive, "Guitar Zero" otherwise).
 */

#include <iostream>
#include <fstream>

using namespace std;

int main()
	{
	// open the file
	ifstream infile("zero.in", ios::in);
	
	// variables
	int n;
	int songNum = 1;
	char c;
	
	// get the input value for each song and solve (stop if 0)
	while (infile >> n && n != 0)
		{
		// start the score and adjust it based on the input
		int score = 0;
		for (int i = 0; i < n; ++i)
			{
			infile >> c;
			if (c == '+')
				{
				score++;
				}
			else if (c == '-')
				{
				score--;
				}
			}
		
		// output the result
		cout << "Song " << songNum << ": ";
		if (score > 0)
			{
			cout << "Shreddin" << endl;
			}
		else
			{
			cout << "Guitar Zero" << endl;
			}
		
		// increment song number for next possible song
		songNum++;
		}
	
	//close the file and quit
	infile.close();
	return 0;
	}
