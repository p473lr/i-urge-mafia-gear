
import java.io.*;
import java.util.*;

public class awesome {
   
   public static void main(String[] args) throws FileNotFoundException {
      
      // Open in the input
      Scanner in = new Scanner(new File("awesome.in"));
      
      // Read in the number of items
      int numItems = in.nextInt();
      
      // Loop for each item
      for (int itemID = 1; itemID <= numItems; itemID++) {
         // Read in the item's name
         String itemName = in.next();
         
         // Output the correct output (putting item in the phrase)
         System.out.printf("Item #%d: %s! It's awesome!%n", itemID, itemName);
      }

      // Close file
      in.close();
   }
}
