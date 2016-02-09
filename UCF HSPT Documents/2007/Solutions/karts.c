/* Kart Racing solution
 * Written in C by Jobby Johns
 * UCF 2007 High School Programming Contest
 */

/* This problem is somewhat difficult since you want to make sure that each
 * person trips on the banana peel that (s)he is supposed to.  The following
 * solution was used: For each year, get the racer information.  Then, handle
 * each course separately within the year.  Get the course information.  Each
 * racer will keep track of where (s)he is on the track and also how much time
 * is left for a banana peel stop (0 if not in one.)  Bananas are sorted as
 * well.
 *
 * To run the race, calculate how long it would take for the fastest racer to
 * hit the first banana.  With this time, move everyone forward to their proper
 * new location based on that time.  Now penalize the fastest player 5 seconds.
 * Repeat with the next banana, making sure that the previous fastest player
 * has their new time penalty (5 seconds) tacked onto the time to reach the
 * next banana.  Use this new time and move everyone forward accordingly
 * (shrinking the penalized player's driving time by his penalty.)  Repeat
 * until all bananas are handled.
 *
 * Now, to make the solution slightly easier to code up (due to the structure
 * of this code), I added a banana at the end of the race if one did not exist
 * there.  By letting the code that runs the race find out who slips on this
 * last banana, that racer must be the winner since (s)he had to reach the end
 * of the course first in order to slip on that banana.
 */

#include <stdio.h>
#include <string.h>

/* We use the racer struct since a racer has multiple variables (name and
 * speed.)   A racer also holds two extra variables which are used when racing
 * within a course.  location represents the distance that the racer is from
 * the start of the course.  timePenalty represents how much longer a racer
 * must wait in order to continue (only used if stopped by a banana peel.)
 */
typedef struct racer
	{
	char name[31];
	int speed;
	double location;
	double timePenalty;
	} racer;

int main()
	{
	/* declare all variables */
	int numYears;
	int year;
	int m, n;
	racer players[9];
	int player;
	int course;
	char courseName[31];
	int courseLength;
	int b;
	int coursePeels[12];
	int peel;
	int i, j, temp;
	int fastestPlayer;
	double fastestTime;
	double time;
	FILE *infile;
	
	/* open the file */
	infile = fopen("karts.in", "r");
	
	/* get the number of years */
	fscanf(infile, "%d", &numYears);
	
	/* handle each year */
	for (year = 1; year <= numYears; ++year)
		{

                printf("Circuit #%d:\n", year);

		/* get the number of racers */
		fscanf(infile, "%d", &m);
		
		/* get the racers themselves */
		for (player = 0; player < m; ++player)
			{
			fscanf(infile, "%s %d", players[player].name, &(players[player].speed));
			}
		
		/* get the number of courses */
		fscanf(infile, "%d", &n);
         
		/* get each course and output for each course */
		for (course = 0; course < n; ++course)
			{
			/* get the course information from the input */
			fscanf(infile, "%s %d %d", courseName, &courseLength, &b);
			for (peel = 0; peel < b; ++peel)
				{
				fscanf(infile, "%d", &coursePeels[peel]);
				}
			
			/* reset each racer's location and penalty to start of course */
			for (player = 0; player < m; ++player)
				{
				players[player].location = 0.0;
				players[player].timePenalty = 0.0;
				}
			
			/* Sort the bananas */
			for (i = 0; i < b; ++i)
				{
				for (j = i+1; j < b; ++j)
					{
					if (coursePeels[i] > coursePeels[j])
						{
						temp = coursePeels[i];
						coursePeels[i] = coursePeels[j];
						coursePeels[j] = temp;
						}
					}
				}
			
			/* add an extra peel at the end of the race if no peel is at the
			 * end so that we can we can use the following for loop to find out
			 * who wins at the end */
			if (b == 0 || coursePeels[b-1] != courseLength)
				{
				coursePeels[b] = courseLength;
				b++;
				}
			
			/* go through each banana and calculate where everyone will be */
			for (peel = 0; peel < b; ++peel)
				{
				/* calculate who will get to the peel first */
				fastestPlayer = -1;
				fastestTime = 100000.0;
				for (player = 0; player < m; ++player)
					{
					/* to calculate the time for this player to reach the next
					 * peel, we take the distance to the next peel from the
					 * player's current location, divide that distance by the
					 * player's speed to get the time it would take to get
					 * there, and add the time penalty the player has in case
					 * the player is already slipping on a peel
					 */
					time = (coursePeels[peel] - players[player].location);
					time /= players[player].speed;
					time += players[player].timePenalty;
					
					/* set this to fastest time if so */
					if (time < fastestTime)
						{
						fastestPlayer = player;
						fastestTime = time;
						}
					}
				
				/* now that we have the time that the next banana will be hit,
				 * adjust the players so that they will be at the locations
				 * they should be when that banana is hit */
				for (player = 0; player < m; ++player)
					{
					/* if the time penalty for a racer is less than the time to
					 * the next banana, simply reduce the penalty.  Otherwise,
					 * subtract the penalty (even if it is 0 since 0 should not
					 * affect the value) from the time and use that time to
					 * find the extra distance that will be added to the
					 * current location.  Set the player's penalty to 0 in case
					 * a penalty existed.
					 */
					if (fastestTime <= players[player].timePenalty)
						{
						players[player].timePenalty -= fastestTime;
						}
					else
						{
						players[player].location += (fastestTime - players[player].timePenalty) * players[player].speed;
						players[player].timePenalty = 0.0;
						}
					}
				
				/* Now penalize the racer who trips on  the peel */
				players[fastestPlayer].timePenalty = 5.0;

				}
			
			/* now that the peels are done with,  our last peel caused the
			 * winner to trip at the finish line... this player is the winner!
			 * Use this data to output the winner
			 */
			printf("%s: %s is the winner!\n", courseName, players[fastestPlayer].name);
			}
		
		/* output a blank line at the end of the year */
		printf("\n");
		}
	
	/* close the file and quit */
	fclose(infile);
	return 0;
	}
