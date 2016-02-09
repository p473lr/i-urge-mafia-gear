#include <stdio.h>
#include <math.h>

using namespace std;

int cross(int x1, int y1, int x2, int y2)
{
    return x1*y2 - x2*y1;
}

double pointLineDistance(int x1, int y1, int x2, int y2, int px, int py)
{
    return fabs(cross(x2-x1, y2-y1, px-x1, py-y1))/hypot(x2-x1, y2-y1);
}

int main()
{
    int tests;
    scanf("%d", &tests);
    for(int t = 1; t<=tests; t++)
    {
        int r, n;
        scanf("%d %d", &r, &n);
        int x[n];
        int y[n];
        for(int i = 0; i<n; i++)
            scanf("%d %d", x+i, y+i);
        bool isSafe = true;
        for(int j = n-1, i = 0; i<n; j=i++)
            if(pointLineDistance(x[i], y[i], x[j], y[j], 0, 0) <= r)
                isSafe = false;
        printf("Yard #%d: %s\n", t, isSafe?"Fly away!":"Better not risk it.");
    }

    return 0;
}
