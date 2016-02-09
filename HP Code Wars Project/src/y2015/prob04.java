// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Integer;

public class prob04 {

    public prob04() {
        readInput();
    }
    
    public static void main(String[] args) {
        prob04 instance = new prob04();
    }
    
    private void readInput() {
    
        try {
        
            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;
            
            aLine = standardIn.readLine();
            
            while (null != aLine && false == aLine.equals("0 0")) {
            
                String[] numbers = aLine.split(" ");
                int a = new Integer(numbers[0]).intValue();
                int b = new Integer(numbers[1]).intValue();
                
                int c = a*b;
                System.out.println(c);
                aLine = standardIn.readLine();
            }
            
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

}
