/*
UCF's 2007 High School Programming Contest
Marquee
Solution Prepared by: Nick Beato

To solve this problem, we will simulate the marquee sign in a string.  The
cases we need to watch out for are:

1) When the number of characters that a sign may display is equal to the message length.
In this case, we simply output the message.

2) When the number of characters that a sign may display is larger than the message length.
In this case, we need to append spaces to the end of the input message, reducing it to the
previous case.

3) When the message will not fit on the sign.  In this case, we simulate the marquee.  Since
there is a space separating the end of the message from the beginning during a cycle, we can
directly append this to the message.  Notice that the number of lines in the output is then
equal to the message length, so we known exactly how many times to cycle the message.

With that, let's look at the solution.

*/

#include <iostream>
#include <string>
#include <fstream>
#include <cstdlib>

using namespace std;

int main() {
	ifstream in("marquee.in");
        string tmp;

	// n in "the input"
	int numberOfSigns;
        getline(in, tmp);
        numberOfSigns = atoi(tmp.c_str());

	// process all n signs, output each sign as it is processed
	for(int sign = 1; sign <= numberOfSigns; sign++) {
		// the sign's message
		string message;

		// the number of characters this sign can display
		int capacity;

		// read the sign information.
		// cin.get() is invoked to remove the '\n' character
		// from the line with the previous integer.
		getline(in, message);
                getline(in, tmp);
                capacity = atoi(tmp.c_str());

		// output the header
		cout << "Sign #" << sign << ":" << endl;

		// case 1 and case 2
		if(capacity >= message.length()) {

			// reduces case 2 to case 1 by adding spaces
			while(message.length() < capacity)
				message += " ";

			cout << "[" << message << "]" << endl;
		}

		// case 3
		else {
			// add the extra space during the cycle
			message += " ";

			// simulate the marquee by outputing the sign
			// then moving the first character to the end of the string
			for(int i = 0; i < message.length(); i++) {
				cout << "[" << message.substr(0, capacity) << "]" << endl;
				message = message.substr(1) + message[0];
			}
		}

		cout << endl;
	}

	in.close();
	return 0;
}
