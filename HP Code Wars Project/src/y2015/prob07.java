package y2015;

// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class prob07 {

    public static void main(String[] args) {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
            
            while (line != null && !line.equals("0")) {
                double period = new Double(line).doubleValue();
                
                double pSq = period*period;
                double radius = Math.pow(pSq, (1/3.0));
                System.out.println(radius);
                line = stdIn.readLine();
            }
            
            System.out.println(line);
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
