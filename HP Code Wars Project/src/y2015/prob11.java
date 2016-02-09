package y2015;

// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class prob11 {

    public static void main(String[] args) {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
        
            while (line != null && !line.equals("0 0 0")) {
                
                String[] numbers = line.split(" ");
                int size = new Integer(numbers[0]).intValue();
                int rows = new Integer(numbers[1]).intValue();
                int cols = new Integer(numbers[2]).intValue();
                
                //System.out.println("size " + size + " rows " + rows + " cols " + cols);
                for (int row = 0; row < rows; row++) {
                    printDiamond(size, cols);
                }
                
                line = stdIn.readLine();
            }
            //System.out.println(line);
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
    
    private static void printDiamond(int size, int cols) {
        
        for (int row = 1; row <= size; row++) {
            printDiamondRow(size, row, cols);
        }
    }
    
    
    private static void printDiamondRow(int size, int row, int cols) {
        
        String firstSlash = null;
        String secondSlash = null;
        
        if (row <= (size/2)) {
            firstSlash = "/";
            secondSlash = "\\";
        }
        else {
            firstSlash = "\\";
            secondSlash = "/";
            row = (size - row) + 1;
        }
        
        int numSlashes = (size - (row*2));
        int hashBefore = numSlashes/2;
        int hashAfter = size - hashBefore;
        
        for (int col = 0; col < cols; col++) {
            for (int index = 0; index <= size; index++) {
                String toPrint = "";
                if (index < hashBefore || index > hashAfter) {
                    toPrint = "#";
                }
                else if (index < (hashBefore+row)) {
                    toPrint = firstSlash;
                }
                else if (index < hashAfter) {
                    toPrint = secondSlash;
                }
                System.out.print(toPrint);
            }
        }
        System.out.println();
    }
}
