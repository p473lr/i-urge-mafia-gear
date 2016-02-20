
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: The 3,155,760,000 Seconds of This Century (parody)
//
// Author:        Matt Fontaine & Glenn Martin
// Judge Data:    Glenn Martin
// C Solution:    Glenn Martin
// Java Solution: Glenn Martin
// Verifier:      Glenn Martin

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class parody 
{
   public static void main(String[] args) throws IOException 
   {
      // Open file for reading
      Scanner in = new Scanner(new File("parody.in"));

      // Get in the number of songs
      int numSongs = in.nextInt();

      // Loop over the songs
      for (int i=0; i < numSongs; i++)
      {
         // Get the stanza
         int stanza = in.nextInt();

         // Sing the rest of the stanzas in this set
         System.out.printf("Song #%d: ", i+1);
         for (int j=stanza-1; j > 0; j--)
            System.out.printf("%d ", j);
         System.out.printf("%n");
      }

      // Done
      in.close();
   }
}

