import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class prob03
{

    String[] inputLines;
    int[] digitToMilliwattsArray = {90, 30, 75, 75, 60, 75, 90, 45, 105, 90};

    public prob03()
    {
        readInput();
        
        for (int lineIndex = 0; lineIndex < inputLines.length; lineIndex++)
        {
            String upcCode = inputLines[lineIndex];
            
            int checkDigit = calculateClockPower(upcCode);
            
            System.out.println(checkDigit + " milliamps");
        }
    }
    public static void main(String[] args)
    {
        prob03 instance = new prob03();
    }
    
    private int calculateClockPower(String inputLine)
    {
        String[] clockSplit = inputLine.split(":");
        int milliwatts = 20;
        
        String hours = clockSplit[0];
        String minutes = clockSplit[1];
        
        int singleDigit = new Integer(hours.substring(0,1));
        if (hours.length() > 1)
        {
            milliwatts += digitToMilliwattsArray[1];
            singleDigit = new Integer(hours.substring(1));
        }
        milliwatts += digitToMilliwattsArray[singleDigit];
        
        int tensDigit = new Integer(minutes.substring(0,1));
        singleDigit = new Integer(minutes.substring(1));
        
        milliwatts += digitToMilliwattsArray[tensDigit];
        milliwatts += digitToMilliwattsArray[singleDigit];
        
        return milliwatts;
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
