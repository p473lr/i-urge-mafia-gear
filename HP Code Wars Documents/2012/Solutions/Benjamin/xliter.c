/*
 * Sample solution for HP CodeWars 2012
 * Problem 1
 * "X Litres of Ginger Soda"
 */

#include <stdio.h>
#include <stdlib.h>

#define LITER_IN_GALLON 3.785 // Given

int main(void)
{
    char input[255];
    double litres;
    double gallons_with_fraction;
    int gallons_whole;
    double fraction;

    // Read one line at a time, until there is no more input
    while(fgets(input, sizeof(input), stdin) != NULL)
    {

        litres = atof(input);

        gallons_with_fraction = litres/LITER_IN_GALLON;
        gallons_whole = (int) gallons_with_fraction; // This truncates
        fraction = gallons_with_fraction - gallons_whole;

        if(fraction >= 0.5)
        {
            // Add one to round up
            gallons_whole += 1;
        }

        printf("%d\n", gallons_whole);
    }

    return 0;
}
