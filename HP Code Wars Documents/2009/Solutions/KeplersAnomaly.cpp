// ============================================================================
//
// Kepler's Anomaly - Code Wars 2009
// written by Lee Jenkins
//
// ============================================================================

#include <math.h>
#include <iostream>
#include <string>

using namespace std;

int main( int argc, char* argv[] )
{
    string planet;
    float M, e, E1, E2, v;
    while( true )
    {
        cin >> planet;
        cin >> M;
        cin >> e;
        cin >> E2;
        cin >> v;
        if( planet == "END" )
            break;

        do
        {
            E1 = E2;
            E2 = M + e * sin( E1 );
        }
        while( fabs( E2 - E1 ) >= v );

        cout << planet << " " << E2 << endl;

    }
}
