////////////////////////////////////////////////////////////////////////////////
//                                                                            //
//    Problem: Ali's Disorganized Discs                                       //
//                                                                            //
//    Input file: organize.in                                                 //
//                                                                            //
//                                                                            //
//    This is a combinations problem. The size of the combinations though     //
//    is small enough to brute force (There were only 3 combinations.)        //
//    The following code will show how to do this in c++.                     //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>

#define DISK_DIAMETER 120

bool findCombination(int boxLength, int boxWidth, int boxHeight, int diskHeight)
{
    // Here we just need to find two things:
    //     1. A combination of two of the box's dimensions that are both
    //        greater than of equal to the disks diameter.
    //     2. The third dimension needs to be greater than or equal to the
    //        disks height.

    // We could have done this with a function that recursively checks all the
    // combinations but since n!/(r!(n-r)!) = 3!/(2!(1!)) = 3 combinations, I
    // found it faster to copy bast a brute force.

    // Let's check if the bottom being the length and width will work.
    if(boxLength >= DISK_DIAMETER &&
       boxWidth >= DISK_DIAMETER)
    {
        // Let's see if the height will hold the disks hiehgt.
        if(boxHeight >= diskHeight)
        {
            // Found a combination!
            return true;
        }
    }

    // Let's check if the bottom being the length and width will work.
    if(boxLength >= DISK_DIAMETER &&
       boxHeight >= DISK_DIAMETER)
    {
        // Let's see if the height will hold the disks hiehgt.
        if(boxWidth >= diskHeight)
        {
            // Found a combination!
            return true;
        }
    }

    // Let's check if the bottom being the length and width will work.
    if(boxHeight >= DISK_DIAMETER &&
       boxWidth >= DISK_DIAMETER)
    {
        // Let's see if the height will hold the disks hiehgt.
        if(boxLength >= diskHeight)
        {
            // Found a combination!
            return true;
        }
    }

    // No combination found.
    return false;
}

int main()
{
    FILE *inFile;       // This is a pointer to the input file we need to read.
    int boxLength;      // This is the length of the box.
    int boxWidth;       // This is the width of the box.
    int boxHeight;      // This is the height of the box.
    int cdAmount;       // This is the amount of cd's we need to fit in the box.
    int boxCount;       // This will track the box we are processing.

    // Let's initialize the variables we created.
    inFile     = NULL;    // This means it points to nothing.

    boxLength  = 0;
    boxWidth   = 0;       // Let's initialize the size of the box's dimensions
    boxHeight  = 0;       // to zero.

    cdAmount   = 0;       // Start with zero cd's.

    boxCount   = 0;       // Start with a zero index for the box count.

    // First we need to open the input file for reading.
    inFile = fopen("organize.in", "r");

    // Now we need to read in the box's dimensions and how many cd's there will
    // be.
    // Don't forget to use the address of the variables we want to read in.
    // Please note: The order of the box's dimensions is not specified because
    // it doesn't matter.
    fscanf(inFile, "%d %d %d %d", &boxLength, &boxWidth, &boxHeight, &cdAmount);

    // Now as long as we haven't read in all zeros we can continue.
    // Otherwise we need to stop.
    // The || operator means 'or'.
    while(boxLength != 0 && boxWidth != 0 && boxHeight != 0 && cdAmount != 0)
    {
        // Now since all the size are in milimeters we can ignore units.
        int diskHeight = 2 * cdAmount;    // The height of the stack of disks.
        bool answer = false;              // This will keep track on wether or
                                          // not we have found a combination
                                          // that works.

        // Now we need to do a few things to see if our cd stack fit's into
        // the box or not:
        //      1. Try a combinations of two box dimensions and see if both
        //         dimensions are bigger than the disks diameter.
        //      2. If step 1 passed then test if the third dimension of the box
        //         is bigger than the height of the stack of disks.
        //      3. If 3 passed then our cd's fit, other wise try another
        //         combination. If all combinations have been tried they our
        //         cd's do not fit.

        // I'm going to use a function to find if there is a combination that
        // works or not.
        answer = findCombination(boxLength, boxWidth, boxHeight, diskHeight);

        // If we found a combination then print that the disks fit.
        // Otherwise print that they don't fit.
        if(answer)
        {
            printf("Box %d: Stack of %d discs fits!\n", ++boxCount, cdAmount);
        }
        else
        {
            printf("Box %d: Stack of %d discs does not fit.\n",
                   ++boxCount, cdAmount);
        }

        // Now we need to read in the next cases box dimensions and how many
        // cd's there will be.
        // Don't forget to use the address of the variables we want to read in.
        fscanf(inFile, "%d %d %d %d", &boxLength, &boxWidth, &boxHeight,
               &cdAmount);
    }

    return 0;
}
