// Take as input the total number of peppers, then print the most efficient
//   set of packages of 5, 7, and 13 to reach that number.

#include <string>
#include <iostream>

using namespace std;

// Print one line to show the number of packages of a particular size.
void packagePrint(int packages, int size)
{
	if (packages > 0) // Don't print a line for 0 packages
	{
	    cout << packages << " package";
		if (packages>1) cout << "s";     // Handle plural
		cout << " of " << size << endl;
	}
}

int main( int argc, char* argv[] )
{
   int peppers, pep13, pep7, pep5, remain13, remain7;

   do
   {
	   bool done = false;
	   cout << endl << "Enter the number of peppers (0 to end): ";
	   cin >> peppers;
   
	   for (pep13=peppers/13; (pep13>=0) && (!done); pep13--)
	   {
		   remain13 = peppers - (13*pep13);
		   for (pep7 = remain13/7; (pep7>=0) && (!done); pep7--)
		   {
			   remain7 = remain13 - (7*pep7);
			   
			   if ((remain7 % 5 == 0) &&  // If 5 divides evenly, we found our solution!
				   (((pep5 = remain7/5)>0) || (pep13>0) || (pep7>0))) 
			   {
				   // Print the results
				   done = true;
				   cout << peppers << " peppers can be packed most economically in:" << endl;
				   packagePrint(pep13, 13);
				   packagePrint(pep7, 7);
				   packagePrint(pep5, 5);
				   cout << (pep13+pep7+pep5) <<" total packages." << endl;
			   }// If mod 5 == 0
		   }// for loop for 7
	   }// for loop for 13
   
	   if ((!done) && (peppers>0)) // No solution was found. Print error.
		   cout << peppers << " peppers cannot be packed." << endl;

   } while (peppers > 0);
}

