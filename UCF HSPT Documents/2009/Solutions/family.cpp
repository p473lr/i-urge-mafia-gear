
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


// List of names
int numNames;
char names[300][300];

// Matrix indicating connections between people
bool connections[300][300];


// Flags indicating whether or not we've visited this person in our search
bool visited[300];


// This function searches the family tree from the starting person to the
// target person, using what's known as a Depth-First Search (or DFS)
bool dfs(int start, int target)
{
   int i;

   // If this node is adjacent to the target node, we're done
   if (start == target)
      return true;

   // Now, recursively visit any nodes to which start node is connected
   for (i = 0; i < numNames; i++)
   {
      if (connections[start][i] && !visited[i])
      {
         // Mark the starting node as visited
         visited[i] = true;

         // If this path gets us to our target, we're done, otherwise keep
         // looking
         if (dfs(i, target))
            return true;
      }
   }

   // We didn't find the node we're looking for, so return false
   return false;
}


int getOrAddName(char * name)
{
   int i;

   // Search the list for the given name
   for (i = 0; i < numNames; i++)
   {
      // If this name matches, return its index
      if (strcmp(names[i], name) == 0)
         return i;
   }

   // We didn't find the name we're looking for, so add it to the list
   strcpy(names[numNames], name);
   numNames++;

   // Return the index of name at the end of the list (the one we just added)
   return numNames-1;
}


int main(void)
{
   FILE *   fp;
   char     line[256];
   char *   name;
   int      numConnections;
   int      familyNum;
   int      i;
   int      parent1, parent2, child;
   int      start, target;

   // Open the input file
   fp = fopen("family.in", "r");

   // Start the family count at 1
   familyNum = 1;

   // Read in the number of connections
   fgets(line, sizeof(line), fp);
   numConnections = atoi(line);
   while (numConnections > 0)
   {
      // Initialize our data structures that hold the family tree information
      numNames = 0;
      memset(names, 0, sizeof(names));
      memset(connections, 0, sizeof(connections));

      // Read in each connection
      for (i = 0; i < numConnections; i++)
      {
         // Read the entire line
         fgets(line, sizeof(line), fp);

         // Parse the first name on the line and get it from (or add it to)
         // the list
         name = strtok(line, " \n\r");
         parent1 = getOrAddName(name);

         // Parse the second name on the line and get it from (or add it to)
         // the list
         name = strtok(NULL, " \n\r");
         parent2 = getOrAddName(name);

         // Parse the third name on the line and... you get the idea
         name = strtok(NULL, " \n\r");
         child = getOrAddName(name);

         // Enter the connections in our connection matrix
         connections[parent1][child] = true;
         connections[child][parent1] = true;
         connections[parent2][child] = true;
         connections[child][parent2] = true;
      }

      // Read the next line and get the two people we need to check
      fgets(line, sizeof(line), fp);
      name = strtok(line, " \n\r");
      start = getOrAddName(name);
      name = strtok(NULL, " \n\r");
      target = getOrAddName(name);

      // Now, run the DFS to see if the two people are related
      memset(visited, 0, sizeof(visited));
      if (dfs(start, target))
         printf("Family #%d: Related.\n", familyNum);
      else
         printf("Family #%d: Not related.\n", familyNum);
      
      // Read in the number of connections in the next family tree
      familyNum++;
      fgets(line, sizeof(line), fp);
      numConnections = atoi(line);
   }

   // Close the input file
   fclose(fp);

   return 0;
}
