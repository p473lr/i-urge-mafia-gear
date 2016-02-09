
#include <iostream>
#include <string>
#include <list>
#include <map>

using namespace std;

map<string,int> positionMap;
// the order array is the reverse of the positionMap, except that the
// positionMap uses one-based indexing and the order array uses standard
// zero-based indexing.
string          order[4];

class Statement
{
   public:
      virtual bool isFalse()=0;
};

class StatementWas : public Statement
{
   private:
      string name;
      int    position;
   public:
      StatementWas( string speaker, string person, string newPosition )
      {
         if( person == "I" )
            name = speaker;
         else
            name = person;

         if( newPosition == "FIRST" )
            position = 1;
         if( newPosition == "SECOND" )
            position = 2;
         if( newPosition == "THIRD" )
            position = 3;
         if( newPosition == "LAST" )
            position = 4;
         // cout << "new Statement " << name << " was " << position << endl;
      };
      bool isFalse()
      {
         return positionMap[ name ] != position;
      };
};

class StatementBeat : public Statement
{
   private:
      string name1;
      string name2;
   public:
      StatementBeat( string speaker, string person1, string person2 )
      {
         if( person1 == "I" )
            name1 = speaker;
         else
            name1 = person1;
         name2 = person2;
         // cout << "new Statement " << name1 << " beat " << name2 << endl;
      };
      bool isFalse()
      {
         return positionMap[ name1 ] > positionMap[ name2 ];
      };
};

class Scanner
{
   public:
      void Run()
      {
         ReadStatements();
         SetAll(1);
         for( int i=0; i<4; i++ )
            cout << order[i] << " ";
         cout << endl;
      };

   private:
      // nameList and flagList are used together. nameList is the master list of
      // names stored in speaker order and flagList indicates if a particular
      // speaker is already being considering in a solution sequence.
      string             nameList[4];
      int                flagList[4];
      list<Statement*>   statementList;

      void ReadStatements()
      {
         string speaker, subject, verb, predicate;
         for( int i=0; i<4; i++ )
         {
            cin >> speaker >> subject >> verb >> predicate;
            // trim the colon char from the speaker token
            speaker.resize( speaker.size()-1 );
            nameList[i] = speaker;
            flagList[i] = 0;
            Statement* statement;
            if( verb == "WAS" )
               statement = new StatementWas( speaker, subject, predicate );
            else
               statement = new StatementBeat( speaker, subject, predicate );
            statementList.push_back( statement );
         }
      };

      bool SetAll( int level )
      {
         bool done = false;
         for( int i=0; i<4 && !done; i++ )
         {
            if( flagList[i] == 0 )
            {
               // try position level for nameList[i]
               flagList[i] = 1;
               positionMap[ nameList[ i ] ] = level;
               order[ level-1 ] = nameList[ i ];

               if( level < 4 )
                  done = SetAll( level+1 );
               else
               {
                  // a solution is a sequence of names for
                  // which all of the statements are false
                  list<Statement*>::iterator c;
                  bool possible = true;
                  for( c=statementList.begin(); possible && c != statementList.end(); c++ )
                  {
                     possible = (*c)->isFalse();
                  }
                  done = possible;
               }
               flagList[i] = 0;
            }
         }
         return done;
      };
};

int main( int argc, char* argv[] )
{
   Scanner scanner;
   scanner.Run();
}
