
#include <vector>
#include <map>
#include <string>
#include <iostream>
#include <fstream>

#include <math.h>

using namespace std;

class PointValue
{
   char letter;
   int  value;
};


int main( int argc, char* argv[] )
{
   map<char,int> letterValues;
   
   letterValues.insert( pair<char,int>( 'A', 1 ) );
   letterValues.insert( pair<char,int>( 'B', 3 ) );
   letterValues.insert( pair<char,int>( 'C', 3 ) );
   letterValues.insert( pair<char,int>( 'D', 2 ) );
   letterValues.insert( pair<char,int>( 'E', 1 ) );
   letterValues.insert( pair<char,int>( 'F', 4 ) );
   letterValues.insert( pair<char,int>( 'G', 2 ) );
   letterValues.insert( pair<char,int>( 'H', 4 ) );
   letterValues.insert( pair<char,int>( 'I', 1 ) );
   letterValues.insert( pair<char,int>( 'J', 8 ) );
   letterValues.insert( pair<char,int>( 'K', 5 ) );
   letterValues.insert( pair<char,int>( 'L', 1 ) );
   letterValues.insert( pair<char,int>( 'M', 3 ) );
   letterValues.insert( pair<char,int>( 'N', 1 ) );
   letterValues.insert( pair<char,int>( 'O', 1 ) );
   letterValues.insert( pair<char,int>( 'P', 3 ) );
   letterValues.insert( pair<char,int>( 'Q', 10 ) );
   letterValues.insert( pair<char,int>( 'R', 1 ) );
   letterValues.insert( pair<char,int>( 'S', 1 ) );
   letterValues.insert( pair<char,int>( 'T', 1 ) );
   letterValues.insert( pair<char,int>( 'U', 1 ) );
   letterValues.insert( pair<char,int>( 'V', 4 ) );
   letterValues.insert( pair<char,int>( 'W', 4 ) );
   letterValues.insert( pair<char,int>( 'X', 8 ) );
   letterValues.insert( pair<char,int>( 'Y', 4 ) );
   letterValues.insert( pair<char,int>( 'Z', 10 ) );

   string grid[99][99];
   int gridSize;
   cin >> gridSize;
   for( int row=0; row<gridSize; row++ )
   {
      for( int col=0; col<gridSize; col++ )
      {
         cin >> grid[row][col];
      }
   }
   int wordCount;
   cin >> wordCount;
   for( int i=0; i<wordCount; i++ )
   {
      string word;
      cin >> word;
      int row, col;
      cin >> row;
      cin >> col;
      --row; // input is ranged from 1 to N
      --col; // c++ indexes range from 0 to N-1
      char direction;
      cin >> direction;
      int dr = 0;
      int dc = 0;
      if( direction == 'H' )
         dc = 1;
      else
         dr = 1;
      int wordMultiplier = 1;
      int score = 0;
      for( int j=0; j<word.length(); j++ )
      {
         int letterMultiplier = 1;
         if( grid[row][col] == "2L" )
            letterMultiplier = 2;
         if( grid[row][col] == "3L" )
            letterMultiplier = 3;
         if( grid[row][col] == "2W" )
            wordMultiplier *= 2;
         if( grid[row][col] == "3W" )
            wordMultiplier *= 3;
         /*
         cout << "letter: " << word[j];
         cout << " in " << grid[row][col];
         cout << " LM=" << letterMultiplier;
         cout << " WM=" << wordMultiplier;
         */
         score += letterValues[ word[j] ] * letterMultiplier;
         // cout << " score: " << score << endl;
         row += dr;
         col += dc;
      }
      score *= wordMultiplier;
      cout << word << " " << score << endl;
   }
}

