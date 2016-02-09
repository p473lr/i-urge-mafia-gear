#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <queue>

using namespace std;

/*
The approach to this problem is simple when you break down the problem
into a simple operation.  In this case, we need to check that a piece of
ice (1) will be fitting into an empty slot (0).  To do this quickly, we'll
first convert the rows of trays into binary numbers, which allows us to
use the XOR operation.  If the XOR operation results in all the bits turned
on, then the tray can fit into the other.  Anything else means the trays
don't fit into each other.
*/

class Tray
{
	public:
		int* top;
		int* bottom;
		int size;
		int index;
		Tray(int size, int index)
		{
			top = (int*)malloc(sizeof(int) * size);
			bottom = (int*)malloc(sizeof(int) * size);
			this->size = size;
			this->index = index;
		}
		~Tray()
		{
			free(top);
			free(bottom);
		}
		//checks if the "other" tray will fit on top of this one
		int can_stack(Tray* other, int all)
		{
			for(int i = 0; i < size; ++i)
			{
				// The ^ symbol means xor in c++, this line checks
				// if the top can fit into the bottom
				if((top[i] ^ other->bottom[i]) != all)
					return 0;
			}
			return 1;
		}
};

int num_trays;
int height;
int width;
queue<Tray*> trays;
queue<Tray*> answer;

int main()
{
	fstream fio("tray.in", fstream::in);

   int temp;
	int count;
	int T;
	fio >> T;
	for(int t = 1; t <= T; ++t)
	{
		fio >> num_trays;
		fio >> height;
		fio >> width;
		
		//we have to read in each tray
		for(int z = 0; z < num_trays; ++z)
		{
			//first, we read in the top...
			Tray* tr = new Tray(height, z + 1);
			for(int y = 0; y < height; ++y)
			{
				int row = 0;
				int pow = 1;
				for(int x = 0; x < width; ++x)
				{
					fio >> temp;
					if(temp == 1)
						row += pow;
					pow = pow << 1;
				}
				tr->top[y] = row;
			}
			
			//then we read in the bottom
			count = 0;
			for(int y = height - 1; y >= 0; --y)
			{
				int row = 0;
				int pow = 1;
				for(int x = 0; x < width; ++x)
				{
					fio >> temp;
					if(temp == 1)
					{
						row += pow;
						count++;	//count checks if the tray has no ice on the bottom
					}
					pow = pow << 1;
				}
				tr->bottom[y] = row;
			}
			//if it's the bottom tray, we add it to the solution
			//otherwise, we add it to the unused ones
			if(count == 0)
				answer.push(tr);
			else
				trays.push(tr);
		}
		
		
		//next, we loop until we've used all the trays
		while(trays.size() > 0)
		{
			Tray* current = trays.front();
			trays.pop();
			
			if(answer.back()->can_stack(current, (1 << width) - 1))
				answer.push(current);
			else
				trays.push(current);
		}
		
		//once we have stacked all the trays, we print out the solution
		cout << "Stack " << t << ":";
		while(answer.size() > 0)
		{
			Tray* current = answer.front();
			answer.pop();
			cout << " " << current->index;
		}
		cout << endl;
	}

   // Close the file
   fio.close();
}
