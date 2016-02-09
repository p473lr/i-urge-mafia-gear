#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <queue>

#define LAVA 0
#define SAFE 1
#define VISITED -1

using namespace std;

class point
{
	public:
		int x;
		int y;
		int steps;
		point(int x, int y, int steps)
		{
			this->x = x;
			this->y = y;
			this->steps = steps;
		}
};

char row[52];
int width;
int height;
int num_steps;
int can_reach;
int map[50][50];
point* start;
queue<point*> q;

int main()
{
	int T;
	ifstream myfile ("lava.in");
	myfile >> T;
	for(int t = 1; t <= T; ++t)
	{
		can_reach = 0;
		
		//The first step is to read in the input data
		myfile >> height;
		myfile >> width;
		myfile >> num_steps;
		for(int y = 0; y < height; ++y)
		{
			//we load in the entire row of characters
			myfile >> row;
			for(int x = 0; x < width; ++x)
			{
				//and then determine which type of space it is
				switch(row[x])
				{
				case '.':
					map[x][y] = SAFE;
					break;
				case 'L':
					map[x][y] = LAVA;
					break;
				case 'S':
					//if it's the starting space, we make sure to remember it
					map[x][y] = VISITED;
					start = new point(x, y, 0);
					break;
				}
			}
		}
		
		//now that we have the map loaded, we can perform a Breadth-First-Search (BFS)
		//the BFS starts at the starting location 'S' and radiates outward n times
		//we'll keep track of where we're currently examining using the queue
		q.push(start);
		while(!q.empty())
		{
			//at each step, we deque the current location and move around from there
			point* current = q.front();
			q.pop();
			can_reach++;
			if(current->steps < num_steps)
			{
				if(current->y + 1 < height && map[current->x][current->y + 1] == SAFE)
				{
					q.push(new point(current->x, current->y + 1, current->steps + 1));
					map[current->x][current->y + 1] = VISITED;
				}
				if(current->y - 1 >= 0 && map[current->x][current->y - 1] == SAFE)
				{
					q.push(new point(current->x, current->y - 1, current->steps + 1));
					map[current->x][current->y - 1] = VISITED;
				}
				if(current->x + 1 < width && map[current->x + 1][current->y] == SAFE)
				{
					q.push(new point(current->x + 1, current->y, current->steps + 1));
					map[current->x + 1][current->y] = VISITED;
				}
				if(current->x - 1 >= 0 && map[current->x - 1][current->y] == SAFE)
				{
					q.push(new point(current->x - 1, current->y, current->steps + 1));
					map[current->x - 1][current->y] = VISITED;
				}
			}
		}
		
		//lastly, we output the result
		if(can_reach == 1)
			cout << "Room #" << t << ": They can reach 1 piece of furniture." << endl << endl;
		else
			cout << "Room #" << t << ": They can reach " << can_reach << " pieces of furniture." << endl << endl;
	}
}
