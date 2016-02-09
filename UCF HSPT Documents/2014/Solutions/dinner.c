
#include <stdio.h>
#include <stdlib.h>

int min(int a, int b);
long long choose(int a, int b);

int main()
{
    int t, i, j, k, cases, n;

    // Open the input file
    FILE *input = fopen("dinner.in", "r");

    // Read in the number of input cases and iterate over them
    fscanf(input, "%d", &cases);
    for (t = 0; t < cases; t++)
    {
        // Read in the amount of the bill
        fscanf(input, "%d", &n);

        // Initialize the answer to 0.
        long long res = 0; 

        // Iterate over all possible combinations of of $10's, $5's and $2's
        // i - the number of $10 bills
        // j - the number of $5 bills
        // k - the number of $2 bills
        for (i = 0; i <= 10; i++) 
        {
            for (j = 0; j <= 20; j++) 
            {
                for (k = 0; k <= 50; k++) 
                {
                    // See if these values add up to the correct amount; 
                    // if so, we add to our total.
                    if (10*i + 5*j + 2*k == n) 
                    {
                        /*
                         * The number of ways to arrange the bills in a pile.
                         * We have i + j + k positions in our pile of bills.
                         * We know that there are i $10 bills, j $5 bills, 
                         * and k $2 bills.
                         * First, we choose exactly i of these positions 
                         * to hold $10 bills = (i+j+k) choose (i) ways
                         * Then, from the remaining j+k positions, we 
                         * choose exactly of j of them to hold 
                         * $5 bills = (j+k) choose (j) ways
                         * Finally, there are only k positions left to 
                         * place the k $2 bills.
                         * So there is only one way to arrange 
                         * the $2 bills (by placing one in every slot).
                         * Note that n choose k = n choose (n-k), so....
                         */
                        res += choose(i+j+k, i) * choose(j+k, j);
                    }
                }
            }
        }

        // Output the answer.
        printf("Dinner #%d: %lld\n", t+1, res);
    }

    // Close file and return.
    fclose(input);
    return 0;
}

int min(int a, int b)
{
    if (a < b) 
        return a;
    else 
        return b;
}

long long choose(int a, int b)
{
    // This is valid because n choose k = n choose (n-k)
    b = min(b, a - b); 

    long long res = 1;
    int i;
    for (i = 0; i < b; i++) 
       res *= (a-i);
    for (i = 1; i <= b; i++) 
       res /= i;
    return res;
}
