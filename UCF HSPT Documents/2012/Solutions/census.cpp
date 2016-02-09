#include <iostream>
#include <string>
#include <sstream>
#include <fstream>
#include <algorithm>

using namespace std;

// Global Variables
// Rules about the Current House
bool hasOldest, hasYoungest, hasMiddle, hasTriplet, hasTwin;
int children;
// Keep these answers handy during the recursion so we know the answer when we are done
int numAns;
int answer[100];

// Generate all valid answers recursively
//      int* age     - array of all generated ages so far
//      int  i       - position in the array
//      int  cursum  - current sum of elements in the array
//      int  curprod - current remaining product 
void go(int* age, int i, int cursum, int curprod)
{
   // Base case
   // Impossible case, prune this line of investigation
   if (cursum < 0) return;
   
   // We are done factoring the number, check for validity
   if (curprod == 1)
   {
      int oldi = i;
      // place all the age 1's
      while (cursum > 0) { age[i++] = 1; cursum--; }
      
      // Check this to see if it follows the rules
      // Has unique oldest
      bool r1 = (i == 1 || age[0] != age[1]);
      // Has unique youngest
      bool r2 = (i == 1 || age[i-1] != age[i-2]);
      // Has unique middle
      bool r3 = (i == 1 || (i%2 == 1 && age[i/2-1] != age[i/2] && age[i/2+1] != age[i/2]));
      // Has triplets
      bool r4 = false;
      for (int j = 0; j < i-2; ++j)
         if ((j == 0 || age[j-1] != age[j]) && (age[j] == age[j+1]) && (age[j+1] == age[j+2]) && (j+3 >= i || age[j+2] != age[j+3])) r4 = true;
      // Has twins
      bool r5 = false;
      for (int j = 0; j < i-1; ++j)
         if ((j-1 < 0 || age[j-1] != age[j]) && age[j] == age[j+1] && (j+2 >= i || age[j+1] != age[j+2])) r5 = true;
      // Has n Children
      int r6 = i;
      
      // Does this answer match?
      bool match = true;
      if (hasOldest && !r1) match = false;
      if (hasYoungest && !r2) match = false;
      if (hasMiddle && !r3) match = false;
      if (hasTriplet && !r4) match = false;
      if (hasTwin && !r5) match = false;
      if (children > -1 && children != r6) match = false;
      
      // Count the match 
      if (match)
      {
         ++numAns;ofstream myfile;
         for (int j = 0; j < 100; ++j)
            answer[j] = age[j];
      }
      
      // restore the negative ones
      for (int j = 99; j >= oldi; --j) age[j] = -1;
      
      return;
   }
   
   // Continue generating all possible age assignments
   
   // Try all possible ages for this location, based on the product
   // I will never take a value larger than the last age so it will stay in order
   for (int check = i > 0 ? age[i-1] : curprod; check > 1; --check)
   {
      if (curprod%check != 0) continue;
      
      // take all divisible numbers
      age[i] = check;
      go(age, i+1, cursum - check, curprod/check);
   }
   
   // Make the terminating age -1
   age[i] = -1;
}

int main(int argc, char** argv)
{
   // Open in the input file
   ifstream fin;
   fin.open("census.in");

   // Read in the number of houses and loop the program once per house
   int H;
   fin >> H;
   for (int h = 1; h <= H; ++h)
   {
      // Read in the Sum, Product and number of Facts
      int S,P,F;
      fin >> S >> P >> F;
      // Read in an empty newline
      fin.ignore();
      
      // Default values for rules
      hasOldest = hasYoungest = hasMiddle = hasTriplet = hasTwin = false;
      children = -1;
      
      // Catch the trick case where they tell you they have two different numbers of children
      bool oneage = true;
      
      // Read in rules
      for (int f = 1; f <= F; ++f)
      {
         string line;
         getline(fin, line);
         
         if (line.compare("has Oldest Child") == 0) hasOldest = true;
         else if (line.compare("has Youngest Child") == 0) hasYoungest = true;
         else if (line.compare("has Middle Child") == 0) hasMiddle = true;
         else if (line.compare("has Triplets") == 0) hasTriplet = true;
         else if (line.compare("has Twins") == 0) hasTwin = true;
         else {
            int newchildren = atoi(line.substr(4,2).c_str()); 
            if (children != -1 && newchildren != children) oneage = false;
            else children = newchildren;
         }
      }
      
      // If there are contradicting rules then don't even search
      if (oneage)
      {
         // Use this to tell how many possible answers there are
         numAns = 0;
         int age[100];

         // Fill the answer array and the age array with defaul value (-1)
         for (int i = 0; i < 100; i++) age[i] = answer[i] = -1;
      
         // This recursive function generates all possible answers
         go(age, 0, S, P);
      }
      else
         numAns = 0;
      
      // Print out the results
      cout << "House #" << h << ":" << endl;
      if (numAns == 0)
         cout << "Contradictory Information" << endl;
      else if (numAns > 1)
         cout << "Not Enough Information" << endl;
      else
      {
         // Print out the array
         for (int i = 99; i >= 0; --i)
            if (answer[i] == -1) continue;
            else cout << answer[i] << " ";
         cout << endl;
      }
      
      cout << endl;
   }
   
   return 0;
}

