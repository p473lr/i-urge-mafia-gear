/*
UCF Highschool Programming Contest, 2010
C/C++ Judge solution for "Adventure
Programmed by Joshua Michalczak

Solution overview:
   We can think of this problem in terms of graph theory by
imagining the pages to be nodes and the reader's choices as
edges connecting nodes.  We can also image the ENDING pages as
connecting to an ending node (though we don't in implementation).
The problem is therefore asking for the longest path from the 
root node (page 1) to the ending node.
   A key step towads realizing the solution is to notice that
the longest path to the ending could actually be calculated
from any node in the graph; i.e.: the problem can be divided
into subproblems which can be calculated seperately.  For example,
take a look at test case 2 from the problem set:
            5
			4 5
			ENDING
			2
			3
			4
It is not immediately obvious how long the path from page 1 to
the ending is, but let's pretend the reader is on page 2.  You
should be able to tell instantly how long the story is from page
2 to the ending is since it is an ENDING page.  Did you need to
know anything about page 1 to do so?  Or any other page for that
matter?
   These concepts lead us to the solution: dynamic programming.
Once we calculate how long it is from a page to the ending, we
can store that information and save time later on by looking it
up rather than recalculating it.  This technique is known as
"memoization", and makes our solution run in O(n) with O(n) memory.
*/

#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include <string.h>

using namespace std;

//Since this is a memoized problem, we need a memo table.
//This solution only has 1 demension in it's state: the page we are
//currently on.
vector<int> memo;

//We also need to store which pages lead to what.  I'm using
//an adjacency list rather than an adjacency matrix since a page
//will only ever have two edges maximum.
vector< vector<int> > adj;

int n = 0, p = 0;

//These are some strings we need for reading and parsing the data.
char buffer[500];
char* token;

//This is our recursive function that calculates the longest
//path from a page to the ENDING.  It takes in one argument: the
//page we are currently on.
int go(int page)
{
	int best = 0, i, len;

	//If this page has no edges (i.e.: is an ENDING page), then
	//the story from this page is of length 1.
	if(adj[page].empty())
		return 1;

	//If we haven't calculated the solution for this page yet,
	//we need to go ahead and figure it out.
	if(memo[page] < 0)
	{
		//For each page that we're adjacent to...
		for(i = 0; i < adj[page].size(); ++i)
		{
			//See how long the story is from that page.
			len = 1 + go(adj[page][i]);
			//If it's longer than our best so far, remember it.
			if(len > best)
				best = len;
		}
		//We'll save the longest-length stroy from this page
		//into our memo table.
		memo[page] = best;
	}

	//Whether or not we calculated the solution or it  was
	//already calculated and saved, we need to return the answer.
	return memo[page];
}

int main()
{
	int book, page;
	
	FILE* ifp = fopen("adventure.in","r");
	
	//Read in an entire line and set n
	fgets(buffer, 500, ifp);
	sscanf(buffer, " %d", &n);

	//For each book in the input file...
	for(book = 1; book <= n; ++book)
	{
		//Read in an entire line and set p
		fgets(buffer, 500, ifp);
		sscanf(buffer, " %d", &p);

		//Clear out the memo table of any previous work
		//it might contain...
		memo.clear();
		//and initialize all the values to -1.  -1 is special
		//to us because we know the story length will never be a
		//negative number.  So if we see -1, it must mean we haven't
		//calculated the length for that page yet.
		memo.resize(p, -1);

		//Also clear out the adjacency list.
		adj.clear();
		adj.resize(p, vector<int>());

		//For each page in this book...
		for(page = 0; page < p; ++page)
		{
			//Read in the entire line
			fgets(buffer, 500, ifp);
			//If it's not an ENDING page...
			if(!strncmp(buffer, "ENDING", 6) == 0)
			{
				//tokenize the string by splitting it on
				//spaces (if there are any).
				token = strtok(buffer, " \n");

				//As long as we haven't run out of tokens...
				while(token != NULL)
				{
					//Add this token (minus 1, since arrays
					//are 0 based) as an adjacent member of
					//this page.
					adj[page].push_back(atoi(token) - 1);
					token = strtok(NULL, " \n");
				}
			} 
		}

		//After reading all the input, we can caluclate and output the answer.
		printf("Book #%d: The longest story is %d pages.\n", book, go(0));
	}

	return 0;
}
