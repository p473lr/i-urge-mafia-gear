
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fire {
   fire() throws FileNotFoundException {
      // Open input file
      Scanner in = new Scanner(new File("fire.in"));

      // Loop over the cases
      int tests = in.nextInt();
      for(int t = 0; t < tests; t++) {
         // Read in the input for this test case
         h = in.nextInt();
         w = in.nextInt();
         grid = new char[h][];
         for (int i = 0; i < h; i++)
            grid[i] = in.next().toCharArray();
         
         // Search for the dragons
         for (int y = 0; y<h; y++)
            for (int x = 0; x<w; x++) {
               // Is this tile a dragon?
               int dir = isDragon(grid[y][x]);

               // Not a dragon so skip to next
               if (dir == -1)
                  continue;

               // Found one, time to fire!
               
               // Fire the middle fire
               fire(x+dx[dir], y+dy[dir], dir);
               
               // Then, fire the secondary fires
               for (int d = dir+1; d<=dir+3; d+=2) {
                  int hd = d%4;
                  
                  // Calculate the initial start point of the secondary fire
                  int fx = x+dx[dir]+dx[hd];
                  int fy = y+dy[dir]+dy[hd];
                  
                  // Move along the path until we hit something
                  while (true) {
                     // See if we hit the edge of the map
                     if (fx < 0 || fy < 0 || fx >= w || fy >= h)
                        break; 

                     // See if we hit a block or another dragon
                     if (grid[fy][fx] != 'F' && grid[fy][fx] != '.')
                        break; 
                     
                     // We're good, so fire a primary fire
                     fire(fx, fy, dir);
                     
                     // Then, move the secondary fire
                     fx += dx[dir]+dx[hd];
                     fy += dy[dir]+dy[hd];
                  }
               }
            }
         
         // Output the result
         System.out.println("Map #" + (t+1) + ":");
         for (int i = 0; i < h; i++)
            System.out.println(new String(grid[i]));
         System.out.println();
      }

      // Close input file
      in.close();
   }
   
   // Displacement arrays for translation in the cardinal directions
   int[] dx = {0,1,0,-1};
   int[] dy = {1,0,-1,0};
   
   // a few useful globals (of the map)
   int w, h;
   char[][] grid;
   
   // Shoots flame in one direction placing down fire until it 
   // reaches a block or another dragon
   void fire(int x, int y, int d) {
      while (true) {
         // See if we hit the edge of the map
         if (x < 0 || y < 0 || x >= w || y >= h)
            break; 

         // See if we hit a dragon or a block
         if(grid[y][x] != 'F' && grid[y][x] != '.')
            break; 

         // Mark this as fire and move to next location to check
         grid[y][x] = 'F';
         x += dx[d];
         y += dy[d];
      }
   }
   
   // Returns the direction index of dragon represented by c
   // if c isn't a dragon -1 is returned
   int isDragon(char c) {
      switch(c){
         case 'V':
            return 0;
         case '>':
            return 1;
         case '^':
            return 2;
         case '<':
            return 3;
      }
      return -1;
   }
   
   // yay main!!
   public static void main(String[] args) throws FileNotFoundException {
      new fire();
   }
}
