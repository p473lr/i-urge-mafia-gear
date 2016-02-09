
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <iostream>

using namespace std;

int Reverse( int n )
{
   int nReverse = 0;
   int nTemp = n;
   while( nTemp > 0 )
   {
      int digit = nTemp % 10;
      nTemp /= 10;
      nReverse = 10*nReverse + digit;
   }
   return nReverse;
}

bool isPalindrome( int n )
{
   return n == Reverse(n);
}

int main( int argc, char* argv[] )
{
   int n;
   cin >> n;
   while( !isPalindrome(n) )
      n += Reverse( n );

   cout << n << endl;
}
