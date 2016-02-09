package y2014;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class prob11
{

    String[] inputLines;
    int[] digitToMilliwattsArray = {90, 30, 75, 75, 60, 75, 90, 45, 105, 90};

    public prob11()
    {
        readInput();
        
        
    }
    public static void main(String[] args)
    {
        prob11 instance = new prob11();
    }
    
    private int calculateRemainder(String inputLine)
    {
        String[] clockSplit = inputLine.split(" ");
        
        int a = new Integer(clockSplit[0]);
        int b = new Integer(clockSplit[1]);
        int c = new Integer(clockSplit[2]);
        int x = new Integer(clockSplit[3]);
        int y = new Integer(clockSplit[4]);
        int z = new Integer(clockSplit[5]);
        
        //System.out.println("a " + a + " b " + b + " c " + c + " --  x " + x + " y " + y + " z " + z);
        int nominator;
        
        for (nominator = 1; nominator <= 999999; nominator++)
        {
            if ((nominator % a == x) 
                && (nominator % b == y)
                && (nominator % c == z))
            {
                break;
            }   
        }
        return nominator;
    }
    
    private void readInput()
    {
        
        try 
        {
            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;
            boolean moreLines = true;
            do 
            {
                aLine = standardIn.readLine();
                if (aLine.equals("-1 -1 -1 -1 -1 -1"))
                {
                    moreLines = false;
                    break;
                }
                int remainder = calculateRemainder(aLine);
                System.out.println(remainder);
                
            } while (null != aLine && moreLines);
            
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }
}
