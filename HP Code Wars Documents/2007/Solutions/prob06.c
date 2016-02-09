#include <stdio.h>

#define SIEVE_SIZE 1001
int sieve[SIEVE_SIZE];

int main(int argc, char *argv[])
{
        char buf[30];
        int n;
        int i, nr, p;

        printf("Enter a number:  ");
        gets(buf);

        n = atoi(buf);

        if (n<1 || n>1000) {
                printf("Number must be between 1 and 1000\n");
                return -1;
        }

        // Compute the sieve
        nr = 0;
        for(i=2; i<=n; i++) {
                // Did we find a prime 
                if (sieve[i] == 0) {
                        // Newline after 5 numbers
                        if (nr && nr % 5 == 0) printf("\n");
                        nr++;
                        printf("%10d", i);

                        // Fill in the sieve with all multiples of
                        // this prime
                        p = 2*i;
                        while(p < SIEVE_SIZE) {
                                sieve[p] = 1;
                                p += i;
                        }
                }
        }
        printf("\n");

        return 0;

}
