#include <cstdio>
#include <cstring>
#include <cmath>

int main(void)
{
    FILE *fp;
    char line[80], *nl;
    int tripNumber;
    int length1, length2;
    int speed1, speed2;
    int shortLength;
    int shortSpeed;
    int dl, ds;
    double t, targetSpeed;


    // Open the input file
    fp = fopen("bus.in", "r");

    // Read in the first line, and trim off the newline character (if any)
    fgets(line, sizeof(line), fp);
    nl = strchr(line, '\n');
    if (nl != NULL)
        *nl = 0;

    // Initialize the trip counter
    tripNumber = 0;

    // Parse the first set of input parameters
    sscanf(line, "%d %d %d %d %d",
        &length1, &speed1, &length2, &speed2, &shortLength);

    // Keep looping until we get a shortLength of zero
    while (shortLength > 0)
    {
        // Increment the trip number
        tripNumber++;

        // We'll solve this parameterically.  First, compute the
        // vector V from the first point (length1, speed1) to the second
        // (length2, speed2)
        dl = length2 - length1;
        ds = speed2 - speed1;

        // Now, we'll use this vector to compute a parameter (t) that when
        // multiplied by the vector will get us to the point we want.
        // Since we know the target length, we can solve for the
        // parameter:
        //
        // l = l0 + t*dl  or t = (l - l0) / dl
        //
        // We'll cast the quantities to doubles to make sure we use
        // floating-point division
        //
        t = (double)(shortLength - length1) / (double)dl;

        // Now, we can use the parameter t to solve for the target speed
        // like this:
        //
        // s = s0 + t*ds
        //
        // Again, we cast to doubles to make sure we're using floating-
        // point math
        //
        targetSpeed = (double)speed1 + (t * (double)ds);

        // The problem specified that we need to find the lowest integer
        // speed that is large enough to achieve a short bus, so we need
        // to take the ceiling of (that is, the smallest integer greater
        // than or equal to) the speed we computed.
        shortSpeed = (int)ceil(targetSpeed);

        // It's possible that the resulting speed will be negative, so
        // we need to check for that before we print out the result.
        // Ali's bus can't back up, so a negative speed is impossible.
        if (shortSpeed > 0)
            printf("Ali's Bus Trip #%d: The bus must travel at least "
                "%d kph.\n", tripNumber, shortSpeed);
        else
            printf("Ali's Bus Trip #%d: The bus must travel at least "
                "0 kph.\n", tripNumber);

        // Read in the next line, and trim off the newline character (if any)
        fgets(line, sizeof(line), fp);
        nl = strchr(line, '\n');
        if (nl != NULL)
            *nl = 0;

        // Parse the first set of input parameters
        sscanf(line, "%d %d %d %d %d",
            &length1, &speed1, &length2, &speed2, &shortLength);
    }

    // Close the input file
    fclose(fp);

    return 0;
}
