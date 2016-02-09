
#include <vector>
#include <map>
#include <string>
#include <iostream>
#include <fstream>

#include <math.h>

using namespace std;

class ItemCount
{
   public:
      int     count;
      string  name;
};

int main( int argc, char* argv[] )
{
   ItemCount itemList[4];
   map<string,int> numberMap;
   
   numberMap.insert( pair<string,int>( "TWO", 2 ) );
   numberMap.insert( pair<string,int>( "THREE", 3 ) );
   numberMap.insert( pair<string,int>( "FOUR", 4 ) );
   numberMap.insert( pair<string,int>( "FIVE", 5 ) );
   numberMap.insert( pair<string,int>( "SIX", 6 ) );
   numberMap.insert( pair<string,int>( "SEVEN", 7 ) );
   numberMap.insert( pair<string,int>( "EIGHT", 8 ) );
   numberMap.insert( pair<string,int>( "NINE", 9 ) );
   numberMap.insert( pair<string,int>( "TEN", 10 ) );
   numberMap.insert( pair<string,int>( "ELEVEN", 11 ) );
   numberMap.insert( pair<string,int>( "TWELVE", 12 ) );
   numberMap.insert( pair<string,int>( "THIRTEEN", 13 ) );

   string token;
   // read the first four lines in a loop
   for( int i=0; i<4; i++ )
   {
      // skip the first three words of each line
      for( int j=0; j<3; j++ )
         cin >> token;
      // read the number as a word
      cin >> token;
      // convert the word to an integer
      itemList[i].count = numberMap[ token ];
      // read the item name
      cin >> itemList[i].name;
   }
   // skip the first two words of the last line
   for( int j=0; j<2; j++ )
      cin >> token;
   // read the "something" that the user wants us to calculate
   cin >> token; 
   token.erase( token.length() - 1 ); // remove the question mark
   int product = 1;
   for( int i=0; i<4; i++ )
   {
      product *= itemList[i].count;
      if( itemList[i].name == token )
         cout << product << " " << token << endl;
   }
}

