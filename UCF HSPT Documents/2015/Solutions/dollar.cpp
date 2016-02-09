#include <stdio.h>

using namespace std;

int main()
{
    // Read the number of people that ask Stephen for money.
    int tests;
    scanf("%d", &tests);

    // Loop through each person that asks him.
    for(int t = 1; t<=tests; t++)
    {
        // Read the number of times this person asks for money.
        int times;
        scanf("%d", &times);

        // The answer is times/2 with integer division.
        int ans = times/2;
        printf("Person #%d: $%d\n", t, ans);
    }
    return 0;
}
