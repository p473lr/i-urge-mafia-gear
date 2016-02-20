import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class lice
{

    static String registered[];

    // This is a simple function that returns the minimum of the three integers
    // given
    public static int min3(int a, int b, int c)
    {
        if (a < b)
        {
            if (a < c)
                return a;
            else
                return c;
        }
        else
        {
            if (b < c)
                return b;
            else
                return c;
        }
    }

    // This is a recursive method for computing the edit distance of two
    // strings. It recursively pulls characters off the end of each string
    // until one or both strings are empty.  This allows an exact edit
    // distance to be computed.  There are more efficient algorithms using
    // the dynamic programming technique, but this method works fine for
    // short strings. 
    public static int editDistance(String s1, String s2)
    {
       int length1, length2;
       char ch1, ch2;
       String t1, t2;
       int d1, d2, d3;

       // Find the length of the two strings
       length1 = s1.length();
       length2 = s2.length();

       // If both strings are empty, the edit distance is zero
       if ((length1 == 0) && (length2 == 0))
       {
           return 0; 
       }
       // If one of the strings is empty, the edit distance is the length of
       // the other string
       else if (length1 == 0)
       {
           return length2;
       }
       else if (length2 == 0)
       {
           return length1;
       }
       else
       {
           // If both strings are non-empty, then there are three possibilities.
           // First shorten s1 and s2 by one character each, and remember
           // the character we strip off
           ch1 = s1.charAt(length1-1);
           t1 = s1.substring(0, length1-1);
           ch2 = s2.charAt(length2-1);
           t2 = s2.substring(0, length2-1);

           // The first possibility is the edit distance of the two shorter
           // strings, plus the edit distance of the two characters we just
           // stripped. If the two characters match, the edit distance for those
           // two charcters is zero, otherwise it is one.  We'll compute this
           // value and remember it.
           d1 = editDistance(t1, t2);
           if (ch1 != ch2)
               d1 += 1;

           // The second possibility is to delete ch1 from s1, which gives this:
           d2 = editDistance(t1, s2) + 1;

           // Likewise, we could delete ch2 from s2, which gives this:
           d3 = editDistance(s1, t2) + 1;

           // We want the minimum edit distance, so we return the minimum of the
           // three quantities we computed
           return min3(d1, d2, d3);
       }
    }

    public static void main(String args[]) throws IOException
    {
        BufferedReader fp;
        String line;
        StringTokenizer lineTokens;
        int cases, caseNum;
        int plates, plateNum;
        int regs, regNum;
        int minEditDist, editDist;

        // Open the input file
        fp = new BufferedReader(new FileReader("lice.in"));

        // Create an array for the registered plates
        registered = new String[256];

        // Read the first line, and parse out the number of cases
        line = fp.readLine().trim();
        cases = Integer.parseInt(line);

        // Iterate for each case in the input
        for (caseNum = 1; caseNum <= cases; caseNum++)
        {
            // Print the output header:
            System.out.println("Registration " + caseNum + ":");

            // Read in the number of existing plates
            line = fp.readLine().trim();
            plates = Integer.parseInt(line);

            // Read in the plates
            for (plateNum = 0; plateNum < plates; plateNum++)
            {
                // Read the next plate
                line = fp.readLine().trim();
 
                // Copy the plate to the registration list
                registered[plateNum] = line;
            }

            // Read in the number of registration applications
            line = fp.readLine().trim();
            regs = Integer.parseInt(line);

            // Read in the registrations
            for (regNum = 0; regNum < regs; regNum++)
            {
                // Read the next plate
                line = fp.readLine().trim();

                // Initialize the minimum edit distance
                minEditDist = Integer.MAX_VALUE;

                // Iterate over the existing plates and find the smallest
                // edit distance
                plateNum = 0;
                while ((plateNum < plates) && (minEditDist > 1))
                {
                    // Compute the edit distance of this plate with the
                    // existing registered plates
                    editDist = editDistance(line, registered[plateNum]);

                    // If this distance is less than the smallest distance
                    // we've found so far, remember it
                    if (editDist < minEditDist)
                        minEditDist = editDist;

                    // Check the next plate
                    plateNum++;
                }
 
                // Print the output
                if (minEditDist <= 1)
                {
                    System.out.println(line + " is NOT registered.");
                }
                else
                {
                    System.out.println(line + " is registered.");

                    // Copy the plate to the registration list
                    registered[plateNum] = line;
                    plates++;
                }
            }

            // Leave a blank line
            System.out.println();
        }
    }
}
