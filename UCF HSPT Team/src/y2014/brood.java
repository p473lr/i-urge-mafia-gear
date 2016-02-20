
import java.util.*;
import java.io.*;

public class brood
{
   /*
    * The idea used in this solution is that if there is a way to place 
    * all the dragons in the largest social group without having two of 
    * them next to each other, there is a way to place all the dragons. 
    * To see why this is true, we can split the problem into three cases.
    * 
    * Case 1: Both sides of Ms. G have an even number of dragons
    * 
    * Here is a method to solve this case. We will number all the positions 
    * where dragons can be placed from left to right, starting at 1 and 
    * ignoring Ms. G's position. Place the dragons in every odd position 
    * from left to right, starting with the largest group, then the second 
    * largest group, and so on. When you get to the end, loop back to the
    * left side and fill the even positions from left to right. We can see 
    * that the position of Ms. G doesn't affect this method because both 
    * sides are even.
    * 
    * Clearly, the size of the largest group has to be <= n / 2, or else 
    * we can't place * them correctly. Suppose the size of the largest 
    * group is k (k <= n / 2). Using the * method described above, the only 
    * way for another group to have an invalid position is to place at 
    * least one dragon in an odd position to the right of the largest group
    * and then fill in the k even positions next to dragons from the 
    * largest group. However, the only way for this to happen is to have 
    * at least k + 1 dragons in this group, which is larger than the 
    * largest group. So, we've shown that if we can place all the dragons
    * from the largest group, we can also place the rest of the dragons.
    * 
    * Case 2: One side of Ms. G has an even number of dragons and the 
    * other side has an odd number
    * 
    * If we put the even side on the left, then this case works for the 
    * same reason as Case 1.
    * 
    * Case 3: Both sides of Ms. G have an odd number of dragons
    * 
    * In this case, we might have to place dragons from the largest group 
    * on both sides of Ms. G. For example, if n = 10 and group 1 has 
    * 6 dragons, we can place them like this:
    * 
    * 1_1_1|1_1_1
    * 
    * where the | indicates Ms. G. None of the remaining spaces are adjacent, 
    * so all of the dragons can be placed. If the largest group has at 
    * least one less dragon, then we can shift the dragons on the right 
    * side to the right by one position, and it becomes the same problem 
    * as Case 1.
    * 
    * Since we've shown that all cases can be solved if we can already 
    * place the largest group, all we have to do is check if the largest 
    * group can be placed correctly.
    */

   public static void main(String[] args) throws IOException
   {
      // Open input file
      Scanner in = new Scanner(new File("brood.in"));
      
      // Loop over number of cases
      int t = in.nextInt();
      for (int x = 0; x < t; x++)
      {
         // Get group size
         int n = in.nextInt();
         
         // Get the maximum group size
         int[] size = new int[n];
         int maxSize = 0;
         for (int y = 0; y < n; y++)
         {
            // Read which group this dragon is in
            int currentGroup = in.nextInt() - 1;

            // Keep size of this group (and track size of largest group)
            size[currentGroup]++;
            maxSize = Math.max(maxSize, size[currentGroup]);
         }
         
         // Print prefix for case
         System.out.print("Class #" + (x + 1) + ": ");
         
         // Check if the largest group can be placed (the largest group has to
         // be split properly or we know we can't have a solution otherwise)
         if (((n / 2) + 1) / 2 + ((n + 1) / 2 + 1) / 2 >= maxSize)
         {
            System.out.println("YES");
         }
         else
         {
            System.out.println("NO");
         }
      }

      // Close file
      in.close();
   }
}
