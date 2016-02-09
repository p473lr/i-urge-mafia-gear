

#include <string>
#include <iostream>

using namespace std;

int main( int argc, char* argv[] )
{
   int count[6] = { 0, 0, 0, 0, 0, 0 };
   float total = -1;
   float sum = 0;
   int rating;
   do
   {
      cin >> rating;
      sum += rating;
      ++count[ rating ];
      ++total;
   } while( rating > 0 );
   for( int i=5; i>0; i-- )
   {
      cout << i << " (";
      if( count[i] < 10 )
         cout << " ";
      cout << count[i] << ") |";
      for( int j=0; j<count[i]; j++ )
         cout << "=";
      cout << endl;
   }
   float average = sum / total;
   cout << "Average rating: " << average << endl;
}
