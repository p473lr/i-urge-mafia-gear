/*
 * PERLSwine.java
 *
 * Created on February 16, 2008, 10:35 PM
 *
 * @author Powell "Hap" Hazzard
 * HP CodeWars "PERLs Before Swine"
 *
 *
 */


import java.io.*;

/**
 *
 * @author hpadmin
 */
public class prob06 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        String input = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        //  Gather input from the user until they enter "END"
        while (1==1) {  // loop forever until someone enter "END"
            int splitPos;
            
            // prompt the user for the conditional statement
            System.out.println("\n\nEnter the conditional statement to convert: (or END to exit)");
            input = br.readLine();
            
            // forever loop, exit by typing END
            if (input.compareTo("END")==0)
                break; // break while
            
            // Let's find the equal sign and work back'
            if ((splitPos = input.lastIndexOf('=')) == -1) {
                // Humm, not equal sign, what's up?
                System.out.println("Missing '='...\nplease enter the statement again");
                continue;
            }
            // find first non-space value.  White space trimming
            while (splitPos != 0 && input.charAt(--splitPos)==' ');
            
            // Now are are parsing the variable name, find the next space value
            while (splitPos != 0 && input.charAt(--splitPos)!=' ');
            if (splitPos!=0) {
                // break string in two
                // switch the two, remove the ";" then readd it
                System.out.println(input.substring(splitPos + 1,
                        input.indexOf(';'))+" "+
                        input.substring(0,splitPos)+";");
            } else {
                // what? No, conditional before the equal
                System.out.println("Missing conditional if statement\n" +
                        "or the 'if' is already after the '='\n" +
                        "Please enter the statement again");
                continue;
            }
        }
        
    }
    
    
}
