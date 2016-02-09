//---------------------------------------------------------------------------
//
// ParsingAncientManuscripts.cpp
// written by Lee Jenkins
//
// this program uses a recursive parser to search for word divisions in
// a stream on input characters with no spaces between words. if the parser
// finds that it cannot match a word to the current input stream, it back-
// tracks and rejects the previous word assignment. the program assumes that
// there will be exactly one correct way to divide the input into words.
//
// this solution relies on the stl list, which provides methods for
// pushing and popping elements on the front *and* back of the list
//     push_front adds an element to the front of the list
//     push_back adds an element to the back of the list
//     pop_front removes an element from the front of the list
//     pop_back removes an element from the back of the list
//
//---------------------------------------------------------------------------
#include <list>
#include <string>
#include <iostream>
#include <fstream>
#pragma hdrstop
//---------------------------------------------------------------------------
// the Dictionary stores the valid words and provides an interface for
// adding words and looking to see if a word already exists
//---------------------------------------------------------------------------
using namespace std;
class Dictionary
{
    public:
        list<string> wordList;
        int maxWordLength;
        Dictionary() : maxWordLength(0) { };
        void Add( string word )
        {
            wordList.push_back( word );
            if( word.length() > maxWordLength )
                maxWordLength = word.length();
        };
        bool Lookup( string token )
        {
            list<string>::iterator word;
            for( word=wordList.begin(); word!=wordList.end(); word++ )
            {
                if( token == *word )
                    return true;
            }
            return false;
        };
};
//---------------------------------------------------------------------------
Dictionary dictionary;
//---------------------------------------------------------------------------
//
// this short little function does the actual word division
//
// this function takes a list of letters as input and puts words that it
// finds into the list of words.
//
// the process is simplified by the approach that each time the function
// is called it only has to find the next word and nothing more. after
// finding a word, the function calls itself recursively to find the next
// word. if the function cannot find a word in the list of letters, it
// returns a value of false. if the nested recursive call returns false,
// then the current word division cannot be correct. the function returns
// a value of true if the list of letters is empty or if the nested call
// returns true.
//
//---------------------------------------------------------------------------
bool parse( list<char> letters, list<string>& words )
{
    if( letters.empty() )
        return true; // if no more letters, then we are done!

    string word = "";
    while( !letters.empty() && word.length() < dictionary.maxWordLength )
    {
        word += letters.front(); // get the next letter from the front
        letters.pop_front(); // popping the stack is a separate step
        if( dictionary.Lookup(word) )
        {
            // cout << "matched: " << word << endl;
            words.push_back( word ); // push the parsed word
            if( parse( letters, words ) ) // and try to parse the rest
                return true; // if it worked, we're done!
            words.pop_back(); // no worky :-( remove the parsed word
        }
    }
    // cout << "need to back track... " << word << endl;
    return false;
}
//---------------------------------------------------------------------------
#pragma argsused
int main(int argc, char* argv[])
{
    list<char> letters; // letters that still need to be parsed
    list<string> words; // words that have already been parsed
    string token;
    int mode = 0;
    // read the dictionary one word at a time
    while( cin >> token )
    {
        if( mode == 0 && token == "[DICTIONARY]" )
            mode = 1;
        else if( mode == 1 && token == "[MANUSCRIPT]" )
            break;
        else
        {
            dictionary.Add( token );
            // cout << token << endl;
        }
    }
    char c;
    // read the manuscript one character at a time
    while( cin >> c )
    {
        if( c >= 'A' && c <= 'Z' )
            letters.push_back( c );
        if( c == '.' )
            break;
    }

    parse( letters, words ); // recursive parsing routine

    list<string>::iterator word;
    int lineLength = 0;
    for( word=words.begin(); word!=words.end(); word++ )
    {
        lineLength += (*word).length() + 1;
        if( lineLength >= 80 )
        {
            lineLength = (*word).length() + 1;
            cout << endl;
        }
        cout << *word << " ";
    }
    cout << endl;

    return 0;
}
//---------------------------------------------------------------------------

