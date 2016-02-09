

#include <string>
#include <iostream>

using namespace std;

const int MAX = 100;
int list[ MAX ];
int listsize = 0;

bool isRepeat( int n )
{
   for( int i=0; i<listsize; i++ )
      if( list[i] == n )
         return true;
   return false;
}


int main( int argc, char* argv[] )
{
   int x, n;
   cin >> x;
   n = x;
   // check for the two end conditions
   while( n != 1 && ! isRepeat( n ) )
   {
      // add n to the list of previously encountered numbers
      list[ listsize++ ] = n;
      // compute the sum of the squares of the digits of n
      int sum = 0;
      while( n > 0 )
      {
         int d = n % 10;
         sum += d * d;
         n /= 10;
      }
      // replace n with the sum of squares
      n = sum;
      // cout << n << endl;
   }
   if( n == 1 )
      cout << x << " is a happy number" << endl;
   else
      cout << x << " is an unhappy number" << endl;
}

