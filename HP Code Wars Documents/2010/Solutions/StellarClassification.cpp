

#include <string>
#include <iostream>

using namespace std;

int main( int argc, char* argv[] )
{
   string starName;
   cin >> starName;
   while( starName != "END" )
   {
      float b, v;
      cin >> b;
      cin >> v;
      float bv = b - v;
      cout << starName << " " << bv << " ";
      if( bv < -0.250 )
         cout << "O";
      else if( bv < 0.000 )
         cout << "B";
      else if( bv < 0.250 )
         cout << "A";
      else if( bv < 0.500 )
         cout << "F";
      else if( bv < 1.000 )
         cout << "G";
      else if( bv < 1.500 )
         cout << "K";
      else
         cout << "M";

      cout << endl;
      cin >> starName;
   }
}
