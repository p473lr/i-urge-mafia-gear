
#include <vector>
#include <map>
#include <string>
#include <iostream>

using namespace std;

class OreLine
{
   public:
      string  ore;
      int     mass;
      bool    used;
};

class Digger
{
   private:
      int fuel;
   public:
      vector<OreLine*>      orePool;
      Digger( int totalFuel )
      {
         fuel = totalFuel;
      };
      void Dig( int reserveFuel )
      {
         while( ! cin.eof() )
         {
            int lineDigFuel;
            OreLine* line = new OreLine();
            cin >> lineDigFuel;
            cin >> line->ore;
            cin >> line->mass;
            line->used = false;
            if( lineDigFuel == 0 || fuel - lineDigFuel < reserveFuel )
               break;
            fuel -= lineDigFuel;
            orePool.push_back( line );
         }
      };
};

typedef pair<string,float>  OreValue;

class Sum
{
   private:
      int sum;
   public:
      Sum( int value = 0 ) : sum( value ) { };
      operator int() const
      {
         return sum;
      };
      int operator()( int value )
      {
         sum += value;
         return sum;
      };
      void Reset( int newSum = 0 )
      {
         sum = newSum;
      };
};

class CargoHold
{
   private:
      int                cargoCapacityMass;
      map<string,float>  oreValues;
   public:
      CargoHold( int cargoSize )
      {
         cargoCapacityMass = cargoSize;
         oreValues.insert( OreValue( "Cu",     1.730 ) );
         oreValues.insert( OreValue( "Zn",     1.130 ) );
         oreValues.insert( OreValue( "H2O",    3.720 ) );
         oreValues.insert( OreValue( "Fe",     0.410 ) );
         oreValues.insert( OreValue( "Si",     0.320 ) );
         oreValues.insert( OreValue( "Mg",     3.460 ) );
         oreValues.insert( OreValue( "C",      2.750 ) );
         oreValues.insert( OreValue( "Pt", 25000.000 ) );
         oreValues.insert( OreValue( "Au", 12260.000 ) );
         oreValues.insert( OreValue( "Ag",   190.629 ) );
      };
      void StoreCargo( Digger& digger )
      {
         vector<int> storageMask;
         float       maxValue = 0;
         int         maxMass = 0;
         int         poolSize = digger.orePool.size();
         for( int i=0; i<poolSize; i++ )
         {
            storageMask.push_back(0);
         }
         storageMask[0] = 1;
         Sum sum;
         while( for_each( storageMask.begin(), storageMask.end(), sum ) != 0 )
         {
            float maskValue = 0;
            int   maskMass = 0;
            for( int i=0; i<poolSize; i++ )
            {
               if( storageMask[i] )
               {
                  float oreValue  = oreValues[ digger.orePool[i]->ore ];
                  maskValue += digger.orePool[i]->mass * oreValue;
                  maskMass  += digger.orePool[i]->mass;
                  if( cargoCapacityMass - maskMass < 0 )
                  {
                     maskValue = 0;
                     break;
                  }
               }
            }
            if( maskValue > maxValue )
            {
               maxValue = maskValue;
               maxMass  = maskMass;
            }
            for( int carry=1, i=0; carry == 1 && i < poolSize; i++ )
            {
               if( storageMask[i] == 0 )
               {
                  storageMask[i] = 1;
                  carry = 0;
               }
               else
               {
                  storageMask[i] = 0;
               }
            }
         }
         cout << "kg=" << maxMass << " value=" << maxValue << endl;
      };
};

int main( int argc, char* argv[] )
{
   int totalFuel, reserveFuel, cargoSize;
   cin >> totalFuel;
   cin >> reserveFuel;
   cin >> cargoSize;

   Digger digger( totalFuel );
   digger.Dig( reserveFuel );

   CargoHold cargoHold( cargoSize );
   cargoHold.StoreCargo( digger );
}

