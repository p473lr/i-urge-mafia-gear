

#include <vector>
#include <map>
#include <string>
#include <iostream>
#include <fstream>

#include <ctype.h>
#include <math.h>

using namespace std;

bool debug = false;

int main( int argc, char* argv[] )
{
    vector<string> dictionary;
    bool done = false;
    while( ! done )
    {
        string word;
        cin >> word;
        if( word == "|" )
            done = true;
        else
            dictionary.push_back( word );
    }
    done = false;
    while( ! done )
    {
        string abbr;
        cin >> abbr;
        if( abbr == "~" )
            done = true;
        else
        {
            string candidate = abbr;
            int    cScore = 99;
            bool   tie = false;

            if(debug) cout << "abbr: " << abbr << endl;

            int abbrLength = abbr.length();
            string punctuation = "";
            if( !isalpha( abbr[ abbrLength-1 ] ) )
            {
                punctuation = abbr[ --abbrLength ];
                if(debug) cout << " punctuation:" << punctuation << endl;
            }

            for( int d=0; d<dictionary.size(); d++ )
            {
                string word = dictionary[d];

                if(debug) cout << "word: " << word << "    ";

                if( word.length() < abbrLength )
                {
                    if(debug) cout << " -- too short" << endl;
                    continue;
                }
                int w = 0;
                int wScore = 0;
                int a=0;
                while( w<word.length() )
                {
                   if( a == abbrLength )
                   {
                       ++w;
                       ++wScore;
                   }
                   else if( abbr[a] == word[w++] )
                   {
                       ++a;
                   }
                   else
                       ++wScore;
                }

                if(debug) cout << "score: " << wScore;
                if(debug) cout << "     a=" << a << endl;

                if( a == abbrLength )
                {
                    if( wScore == cScore )
                    {
                        tie = true;
                        if(debug) cout << "TIE!" << endl;
                    }
                    else if( wScore < cScore )
                    {
                        if(debug) cout << "NEW WINNER!" << endl;
                        candidate = word;
                        cScore = wScore;
                        tie = false;
                    }
                }
            }
            if( tie )
                candidate = abbr;
            cout << candidate << punctuation << " ";

        }
    }
    cout << endl;
}

