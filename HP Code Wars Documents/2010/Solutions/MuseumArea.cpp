
#include <string>
#include <iostream>
#include <vector>
#include <math.h>

using namespace std;

class Corner
{
   public:
      float x;
      float y;
};

float Distance( Corner& c1, Corner& c2 )
{
   float dx = c1.x - c2.x;
   float dy = c1.y - c2.y;
   return sqrt( dx*dx + dy*dy );
}

int main( int argc, char* argv[] )
{
   // read the number of corners
   int cornerCount;
   cin >> cornerCount;
   // read the x,y coordinates of each corner and add them to the list
   vector<Corner*> cornerList;
   for( int i=0; i<cornerCount; i++ )
   {
      Corner* corner = new Corner();
      cin >> corner->x;
      cin >> corner->y;
      cornerList.push_back( corner );
   }
   // add the first corner again at the end of the list
   cornerList.push_back( cornerList[0] );
   //
   float area = 0.0;
   for( int i=1; i<cornerCount; i++ )
   {
      Corner* c0 = cornerList[0];
      Corner* c1 = cornerList[i];
      Corner* c2 = cornerList[i+1];
      float a = Distance( *c0, *c1 ); 
      float b = Distance( *c1, *c2 ); 
      float c = Distance( *c2, *c0 ); 
      float p = ( a + b + c ) / 2.0;
      float A = sqrt( p * (p-a) * (p-b) * (p-c) );
      area += A;
   }
   cout << area << endl;
}

