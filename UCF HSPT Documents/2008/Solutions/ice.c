#include <stdio.h>

int main()
{
	//Declare our file pointer and open the file "ice.in"
	FILE *fp;
	fp = fopen("ice.in", "r");
	
	//Read in the number of cases to be processed
	int numCases = 0;
	fscanf(fp, "%d", &numCases);
	
	//for each case...
	int z;
	for (z = 1; z <= numCases; z++)
	{
		//Print out our current case
		printf("Session #%d:\n", z);
		
		//We will use iceCounter to determine the current number
		//of ice cubes in the current cup. 
		int iceCounter = 0;
		
		//We will use numCups to keep track of the current cup number.
		int numCups = 1;
		
		//This variable will keep track of the number of times the ice machine
		//dispenses ice. 
		int numDispense  = 0;
		fscanf(fp, "%d", &numDispense);
		
		//For each time we dispense ice...
		int i = 0;
		for (i = 0; i < numDispense; i++)
		{
			//Find the number of ice cubes dispensed
			int dispensed = 0;
			fscanf(fp, "%d", &dispensed);
			
			//Add to the ice counter
			iceCounter += dispensed;
			
			//If we have exactly have a full cup, reset the counter
			//and print a message
			if (iceCounter == 7)
			{
				printf("   Cup #%d: Perfect!\n", numCups);
				numCups++;
				iceCounter = 0;
			}
			//If we overfilled, print out by how much
			else if (iceCounter > 7)
			{
				printf("   Cup #%d: %d cubes too many!\n", numCups, iceCounter - 7);
				numCups++;
				iceCounter = 0;
			}
		}
		
		//Check to see if we have any ice left in the current cup. If we do, print out a message
		if (iceCounter > 0)
		{
			printf("   Cup #%d: Need %d more cubes!\n", numCups, 7-iceCounter);
		}
		printf("\n");
	}
	
	return 0;
}
