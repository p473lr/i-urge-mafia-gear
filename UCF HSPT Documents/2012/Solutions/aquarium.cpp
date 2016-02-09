#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>

using namespace std;

// Datastructure to hold information about each fish
struct Fish {
	// Fish Name
	string name;
	// Fish Sequence String
	string sequence;
	// Fish Position and initial size
	int x,y,s;
	// How many fish this one has eaten
	int eaten;
	// How many have I eaten this round (in case some one eats me!)
	int lastEaten;
	// What its last move was (used for tie-breaking)
	int lastMove;
	// Is this fish alive?
	bool alive;
};

int main(int argc, char** argv)
{
	ifstream fin;
	fin.open("aquarium.in");

	// Read in the number of aquariums
	int A;
	fin >> A;
	for (int a = 1; a <= A; ++a)
	{
		// Read in all the parameters about this aquarium
		int R,C,N,T;
		fin >> R >> C >> N >> T;
		
		// Create the aquarium
		// NOTE* R -> x C -> y
		char grid[R][C];
		for (int r = 0; r < R; ++r)
			fin >> grid[r];
		
		// Create the array of fish
		Fish fish[N];
		for (int n = 0; n < N; ++n)
		{ 
			fin >> fish[n].name >> fish[n].s >> fish[n].y >> fish[n].x >> fish[n].sequence; 
			--fish[n].x; // zero-index is the best index
			--fish[n].y;
			// default values
			fish[n].alive = true; 
			fish[n].eaten = 0; 
			fish[n].lastEaten = 0;
		}
		
		// Simulate time steps
		for (int t = 0; t < T; ++t)
		{
			// Iterate through each fish
			for (int n = 0; n < N; ++n)
			{
				// This is a dead fish. We don't care about dead fish here.
				if (!fish[n].alive) continue;
				
				// Update my size with however many I ate last round
				fish[n].eaten += fish[n].lastEaten;
				fish[n].lastEaten = 0;
				
				// Move the alive fish
				char move = fish[n].sequence[t%fish[n].sequence.size()];
				if (move == 'U')
				{
					if (fish[n].y > 0 && grid[fish[n].y-1][fish[n].x] != 'X') { fish[n].y--; fish[n].lastMove = 1; }
					else fish[n].lastMove = 0; // stationary
				}
				else if (move == 'D')
				{
					if (fish[n].y < R-1 && grid[fish[n].y+1][fish[n].x] != 'X') { fish[n].y++; fish[n].lastMove = 4; }
					else fish[n].lastMove = 0; // stationary
				}
				else if (move == 'L')
				{
					if (fish[n].x > 0 && grid[fish[n].y][fish[n].x-1] != 'X') { fish[n].x--; fish[n].lastMove = 2; }
					else fish[n].lastMove = 0; // stationary
				}
				else if (move == 'R')
				{
					if (fish[n].x < C-1 && grid[fish[n].y][fish[n].x+1] != 'X') { fish[n].x++; fish[n].lastMove = 3; }
					else fish[n].lastMove =  0; // stationary
				}
				
				// Check for collisions with any other alive fish that has already moved
				for (int k = 0; k < n; ++k)
				{
					if (!fish[k].alive) continue;
					
					// It's Eating time! Two fish ended in the same space
					if (fish[k].x == fish[n].x && fish[k].y == fish[n].y)
					{
						// K is the larger fish
						if (fish[k].s + fish[k].eaten > fish[n].s + fish[n].eaten)
						{
							fish[k].lastEaten += fish[n].lastEaten+1;
							fish[n].lastEaten = 0;
							fish[n].alive = false;
						}
						// N is the larger fish
						else if (fish[k].s + fish[k].eaten < fish[n].s + fish[n].eaten)
						{
							fish[n].lastEaten += fish[k].lastEaten+1;
							fish[k].lastEaten = 0;
							fish[k].alive = false;
						}
						else // tiebreak
						{
							// K is the larger fish
							if (fish[k].lastMove > fish[n].lastMove)
							{
								fish[k].lastEaten += fish[n].lastEaten+1;
								fish[n].lastEaten = 0;
								fish[n].alive = false;
							}
							// N is the larger fish
							else
							{
								fish[n].lastEaten += fish[k].lastEaten+1;
								fish[k].lastEaten = 0;
								fish[k].alive = false;
							}
						}
					}
				}
			}
		}
		
		// Simulation complete
		// Print out the results
		cout << "Aquarium #" << a << ":" << endl;
		for (int n = 0; n < N; ++n)
		{
			// Fish number
			cout << (n+1) << ". ";
			
			// Is it dead?
			if (!fish[n].alive) cout << "(Deceased) ";
			
			// How big is this fish?
			for (int big = 0; big < fish[n].eaten + fish[n].lastEaten; ++big)
				cout << "Big ";
				
			// What is its name?
			cout << fish[n].name << endl;
		}
		cout << endl;
	}
	
	return 0;
}
