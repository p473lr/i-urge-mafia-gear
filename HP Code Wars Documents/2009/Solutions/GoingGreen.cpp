// ============================================================================
//
// Going Green - Code Wars 2009
// written by Lee Jenkins
//
// ============================================================================

#include <math.h>
#include <iostream>
#include <string>
#include <map>

using namespace std;


bool IsLeapYear( int year )
{
    if( (year%400) == 0 )
        return true;

    if( (year%4) == 0 && (year%100) != 0 )
        return true;

    return false;
}

int GetNumDays( string month, int year )
{
    map<string,int> days;
    days["JANUARY"] = 31;
    days["FEBRUARY"] = 28;
    days["MARCH"] = 31;
    days["APRIL"] = 30;
    days["MAY"] = 31;
    days["JUNE"] = 30;
    days["JULY"] = 31;
    days["AUGUST"] = 31;
    days["SEPTEMBER"] = 30;
    days["OCTOBER"] = 31;
    days["NOVEMBER"] = 30;
    days["DECEMBER"] = 31;

    // cout << "month: '" << month << "'" << endl;

    int result = days[ month ];

    if( month == "FEBRUARY" && IsLeapYear(year) )
        ++result;

    // cout << "days: " << result << endl;

    return result;
}

int main( int argc, char* argv[] )
{
    string month;
    int year;
    int day;
    int start;
    int end;
    int value;
    int previousValue;
    int numDays;

    cin >> month >> year;
    while( month != "END" )
    {
        numDays = GetNumDays( month, year );
        start = 0;
        previousValue = 9999;
        for( int i=1; i<=numDays; i++ )
        {
            cin >> value;

            if( start && previousValue > value )
            {
                cout << month << " " << start;
                end = i-1;
                if( end > start )
                    cout << "-" << end;
                cout << ", " << year << ": " << previousValue << endl;
                start = 0;
            }

            if( value > previousValue )
                start = i;

            previousValue = value;
        }
        cin >> month >> year;
    }

}
