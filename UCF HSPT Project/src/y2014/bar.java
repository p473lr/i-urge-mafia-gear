
import java.util.*;
import java.io.*;

public class bar {
   public static void main(String[] args) throws IOException {
      // Open input file
      Scanner br = new Scanner(new File("bar.in"));

      // Read in number of cases and loop over them
      int testCases = br.nextInt();
      for (int caseOn = 1; caseOn<=testCases; caseOn++) {
         // Read in the number of checkpoints
         int checkPoints = br.nextInt();

         // Read in Gabe's and then Aaron's times for each checkpoint
         int[] gabeTimes = new int[checkPoints];
         int[] aaronTimes = new int[checkPoints];
         for(int i = 0;i<checkPoints;i++){
            gabeTimes[i] = br.nextInt();
         }
         for(int i = 0;i<checkPoints;i++){
            aaronTimes[i] = br.nextInt();
         }

         // Initally no one is winning and we have no excitement :-(
         boolean gabeWinning = false;
         boolean aaronWinning = false;
         int excitement = 0;

         for (int i = 0;i<checkPoints;i++) {
            // If Gabe got to this checkpoint first we need to set him 
            // as the winner; if Aaron was winning before they have 
            // switched so our answer increases by one
            if (gabeTimes[i] < aaronTimes[i]) {
               if (aaronWinning) {
                  // A switch happened!
                  excitement++;
                  aaronWinning = false;
               }

               // Gabe is winning
               gabeWinning = true;
            }

            // But if Aaron got to this checkpoint first we need to set 
            // him as the winner; if Gabe was winning before they 
            // have switched so our answer increases by one
            else if (aaronTimes[i] < gabeTimes[i]) {
               if (gabeWinning) {
                  // Gabe was winning but now Aaron is now!
                  excitement++;
                  gabeWinning = false;
               }

               // Aaron is winning
               aaronWinning = true;
            }
         }

         // Output final answer
         System.out.printf("Video #%d: %d%n", caseOn, excitement);
      }

      // Close file
      br.close();
   }
}
