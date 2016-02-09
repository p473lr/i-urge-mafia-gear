
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Crafty Trolling (craft)
//
// Author:        Skyler Goodell
// Judge Data:    Nick Buelich
// C Solution:    Antony Stabile
// Java Solution: Andrew Harn
// Verifier:      Aaron Teetor

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Scanner;


public class craft {

   //Delta array to direct where the lava goes
   int[][] delta = new int[][] {
         {0, 0, 1},
         {0, 1, 0},
         {0, 0 , -1},
         {0, -1, 0}
   };

   //Constructor - Holds the solution
   public craft() {

      //Open up the file
      Scanner sc = null;
      try {
         sc = new Scanner(new File("craft.in"));
      } catch (FileNotFoundException e) {
      }

      //Get the number of maps
      int numberOfMaps = sc.nextInt();

      //Process each map
      for (int mapNumber = 1; mapNumber <= numberOfMaps; mapNumber++) {

         //Get the dimensions of the map
         int l = sc.nextInt(),
             w = sc.nextInt(),
             h = sc.nextInt();

         //This stores our answer
         int lava = 0;

         //Queue of lava block coordinates
         ArrayDeque<int[]> q = new ArrayDeque<int[]>();

         //Create the map and visited array
         char [][][] map = new char[h][l][];
         boolean [][][] visited = new boolean[h][l][w];

         //Read in the map
         for (int i = 0; i < h; i++) {

            for (int j = 0; j < l; j++) {
               map[i][j] = sc.next().toCharArray();

               //Find the lava block and add it to our queue of lava
               for (int k = 0; k < w; k++) {

                  if (map[i][j][k] == '*') {

                     //And add them to the queue
                     lava++;
                     visited[i][j][k] = true;
                     q.add(new int[]{i, j, k});
                  }   
               }
            }
         }

         //Run a Breadth First Search (BFS) to see where the lava will spread
         while (!q.isEmpty()) {

            //Get the next block and its coordinates
            int[] nextBlock = q.poll();

            int x = nextBlock[0],
                y = nextBlock[1],
                z = nextBlock[2];

            //See if we can move down
            if (x < h - 1 && map[x + 1][y][z] == '.') {

               //And we haven't visited that space before
               if (!visited[x + 1][y][z]) {
                  //If so, move down and add the new block to the queue
                  lava++;
                  visited[x + 1][y][z] = true;
                  q.add(new int[]{x + 1, y, z});
               }
            }
            //Otherwise, spread horizontally
            else {
               for (int i = 0; i < 4; i++) {

                  int newX = x + delta[i][0],
                      newY = y + delta[i][1],
                      newZ = z + delta[i][2];

                  //Make sure it's in the board
                  if (newY >= 0 && newY < l && newZ >= 0 && newZ < w) {
                     //Then see if it's empty
                     if (map[newX][newY][newZ] == '.' && 
                         !visited[newX][newY][newZ] ) {
                        //If so, move down and add the new block to the queue
                        lava++;
                        visited[newX][newY][newZ] = true;
                        q.add(new int[]{newX, newY, newZ});
                     }
                  }
               }
            }
         }

         //Lastly, print out the number of lava blocks
         System.out.println("Map #" + mapNumber + ": " + lava);
      }

      // Close file
      sc.close();
   }

   //Main method
   public static void main(String args[]) {
      //Solution written in the constructor
      new craft();
   }
}
