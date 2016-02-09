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
typedef long long LL;

const int INF = 987654321;
vector<string> split(string s)
{
    vector<string> ret;
    ret.clear();
    string t = "";
    for(int i = 0; i < s.size(); i++)
    {
        if(!isdigit(s[i]))
        {
            ret.push_back(t);
            t = "";
        }
        else
            t += s[i];
    }

    if(t != "")
        ret.push_back(t);
    return ret;
}

int parse(string s)
{
    vector<string> toks = split(s);
    int a = 0, b = 0, c = 0, p = 0;
    if(toks.size() == 3)
    {
        istringstream one(toks[0]);
        one >> a;
        p++;
    }

    istringstream two(toks[p++]);
    two >> b;
    istringstream three(toks[p++]);
    three >> c;

    return (a*c + b) * (8/c);
}

int get()
{
    string s;
    getline(cin, s);
    istringstream foo(s);
    int ret;
    foo >> ret;
    return ret;
}

void print(int num)
{
    int den = 8;
    bool sign = false;
    if(num < 0)
    {
        num *= -1;
        sign = true;
    }

    int a = num/den, b = num % den, c = den;
    while(b % 2 == 0 && c % 2 == 0)
    {
        b /= 2;
        c /= 2;
    }
    if(sign) printf("-");
    if(a != 0)
        printf("%d ", a);
    printf("%d/%d.\n", b, c);
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("stock.in", "rt", stdin);
    int N = get();
    for(int k = 1; k <= N; k++)
    {
        int M = get();
        vector<int> v;
        string s;
        for(int i = 0; i < M; i++)
        {
            string s;
            getline(cin, s);
            v.push_back(parse(s));
        }

        int best = -INF, start = -1, end = -1;
        for(int i = 0; i < M; i++)
            for(int j = i+1; j < M; j++)
                if(best < v[j] - v[i])
                {
                    best = v[j] - v[i];
                    start = i;
                    end = j;
                }
        printf("Period %d: The biggest gain was from day %d to day %d of ", k, start+1, end+1);
        print(best);
    }
    return 0;
}



