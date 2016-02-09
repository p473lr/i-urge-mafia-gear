

#include <list>
#include <string>
#include <iostream>

using namespace std;

int main( int argc, char* argv[] )
{
   list<string> myList;
   typedef list<string>::iterator iter;
   string operation, arg1, arg2;
   while( operation != "SHOW" )
   {
      cin >> operation;
      if( operation == "ADD" )
      {
         cin >> arg1;
         myList.push_back( arg1 );
      }
      else if( operation == "INSERT" )
      {
         cin >> arg1;
         cin >> arg2;
         for( iter item=myList.begin(); item!=myList.end(); ++item )
         {
            if( *item == arg2 )
            {
               myList.insert( item, arg1 );
               break;
            }
        }
      }
      else if( operation == "REMOVE" )
      {
         cin >> arg1;
         myList.remove( arg1 );
      }
      else if( operation == "SHOW" )
      {
         for( iter item=myList.begin(); item!=myList.end(); ++item )
         {
            cout << *item << " ";
         }
         cout << endl;
      }
   }
}
