
#include <vector>
#include <string>
#include <iostream>

using namespace std;

class Atom
{
    public:
        string symbol;
        float  mass;
        Atom( string newSymbol, float newMass ) :
            symbol( newSymbol ),
            mass( newMass )
        {
        };
};


class PeriodicTable
{
    private:
        vector<Atom*> atomList;
    public:
        PeriodicTable( )
        {
            atomList.push_back( new Atom(  "H",   1.008 ));
            atomList.push_back( new Atom(  "He",  4.003 ));
            atomList.push_back( new Atom(  "Li",  6.941 ));
            atomList.push_back( new Atom(  "Be",  9.012 ));
            atomList.push_back( new Atom(  "B",  10.81 ));
            atomList.push_back( new Atom(  "C",  12.01 ));
            atomList.push_back( new Atom(  "N",  14.01 ));
            atomList.push_back( new Atom(  "O",  16.00 ));
            atomList.push_back( new Atom(  "F",  19.00 ));
            atomList.push_back( new Atom(  "Ne", 20.18 ));
            atomList.push_back( new Atom(  "Na", 22.99 ));
            atomList.push_back( new Atom(  "Mg", 24.31 ));
            atomList.push_back( new Atom(  "Al", 26.98 ));
            atomList.push_back( new Atom(  "Si", 28.09 ));
            atomList.push_back( new Atom(  "P",  30.97 ));
            atomList.push_back( new Atom(  "S",  32.07 ));
            atomList.push_back( new Atom(  "Cl", 35.45 ));
            atomList.push_back( new Atom(  "Ar", 39.95 ));
        };
        float GetMass( string symbol )
        {
            vector<Atom*>::iterator i;
            for( i=atomList.begin(); i != atomList.end(); i++ )
                if( (*i)->symbol == symbol )
                    return (*i)->mass;
            return 9e99; // big mass means symbol not found
        };
};



int main( int argc, char* argv[] )
{
    PeriodicTable table;
    string formula, symbol, token;
    float mass, count;
    char c;
    symbol = "";
    count = 0.0;
    mass = 0.0;
    for( cin >> formula; formula != "END"; cin >> formula )
    {
        for( int i=0; i<formula.length(); i++ )
        {
            c = formula[i];
            if( c >= 'A' && c <= 'Z' )
            {
                if( symbol.length() )
                {
                    if( count == 0.0 )
                        count = 1.0;
                    mass += count * table.GetMass( symbol );
                    count = 0.0;
                }
                symbol = c;
            }
            else if( c >= 'a' && c <= 'z' )
            {
                symbol += c;
            }
            else // if( c >= '0' && c <= '9' )
                count = 10 * count +  float(c - '0');
        }
        if( count == 0.0 )
            count = 1.0;
        mass += count * table.GetMass( symbol );
        cout << formula << " " << mass << endl;
        symbol = "";
        count = 0.0;
        mass = 0.0;
    }

    return 0;
}

