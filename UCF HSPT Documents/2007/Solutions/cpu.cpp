/*
UCF's 2007 High School Programming Contest
CPU
Solution Prepared by: Nick Beato

This problem is a well-known computer science problem called a minimal
spanning tree (MST from now on...).  The MST problem states: given a
collection of nodes and the cost of connecting 2 nodes, find the minimal
cost to connect all of the nodes.

Without boring you with mathematics, let's makes some observations.

1) To connect all n subsystems, you need exactly n-1 wires
Why? Think about it... if you have 2 subsystems, then you need 1 wire.
If you have 3 subsystems, you just connect 2 of them to a central point.
Try drawing 4 on paper and convince yourself that adding 4 wires will
add too much wiring and adding 2 is not enough.

2) You want to differentiate "connected" subsystems from the ones
that are still sitting around.  So we'll call the ones that are connected
already "the computer chip". We need to connect the remaining subsystems to
this chip, one at a time.

3) Every subsystem must exist in the final answer, so you can pick
any one of them as a starting point to arrive at an optimal answer.

4) Once you connect a subsystem to "the chip", it is part of the chip
and you can ignore it.

Given this, we are going to connect the subsystems.  We will start off
by arbitrarily selecting a subsystem as "the chip".  Once we have that, we
will add one subsystem at a time to "the chip" until we run out of subsystems
to add.  Notice we will have n-1 subsystems to connect after picking the
first one.

For the interested reader, this solution is known as "Prim's Algorithm".
Prim's algorithm focuses on connecting 1 node at a time to a growing
beast, always using the cheapest connection.

Another popular way to solve this problem is "Kruskal's Algorithm".  It is the
same idea, except Kruskal decided to go through every wire, cheapest to most
expensive, trying to add it to the system.  If adding it will cause a redundancy,
the wire is not used.  That would also work, but is slightly more complicated
to code.

*/

#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

int main() {
	ifstream in("cpu.in");

	// n from "the input"
	int numberOfDesigns;
	in >> numberOfDesigns;

	// process each design
	for(int design = 1; design <= numberOfDesigns; design++) {
		int numberOfSubsystems;
		in >> numberOfSubsystems;

		// stores the input for this design
		vector <vector <int> > connections(numberOfSubsystems, vector <int> (numberOfSubsystems));
		for(int i = 0; i < numberOfSubsystems; i++)
			for(int j = 0; j < numberOfSubsystems; j++)
				in >> connections[i][j];

		// indicates the amount of wire used to solve this design
		int wireUsed = 0;

		// indicates whether or not the subsystem is connected to the "main chip"
		vector <bool> subsystemConnected(numberOfSubsystems, false);

		// arbitrarily select "the chip"
		subsystemConnected[0] = true;

		// use n-1 wires to connect the remaining n-1 subsystems
		for(int count = 0; count < numberOfSubsystems - 1; count++) {
			int besti, bestj;
			besti = bestj = -1;

			// find the cheapest wire to add
			// make sure that the wire will actually connect
			// something "on the chip" to something "off the chip"
			for(int i = 0; i < numberOfSubsystems; i++)
				for(int j = 0; j < numberOfSubsystems; j++)
					if(connections[i][j] > 0 && subsystemConnected[i] && !subsystemConnected[j])
						if(besti == -1 || connections[i][j] < connections[besti][bestj]) {
							besti = i;
							bestj = j;
						}

			// mark that the next subsystem has been added to the "chip"
			// and take note of the amount of wire used to do so
			subsystemConnected[bestj] = true;
			wireUsed += connections[besti][bestj];
		}
		
		// we finished connecting everything. output the amount of wire used
		cout << "Design " << design << ": " << wireUsed << " micrometers" << endl;
	}

	in.close();
	return 0;
}
