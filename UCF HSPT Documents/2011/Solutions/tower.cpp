#include <iostream>

using namespace std;

int main() {
	
	//Declare numTowers as an integer
	int numTowers;
	//Input the number of towers to be processed
	cin >> numTowers;
	
	//Loop through each tower
	for (int t=0; t<numTowers; t++) {
		
		//Declare stairsInTower as an integer
		int stairsInTower;
		//Input the number of strairs in the current tower
		cin >> stairsInTower;
		
		//Output the number of stairs in the current tower
		cout << stairsInTower << endl;	
		
	//End of for loop
	}
	
	return 0;
	
}
