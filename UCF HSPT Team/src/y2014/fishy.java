
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class fishy {

   public static final int minFishLength = 4;
   
   public static void main(String[] args) {
      Scanner in = null;
      
      // Open input file
      try {
         in = new Scanner(new File("fishy.in"));
      } catch (FileNotFoundException e) {
         System.out.println("File not found!");
         System.exit(1);
      }
      
      // Number of days you need to compute Nikolai's income
      int d = in.nextInt();
      
      // Iterate through each day
      for(int i = 0; i < d; i++){
         // Length of Nikolai's stand for the day
         int l = in.nextInt();
         in.nextLine();
         
         // Nikolai's stand for the day
         String stand = in.nextLine();
         
         // Regular expressions for valid fish
         String rightFish = "><(\\)*)o>";
         String leftFish = "<o(\\(*)><";
         
         // Get each of the valid fish from the stand that match the 
         // whole fish conditions
         ArrayList<String> validFish = new ArrayList<String>();
         validFish.addAll(getMatches(stand, rightFish));
         validFish.addAll(getMatches(stand, leftFish));
         
         // If no valid fish are found, then Nikolai will make 0 rubles that day
         if (validFish.isEmpty()){
            System.out.println("Day #" + (i+1) + ": 0 rubles");
         }
         else {
            // Otherwise, loop through each of the valid fish to 
            // sum up the fish parts

            // Current total fish cost
            int total = 0;
            
            // Loop through valid fish
            for (int j = 0; j < validFish.size(); j++) {
               // Cut out the head and tail
               int edibleLength = validFish.get(j).length() - minFishLength;
               
               // Add the cost (squared torso length) to the total
               total += (edibleLength*edibleLength);
            }
            
            // Print out Nikolai's earnings for the day
            System.out.println("Day #" + (i+1) + ": " + total + " rubles");
         }
      }

      // Close the input file
      in.close();
   }

   // Will find all valid substrings within the given String s that 
   // match the given regular expression
   public static ArrayList<String> getMatches(String s, String regex) {
      // Stores any valid fish found
      ArrayList<String> matches = new ArrayList<String>();
      
      // Iterate through substrings
      for (int i = 0; i <= s.length()-minFishLength; i++) {
         for (int j = i+minFishLength; j <= s.length(); j++) {
            // If the current substring matches the regular expression, 
            // it is added the the matches list
            if(s.substring(i, j).matches(regex)){
               matches.add(s.substring(i, j));
               
               // Don't need to continue looping from this i value
               break;
            }
         }
      }
      
      // Returns the valid fish found
      return matches;
   }
}

