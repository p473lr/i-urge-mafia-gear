
#include <math.h>
#include <string>
#include <vector>
#include <iostream>

using namespace std;

int compare( const string& a, const string& b )
{
   static string sequence = "QWERTYUIOPASDFGHJKLZXCVBNM";
   int maxLength = max( a.length(), b.length() );
   int result = 0;
   for( int i=0; result==0 && i<maxLength; i++ )
   {
      result = int( sequence.find( a[i] ) ) -
               int( sequence.find( b[i] ) );
   }
   if( result == 0 )
      result = int( a.length() ) - int( b.length() );
   return result;
}

// simple insertion sort
void insertionSort( vector<string>& wordList )
{
   int wordCount = wordList.size();
   string word, temp;
   for( int i=1; i<wordCount; i++ )
   {
      int j = i;
      while( j > 0 && compare( wordList[j-1], wordList[j] ) > 0 )
      {
         temp = wordList[j];
         wordList[j] = wordList[j-1];
         wordList[j-1] = temp;
         --j;
      }
   }
}

int main( int argc, char* argv[] )
{
   vector<string> wordList;
   string word;
   while( true )
   {
      cin >> word;
      if( word == "." )
         break;
      wordList.push_back( word );
   }
   insertionSort( wordList );
   for( int i=0; i<wordList.size(); i++ )
      cout << wordList[i] << endl;
}

