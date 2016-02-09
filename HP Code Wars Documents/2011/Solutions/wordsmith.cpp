
#include <string>
#include <iostream>
#include <vector>
#include <map>
#include <set>

using namespace std;

typedef map<string,int> WordCount;
typedef map<string,int>::iterator wciter;

typedef map<string,WordCount*> PositionText;
typedef map<string,WordCount*>::iterator ptiter;

PositionText PreText;
PositionText PostText;

PositionText PrePairText;
PositionText PostPairText;

PositionText PreTripletText;
PositionText PostTripletText;

const string BEGIN_KEY = "*BEGIN*";
const string END_KEY   = "*END*";
const string WORD_KEY  = "*WORD*";

void AddCount( PositionText& pText, const string& rootWord, const string& nearWord )
{
    cout << "AddCout - key='" << rootWord << "', word='" << nearWord << "'" << endl;

    if( pText.find( rootWord ) == pText.end() )
        pText[ rootWord ] = new WordCount;

    WordCount& wc = *pText[ rootWord ];
    if( wc.find( nearWord ) == wc.end() )
    {
        wc[ nearWord ] = 0;
    }

    ++wc[ nearWord ];
}

string Convert( const string& token, bool& isEnd )
{
    string word;
    for( int i=0; i<token.length(); i++ )
    {
        char c = token[i];
        if( c >= 'a' && c <= 'z' )
            c = c + 'A' - 'a';
        if( ( c >= 'A' && c <= 'Z' ) || ( c >= '0' && c <= '9' ) )
            word += c;
        isEnd = ( c == '.' || c == ';' );
    }
    return word;
}

void DumpPositionTable( PositionText& ptext )
{
    for( ptiter ip=ptext.begin(); ip!=ptext.end(); ip++ )
    {
        cout << "DumpPositionTable for key value ='" << ip->first << "'" << endl;
        WordCount* wc = ip->second;
        for( wciter iwc=wc->begin(); iwc!=wc->end(); iwc++ )
        {
            cout << iwc->first << ": " << iwc->second << endl;
        }
    }
}

string GetMaxWord( WordCount& wordCount )
{
    string word;
    int maxCount = 0;
    for( wciter iwc=wordCount.begin(); iwc!=wordCount.end(); iwc++ )
    {
        // cout << iwc->first << ": " << iwc->second << endl;
        if( iwc->second > maxCount )
        {
            word = iwc->first;
            maxCount = iwc->second;
        }
    }
    return word;
}

string GetRandWord( WordCount& wordCount, int accumulation )
{
   WordCount wordTable;
   int totalCount = 0;
   // cout << "-----[ RANDOM TABLE ]-----" << endl;
   int terminalWeight = 0;
   if( accumulation > 5 )
   {
       int distance = accumulation - 5;
       terminalWeight = distance * distance * distance;
   }
   for( wciter iwc=wordCount.begin(); iwc!=wordCount.end(); iwc++ )
   {
       totalCount += iwc->second * iwc->second;
       if( iwc->first == BEGIN_KEY || iwc->first == END_KEY )
       {
           totalCount += terminalWeight;
       }
       wordTable[ iwc->first ] = totalCount;
       // cout << totalCount << " " << iwc->first << endl;
   }
   int roll = rand() % totalCount;
   // cout << "  --> roll: " << roll << endl;

   totalCount = 0;
   string word;
   // cout << "-----[ SAME TABLE? ]-----" << endl;
   for( wciter iwc=wordTable.begin(); iwc!=wordTable.end(); iwc++ )
   {
       // cout << iwc->second << " " << iwc->first << endl;
       if( roll <= iwc->second )
       {
           word = iwc->first;
           break;
       }
   }
   // cout << "  --> word: " << word << endl;
   return word;
}

void CopyWordCounts( WordCount& targetWordCount, WordCount& sourceWordCount, int weight )
{
    for( wciter iwc=sourceWordCount.begin(); iwc!=sourceWordCount.end(); iwc++ )
    {
        if( targetWordCount.find( iwc->first ) == targetWordCount.end() )
            targetWordCount[ iwc->first ] = 0;
        targetWordCount[ iwc->first ] += weight * iwc->second;
    }
}

