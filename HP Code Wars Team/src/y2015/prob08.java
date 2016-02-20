// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class prob08 {

    public static void main(String[] args) {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
            
            int linesToRead = new Integer(line).intValue();
            
            int numEmpty = 0;
            int numDuplicate = 0;
            int numNoCube = 0;
            
            String[] cubes = new String[300];
            Set<Integer> duplicates = new HashSet<Integer>();
            for (int lineNum = 0; lineNum < linesToRead; lineNum++) {
                
                line = stdIn.readLine();
                String[] parts = line.split(" ");
                
                String name = parts[0];
                int cubeNumber = new Integer(parts[1]).intValue();
                
                if (0 == cubeNumber) {
                    numNoCube++;
                    continue;
                }
                
                String currentAssignment = cubes[cubeNumber];
                
                // Cube never used
                if (null == currentAssignment) {
                    currentAssignment = name;
                    
                    // Empty cube
                    if (currentAssignment.equals("NA")) {
                        numEmpty++;
                    }
                }
                
                // If cube is already assigned to someone else
                if (false == currentAssignment.equals(name)) {

                    // cube was empty, now assigned
                    if (currentAssignment.equals("NA")) {
                        currentAssignment = name;
                        numEmpty--;
                    }
                    else if (false == name.equals("NA")) {
                        // assignment is not empty, so it's a duplicate
                        if (true == duplicates.add(cubeNumber)) {
                            numDuplicate++;
                        }
                    }
                    // ignore else case where cube was assigned but is now NA
                } 
                
                // update current cube assignment
                cubes[cubeNumber] = currentAssignment;
            }
            
            System.out.println("Empty Cubes: " + numEmpty);
            System.out.println("Duplicate Cube Assignments: " + numDuplicate);
            System.out.println("Employees without Cube: " + numNoCube);
            
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
