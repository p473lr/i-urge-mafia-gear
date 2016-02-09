

#include <list>
#include <string>
#include <iostream>

using namespace std;

char grid[4][4];

class Shape
{
   public:
   string code;
   int mask[3][3];
   char letter;
   int row;
   int col;
   bool InsertAt( int r, int c )
   {
      bool valid = true;
      row = r;
      col = c;
      for( int i=0; valid && i<3; i++ )
      {
         for( int j=0; valid && j<3; j++ )
         {
            if( mask[i][j] )
            {
               int r1 = row + i;
               int c1 = col + j;
               if( r1 < 0 || r1 > 3 || c1 < 0 || c1 > 3 )
                  valid = false;
               if( grid[r1][c1] != 0 )
                  valid = false;
            }
         }
      }
      if( valid )
         SetGridFromMask(letter);

      return valid;
   };

   bool Remove( void )
   {
      SetGridFromMask(0);
   };
   private:
   void SetGridFromMask( char value )
   {
      for( int i=0; i<3; i++ )
         for( int j=0; j<3; j++ )
            if( mask[i][j] )
               grid[row+i][col+j] = value;
   };
};


int mask[3][3] =
{
   { 3, 2, 1 },
   { 3, 2, 1 },
   { 3, 2, 1 },
};

Shape shapeList[] =
{
   { "J1", { { 0, 1, 0 },
             { 0, 1, 0 },
             { 1, 1, 0 } } },
   { "J2", { { 1, 0, 0 },
             { 1, 1, 1 },
             { 0, 0, 0 } } },
   { "J3", { { 1, 1, 0 },
             { 1, 0, 0 },
             { 1, 0, 0 } } },
   { "J4", { { 1, 1, 1 },
             { 0, 0, 1 },
             { 0, 0, 0 } } },
   { "L1", { { 1, 0, 0 },
             { 1, 0, 0 },
             { 1, 1, 0 } } },
   { "L2", { { 1, 1, 1 },
             { 1, 0, 0 },
             { 0, 0, 0 } } },
   { "L3", { { 1, 1, 0 },
             { 0, 1, 0 },
             { 0, 1, 0 } } },
   { "L4", { { 0, 0, 1 },
             { 1, 1, 1 },
             { 0, 0, 0 } } },
   { "T1", { { 1, 1, 1 },
             { 0, 1, 0 },
             { 0, 0, 0 } } },
   { "T2", { { 0, 1, 0 },
             { 1, 1, 0 },
             { 0, 1, 0 } } },
   { "T3", { { 0, 1, 0 },
             { 1, 1, 1 },
             { 0, 0, 0 } } },
   { "T4", { { 1, 0, 0 },
             { 1, 1, 0 },
             { 1, 0, 0 } } },
   { "Z1", { { 1, 1, 0 },
             { 0, 1, 1 },
             { 0, 0, 0 } } },
   { "Z2", { { 0, 1, 0 },
             { 1, 1, 0 },
             { 1, 0, 0 } } },
   { "S1", { { 0, 1, 1 },
             { 1, 1, 0 },
             { 0, 0, 0 } } },
   { "S2", { { 1, 0, 0 },
             { 1, 1, 0 },
             { 0, 1, 0 } } },
   { "I1", { { 1, 0, 0 },
             { 1, 0, 0 },
             { 0, 0, 0 } } },
   { "I2", { { 1, 1, 0 },
             { 0, 0, 0 },
             { 0, 0, 0 } } },
   { "XX", { { 1, 1, 0 },
             { 0, 0, 0 },
             { 0, 0, 0 } } },
};

Shape& GetShape( string code )
{
   int i=0;
   while(1)
   {
      if( shapeList[i].code == code )
         return shapeList[i];
      if( shapeList[i].code == "XX" )
      {
         cout << "cannot find code " << code << endl;
         exit(1);
      }
      ++i;
   }
}


string shapeCodeList[5];
int shapeCodeCount;


void ShowGrid( void )
{
   for( int i=0; i<4; i++ )
   {
      for( int j=0; j<4; j++ )
      {
         cout << grid[i][j];
      }
      cout << endl;
   }
}


bool FitShape( int shapeCodeIndex )
{
   if( shapeCodeIndex == shapeCodeCount )
      return true;

   for( int row=0; row<4; row++ )
   {
      for( int col=0; col<4; col++ )
      {
         Shape& shape = GetShape( shapeCodeList[ shapeCodeIndex ] );
         if( shape.InsertAt( row, col ) )
         {
            // for debugging: ShowGrid();
            if( FitShape( shapeCodeIndex+1 ) )
               return true;
            else
               shape.Remove();
         }
      }
   }
   return false;
}


int main( int argc, char* argv[] )
{
   // initialize the grid to empty
   for( int i=0; i<4; i++ )
      for( int j=0; j<4; j++ )
         grid[i][j] = 0;

   cin >> shapeCodeCount;
   for( int i=0; i<shapeCodeCount; i++ )
   {
      cin >> shapeCodeList[i];
      // cout << "shapeCodeList[i] is " << shapeCodeList[i] << endl;
      Shape& shape = GetShape( shapeCodeList[i] );
      shape.letter = 'A' + i;
      // cout << "shape letter is " << shape.letter << endl;
   }
   // cout << "call FitShape()" << endl;
   FitShape( 0 );
   ShowGrid();
}

