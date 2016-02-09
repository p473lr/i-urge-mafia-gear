// ============================================================================
//
// Set Operations program - Code Wars 2009
// written by Lee Jenkins
//
// this solution uses more object design than you might use in a timed
// contest setting. the original version of the code used the same basic
// substring replacement parsing as shown here, but it was pretty ugly.
//
// use of stl has been kept to a minimum for the benefit of java programmers
// reading this code. the vector type is a basic dynamic list management
// class: you can add (push) items, remove (pop) items, retrieve items using
// standard array[i] syntax, and so on.
//
// ============================================================================

#include <vector>
#include <string>
#include <iostream>

using namespace std;

// ============================================================================
//
// class Set manages a single set, including methods for adding and retrieving
// set members, plus the set operations defined in the program description.
// yes, I could have used the stl::set class, but where's the fun in that?
//
// ============================================================================
const int MAX_MEMBERS = 26;
class Set
{
    private:
        char name;
        int count;
        int member[ MAX_MEMBERS ];
    public:
        Set( char newName='\0' ) : name(newName), count(0) { };
        ~Set( ) { };
        void Add( int n )
        {
            if( count == MAX_MEMBERS )
                return;
            for( int i=0; i<count; i++ )
                if( member[i] == n )
                    return;
            member[ count++ ] = n;
        };
        static int intCompare( const void* a, const void* b);
        void Sort( void )
        {
            qsort( member, count, sizeof(int), intCompare );
        };
        char GetName( void )
        {
            return name;
        };
        int GetCount( void )
        {
            return count;
        };
        int GetMember( int i )
        {
            return member[i];
        };
        bool IsMember( int n )
        {
            for( int i=0; i<count; i++ )
                if( member[i] == n )
                    return true;
            return false;
        };
        void Union( Set& a, Set& b )
        {
            count =0;
            for( int i=0; i<a.count; i++ )
                Add( a.member[i] );
            for( int i=0; i<b.count; i++ )
                Add( b.member[i] );
        };
        void Intersection( Set& a, Set& b )
        {
            count =0;
            for( int i=0; i<a.count; i++ )
                if( b.IsMember( a.member[i] ) )
                    Add( a.member[i] );
        };
        void Difference( Set& a, Set& b )
        {
            count =0;
            for( int i=0; i<a.count; i++ )
                if( !b.IsMember( a.member[i] ) )
                    Add( a.member[i] );
        };
        // Set Complement: !A is just U - A
        // this operation is not implemented here since you have to
        // have access to the universal set to do a complement. for
        // this contest I could have just hard-coded the universal
        // set here, but the thought of doing that made me feel icky.
};
//
// static methods are defined outside the class body in c++
//
int Set::intCompare( const void* a, const void* b)
{
    const int *inta = (const int *)a;
    const int *intb = (const int *)b;
    // comparison returns negative if b > a
    //                    positive if a > b
    return *inta - *intb;
}

// ============================================================================
//
// the SetList class manages a list of sets. it actually has two lists:
// the "permanent" list and a temporary list. the permanent list stores the
// sets that were defined in the first section of the input. the temp list
// stores the results of each operation as it is parsed out of the expression.
// the temp list gets cleared out after each expression is parsed.
//
// ============================================================================
class SetList
{
    private:
        vector<Set*> setList;
        vector<Set*> tempList;
        void ClearList( vector<Set*>& list )
        {
            while( list.size() > 0 )
            {
                delete list[list.size()-1];
                list.pop_back();
            }
        };
    public:
        SetList( )
        {
            // nothing to do
        };
        ~SetList( )
        {
            ClearList( setList );
            ClearList( tempList );
        };
        void Add( Set* set )
        {
            setList.push_back( set );
        };
        void AddTemp( Set* set )
        {
            tempList.push_back( set );
        };
        void ClearTemp( void )
        {
            ClearList( tempList );
        };
        Set* Get( char name )
        {
            //
            // look for the set name in setList
            //
            for( int i=0; i<setList.size(); i++ )
                if( setList[i]->GetName() == name )
                    return setList[i];
            //
            // look for the set name in tempList
            //
            for( int i=0; i<tempList.size(); i++ )
                if( tempList[i]->GetName() == name )
                    return tempList[i];
            //
            // for a contest problem we assume all lookups
            // will succeed, but in a general solution
            // that might not be such a good assumption
            //
        };
};

