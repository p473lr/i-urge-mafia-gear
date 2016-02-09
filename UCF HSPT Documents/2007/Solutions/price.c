/* Price Check solution
 * Written in C by Jobby Johns
 * UCF 2007 High School Programming Contest
 */

/**
 * To solve this problem, get all the inputs one at a time and see which number
 * is the largest, keeping track of indices.  When you see a larger value,
 * update the variables keeping track of the current largest value and continue
 * until done.  Print out the variables.
 */

#include <stdio.h>

int main()
	{
	/* variables and file opening */
	FILE *infile;
	int n, c;
	int i, j, bestIndex;
	double val, bestVal;
	infile = fopen("price.in", "r");
	
	/* get the number of collections */
	fscanf(infile, "%d", &n);
	for (i = 1; i <= n; i++)
		{
		/* get the number of cards */
		fscanf(infile, "%d", &c);
		
		/* go through the cards, updating the best value and index as needed */
		/* start with a negative value since it is lower than possible */
		bestVal = -1.00;
		for (j = 1; j <= c; j++)
			{
			fscanf(infile, "%lf", &val);
			if (val > bestVal)
				{
				bestIndex = j;
				bestVal = val;
				}
			}
		
		/* Output the solution */
		printf("Collection %d: %d %.2lf\n", i, bestIndex, bestVal);
		}
	
	/* close the file and exit */
	fclose(infile);
	return 0;
	}
