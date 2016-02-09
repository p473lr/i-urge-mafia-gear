
#include <stdio.h>
#include <math.h>

// Defining pi to be the value given in the problem
#define PI 3.141592653589793


int main() {

    // Reopen stdin from the file
    freopen("tingling.in", "r", stdin);

    // Read in the number of villians
    int numVillians;
    scanf("%d", &numVillians);

    // Loop through the test cases
    int i;
    for (i = 0; i < numVillians; i++) {

        // Read in the villian name
        char villianName[50];
        scanf("%s", villianName);

        // Reading in p, the signal power
        double p;
        scanf("%lf", &p);

        // The question is asking us to find the distance between SpiderMan 
        // and the villian.
        // From the problem we know the villian is in a circle with area p^2.
        // So since the area of a circle is pi*r^2,  pi*r^2 = Area = p^2.
        // Also, it happens that r is the distance from SpiderMan to the 
        // edge of the circle, which is the value we want.
        // We solve for r: r^2= (p^2)/pi
        //                 r = p/sqrt(pi)
        double r = p / sqrt(PI);

        // Print the output
        printf("%s is %.3lf feet away.\n", villianName, r);
    }

    // Done
    return 0;
}

