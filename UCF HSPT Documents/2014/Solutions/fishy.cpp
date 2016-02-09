#include <stdio.h>

int main()
{
    FILE *in = fopen("fishy.in", "r");
    int tests;
    fscanf(in, "%d", &tests);
    for(int t = 0; t<tests; t++)
    {
        // read the input for this test case
        int n;
        fscanf(in, "%d", &n);
        char ary[n+1];
        fscanf(in, "%s", ary);

        int res = 0;

        // lets check for fish with the head on the left
        for(int i = 0; i<n-1; i++)
        {
            if(ary[i] == '<' && ary[i+1] == 'o') // this could be the start of a head
            {
                int j = i+2;
                int len = 0;
                //compute the length of the body
                while(j<n && ary[j] == '(')
                {
                    j++;
                    len++;
                }
                if(len == 0)
                    continue; // this fish is empty :(
                if(j >= n-1)
                    continue; // its impossible for this fish to have a tail
                if(ary[j] == '>' && ary[j+1] == '<')
                    res += len*len; // we found a fish!
            }
        }

        // now fish with a head on the right
        for(int i = 0; i<n-1; i++)
        {
            if(ary[i] == '>' && ary[i+1] == '<') // this could be the start of a tail
            {
                int j = i+2;
                int len = 0;
                //compute the length of the body
                while(j<n && ary[j] == ')')
                {
                    j++;
                    len++;
                }
                if(len == 0)
                    continue; // this fish is empty :(
                if(j >= n-1)
                    continue; // its impossible for this fish to have a tail
                if(ary[j] == 'o' && ary[j+1] == '>')
                    res += len*len; // we found a fish!
            }
        }
        //All done!
        printf("Day #%d: %d rubles\n", t+1, res);
    }
    fclose(in);
}
