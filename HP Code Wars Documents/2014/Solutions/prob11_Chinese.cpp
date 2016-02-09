/*
Chinese Remainder Theorem

The theorem works for any set of divisors [a,b,c] which are mutually prime (a, b, and c don't share common factors.) 
Your program will not need to do the arithmetic, but will only need to identify the number represented by a set of remainders.

Input
Each line of input holds the six numbers "a b c x y z" separated by spaces. Each is less than 1000. 
The last line of input will be six -1s.
3 5 7 2 0 6
7 15 16 3 2 1
23 49 96 3 30 77
127 541 59 17 120 15
21 23 40 0 0 0
-1 -1 -1 -1 -1 -1

Output
For each line, you must print the lowest positive integer N that meets the requirements. N will have at most 6 digits.
20
17
98765
999888
19320
*/

#include <iostream>

using namespace std;

int main( int argc, char* argv[] )
{
	int a,b,c,x,y,z;
	int N;

	for (;;) {
		//cout << endl << "Enter a, b, c.  Then x, y, z.  0 or -1 to end." << endl;
		cin >> a; if (a<1) return 0;
		cin >> b; if (b<1) return 0;
		cin >> c; if (c<1) return 0;
		cin >> x; if (x<0) return 0;
		cin >> y; if (y<0) return 0;
		cin >> z; if (z<0) return 0;

		for (N=x; N<1000000; N+=a) // Loop on the first value and check the others
		{
			if ( (N%b == y) && (N%c == z) )
				break;
		}
		if (N==0) N=a*b*c;  // Only positive integers are accepted.
		cout << N << endl;
	}
	return 0;
}