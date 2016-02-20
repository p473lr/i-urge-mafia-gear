
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Zzzyyx or I'm Out (zzzyyx)
//
// Author:        Mike Galletti
// Judge Data:    Aaron Teetor
// C Solution:    Nick Buelich
// Java Solution: Matt Fontaine
// Verifier:      Antony Stabile


/*

Zzzyyx
-------
This problem has us find the alphabetically first name of exactly k letters.
Even though the problem says we can use more characters it is never optimal
given the constraint that a shorter string breaks ties.

This problem requires a greedy algorithm. Let us first examine the problem 
over a fixed alphabet. This one will do -> "ABCDEFGHIJKLMNOPQRSTUVWXYZ".

The optimal greedy algorithm is to take the earliest alphabetic character 
that still has letters on the floor and place it to the next position on
the string we are building. We can continue to do this until we have a 
string of length k.

More Formal explaination:
------------------------------
Why is the optimal? Consider we are building a string. When we place a new
character at the end of the string we are left with the same subproblem but
with one less character. Notice for the remainder of tie breaking we no longer
have to consider the character just placed. Notice that any string s that is
the same up to some character i will be alphabetically first if that character
comes alphabetically before any other character. So we can consider all string
buildings without thinking about the characters we just placed. Only the counts
remaining matter. Also notice when we place a single character any other 
character in that position that is later alphabetically will be a string 
that is later alphabetically.

Less formal explaination:
------------------------------
Consider tiles CBAB let us form all possible arrangements size 4
sorted alphabetically:
ABBC
ABCB
ACBB
BABC
BACB
BBAC
BBCA
BCAB
BCBA
CABB
CBAB
CBBA

Notice that if we look at the entire leftmost column it is sorted.
The second column is not sorted. But we can throw out any strings that
didn't have an 'A' in the first column:
ABBC
ABCB
ACBB

Notice now the second column is sorted again. We can throw out any strings
without a 'B' in the second column.
ABBC
ABCB

Notice we can continue this process until a single string remains. Our
alphabetically first string I might add.
ABBC

We now have an algorithm that works for a general alphabet. We simply need
to be able to work for all alphabets. This is quite simple. If we
maintain our current alphabet we can "remap" the characters to their
position in the alphabet string.

So for the standard alphabet letters A B and C become 0 1 and 2. For a reverse
alphabet they would become 25, 24, 23. Now we can move through these positions
in order from 0 to 25 and pull out characters to add to our string until
the final string is reached! To determine the original character we simply find
the i-th character in the alphabet string!

*/

import java.util.*;
import java.io.*;

public class zzzyyx
{
   public static void main(String[] args) throws Exception
   {
      // Open input file and pass it to our class
      new zzzyyx(new Scanner(new File("zzzyyx.in")));
   }

   public zzzyyx(Scanner in)
   {
      // We must loop through each week we would like to process
      int w = in.nextInt();
      for (int curWeekId=1; curWeekId<=w; curWeekId++)
      {
         // Read in the input values for each week
         int k = in.nextInt();
         String alphabet = in.next();
         String name = in.next();

         // First we loop through each character and get it's count
         // during this process we 'map' each character to it's position
         // in the alphabet. The array 'letterCount' maintains the number
         // of characters that have the i'th letter in the alphabet.
         // To make life easier on ourselfs we can use the indexOf method
         // in the String class. It does the remapping for us!
         int[] letterCount = new int[26];
         for (char c : name.toCharArray())
            letterCount[alphabet.indexOf(c)]++;

         // Now loop through all characters adding them to our result
         // string. If our string is already big enough then 'pretend'
         // to add it to our result string. This cleans up our implementation.
         StringBuilder sb = new StringBuilder();
         for (int i=0; i<26; i++)
         {
            // Keep using letters while we have them.
            while (letterCount[i]-->0)
            {
               // Now only add the letter if we are smaller than k and
               // use String's fancy charAt method to map back to the
               // original string that we are trying to build.
               if (sb.length() < k)
                  sb.append(alphabet.charAt(i));
            }
         }
         
         // Print our answer!
         System.out.printf("Week #%d: His name is %s!%n", curWeekId, sb);
      }

      // Close scanner
      in.close();
   }
}
