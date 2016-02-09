import java.util.*;
import java.io.*;


public class track 
{
   public static final double PI = 3.141592653589793;

   public static void main(String[] args) throws IOException
   {
      // Open file
      Scanner input = new Scanner(new File("track.in"));

      // Get number of tracks
      int n = input.nextInt();

      // Loop over the number of tracks
      for(int i = 0; i<n; i++)
      {
         // Read the track specification
         int length = input.nextInt();
         int r1 = input.nextInt();
         int r2 = input.nextInt();
         int mackLaps = input.nextInt();
         int zackLaps = input.nextInt();

         // Figure out the distance that Mack and Zack each ran
         double mdist = mackLaps*(2*length +2*PI*r1);
         double zdist = zackLaps*(2*length +2*PI*r2);

         // Output th eheader
         System.out.print("Track #" + (i+1) + ": ");

         // Output the answer
         System.out.println(mdist > zdist ? "Drats!" : "I've run " + (int)(zdist - mdist + .5) + " more meters than Mack!!!");
      }
   }
}
