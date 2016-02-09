#include<stdio.h>

int main() {
	
	int p;
	scanf("%d", &p);

	int t;
	for (t=0; t<p; t++) {
	
		int r;
		scanf("%d", &r);

		// We want to remove every prime factor, one at a time.
		// We can prove that choosing prime factors is optimal
		// because any composite factor contains multiple prime
		// factors, which means we could have gained more licks
		// by choosing each of those to divide out as seperate
		// licks. If we loop up starting at two and dividing
		// out every factor, we can prove we will never see a
		// composite factor used because any prime factor that
		// makes it up will already be removed (since it is 
		// smaller).
		int licks = 0;
		int factor;
		for (factor = 2; r > 1; factor++) {
			while (r % factor == 0) {
				r /= factor;
				licks++;
			}
		}

		printf("Pop #%d: ", t+1);
		if (licks <= 3)
			printf("%d licks? Your goose is cooked!\n", licks);
		else
			printf("A-one... A-two-HOO... A-%d.\n", licks);
	
	}

	return 0;
}
