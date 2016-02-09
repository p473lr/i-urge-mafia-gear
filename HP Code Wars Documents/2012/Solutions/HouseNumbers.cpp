// Take as input the number of houses and the count of each digit
//   on the street.  Then figure out the house numbers.
/*
The input consists of eleven integers. The first is the number of houses.
The next ten are the count of each digit found in the box (count of 0’s, count of 1’s, etc.)
Your program should print the difference between neighboring houses, then all the house 
numbers in numerical order, up to ten numbers on each line.

Example 1
10 1 10 1 1 1 1 1 1 1 1 
Common Difference: 2
9 10 11 12 13 14 15 16 17 18

Example 2
8 0 16 6 0 2 0 0 2 0 0
Common Difference: 700
11 12 711 712 1411 1412 2111 2112

Example 3
18 3 5 4 3 3 4 4 4 7 12
Common Difference: 98
1 2 99 100 197 198 295 296 393 394
491 492 589 590 687 688 785 786

Example 4
8 1 1 3 4 6 7 4 2 3 1
Common Difference: 1112
2345 2346 3457 3458 4569 4570 5681 5682 
*/

#include <string>
#include <iostream>

using namespace std;

#define MAXHOUSE 9999

int digitCount[10];
int totalHouses;
int houseNumbers[40];
int housesNumbered=0;
bool found;

void returnHouse(int houseNum)  // return digits to digitCount
{
	int i;

	for (i=0;i<=3 && houseNum>=1;i++)
	{
		++digitCount[houseNum % 10]; // Restore digit count
		houseNum = houseNum / 10;
	}
}

bool validAndUseHouse(int houseNum) // Check and use house digits
{
	int i;
	bool houseOK=true;
	int origHouseNum = houseNum;

	for (i=0;i<=3 && houseNum>=1;i++)
	{
		if (--digitCount[houseNum % 10] < 0) // Decrease Digit Count
			houseOK=false;		
		houseNum = houseNum / 10;
	}
	if (!houseOK)
		returnHouse(origHouseNum);

	return houseOK;
}

void placeNextOddHouse(int houseNum, int commonDifference)
{
	   if (validAndUseHouse(houseNum)) // Place Odd House
	   {
		   if (validAndUseHouse(houseNum+1)) // Place Even House
		   {
			   houseNumbers[housesNumbered++] = houseNum;
			   houseNumbers[housesNumbered++] = houseNum+1;
			   if (housesNumbered == totalHouses) // Placed enough houses.  Are all digits used?
			   {	
				   int i;	
				   found = true;

				   for (i=0;(i<10) && found;i++)
					   if (digitCount[i] > 0)
						   found = false;  // Not all the digits were used.

				   if (found)
				   {	// Valid solution.  Print it!
						cout << endl << "Common Difference: " << commonDifference << endl;
						for (i = 0; i< totalHouses; i++)
						{
							cout << houseNumbers[i] << " ";
							if (i%10 == 9) cout << endl;
						}
						cout << endl;
				   }
			   }

			   else if (commonDifference >0)
				   placeNextOddHouse(houseNum+commonDifference, commonDifference);

			   else // Diff of zero means a loop is needed for difference
			   {
				   	 // Loop to find difference that works
					for (commonDifference=2; 
						((commonDifference <= (MAXHOUSE-houseNum)/((totalHouses-2)/2))) && !found; 
						commonDifference+=2)
					{
						placeNextOddHouse(houseNum+commonDifference, commonDifference);
					}
			   }

			   houseNumbers[--housesNumbered] = 0;  // (Not really needed, but helps debugging)
			   houseNumbers[--housesNumbered] = 0;
			   returnHouse(houseNum+1); // Return Even House Digits
		   }
		   returnHouse(houseNum); // Return Odd House Digits
	   }
}

int main( int argc, char* argv[] )
{
   int i;
   int firstHouse;

   while (1)
   {
		cout << endl << "Enter the houses and digit counts (0 to end): ";
		cin >> totalHouses;
		if (totalHouses <= 0)
			return 0;

		for (i=0;i<10;i++)
		cin >> digitCount[i];

		for (firstHouse = 1, housesNumbered=0, found = false; 
			(firstHouse < (MAXHOUSE-totalHouses)) && !found; 
			firstHouse+=2)  // try all odd house numbers
		{
			placeNextOddHouse(firstHouse, 0);
		}
  
		if (!found)  // No solution was found. Print so.
			cout << " No House Numbers were found for that input." << endl;
   } // while   
}

