
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Battle of the Best (best)
//
// Author:        Nick Buelich
// Judge Data:    Tyler Brazill
// C Solution:    Aaron Teetor
// Java Solution: Michael Kirsche
// Verifier:      Skyler Goodell

#include <stdio.h>
#include <stdlib.h>

int main(void) {
   //open the file
   FILE* fp;
   fp = fopen("best.in", "r");
   
   char inputHolder[42];
   int numBattles;
   int i, j, k;
   
   //read in the number of battles (cases)
   fgets(inputHolder, 43, fp);
   sscanf(inputHolder,"%d",&numBattles);
   
   //loop over the battles
   for (i = 0; i < numBattles; i++) {   
      //print heading
      printf("Battle #%d! BEGIN!!!\n", i+1);
      
      //read in the names
      char personA[43];
      fgets(personA, 43, fp);
      char personB[43];
      fgets(personB, 43, fp);
      
      //remove the newline from the name
      k = 0;
      while(personA[++k] != '\n');
      personA[k] = '\0';
      k = 0;
      while(personB[++k] != '\n');
      personB[k] = '\0';
      
      //read in the first person's moves
      //removing newlines and keeping the
      //number of moves each person has
      int personAmoves;
      fgets(inputHolder, 43, fp);
      sscanf(inputHolder,"%d",&personAmoves);
      char movesListA[personAmoves][43];
      for(j = 0; j < personAmoves; j++) {
         fgets(movesListA[j], 43, fp);
         k = 0;
         while(movesListA[j][++k] != '\n');
         movesListA[j][k] = '\0';
      }

      //read in the second person's moves
      //removing newlines and keeping the
      //number of moves each person has
      int personBmoves;
      fgets(inputHolder, 43, fp);
      sscanf(inputHolder,"%d",&personBmoves);
      char movesListB[personBmoves][43];
      for(j = 0; j < personBmoves; j++) {
         fgets(movesListB[j], 43, fp);
         k = 0;
         while(movesListB[j][++k] != '\n');
         movesListB[j][k] = '\0';
      }
      
      //print out the moves until there are none left.
      //use two newline characters when a player is defeated
      //for a newline after each case and break the loop.
      j = 0;
      while (1) {
         if (j == personAmoves) {
            //first person just lost
            //use j-1 so it prints the second person's last turn
            printf("%s is defeated by %s's %s!!!\n\n", personA, personB, 
                                                       movesListB[j-1]);
            break;
         }
         printf("%s uses %s!\n", personA, movesListA[j]);
         
         if (j == personBmoves) {
            //second person just lost
            printf("%s is defeated by %s's %s!!!\n\n", personB, personA, 
                                                       movesListA[j]);
            break;
         }
         printf("%s uses %s!\n", personB, movesListB[j]);
         
         j++;
      }
   }

   //exit
   fclose(fp);
   return EXIT_SUCCESS;
}
