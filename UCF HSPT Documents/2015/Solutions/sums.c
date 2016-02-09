#include <stdio.h>
#include <stdlib.h>

/*
Sums.c

This solution is based off of a few observations.

First, we only care about the even/odd parity of a player's number.
Thus we can convert all players numbers to an array containing only zeros and ones.
For the sample hand: 10 5 17 14 23
We can convert this into: 0 1 1 0 1
Both of these starting arrays will generate the same answer.
Our answer will also be the same if we store the sum%2 any time a move is taken instead of storing the sum, this will result in the array always containing only zeros and ones.

So after the first move: 0 1 1 0 1 will transform into 1 1 1 0 1. 
Try working this case our by hand.

Secondly, all moves after the first move do not change this array.
1 1 1 0 1 will stay the same regardless of which player makes a move.
This is because the first number is based off of the parity of the rest, and later on the other moves are based off of the parity of the first.  Thus after the first move is taken, if there was a difference between what the pairty of the first number was, and the pairty of the rest of the numbers, it is corrected on the first move, and no other changes are made.

Using this information, we can optimize our algorithm for calculating the happiness of each crowd member to O(N), see below.

So our entire runtime is O(N*M).

This algorithm could be sped up using inclusion-exclusion to O(MAX(N,M)) == O(M) by reducing our O(N) runtime on a crowd member query to O(1). Thus this problem could be solved if N <= 10 ^ 4

by Nick Buelich
*/

main()
{
	// initalize some variables
	int games, game, N, M, a, b, start, end; 
	scanf("%d",&games);
	// for each data set
	for(game = 1; game <= games; game++){
		scanf("%d %d",&N,&M);
		
		// initialize an array containing the initial set of numbers 
		int array[N];
		for(a=0;a<N;a++)
			scanf("%d",&array[a]);
		
		// the first player updates their hand on turn one, lets update it
		array[0]=0;
		// loop through other players and add their sum to player one, we only care about the even/odd parity in this case, so first mod by two before adding
		for(a=1;a<N;a++)
			array[0]+=array[a]%2;
		// mod all of the hands by two, we only care about even/odd parity, odd is 1, even is 0
		for(a=0;a<N;a++)
			array[a]%=2;
		
		// calculate the total value of the array for a speedier calculation later on
		int total = 0;
		for(a=0;a<N;a++)
			total+=array[a];
		
		printf("Game #%d:\n",game);
		for(a=0;a<M;a++){
			scanf("%d %d",&start,&end);
			// calculate the amount of moves taken while this observer is present
			int diff = (end-start)+1;
			// the number of times we will complete a full round, see all N players, can be calculated by floor(diff/N) or normal integer division.
			// we then can multiply this number by the amount of cheers in a complete round
			long ans = (diff/N)*total;
			
			// we mod diff by N because we only care about how many remaining moves we need to process
			diff%=N;

			// we will start at player x, and observe diff moves 
			int x = (start-1)%N;
			for(b=0;b<diff;b++){
				ans+=array[x]%2;
				x++;
				x%=N;
			}

			// output our answer
			printf("%d\n",ans);
		}
		printf("\n");
	}
	exit(0);
}
