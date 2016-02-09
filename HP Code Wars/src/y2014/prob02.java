package y2014;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class prob02
{

    String[] inputLines;

    public prob02()
    {
        readInput();
        
        for (int lineIndex = 0; lineIndex < inputLines.length; lineIndex++)
        {
            String upcCode = inputLines[lineIndex];
            
            int checkDigit = calculateCheckDigit(upcCode);
            
            System.out.println(upcCode + " " + checkDigit);
        }
    }
    public static void main(String[] args)
    {
        prob02 instance = new prob02();
    }
    
    private int calculateCheckDigit(String inputLine)
    {
        String[] upcCodeSplit = inputLine.split(" ");
        
        int checksum = 0;
        
        for (int oddDigit = 0; oddDigit < upcCodeSplit.length; oddDigit+=2)
        {
            checksum += new Integer(upcCodeSplit[oddDigit]);
        }
        
        checksum *= 3;
        
        for (int evenDigit = 1; evenDigit < upcCodeSplit.length; evenDigit+=2)
        {
            checksum += new Integer(upcCodeSplit[evenDigit]);
        }
        
        checksum %= 10;
        
        if (checksum != 0)
        {
            checksum = 10 - checksum;
        }
        
        return checksum;
    }
    
    private void readInput()
    {
        
        try 
        {
            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;
            aLine = standardIn.readLine();
            int numCodes = new Integer(aLine);
            
            inputLines = new String[numCodes];
            for (int line = 0; line < numCodes; line++)
            {
                aLine = standardIn.readLine();
                if (null == aLine)
                {
                    System.out.println("read NULL line!");
                    System.exit(123);
                }
                inputLines[line] = aLine;
            }
            
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }
}