void BackTrack( string& penultimate, string& previous, const string& word, string afterword="", string after2word="", int accumulation=0 )
{
    // cout << "BackTrack - word=" << word << ", afterword=" << afterword << ", accumulation=" << accumulation << endl;
    string previousWord;
    string triplet = word + " " + afterword + " " + after2word;
    string pair = word + " " + afterword;

    WordCount wordCount;

    if( PreTripletText.find(triplet) != PreTripletText.end() )
    {
        // cout << " -> call GetRandWord( PrePairText[" << pair << "] )" << endl;
        // previousWord = GetRandWord( *PreTripletText[triplet], accumulation );
        CopyWordCounts( wordCount, *PreTripletText[triplet], 9 );
    }
    if( PrePairText.find(pair) != PrePairText.end() )
    {
        // cout << " -> call GetRandWord( PrePairText[" << pair << "] )" << endl;
        // previousWord = GetRandWord( *PrePairText[pair], accumulation );
        CopyWordCounts( wordCount, *PrePairText[pair], 3 );
    }

    {
        // cout << " -> call GetRandWord( PreText[" << word << "] )" << endl;
        // string previousWord = GetMaxWord( *PreText[word] );
        // previousWord = GetRandWord( *PreText[word], accumulation );
        CopyWordCounts( wordCount, *PreText[word], 1 );
    }

    previous = GetRandWord( wordCount, accumulation );

    // cout << "previousWord: " << previousWord << endl;
    if( previous != BEGIN_KEY )
    {
        string peripenultimate;
        BackTrack( peripenultimate, penultimate, previous, word, afterword, accumulation+1 );
        cout << previous << " ";
    }
}

int main( int argc, char* argv[] )
{
    bool isEnd;
    string triplet, pair, token, word;
    string peripenultimate = WORD_KEY;
    string penultimate = WORD_KEY;
    string previous = BEGIN_KEY;
    WordCount mainWordCount;
    while( true )
    {
        cin >> token;
        if( token == END_KEY )
           break;
        word = Convert( token, isEnd );

        cout << "READ WORD FROM INPUT: " << word << endl;

        if( mainWordCount.find( word ) == mainWordCount.end() )
        {
            mainWordCount[ word ] = 0;
        }
        ++mainWordCount[ word ];

        AddCount( PreText, word, previous );
        AddCount( PostText, previous, word );

        if( penultimate != WORD_KEY )
        {
           pair = previous + " " + word;
           AddCount( PrePairText, pair, penultimate );
           pair = penultimate + " " + previous;
           AddCount( PostPairText, pair, word );
           if( penultimate != WORD_KEY )
           {
              triplet = penultimate + " " + previous + " " + word;
              AddCount( PreTripletText, triplet, peripenultimate );
              triplet = peripenultimate + " " + penultimate + " " + previous;
              AddCount( PostTripletText, triplet, word );
           }
        }

        if( isEnd )
        {
           AddCount( PostText, word, END_KEY );
           if( previous != WORD_KEY )
           {
              pair = previous + " " + word;
              AddCount( PostPairText, pair, END_KEY );

              if( penultimate != WORD_KEY )
              {
                 triplet = penultimate + " " + previous + " " + word;
                 AddCount( PostTripletText, triplet, END_KEY );
              }
           }
           previous = BEGIN_KEY;
           peripenultimate = WORD_KEY;
           penultimate = WORD_KEY;
        }
        else
        {
           peripenultimate = penultimate;
           penultimate = previous;
           previous = word;
        }

    }
    // cout << endl;


    cout << "PreText" << endl;
    DumpPositionTable( PreText );
    cout << "PostText" << endl;
    DumpPositionTable( PostText );

    cout << "PrePairText" << endl;
    DumpPositionTable( PrePairText );
    cout << "PostPairText" << endl;
    DumpPositionTable( PostPairText );

    cout << "PreTripletText" << endl;
    DumpPositionTable( PreTripletText );
    cout << "PostTripletText" << endl;
    DumpPositionTable( PostTripletText );


    while(1)
    {
        cin >> word;

        if( word == END_KEY )
            break;

        word = Convert( word, isEnd );
        // cout << endl << "create utterance for word: " << word << endl;

        penultimate = WORD_KEY;
        previous = WORD_KEY;
        BackTrack( penultimate, previous, word );

        int accumulation = 0;
        while( word != END_KEY )
        {  
            cout << word;

            pair = previous + " " + word;
            triplet = penultimate + " " + previous + " " + word;

            // cout << endl << "pair='" << pair << "'" << endl;
            // cout << "triplet='" << triplet << "'" << endl;

            peripenultimate = penultimate;
            penultimate = previous;
            previous = word;


            WordCount wordCount;

            if( PostTripletText.find(triplet) != PostTripletText.end() )
            {
                // cout << " -> call GetRandWord( PostTripletText[" << triplet << "] )" << endl;
                // word = GetRandWord( *PostTripletText[triplet], accumulation++ );
                CopyWordCounts( wordCount, *PostTripletText[triplet], 9 );
            }
            else if( PostPairText.find(pair) != PostPairText.end() )
            {
                // cout << " -> call GetRandWord( PostPairText[" << pair << "] )" << endl;
                // word = GetRandWord( *PostPairText[pair], accumulation++ );
                CopyWordCounts( wordCount, *PostPairText[pair], 3 );
            }
            else
            {
                // cout << " -> call GetRandWord( PostText[" << word << "] )" << endl;
                // word = GetRandWord( *PostText[word], accumulation++ );
                CopyWordCounts( wordCount, *PostText[word], 1 );
            }
            word = GetRandWord( wordCount, accumulation++ );

            if( word == END_KEY )
                cout << "." << endl << endl;
            else
                cout << " ";

        }
    }
}
