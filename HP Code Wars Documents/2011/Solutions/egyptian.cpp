
#include <iostream>

using namespace std;

int primes[] = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 0 };

bool isDivisible( int n, int d )
{
   return ( n % d ) == 0;
}

int main( int argc, char* argv[] )
{
   int count = -1;
   while( count != 0 )
   {
      count = 0;
      int i, p;
      int d[24];
      int n = -1;
      int denominator = 1;
      while( n != 0 )
      {
         cin >> n;
         if( n == 0 )
            break;
         d[count++] = n;
         denominator *= n;
      }

      if( count == 0 )
         break;

      int numerator = 0;
      for( i=0; i<count; i++ )
      {
         numerator += denominator / d[i];
      }

      i = 0;
      p = primes[i++];
      while( p )
      {
         if( isDivisible( numerator, p ) && isDivisible( denominator, p ) )
         {
            numerator /= p;
            denominator /= p;
         }
         else
         {
            p = primes[i++];
         }
      }
      cout << numerator << "/" << denominator << endl;
   }
}
