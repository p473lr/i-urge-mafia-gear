
#include <vector>
#include <string>
#include <iostream>

using namespace std;

class Slot
{
    public:
        int row;
        int col;
        bool across;
        Slot( int newRow, int newCol, bool newAcross )
        : row( newRow ), col( newCol ), across( newAcross )
        {
        };
};


char puzzle[99][99];
int wordCount, height, width;

vector<string> wordList;
bool           wordUsed[99];

vector<Slot*> slotList;
bool          slotUsed[99];

void FindSlots( void )
{
    bool space;

    for( int h=0; h<height; h++ )
        for( int w=0; w<width; w++ )
        {
            // searching down for slot at w , h
            int blocks, letters;
            blocks = 0;
            letters = 0;
            for( int col=w; space && col<w+3; col++ )
                if( puzzle[h][col] == '#' )
                    ++blocks;
                else if( puzzle[h][col] != '-' )
                    ++letters;
            if( blocks == 0 && letters < 3 )
                slotList.push_back( new Slot( h, w, true ) );
            // searching across for slot at w , h
            blocks = 0;
            letters = 0;
            for( int row=h; space && row<h+3; row++ )
                if( puzzle[row][w] == '#' )
                    ++blocks;
                else if( puzzle[row][w] != '-' )
                    ++letters;
            if( blocks == 0 && letters < 3 )
                slotList.push_back( new Slot( h, w, false ) );
        }
    cout << slotList.size() << " slots found" << endl;
    for( int i=0; i<slotList.size(); i++ )
        slotUsed[i] = false;
}


int fitCount = 0;

bool FitWords( void )
{
    cout << "-----------------------------------------------" << endl;
    for( int h=0; h<height; h++ )
    {
        for( int w=0; w<width; w++ )
            cout << puzzle[h][w] << " ";
        cout << endl;
    }

    // find an unused slot
    int slotNum = -1;
    int slotCount = 0;
    for( int i=0; i<slotList.size(); i++ )
    {
        if( !slotUsed[i] )
        {
            slotNum = i;
            ++slotCount;
        }
    }

    cout << slotCount << " slots remaining" << endl;

    // if no free slots, then we're done!
    if( slotNum == -1 )
        return true;


    // make a string of the squares that need to be matched
    string word, squares = "";
    int row = slotList[slotNum]->row;
    int col = slotList[slotNum]->col;

    cout << " fitting for slot at row " << row << " col " << col << (slotList[slotNum]->across?" across":" down") << endl;

    int dr = slotList[slotNum]->across ? 0 : 1;
    int dc = slotList[slotNum]->across ? 1 : 0;
    for( int t=0; t<3; t++ )
        squares += puzzle[row+t*dr][col+t*dc];

    slotUsed[slotNum]  =  true;

    // find a word to fit in the slot
    bool wordFits;
    
    for( int w=0; w<wordList.size()/2; w++ )
    {
        if( wordUsed[w] )
            continue;

        word = wordList[w];
        wordFits = true;
        // cout << "comparing " << squares << " with " << word << endl;
        for( int n=0; n<3; n++ )
        {
            if( squares[n] != '-' && squares[n] != word[n] )
                wordFits = false;
        }
        if( wordFits )
        {
            wordUsed[w]  =  true;
            for( int t=0; t<3; t++ )
                puzzle[row+t*dr][col+t*dc] = word[t];

            cout << " putting '" << word << "' in row " << row << " col " << col << (slotList[slotNum]->across?" across":" down") << endl;

            if( FitWords() )
                return true;

    if( fitCount++ == 125 )
    {
         cout << "debug early termination" << endl;
         return true;
    }

            wordUsed[w]  =  false;
        }
    }
    for( int t=0; t<3; t++ )
        puzzle[row+t*dr][col+t*dc] = squares[t];
    slotUsed[slotNum]  =  false;
    cout << "no word found to fit" << endl;
    return false;
}

int main( int argc, char* argv[] )
{
    string word;
    cin >> wordCount;
    // for( int i=0; i<wordCount; i++ )
    word = "";
    for( cin>>word; word != "END-OF-LIST"; cin>>word )
        wordList.push_back( word );

    for( int i=0; i<wordList.size(); i++ )
        wordUsed[i] = false;

    for( int i=0; i<wordList.size(); i++ )
    {
        string temp = wordList[i];
        int r = rand() % wordList.size();
        wordList[i] = wordList[r];
        wordList[r] = temp;
    }

    for( int i=0; i<wordList.size(); i++ )
        cout << wordList[i] << " ";
    cout << endl;
    cout << wordList.size() << " words found" << endl;

    cin >> width;
    cin >> height;

    for( int h=0; h<height; h++ )
        for( int w=0; w<width; w++ )
            cin >> puzzle[h][w];

    for( int h=0; h<height; h++ )
        puzzle[h][width] = '#';

    for( int w=0; w<width; w++ )
        puzzle[height][w] = '#';

    for( int h=0; h<height; h++ )
    {
        for( int w=0; w<width; w++ )
            cout << puzzle[h][w] << " ";
        cout << endl;
    }

    FindSlots( );

    FitWords( );

    cout << "-----------------------------------------------" << endl;
    for( int h=0; h<height; h++ )
    {
        for( int w=0; w<width; w++ )
            cout << puzzle[h][w] << " ";
        cout << endl;
    }

    return 0;
}

