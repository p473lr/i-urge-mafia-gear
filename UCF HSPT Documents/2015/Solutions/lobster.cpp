#include <stdio.h>

using namespace std;

// This performs a bubble sort where you can only swap offset away(like swap lobster).
void bubbleSort(int *ary, int n, int offset)
{
    bool swapHappend = true;
    while(swapHappend)
    {
        swapHappend = false;
        for(int i = 0; i<n-offset; i++)
            if(ary[i] > ary[i+offset])
            {
                int tmp = ary[i];
                ary[i] = ary[i+offset];
                ary[i+offset] = tmp;
                swapHappend = true;
            }
    }
}

int main()
{
    int tests;
    scanf("%d", &tests);
    for(int t = 1; t<=tests; t++)
    {
        int n, k;
        scanf("%d %d", &n, &k);

        // Read in the array to be sorted
        int ary[n];
        for(int i = 0; i<n; i++)
            scanf("%d", ary+i);

        // Make a copy for real sorting.
        int sortedAry[n];
        for(int i = 0; i<n; i++)
            sortedAry[i] = ary[i];

        bubbleSort(sortedAry, n, 1); // perform a normal bubble sort
        bubbleSort(ary, n, k); // perform a swap lobster bubble sort

        // Test if the two arrays are equivalent.
        bool isSame = true;
        for(int i = 0; i<n; i++)
            if(ary[i] != sortedAry[i])
                isSame = false;
        printf("Lobster #%d: %s\n", t, isSame?"Sortable":"Unsortable");
    }
    return 0;
}
