
import java.io.*;
import java.util.Scanner;


class tray
{
   int       stack;
   int       numTrays;
   IceTray   trays[];

   int       perm[];
   boolean   used[];


   class IceTray
   {
      public int       numRows;
      public int       numColumns;
      public boolean   top[][];
      public boolean   bottom[][];

      public IceTray(int r, int c)
      {
         numRows = r;
         numColumns = c;

         // The state of the ice is represented as a boolean array.  We
         // have one array for the top (the ice in the tray) and one for
         // the bottom (the ice stuck to the bottom of the tray)
         top = new boolean[r][c];
         bottom = new boolean[r][c];
      }

      public boolean fitsOn(IceTray bottomTray)
      {
         int i;
         int j;

         for (i = 0; i < numRows; i++)
         {
            for (j = 0; j < numColumns; j++)
            {
               // This slot fits with the other tray's corresponding slot if
               // and only if this tray's bottom slot is empty while the
               // other tray's top slot is full, or this tray's bottom slot
               // is full, while the other tray's top slot is empty.  In
               // any other case, this tray doesn't fit on the other one.
               // This works out to a simple logical XOR of the two
               // slots
               if (bottom[i][j] ^ bottomTray.top[i][j] == false)
                  return false;
            }
         }

         // If the XOR test passes for all slots, then this tray fits on the
         // other tray
         return true;
      }

      public boolean isBottom()
      {
         int i;
         int j;

         // The bottom tray has no ice stuck to its bottom, see if this is
         // the bottom tray
         for (i = 0; i < numRows; i++)
            for (j = 0; j < numColumns; j++)
               if (bottom[i][j] == true)
                  return false;

         // If we get this far, the bottom of the tray must be clean
         return true;
      }
   }


   boolean findOrder(int level)
   {
      int i;

      // See if we've finished the stack yet
      if (level > 0)
      {
         // Not done yet.  Try to find a tray that will fit at this position
         // on the stack
         for (i = 0; i < numTrays; i++)
         {
            // Make sure we haven't already used this tray
            if (!used[i]) 
            {
               // Make sure this tray fits on the stack as we have it
               // so far (in the case where we're picking the first tray for
               // the stack, make sure we pick a tray that can be the bottom
               // of the stack)
               if (((level == numTrays) &&
                    (trays[i].isBottom())) ||
                   ((level < numTrays) &&
                    (trays[i].fitsOn( trays[perm[level]] ))))
               {
                  // Try to stack this tray next
                  perm[level-1] = i;
                  used[i] = true;

                  // See if it fits
                  if (findOrder(level-1))
                  {
                     // It fits!  Return true to stop searching for other
                     // stacking orders
                     return true;
                  }
                  else
                  {
                     // Even though this tray fits here, the rest of the
                     // stack didn't work out with it here, so take it off
                     // and try it some other place
                     used[i] = false;
                  }
               }
            }
         }

         // Return false to indicate we didn't find a good arrangement
         return false;
      }
      else
      {
         // We found the valid order, so print the output
         System.out.print("Stack " + stack + ":");
         for (i = numTrays-1; i >= 0; i--)
            System.out.print(" " + (perm[i]+1));
         System.out.println();

         // Return true, indicating we found a good arrangement
         return true;
      }
   }

   public void solve() throws Exception
   {
      BufferedReader   reader;
      String           line;
      int              numStacks;
      int              i, j, k;
      Scanner          scanner;
      int              numRows;
      int              numColumns;
      int              val;

      // Open the file
      reader = new BufferedReader(new FileReader("tray.in"));

      // Read the number of stacks to process
      line = reader.readLine().trim();
      numStacks = Integer.parseInt(line);

      // Solve each stack
      for (stack = 1; stack <= numStacks; stack++)
      {
         // Read the number of trays in this stack
         line = reader.readLine().trim();
         numTrays = Integer.parseInt(line);

         // Create the trays array
         trays = new IceTray[numTrays];

         // Read the size of each tray
         line = reader.readLine().trim();
         scanner = new Scanner(line);
         numRows = scanner.nextInt();
         numColumns = scanner.nextInt();

         // Set up the ice trays
         for (i = 0; i < numTrays; i++)
         {
            // Create the tray object
            trays[i] = new IceTray(numRows, numColumns);

            // Read the ice pattern in the tray
            for (j = 0; j < numRows; j++)
            {
               // Read the line representing this row and prepare to scan it
               line = reader.readLine().trim();
               scanner = new Scanner(line);

               for (k = 0; k < numColumns; k++)
               {
                  // Read the next slot value and set the value at the
                  // appropriate slot in the tray
                  val = scanner.nextInt();
                  if (val == 0)
                     trays[i].top[j][k] = false;
                  else
                     trays[i].top[j][k] = true;
               }
            }

            // Repeat for the bottom of the tray
            for (j = 0; j < numRows; j++)
            {
               line = reader.readLine().trim();
               scanner = new Scanner(line);

               for (k = 0; k < numColumns; k++)
               {
                  // Read the next slot value and set the value at the
                  // appropriate slot in the tray
                  val = scanner.nextInt();
                  if (val == 0)
                     trays[i].bottom[numRows-j-1][k] = false;
                  else
                     trays[i].bottom[numRows-j-1][k] = true;
               }
            }

            // Skip the blank line after each tray
            line = reader.readLine();
         }

         // Create the array to hold the final tray order and the
         // temporary array to keep track of which tray is used
         perm = new int[numTrays];
         used = new boolean[numTrays];

         // Finally, find the stacking order
         findOrder(numTrays);
      }
   }

   public tray()
   {
   }

   public static void main(String args[]) throws Exception
   {
      tray   instance;

      // Create an instance of the class and solve it
      instance = new tray();
      instance.solve();
   }
}

