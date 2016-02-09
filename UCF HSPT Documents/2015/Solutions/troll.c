#include <stdio.h>

int main() 
{
    int cases;
    scanf("%d", &cases);
    int cn = 0;

    while (cn ++ < cases) {
	unsigned long long strength, size;
	scanf("%llu %llu", &strength, &size);

	/*Following the steps of the question is the same as the steps to
	multiply binary numbers.  If you've never multiplied binary
	before, try following allong with the steps to see why it works.
	We are going to do this the long way instead of just multiplying
	to show how it works*/

	unsigned long long result = 0;
	while (strength > 0)
	{
	    if (strength % 2 == 1) {
		result += size;
	    }

	    strength /=2;
	    size *=2;
	}

	printf("Situation #%d: %llu\n", cn, result); 
    }
}
