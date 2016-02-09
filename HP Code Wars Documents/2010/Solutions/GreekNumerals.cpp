
#include <vector>
#include <map>
#include <string>
#include <iostream>
#include <fstream>

#include <math.h>

using namespace std;


int main( int argc, char* argv[] )
{
    map<char,int> textValueMap;

    textValueMap.insert( pair<char,int>( 'A',   1 ) );
    textValueMap.insert( pair<char,int>( 'B',   2 ) );
    textValueMap.insert( pair<char,int>( 'G',   3 ) );
    textValueMap.insert( pair<char,int>( 'D',   4 ) );
    textValueMap.insert( pair<char,int>( 'E',   5 ) );
    textValueMap.insert( pair<char,int>( '#',   6 ) );
    textValueMap.insert( pair<char,int>( 'Z',   7 ) );
    textValueMap.insert( pair<char,int>( 'Y',   8 ) );
    textValueMap.insert( pair<char,int>( 'H',   9 ) );
    textValueMap.insert( pair<char,int>( 'I',  10 ) );
    textValueMap.insert( pair<char,int>( 'K',  20 ) );
    textValueMap.insert( pair<char,int>( 'L',  30 ) );
    textValueMap.insert( pair<char,int>( 'M',  40 ) );
    textValueMap.insert( pair<char,int>( 'N',  50 ) );
    textValueMap.insert( pair<char,int>( 'X',  60 ) );
    textValueMap.insert( pair<char,int>( 'O',  70 ) );
    textValueMap.insert( pair<char,int>( 'P',  80 ) );
    textValueMap.insert( pair<char,int>( 'Q',  90 ) );
    textValueMap.insert( pair<char,int>( 'R', 100 ) );
    textValueMap.insert( pair<char,int>( 'S', 200 ) );
    textValueMap.insert( pair<char,int>( 'T', 300 ) );
    textValueMap.insert( pair<char,int>( 'U', 400 ) );
    textValueMap.insert( pair<char,int>( 'F', 500 ) );
    textValueMap.insert( pair<char,int>( 'C', 600 ) );
    textValueMap.insert( pair<char,int>( '$', 700 ) );
    textValueMap.insert( pair<char,int>( 'W', 800 ) );
    textValueMap.insert( pair<char,int>( '3', 900 ) );

    string numeral;
    cin >> numeral;
    while( numeral != "." )
    {
        int sum = 0;
        for( int i=0; i<numeral.length(); i++ )
        {
           char c = numeral[i];
           sum += textValueMap[ c ];
        }
        cout << sum << endl;
        cin >> numeral;
    }

}

