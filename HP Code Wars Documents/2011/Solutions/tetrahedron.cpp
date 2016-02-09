
#include <string>
#include <iostream>

using namespace std;

int main( int argc, char* argv[] )
{
   // there are twelve distinct orientations.
   // if we call { 0, 1, 2, 3 } the "original" orientation,
   // then the other eleven are rotations of the original.
   int orientations[12][4] =
   {
      { 0, 1, 2, 3 },
      { 2, 0, 1, 3 },
      { 1, 2, 0, 3 },
      { 0, 3, 1, 2 },
      { 1, 0, 3, 2 },
      { 3, 1, 0, 2 },
      { 0, 2, 3, 1 },
      { 3, 0, 2, 1 },
      { 2, 3, 0, 1 },
      { 1, 3, 2, 0 },
      { 2, 1, 3, 0 },
      { 3, 2, 1, 0 }
   };
   // it turns out that the actual colors used are irrelevant as
   // long as the letters are unique, which they are.
   string sequence1, sequence2;
   cin >> sequence1 >>  sequence2;
   while( sequence1 != "QUIT" )
   {
      string output = "NOT";
      for( int r=0; r<12 && output == "NOT"; r++ )
      {
         output = "SAME";
         for( int i=0; i<4; i++ )
            if( sequence1[i] != sequence2[ orientations[r][i] ] )
               output = "NOT";
      }
      cout << sequence1 << " " << sequence2 << " " << output << endl;
      cin >> sequence1 >>  sequence2;
   }
}

