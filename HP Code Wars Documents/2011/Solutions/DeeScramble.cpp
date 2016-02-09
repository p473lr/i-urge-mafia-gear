
#include <string>
#include <iostream>

using namespace std;

void unscramble( string& scrambled, string& original, int M )
{
   original = scrambled;
   int N = scrambled.length();
   for( int i=0; i<N; i++ )
   {
      int p = ( i * M ) % N;
      char c = scrambled[p];
      if( c == '~' )
         c = ' ';
      original[i] = c;
   }
}

int main( int argc, char* argv[] )
{
   int N;
   cin >> N;

   string scrambled;
   cin >> scrambled;

   string key = "-DEE";
   string original;
   int M=3;
   while( original.find( key ) == string::npos )
   {
      unscramble( scrambled, original, M++ );
   }
   cout << original << endl;
}

