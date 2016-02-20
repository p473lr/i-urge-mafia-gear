
import java.io.*;
import java.util.*;

public class ketchup {
   
   public static void main(String[] args) {
      
      // Open in the input
      Scanner in = new Scanner(System.in);
      
      // Read in the number of items
      int numItems = in.nextInt();
      
      // Loop for each item
      for (int itemID = 1; itemID <= numItems; itemID++) {
         // Read in the item's name
         String itemName = in.next();
         
         // Output the correct output (putting item in the phrase)
         System.out.printf("Word #%d: How do you %s?%n", itemID, itemName);
      }
   }
}
