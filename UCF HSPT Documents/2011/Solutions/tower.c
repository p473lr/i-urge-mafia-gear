#include <stdio.h>

int main() {
	
	//Declare numTowers as an integer
	int numTowers;
        // Open file
        FILE *infile = fopen("tower.in", "r");
	//Input the number of towers to be processed
	fscanf(infile, "%d",&numTowers);
	
	//Declare t as an integer (the current tower in the loop)
	int t;
	//Loop through each tower
	for (t=0; t<numTowers; t++) {
		
		//Declare stairsInTower as an integer
		int stairsInTower;
		//Input the number of strairs in the current tower
		fscanf(infile, "%d",&stairsInTower);

		//Output the number of stairs in the current tower		
		printf("%d\n",stairsInTower);

	//End of for loop		
	}
	
        // Close file
        fclose(infile);
	return 0;
	
}
