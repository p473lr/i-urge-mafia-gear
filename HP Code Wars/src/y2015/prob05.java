// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class prob05 {

    public static void main(String[] args) {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
            int a = new Integer(line).intValue();
            
            line = stdIn.readLine();
            int b = new Integer(line).intValue();
            
            line = stdIn.readLine();
            int c = new Integer(line).intValue();
            
            System.out.println(a + " x (" + b + " + " + c + ") = " + a + " x " + b + " + " + a + " x " + c);
            System.out.println(a + " x " + (b+c) + " = " + (a*b) + " + " + (a*c));
            System.out.println((a*(b+c)) + " = " + (a*(b+c)));
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
