/*
 * Sample solution for HP CodeWars 2012
 * Problem 2
 * "Ring World"
 */

#include <stdio.h>
#include <stdlib.h>

// Given
#define EARTH_SURFACE (196.935 * 1000000)
#define PI 3.14159265

#define RING_SURFACE(x_radius, x_width) (2 * PI * x_radius * x_width)
            

int main(int argc, char *argv[])
{
    char input[255];
    double radius, width, earths, ring_surface;

    // Read one line at a time, until there is no more input
    while(fgets(input, sizeof(input), stdin) != NULL)
    {

        sscanf(input, "%lf %lf", &radius, &width);

        ring_surface = RING_SURFACE(radius, width);

        earths = ring_surface / EARTH_SURFACE;

        printf("%d EARTHS\n", (int) earths);
    }

    return 0;
}

