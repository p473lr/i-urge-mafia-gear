
#include <string>
#include <iostream>
#include <set>

using namespace std;

set<string> nodeNames;

string generateNodeName( void )
{
   char c;
   char x;
   string nodeName;
   do
   {
      nodeName  = ('A' + rand()%26);
      nodeName += ('0' + rand()%10);
   } while( nodeNames.find( nodeName ) != nodeNames.end() );
   nodeNames.insert( nodeName );
   return nodeName;
}

string getRandomNodeName( void )
{
   string nodeName;
   int x = rand() % nodeNames.size();
   // cout << "[size: " << nodeNames.size() << ", x=" << x << "] ";

   set<string>::iterator n;
   int i;
   for( i=0, n = nodeNames.begin(); i<=x && n != nodeNames.end(); i++, n++ )
   {
      nodeName = *n;
      // cout << "[" << nodeName << "] ";
   }
   return nodeName;
}


int main( int argc, char* argv[] )
{
   const int numConnections = 27;
   srand(numConnections);
   string connections[ numConnections ];
   int i;
   string thisNode, prevNode;
   string startNode, stopNode, path;
   path = startNode = prevNode = generateNodeName();
   for( i=0; i<numConnections/5; i++ )
   {
      thisNode = generateNodeName();
      int r = rand()%2;
      cout << "r:" << r << " ";
      if( r == 0 )
         connections[ i ] = thisNode + " " + prevNode;
      else
         connections[ i ] = prevNode + " " + thisNode;
      cout << connections[ i ] << endl;
      prevNode = thisNode;
      path += " " + thisNode;
   }
   stopNode = thisNode;
   for( ; i<numConnections; i++ )
   {
      prevNode = getRandomNodeName();
      thisNode = generateNodeName();
      int r = rand()%2;
      cout << "r:" << r << " ";
      if( r == 0 )
         connections[ i ] = thisNode + " " + prevNode;
      else
         connections[ i ] = prevNode + " " + thisNode;
      cout << connections[ i ] << endl;
   }
   cout << "*** scramble ***" << endl;
   for( i=0; i<numConnections; i++ )
   {
      int j = rand() % ( numConnections-1 );
      string temp = connections[i];
      connections[i] = connections[j];
      connections[j] = temp;
   }
   for( i=0; i<numConnections; i++ )
      cout << connections[i] << endl;

   cout << "IN " << startNode << endl;
   cout << "EX " << stopNode << endl;
   cout << "PATH: " << path << endl;
}

