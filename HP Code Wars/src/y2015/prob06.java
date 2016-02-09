package y2015;

// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class prob06 {

    public static void main(String[] args) {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            String length = stdIn.readLine();
            String width = stdIn.readLine();
            String depth = stdIn.readLine();
            
            int l = new Integer(length).intValue();
            int w = new Integer(width).intValue();
            int d = new Integer(depth).intValue();
            
            int cubicFeet = l*w*d;
            double cubicYards = cubicFeet/ 27.0;
            int wholeCubicYards = (int) (cubicYards + 0.5);
            System.out.println(wholeCubicYards);
            
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
