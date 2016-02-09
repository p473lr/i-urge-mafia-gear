
#include <math.h>
#include <string>
#include <iostream>

using namespace std;

int main( int argc, char* argv[] )
{
   double lambda, D, density, d;
   cin >> lambda >> D >> density;
   d = 1000 * 1000 / density;
   double theta = asin( lambda / d );
   double y = lambda * D / ( d * cos( theta ) );
   cout << y << endl;
}

