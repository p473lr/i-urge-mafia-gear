#include <iostream>
#include <fstream>

using namespace std;

//If using c, replace "<iostream>" with "<stdio.h>"
// and omit both the include to "<ifstream>" and the "using..." line

int main()
{
    //Set up file input
    ifstream fin("gnome.in"); //if using c, use the following commands instead
    /*
    FILE* fp;
    fp = fopen("gnome.in", "r");
    */

    int pots; //pots in current case
    fin >> pots; //If using c, this line is "fscanf(fp, "%d", &pots);"

    int * potarr; //The array of pots

    int set = 1; //Our 'Garden' counter

    while(pots != 0) //pots == 0 is the end condition
    {
        potarr = new int[pots]; // if using c, this statement becomes
                                // potarr = (int*) calloc(pots, sizeof(int));

        for(int i = 0; i < pots; i++) //fill the pot array
            fin >> potarr[i]; //If using c, this line is "fscanf(fp, "%d", &potarr[i]);"

        int gnomeloc = 0;

        cout << "Garden " << set << ":\n"; //if using c, this line is:
                                           // printf("Garden %d:\n", set);

        while(gnomeloc < pots && pots > 1)
        {
            if(gnomeloc == 0) //The first gnome sorting rule
                gnomeloc = 1;

            //If current location is gnomeloc, to the left is gnomeloc-1
            if(potarr[gnomeloc-1] > potarr[gnomeloc]) //The third gnome sorting rule
            {
                //Swap the values between the two locations using a temporary variable
                int potswap = potarr[gnomeloc-1];
                potarr[gnomeloc-1] = potarr[gnomeloc];
                potarr[gnomeloc] = potswap;

                cout << "The gnome swaps the pots at positions " << gnomeloc;
                cout << " and " << gnomeloc+1 << ".\n"; //Remeber  that pot locations
                                                        // are 1-indexed, so instead of
                                                        // gnomeloc-1, and gnomeloc, we
                                                        // have gnomeloc and gnomeloc+1
                //If using c, the above two lines are:
                // printf("The gome swaps the pots at positions %d and %d.\n", gnomeloc, gnomeloc-1);

                gnomeloc--;
            }
            else
                gnomeloc++; //The second gnome sorting rule
        }

        cout << "Sorted!\n\n"; //One line between outputs
                               // If using c, this line is: printf("Sorted!\n\n");

        //Prepare for the next case
        fin >> pots; //If using c, this line is "fscanf(fp, "%d", &pots);"
        set++;
        delete potarr; //if using c, this is "free(potarr);"
    }

    return 0;
}
