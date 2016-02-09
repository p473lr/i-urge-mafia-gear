
#include <stdio.h>
#include <string.h>

char people[64][128];
bool connectMatrix[64][64];
int  numPeople;

bool used[64];


int getNodeByName(char *name)
{
   int i;

   // Check all the names
   for (i = 0; i < numPeople; i++)
   {
      // If the given name matches the i'th person, return the index
      if (strcmp(people[i], name) == 0)
         return i;
   }

   // Didn't find the given person
   return -1;
}


int getDegree(int node)
{
   int degree;
   int i;

   // Start with zero degree
   degree = 0;

   // Iterate over the nodes in the network
   for (i = 0; i < numPeople; i++)
   {
      // If this node is connected to us (and it's not us), add one to the
      // degree
      if ((i != node) && (connectMatrix[node][i]))
         degree++;
   }

   // Return the degree of the node
   return degree;
}


int dfsCount(int node)
{
   int count;
   int i;

   // Start with a count of 1 (this counts this node)
   count = 1;

   // Mark this node as used, so we don't count it again
   used[node] = true;

   // Iterate over and search our neighbors
   for (i = 0; i < numPeople; i++)
   {
      // If this node is connected to node i, and we haven't already searched
      // node i, search it now
      if ((connectMatrix[node][i]) && (!used[i]))
      {
         // Continue the depth-first search on node i and add the node count
         // from that part of the search to our total count
         count += dfsCount(i);
      }
   }

   // Return the total count
   return count;
}


int componentCount(int node)
{
   int count;

   // Clear the used array
   memset(used, 0, sizeof(used));

   // Do a depth-first search starting at this node, and count the number
   // of nodes visited by the search
   return dfsCount(node);
}


int getCoolness(char *name)
{
   int node;
   int degree;
   int nodeCount;

   // Get the starting node in the network
   node = getNodeByName(name);

   // If this person isn't in the network, their coolness is zero
   if (node < 0)
      return 0;

   // Get the "degree" of this node, which is the number of people
   // directly connected to it.  This is the first part of the coolness
   degree = getDegree(node);

   // Get the number of nodes directly or indirectly connected to this node
   nodeCount = componentCount(node);

   // Return the "coolness", which is the degree plus the node count, minus
   // one (we don't count ourselves in the coolness factor)
   return degree + nodeCount - 1;
}


int main(void)
{
   FILE  *fp;
   char  line[256];
   char  *token;
   int   numNetworks;
   int   net;
   int   i;
   int   numConnections;
   char  name1[128];
   char  name2[128];
   int   node1, node2;
   int   yourCoolness;
   int   numQueries;
   char  *nl;
   int   coolness;

   // Open the input file
   fp = fopen("friends.in", "r");

   // Read the number of networks in the file
   fgets(line, sizeof(line), fp);
   sscanf(line, "%d", &numNetworks);

   // Process the individual networks
   for (net = 0; net < numNetworks; net++)
   {
      // Clear the names and connection matrix
      memset(people, 0, sizeof(people));
      numPeople = 0;
      memset(connectMatrix, 0, sizeof(connectMatrix));

      // Read the number of people on the network
      fgets(line, sizeof(line), fp);
      sscanf(line, "%d", &numPeople);

      // Read in the names of the people
      fgets(line, sizeof(line), fp);
      token = strtok(line, " \r\n");
      for (i = 0; i < numPeople; i++)
      {
         // Copy the person's name
         strcpy(people[i], token);

         // Get the next person's name
         token = strtok(NULL, " \r\n");
      }

      // Get the number of connections in the network
      fgets(line, sizeof(line), fp);
      sscanf(line, "%d", &numConnections);

      // Process the connections
      for (i = 0; i < numConnections; i++)
      {
         // Read the next line
         fgets(line, sizeof(line), fp);

         // Parse the two names that describe the connection
         token = strtok(line, " \r\n");
         strcpy(name1, token);
         token = strtok(NULL, " \r\n");
         strcpy(name2, token);

         // Get the two nodes from the names
         node1 = getNodeByName(name1);
         node2 = getNodeByName(name2);

         // Add the connection in both directions to the connection matrix
         connectMatrix[node1][node2] = true;
         connectMatrix[node2][node1] = true;
      }

      // Compute your coolness first
      yourCoolness = getCoolness("You");

      // Print the output header
      printf("Social Network %d:\n", net+1);

      // Process the queries
      fgets(line, sizeof(line), fp);
      sscanf(line, "%d", &numQueries);
      for (i = 0; i < numQueries; i++)
      {
         // Get the name and trim off the trailing '\n' character
         fgets(line, sizeof(line), fp);
         token = strtok(line, " \n\r");

         // Get this person's coolness
         coolness = getCoolness(token);

         // Compute the difference and output
         printf("   %s: Difference of %d point(s).\n", token,
            yourCoolness - coolness);
      }

      // Leave a blank line, as instructed
      printf("\n");
   }

   // Close the input file
   fclose(fp);
}

