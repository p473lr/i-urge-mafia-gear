// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class prob10 {

    public static void main(String[] args) {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
        
            int numLines = new Integer(line).intValue();
            
            for (int lineNum = 0; lineNum < numLines; lineNum++) {
                line = stdIn.readLine();
                
                String[] parts = line.split(" ");
                for (int part = 1; part < parts.length; part++) {
                    String currentCode = parts[part];
                    
                    if (currentCode.equals("40")) {
                        System.out.print(" ");
                    }
                    else if (currentCode.equals("4B")) {
                        System.out.print(".");
                    }
                    else if (currentCode.equals("6B")) {
                        System.out.print(",");
                    }
                    else if (currentCode.equals("5A")) {
                        System.out.print("!");
                    }
                    else {
                        
                        char c = currentCode.charAt(0);
                        int number = 0;
                        number= new Integer(currentCode.substring(1)).intValue();
                        if (c == '8') {
                            System.out.print((char) (number + 96));
                        }
                        else if (c == '9') {
                            System.out.print((char) (number + 105));
                        }
                        else if (c == 'A') {
                            System.out.print((char) (number +113));
                        }
                        else if (c == 'C') {
                            System.out.print((char) (number + 64));
                        }
                        else if (c == 'D') {
                            System.out.print((char) (number + 73));
                        }
                        else if (c == 'E') {
                            System.out.print((char) (number + 81));
                        }
                        else {
                            System.out.print("OOPS");
                        }
                    }
                }
                System.out.println();
                
            }
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
    
    
}