// ============================================================================
//
// the parse tools class is a loose collection of variables and operations
// needed to manage the parse context. it is needed because the actual
// parsing is split up among several classes.
//
// ============================================================================
class ParseTools
{
    public:
        Set superSet;
        SetList setList;
        string line;
        char nextName;
        //
        ParseTools( )
        {
            Reset();
            // the problem definition states that the universal set (or super
            // set) is simply the list of the integers 1 through 26.
            for( int i=1; i<=26; i++ )
                superSet.Add( i );
        };
        void RemoveSpaces( void )
        {
            string result = "";
            for( int i=0; i<line.length(); i++ )
                if( line[i] != ' ' )
                    result += line[i];
            line = result;
        };
        void Reset( void )
        {
            nextName = 'a';
            setList.ClearTemp();
        }
};

// ============================================================================
//
// the operator class defines the interface for parsing an operator
//
// ============================================================================
class Operator
{
    protected:
        char symbol;
    public:
        Operator( char operationSymbol ) : symbol(operationSymbol)
        { /* nothing to see here */ };
        //
        // the parser uses a loop to look the first occurance of the operator
        // symbol in the line. each descendant class is responsible for
        // 1: parsing the operands, 2: performing the operation, 3: replacing
        // the sub-expression by a single variable, and 4: storing the result
        // of the operation in the setList under that variable name.
        //
        // for example, assume the following expression needs to be parsed
        // for union ( '|' ) operators:
        //
        //      "A-B|C&D|E"
        //          ^---- this is the first | operator in the string
        //
        // the parser would locate the first | between B and C, perform the
        // operation, then replace the substring with a variable that holds
        // the result, like this:
        //
        //      "A- a &D|E"
        //          ^---- the substring "B|C" has been replaced by " a "
        //
        // next, the parser would collapse the spaces and look for the next
        // occurance of the operator symbol. when there are no more operator
        // symbols in the line text, the parser is done with that operation.
        //
        //      "A-a&D|E"
        //      "A-a& b "
        //      "A-a&b"
        //
        // operator precedence is enforced by calling the each operator
        // parser in order of precedence.
        //
        virtual void Parse( ParseTools& context )
        {
            size_t index;
            // remove all spaces -- they aren't important and the parser
            // can be simplified by assuming there are no spaces
            context.RemoveSpaces();
            // loop as long as there is an operator symbol in the string
            while( (index=context.line.find(symbol)) != string::npos )
            {
                // index points to the location of the operator in the line
                ParseExpression( context, index );
                // compress the string
                context.RemoveSpaces();
            }
        };
        // ParseExpression is a pure virtual function
        virtual void ParseExpression( ParseTools& context, size_t index )=0;
};

// ============================================================================
//
// a unary operator indicates an operation on a single variable
//
// ============================================================================
class UnaryOperator : public Operator
{
    public:
        UnaryOperator( char operationSymbol ) : Operator(operationSymbol)
        { /* nothing to see here */ };
        //
        // each unary operator must define its own execution
        //
        virtual void Execute( ParseTools& context, Set& target, Set& source )=0;
        //
        // parsing a unary expression is handled the same way regardless of
        // the operation to be performed
        //
        virtual void ParseExpression( ParseTools& context, size_t index )
        {
            char name = context.line[index+1];
            Set* set = context.setList.Get( name );
            // make a new temporary set variable
            Set* temp = new Set( context.nextName );
            Execute( context, *temp, *set );
            context.setList.AddTemp( temp );
            // replace the operator with a space, and the old variable
            // name with the new temporary set variable name
            context.line[index] = context.nextName++;
            context.line[index+1] = ' ';
        };
};

