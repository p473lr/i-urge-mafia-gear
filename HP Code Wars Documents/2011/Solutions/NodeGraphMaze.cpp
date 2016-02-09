
#include <iostream>
#include <string>
#include <list>
#include <map>

using namespace std;

class Node
{
   public:
      list<string> connections;
};

class NodeMaze
{
   public:
      void Run()
      {
         ReadMap();
         cout << FindPath( inNode, exNode ) << endl;
      };
   private:
      map<string,Node*> nodeMap;
      string inNode, exNode;

      void ReadMap()
      {
         string token1, token2;
         while( token1 != "EX" )
         {
            cin >> token1 >> token2;
            if( token1 == "IN" )
               inNode = token2;
            else if( token1 == "EX" )
               exNode = token2;
            else
            {
               AddLink( token1, token2 );
               AddLink( token2, token1 );
            }
         }
      };

      void AddLink( string nodeNameA, string nodeNameB )
      {
         map<string,Node*>::iterator imap;
         imap = nodeMap.find( nodeNameA );
         if( imap == nodeMap.end() )
         {
            Node* node = new Node();
            nodeMap[ nodeNameA ] = node;
         }
         nodeMap[ nodeNameA ]->connections.push_back( nodeNameB );
      };

      string FindPath( string inNodeName, string exNodeName, string lastNodeName="" )
      {
         string path = "";
         list<string>::iterator iNodeName;
         list<string>& nodeList = nodeMap[ inNodeName ]->connections;

         for( iNodeName  = nodeList.begin(); 
              iNodeName != nodeList.end() && path.length() == 0;
              iNodeName++ )
         {
            // did we find the exNodeName in the connection list?
            if( *iNodeName == exNodeName )
               path = exNodeName; // if so then we're done
            // else search for other paths, but do not back-track
            else if( *iNodeName != lastNodeName )
               path = FindPath( *iNodeName, exNodeName, inNodeName );
         }

         // if a path was found then the inNodeName is prepended to the path
         if( path.length() > 0 )
            path = inNodeName + " " + path;

         return path;
      };
};


#include <set>
#include <vector>
#include <stdlib.h>
#include <time.h>
class MazeMaker
{
   public:
      set<string>     nodeNames;
      vector<string>  nodeVector;
      vector<string>  links;

      string RandomNodeName()
      {
         char letter, number;
         string nodeName;
         do
         {
            number = '0' + rand() % 10;
            letter = 'A' + rand() % 26;
            nodeName = string(1,letter) + string(1,number);
         } while( nodeNames.find( nodeName ) != nodeNames.end() );
         nodeNames.insert( nodeName );
         nodeVector.push_back( nodeName );
         return nodeName;
      };

      string MakeLink( string nodeName1, string nodeName2 )
      {
         string link;
         if( rand() % 2 )
            link = nodeName1 + " " + nodeName2;
         else
            link = nodeName2 + " " + nodeName1;
         return link;
      };

      void Make( int pathLenth, int moreNodes )
      {
         srand( time(NULL) );
         string nodeName1, nodeName2;
         string inNodeName, exNodeName;
         nodeName1 = RandomNodeName();
         inNodeName = nodeName1;
         string path = nodeName1;
         for( int i=1; i<pathLenth; i++ )
         {
            nodeName2 = RandomNodeName();
            links.push_back( MakeLink( nodeName1, nodeName2 ) );
            nodeName1 = nodeName2;
            path += " " + nodeName2;
         }
         exNodeName = nodeName2;
         for( int i=0; i<moreNodes; i++ )
         {
            nodeName1 = nodeVector[ rand() % nodeVector.size() ];
            nodeName2 = RandomNodeName();
            links.push_back( MakeLink( nodeName1, nodeName2 ) );
         }
         for( int i=0; i<links.size(); i++ )
         {
            int x = rand() % links.size();
            string temp = links[i];
            links[i] = links[x];
            links[x] = temp;
         }
         for( int i=0; i<links.size(); i++ )
         {
            cout << links[i] << endl;
         }
         cout << "IN " << inNodeName << endl;
         cout << "EX " << exNodeName << endl;
         cout << path << endl;
      };
};


int main( int argc, char* argv[] )
{
   NodeMaze nodeMaze;
   MazeMaker mm;

   if( argc == 2 && argv[1][0] == '-' )
      nodeMaze.Run();
   else if( argc != 3 )
      cout << "usage: ngm -    OR    ngm <PATH-LEN> <MORE_NODES>" << endl;
   else
      mm.Make( atoi(argv[1]), atoi(argv[2]) );

}


