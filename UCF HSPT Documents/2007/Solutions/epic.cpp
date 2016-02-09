////////////////////////////////////////////////////////////////////////////////
//                                                                            //
//    Problem: The Epic of Aldez: The mirror of Deceit                        //
//                                                                            //
//    Input file: epic.in                                                     //
//                                                                            //
//                                                                            //
//    The secret to solving this problem is to read the whole problem.        //
//    There is a lot to read, but most of it is just story. The true problem  //
//    boils down to reading in each line from the input and reversing it.     //
//    The following code will show how to do this in c++.                     //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>


int main(void)
{
    FILE *inFile;       // This is a pointer to the input file we need to read.
    int dungeonWidth;   // This is the width of the dungeon map.
    int dungeonHeight;  // This is the height of the dungeon map.
    int dungeonCount;   // This will track the dungeon we are processing.

    // Let's initialize the variables we created.
    inFile        = NULL;    // This means it points to nothing.

    dungeonWidth  = 0;       // Let's initialize the size of the map to 0 for
    dungeonHeight = 0;       // both the width and the height.

    dungeonCount  = 0;       // Start with a zero index for the dungeon count.

    // First we need to open the input file for reading.
    inFile = fopen("epic.in", "r");

    // Now we need to read in the dungeons width and height.
    // Don't forget to use the address of the variables we want to read in.
    fscanf(inFile, "%d %d", &dungeonWidth, &dungeonHeight);

    // Now as long as we haven't read in two zeros, for the
    // width and the height of the map, then we can continue.
    // Otherwise we need to stop.
    // The and operator means 'and'.
    while(dungeonWidth != 0 && dungeonHeight != 0)
    {
        char *mapLine;    // This is a line of the map to read in.

        // Let's initialize the map line to the size that we'll be reading in.
        // We add one to the size to allow for the ending character '\0' that
        // terminates all strings.
        mapLine = new char[dungeonWidth + 1];

        // Now let's print the current dungeon header. Dungeon X:
        // Where X is the dungeon we are currently processing.
        // The ++ before the variable increments it by one before using it.
        printf("Dungeon %d:\n", ++dungeonCount);

        // Now read in each line. We use the fact that there are no blank
        // spaces in the map so we can read each line in as a giant string.
        for(int i = 0; i < dungeonHeight; i++)
        {
            // Read in the line that we need to reverse.
            fscanf(inFile, "%s", mapLine);

            // Now we need to print this line out in reverse.
            // To do this we need to start from the end of the string and move
            // to the front.
            // We subtract one because the array is zero indexed.
            for(int j = dungeonWidth - 1; j >= 0; j--)
            {
                // Print out character by character the line in reverse.
                printf("%c", mapLine[j]);
            }

            // Now make sure the next line will print on it's own line.
            printf("\n");
        }

        // Now seperate this dungeons map from the next dungeons map
        // by a blank line.
        printf("\n");

        // Don't forget to delete the memory we allocated. It's oly polite.
        // We use the bracket's since we allocated an array.
        delete [] mapLine;

        // Now we need to read in the dungeons width and height
        // for the next case.
        // Don't forget to use the address of the variables we want to read in.
        fscanf(inFile, "%d %d", &dungeonWidth, &dungeonHeight);
    }

    return 0;
}
