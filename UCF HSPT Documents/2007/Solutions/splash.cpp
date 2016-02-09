// Need to include standard in / out library in order to read input from file
#include <stdio.h>

int main()
{
	FILE *  inputFile;	// The file from which we will input data
	int 	numPonds; 	// Number of reflecting pond ideas			
	int 	length;		// Length of a reflecting pond
	int 	width;		// Width of a retention pond
	int 	area;		// Area of a retention pond
	
	// Open the file "splash.in" for reading
	inputFile = fopen("splash.in","r"); 
	
	// Input the number of ponds from the input file
	fscanf(inputFile,"%d",&numPonds);
	
	// For each pond, we want to input the length and width, then calculate and display the area,
	// using Area = Length * Width
	for (int i  = 0; i < numPonds; i++)
	{		
		// Input the length of the pond
		fscanf(inputFile,"%d",&length);
		
		// Input the width of the pond
		fscanf(inputFile,"%d",&width);
		
		// Calculate the area
		area = length * width;
		
		// Out the area followed by a newline
		printf("%d\n",area);
	}
	
	// All done, so we can close the input file
	fclose(inputFile);
}