// ============================================================================
//
// a binary operator indicates an operation on two variables
//
// ============================================================================
class BinaryOperator : public Operator
{
    public:
        BinaryOperator( char operationSymbol ) : Operator(operationSymbol)
        { /* nothing to see here */ };
        //
        // each binary operator must define its own execution
        //
        virtual void Execute( Set& target, Set& source1, Set& source2 )=0;
        //
        // parsing a binary expression is handled the same way regardless of
        // the operation to be performed
        //
        virtual void ParseExpression( ParseTools& context, size_t index )
        {
            char name1 = context.line[index-1];
            char name2 = context.line[index+1];
            Set* set1 = context.setList.Get( name1 );
            Set* set2 = context.setList.Get( name2 );
            Set* set3 = new Set( context.nextName );
            Execute( *set3, *set1, *set2 );
            context.setList.AddTemp( set3 );
            context.line[index] = context.nextName++;
            context.line[index-1] = ' ';
            context.line[index+1] = ' ';
        };
};

// ============================================================================
//
// now all the operators can be defined in terms of the base classes above
//
// ============================================================================

class ComplementOperator : public UnaryOperator
{
    public:
        ComplementOperator(  ) : UnaryOperator( '!' )
        { /* nothing to see here */ };
        virtual void Execute( ParseTools& context, Set& target, Set& source )
        {
            // !A is the same as U - A
            target.Difference( context.superSet, source );
        };
};

class UnionOperator : public BinaryOperator
{
    public:
        UnionOperator(  ) : BinaryOperator( '|' )
        { /* nothing to see here */ };
        virtual void Execute( Set& target, Set& source1, Set& source2 )
        {
            target.Union( source1, source2 );
        };
};

class DifferenceOperator : public BinaryOperator
{
    public:
        DifferenceOperator(  ) : BinaryOperator( '-' )
        { /* nothing to see here */ };
        virtual void Execute( Set& target, Set& source1, Set& source2 )
        {
            target.Difference( source1, source2 );
        };
};

class IntersectionOperator : public BinaryOperator
{
    public:
        IntersectionOperator(  ) : BinaryOperator( '&' )
        { /* nothing to see here */ };
        virtual void Execute( Set& target, Set& source1, Set& source2 )
        {
            target.Intersection( source1, source2 );
        };
};

// ============================================================================
//
// parsing the set definitions is pretty basic, so it is handled here in main
//
// ============================================================================
int main( int argc, char* argv[] )
{
    int numSets, numExpr;
    char name;
    int count, member;

    vector<Operator*> operatorList;

    //
    // the operators are added to the list in order of precedence
    //
    operatorList.push_back( new ComplementOperator() );
    operatorList.push_back( new UnionOperator() );
    operatorList.push_back( new DifferenceOperator() );
    operatorList.push_back( new IntersectionOperator() );

    ParseTools context;

    //
    // read the set definitions
    //
    cin >> numSets;
    for( int num=0; num<numSets; num++ )
    {
        // parse the set from the input stream
        cin >> name;
        cin >> count;
        Set* set = new Set( name );
        for( int i=0; i<count; i++ )
        {
            cin >> member;
            // cout << "adding " << n << endl;
            set->Add( member );
        }
        context.setList.Add( set );
    }

    cin >> numExpr;
    for( int i=0; i<numExpr; i++ )
    {
        // read an expression
        cin >> context.line;
        // call the parser for each of the operators. the operator parsing
        // is executed in the same order that the operator classes were
        // added to the list. this is how we control precedence order.
        //
        for( int i=0; i<operatorList.size(); i++ )
            operatorList[i]->Parse( context );
        //
        // at this point the line should only be a single variable
        // so get it from the list and sort the set
        //
        Set* result = context.setList.Get( context.line[0] );
        result->Sort();
        // print the number of members
        cout << result->GetCount();
        // print each member in the set
        for( int i=0; i<result->GetCount(); i++ )
            cout << ' ' << result->GetMember( i );
        cout << endl;
        //
        // clear out all the temp vars used during the evaluation
        //
        context.Reset();
    }

}
