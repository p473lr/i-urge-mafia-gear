/*  UCF High School Programming Tournament 2006
 *  "Square in the..." C solution
 *
 *  Notes: The % (modulus) operator is used to implement moving from one
 *         player index to the "next" with looping when the end of the
 *         list is reached.  The useful properties of % for our purposes
 *         are (assuming x, N both integers >= 0):
 *         (x % N) == x if x <  N, and
 *         (x % N) == 0 if x == N
 */
#include <stdio.h>

/* Player information structure.  For each player, we track
 * the name, current HP (there's no reason to keep track of
 * what the starting HP was, so we just subtract from here
 * when a player is attacked), and "strength" or the HP
 * damage a player inflicts when striking.
 */
struct Player
{
    char name[512];
    int hurtPoints;
    int strength;
};

/* "Plays" a single game with information read from the "in" file. */
void playGame(FILE *in)
{
    struct Player players[24]; // per-player information
    
    int numPlayers, numPlayersAlive; // global game info
    int kefka, kefkaTarget=0;        // which is Kefka, and who will he strike?
    int curPlayer, target;           // per-round values

    /****** Read the player info ******/
    fscanf(in, " %d", &numPlayers);
    numPlayersAlive = numPlayers; // Everyone starts out alive (positive HP)
    for (curPlayer = 0; curPlayer < numPlayers; curPlayer++)
    {
	// Read a player's information
	fscanf(in, " %s", players[curPlayer].name);
	fscanf(in, " %d", &players[curPlayer].hurtPoints);
	fscanf(in, " %d", &players[curPlayer].strength);

	// Check whether we just read Kefka's info
	if (strcmp(players[curPlayer].name, "Kefka") == 0)
	    // If so, mark this player as being Kefka
	    kefka = curPlayer;
    }
    
    /****** Run the game ******/
    // keep going until either Kekfa is left alone, or dies
    while (numPlayersAlive > 1 && players[kefka].hurtPoints > 0)
    {
	// Update Kefka's target as needed
	while (kefkaTarget == kefka || players[kefkaTarget].hurtPoints <= 0)
	    kefkaTarget++;
		
	// Move to the next player in the list, wrapping at the end
	curPlayer = (curPlayer + 1) % numPlayers;

	if (players[curPlayer].hurtPoints <= 0)
	    continue; // this player isn't in the game any more, skip
	
	// decide who to strike
	if (curPlayer == kefka)
	    // Kefka strikes the first live opponent
	    target = kefkaTarget;
	else
	    // everyone else strikes Kefka
	    target = kefka;
	
	// Calculate the results of the strike
	players[target].hurtPoints -= players[curPlayer].strength;
	printf("%s struck %s for %d points of hurt.\n",
		players[curPlayer].name,
		players[target].name,
		players[curPlayer].strength);

	if (players[target].hurtPoints <= 0) // the target is removed
	{
	    printf("%s has been removed from the game.\n",
		    players[target].name);
	    numPlayersAlive--;
	}
    }

    // Game is over, check the result
    if (players[kefka].hurtPoints > 0)
	printf("The world will be cast into perpetual ruin.\n\n");
    else
	printf("The world has been saved!\n\n");
}

/*  Main Program
 *
 *  Reads in the number of games, and then for each game:
 *  1. Prints the "Game #x:" header
 *  2. Calls playGame to handle the game
 */
int main(int argc, char *argv[])
{
    FILE *in = fopen("square.in", "rt"); // input file
    int numGames, curGame;               // game tracking data
    fscanf(in, " %d", &numGames);
    for (curGame = 1; curGame <= numGames; curGame++) // for each game...
    {
	printf("Game #%d:\n", curGame); // print a header
	playGame(in);                   // and play!
    }
    return 0;
}

