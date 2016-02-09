
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: You're Too Slow! (slow)
//
// Author:        Aaron Teetor
// Judge Data:    Antony Stabile
// C Solution:    Glenn Martin
// Java Solution: Danny Wasserman
// Verifier:      Patrick Fenelon

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class slow {
  public static void main(String[] args) throws IOException {
    // Open input file.
    Scanner in = new Scanner(new File("slow.in"));

    // Read in number of simulations and loop over them.
    int simulations = in.nextInt();
    for(int simOn = 1; simOn <= simulations; simOn++) {
      // Read number of episodes left in the season.
      int numEpisodes = in.nextInt();
      // Read number of rooms in this season.
      int rooms = in.nextInt();
      
      // Create space for rooms and enemies.
      Point[] roomLocations = new Point[rooms];
      int[] numEnemies = new int[rooms];

 
      // Read in each room.
      for(int i = 0; i < rooms; i++) {
        roomLocations[i] = new Point(in.nextInt(), in.nextInt());
        numEnemies[i] = in.nextInt();
      }
      
      // Preprocess the time it takes to get from 1 room to another.
      // Time is stored in minutes.
      double[] timeToMove = new double[rooms];
      for(int i = 0; i < rooms-1; i++) {
        // Find the distance between the two rooms.
        double distance = roomLocations[i].distance(roomLocations[i+1]);
        
        // Compute time it takes to get to the next room (handle sonic 
        // accelerating).
        double time = 0;
        if(distance <= 10) {
          // entire time sonic moves at 10 km/min.
          time = distance / 10;
        } else {
          time = 1;
          distance -= 10;
          // After the first 10 KM he moves at 20 km/min.
          time += distance / 20;
        }

        // Store the final time.
        timeToMove[i] = time;
      }
      
      // The time to move from the last room to a 'final' room is zero.
      // this makes it easy to detect if Sonic finished the labyrinths.
      
      // Start in first room.
      int roomOn = 0;

      // Process each episode.
      for(int epOn = 1; epOn <= numEpisodes; epOn++) {
        // Figure out length of this episode.
        double time = 22; 
        if(epOn == numEpisodes) {
          // Season finale has 30 minutes.
          time = 30; 
        }

        // Flag for seeing if something was processed during the 
        // next loop for the episode.
        boolean changed = true;

        // Loop while something changes
        while(changed) {
          // Default that nothing will change this loop
          changed=false;

          // While there are enemies that need to be defeated, defeat one!
          while(numEnemies[roomOn] > 0) {
            // If 3 minutes are not available, there is not enough time.
            if(time < 3) break;

            // Defeat the enemy!
            changed=true; // enemy defeated, sonic moved on!
            time-=3; // it takes 3 minutes to defeat an enemy.
            numEnemies[roomOn]--; // an enemy was defeated, update.
          }
          
          // if there are no more enemies, time to move on.
          if(numEnemies[roomOn]==0) {
            // For this part, we can consider ourselves to still be in 
            // the current room and there is just a timer running that 
            // is the time to the next room.
            if(timeToMove[roomOn] <= time) {
              changed=true; //sonic changed rooms!
              time-=timeToMove[roomOn]; // took time to get there.
              roomOn++; // move rooms!
            } else {
              // If he doesn't change rooms...
              timeToMove[roomOn]-=time; // use the timer for running.
              time=0;
            }
          }

          // If sonic finished, break out.
          if(roomOn==rooms) break;
        }

        // If sonic finished, break out.
        if(roomOn==rooms) break;
      }
      
      // If sonic made it through all the rooms (numbered from 0...(rooms-1)) 
      // then he should be in 'rooms' since it takes no time to move to 
      // the 'final' room.
      if(roomOn == rooms) {
        System.out.printf("Simulation #%d: Abort fortress!\n",simOn);
      } else {
        System.out.printf("Simulation #%d: You're too slow!\n",simOn);
      }
    }

    // Close scanner
    in.close();
  }
  

  // Point class used to represent where a room is.
  static class Point {
    long x, y;
    public Point(long x, long y) {
      this.x = x;
      this.y = y;
    }
    
    // return the distance between two points.
    // a^2 + b^2 = c^2 ==> c = Sqrt(a^2 + b^2).
    // a is the x distance and b is the y distance.
    public double distance(Point o) {
      return Math.sqrt( (x-o.x)*(x-o.x) + (y-o.y)*(y-o.y) );
    }
    
    public String toString() {
      return String.format("(%d, %d)",x,y);
    }
  }
}
