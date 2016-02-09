
#include <vector>
#include <map>
#include <string>
#include <iostream>
#include <fstream>

#include <math.h>

using namespace std;

int grid[9][9];
int gridSize;
int* gridRefList[82];

bool IsGridValid()
{
    bool isValid = true;
    int rowValue[9][10];
    int colValue[9][10];
    for( int i=0; i<9; i++ )
    {
        for( int j=0; j<10; j++ )
        {
            rowValue[i][j] = 0;
            colValue[i][j] = 0;
        }
    }
    // check for duplicate values in each row and column
    for( int row=0; isValid && row<gridSize; row++ )
    {
        for( int col=0; isValid && col<gridSize; col++ )
        {
            int value = grid[ row ][ col ];
            // cout << value << " in grid[ " << row << " ][ " << col << " ]" << endl;
            if( value == 0 )
                continue;
            if( rowValue[ row ][ value ] || colValue[ col ][ value ] )
                isValid = false;
            rowValue[ row ][ value ] = 1;
            colValue[ col ][ value ] = 1;
        }
    }
    return isValid;
}


void ShowGrid()
{
	for( int row=0; row<gridSize; row++ )
	{
		for( int col=0; col<gridSize; col++ )
		{
			cout << grid[row][col] << " ";
		}
		cout << endl;
	}
}

class Cage
{
	public:
	   int goal;
	   vector<int> gridIndexes;
	   virtual bool IsValid( void ) = 0;
       int NumCells( void )
       {
		  return gridIndexes.size();
       };
       void SetCell( int index, int value )
       {
           *gridRefList[ gridIndexes[index] ] = value;
       };

};

// #define CAGE_DEBUG

class CageAddition : public Cage
{
   public:
	  virtual bool IsValid( void )
	  {

		  int sum = 0;
		  #ifdef CAGE_DEBUG
		  cout << "addition, goal:" << goal << endl;
		  #endif
		  for( int i=0; i<gridIndexes.size(); i++ )
		  {
			  sum += *gridRefList[ gridIndexes[i] ];
		  }
		  #ifdef CAGE_DEBUG
		  cout << "sum:" << sum << endl;
		  #endif
		  return ( sum == goal );
	  };
};

class CageSubtraction : public Cage
{
   public:
	  virtual bool IsValid( void )
	  {
		  int difference;
		  #ifdef CAGE_DEBUG
		  cout << "difference, goal:" << goal << endl;
		  #endif
		  for( int i=0; i<2; i++ )
		  {
			  #ifdef CAGE_DEBUG
			  cout << *gridRefList[gridIndexes[i]] << "," <<
						   *gridRefList[gridIndexes[1-i]] << endl;
			  #endif
			  difference = *gridRefList[gridIndexes[i]] -
						   *gridRefList[gridIndexes[1-i]];
			  #ifdef CAGE_DEBUG
			  cout << "difference:" << difference << endl;
			  #endif
			  if( difference == goal )
				 return true;
		  }
		  return false;
	  };
};

class CageMultiplication : public Cage
{
   public:
	  virtual bool IsValid( void )
	  {
		  int product = 1;
		  #ifdef CAGE_DEBUG
		  cout << "multiplication, goal:" << goal << endl;
		  #endif
		  for( int i=0; i<gridIndexes.size(); i++ )
		  {
			  // cout << "*gridRefList[ gridIndexes[" << i << "] ] = " <<
              //          *gridRefList[ gridIndexes[i] ] << endl;
			  product *= *gridRefList[ gridIndexes[i] ];
		  }
		  #ifdef CAGE_DEBUG
		  cout << "product:" << product << endl;
		  #endif
		  return ( product == goal );
	  };
};

class CageDivision : public Cage
{
   public:
	  virtual bool IsValid( void )
	  {
		  int quotient;
		  #ifdef CAGE_DEBUG
		  cout << "quotient, goal:" << goal << endl;
		  #endif
		  for( int i=0; i<2; i++ )
		  {
			  quotient = *gridRefList[gridIndexes[i]] /
						 *gridRefList[gridIndexes[1-i]];
			  #ifdef CAGE_DEBUG
			  cout << "quotient:" << quotient << endl;
			  #endif
			  if( quotient == goal )
				 return true;
		  }
		  return false;
	  };
};



int cageCount;
vector<Cage*> cageList;

bool FillInValuesFor( int cageIndex )
{
    if( cageIndex == cageCount )
        return true;

    int numCells = cageList[cageIndex]->NumCells();
    int min = 1;
    int max = gridSize;
    // cout << "cage " << cageIndex << " has " << numCells << " cells" << endl;
    for( int cellNum=1; cellNum<numCells; cellNum++ )
    {
        min = min*10 + 1;
        max = max*10 + gridSize;
    }
    for( int value=min; value<=max; value++ )
    {
        int temp = value;
        bool fail = false;
        // cout << "try value " << value << " in cage " << cageIndex << endl;
        for( int cellNum=0; !fail && cellNum<numCells; cellNum++ )
        {
            int digit = temp % 10;
            if( digit == 0 || digit > gridSize )
               fail = true;
            cageList[cageIndex]->SetCell( cellNum, digit );
            temp /= 10;
        }
        if( fail )
        {
            // cout << "bad digit" << endl;
        }
        else if( !cageList[cageIndex]->IsValid() )
        {
            // ShowGrid();
            // cout << "invalid cage" << endl;
        }
        else if( !IsGridValid() )
        {
            // ShowGrid();
            // cout << "repeated digit in rol/col" << endl;
        }
        else
        {
            // ShowGrid();
            if( FillInValuesFor( cageIndex+1 ) )
                return true;
        }
        // didn't work, reset cells to zeroes
        for( int cellNum=0; cellNum<numCells; cellNum++ )
            cageList[cageIndex]->SetCell( cellNum, 0 );
    }
    return false;
}


int main( int argc, char* argv[] )
{
	cin >> gridSize;

	// initialize the grid and the grid indexes
	int index = 1;
	for( int row=0; row<gridSize; row++ )
	{
		for( int col=0; col<gridSize; col++ )
		{
			grid[row][col] = 0;
			gridRefList[ index++ ] = &( grid[row][col] );
		}
	}

	cin >> cageCount;
	for( int i=0; i<cageCount; i++ )
	{
		int goalNumber;
		char operation;
		cin >> goalNumber;
		cin >> operation;
		Cage* cage;
		switch( operation )
		{
			case '+':
				cage = new CageAddition();
				break;
			case '-':
				cage = new CageSubtraction();
				break;
			case 'x':
				cage = new CageMultiplication();
				break;
			case '/':
				cage = new CageDivision();
				break;
		}
		cage->goal = goalNumber;
		int cellCount, cellIndex;
		cin >> cellCount;
		for( int j=0; j<cellCount; j++ )
		{
			cin >> cellIndex;
			if( cellIndex > gridSize*gridSize )
			   cout << "cellIndex > gridSize*gridSize" << endl;
			if( cellIndex <= 0 )
			   cout << "cellIndex <= 0" << endl;
			cage->gridIndexes.push_back( cellIndex );
		}
		cageList.push_back( cage );
	}


    FillInValuesFor( 0 );
    ShowGrid();

}

