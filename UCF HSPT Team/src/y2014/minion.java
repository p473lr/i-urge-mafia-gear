
import java.io.*;
import java.util.*;

public class minion {
   
   public static void main(String[] args) throws FileNotFoundException {
      // Open the file
      Scanner in = new Scanner(new File("minion.in"));
      
      // Read in the number of scenarios
      int numScenarios = in.nextInt();
      
      // Loop for each scenario
      for (int scenarioID = 1; scenarioID <= numScenarios; scenarioID++) {
         // Read in the objects mass and time
         double objectMass = in.nextDouble();
         double objectTime = in.nextDouble();
         
         // Read in Phil's mass
         double philsMass = in.nextDouble();
         
         // Solve for k, the constant described in the problem 
         double k = objectMass*objectTime;
         
         // Calculate Phil's shrink time based on k
         double shrinkTime = k/philsMass;
         
         // Output the answer
         System.out.printf("Scenario #%d: %.2f%n", scenarioID, shrinkTime);
      }

      // Close the input
      in.close();
   }
}
