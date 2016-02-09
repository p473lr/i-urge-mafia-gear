#include <stdlib.h>
#include <stdio.h>

int main()
{
    int b;
    scanf("%d", &b);

    // Loop through each test case
    int i;
    for(i = 0; i < b; i++)
    {
        int a, v;
        scanf("%d %d", &a, &v);

        printf("Breakfast #%d: ", i + 1);
        if(a < 18)
        {
            // Gaston is still a lad
            printf("4");
        }
        else if(v < 350000)
        {
            // Gaston is grown but not the size of a barge yet
            printf("5");
        }
        else
        {
            // Gaston is the size of a barge
            printf("0");
        }

        printf("\n");
    }

    return 0;
}
