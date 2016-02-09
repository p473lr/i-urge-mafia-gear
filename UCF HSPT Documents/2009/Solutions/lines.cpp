#include <iostream>
#include <sstream>
#include <cstdio>
#include <cstdlib>
#include <string>
#include <cstring>
#include <vector>
#include <queue>
#include <map>
#include <set>
#include <stack>
#include <algorithm>
#include <cmath>
using namespace std;


vector<int> multi;
int P, S, C;

int solo()
{
    int pos = 0, left = S+1, coaster = 0;
    while(left > 0)
    {
        coaster++;
        int cap = C;
        while(pos < multi.size() && cap >= multi[pos])
            cap -= multi[pos++];
        if(cap > 0)
            left -= min(cap, left);
    }
    return coaster;
}

int regular()
{
    multi.push_back(1);
    int pos = 0, left = S, coaster = 0;
    while(pos < multi.size())
    {
        coaster++;
        int cap = C;
        while(pos < multi.size() && cap >= multi[pos])
            cap -= multi[pos++];
        if(cap > 0)
            left -= min(cap, left);
    }
    return coaster;
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("lines.in", "rt", stdin);

    for(int T = 1; ; T++)
    {
        cin >> P >> S >> C;
        if(P + S + C == 0)
            break;
        multi.clear();
        for(int i = 0; i < P; i++)
        {
            int x; cin >> x;
            multi.push_back(x);
        }
        int one = solo();
        int two = regular();

        printf("Scenario #%d: MHR rides coaster #%d, using ", T, min(one, two));
        if(one == two)
            printf("either line.\n");
        else if(one > two)
            printf("the regular line.\n");
        else
            printf("the single rider line.\n");
    }
    return 0;
}



