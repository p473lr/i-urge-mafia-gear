#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>
using namespace std;

ifstream fin("batlantis.in"); 

string s1, s2;
string solve() {
	fin >> s1 >> s2;

	// Try all suffixes of s1 starting with the longest
	for(int i = 0; i < s1.length(); i++) {
		string suffix = s1.substr(i);
		// Check if s2 starts with this suffix
		if(s2.find(suffix) == 0) {
			// Merge
			return s1 + s2.substr(suffix.length());
		}
	}

	// Nothing to merge
	return s1 + s2;
}

int t;
int main() {
	fin >> t;
	for(int i = 1; i <= t; i++)
		cout << "Entry #" << i << ": " << solve() << endl;
	return 0;
}

