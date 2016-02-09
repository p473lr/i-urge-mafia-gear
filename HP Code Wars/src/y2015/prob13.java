// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class prob13 {

    private String binary = "";
    private String number;
    private String n;
    private List<String> answers = new ArrayList<String>();
    public prob13() {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            number = stdIn.readLine();
            n = stdIn.readLine();        
         
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
        
        int numSubStrings = new Integer(n).intValue();
        findSolution(number.substring(0, number.length()-1), 0, numSubStrings);
        Collections.reverse(answers);
        
        for (String answer : answers) {
            System.out.println(answer);
        }
        
    }
    
    private boolean isRepeatingBinary(String numberToCheck) {
        
        boolean result = true;
        //System.out.print("testing " + numberToCheck);
        try { 
            int currentNumber = new Integer(numberToCheck).intValue();
            String currentBinary = Integer.toBinaryString(currentNumber);
            
            for (int index = 0; index < currentBinary.length(); index++) {
                char currentChar = currentBinary.charAt(index);
                
                if ((index%2)==0 && '1' != currentChar) {
                    result = false;
                    break;
                }
                
                if ((index%2==1) && '0' != currentChar) {
                    result = false;
                    break;
                }
            }
        }
        catch (NumberFormatException nex) {
            //nex.printStackTrace();
            result = false;
        }
        //System.out.println(" ... " + result);
        return result;
    }
    
    private boolean findSolution(String numbers, int startIndex, int numSubStrings) {
        
        //System.out.println("startIndex " + startIndex + " numSubStrings " + numSubStrings + " - " + numbers);
        // no more sub strings, used all numbers
        if (0 == numSubStrings && startIndex == numbers.length()) {
            //System.out.println("help");
            return true;
        }
        
        // no more sub strings, still have numbers left
        if (0 == numSubStrings && startIndex != numbers.length()) {
            return false;
        }
        
        for(int endIndex = startIndex + 1; endIndex <= numbers.length(); endIndex++) {
            String currentSubstring = numbers.substring(startIndex, endIndex);
            //System.out.println("currentSubstring " + currentSubstring);
            
            if (true == isRepeatingBinary(currentSubstring)) {
                //System.out.println("found rep binary substring " + currentSubstring + " start " + startIndex + " end " + endIndex);
                
                boolean recursiveValue = findSolution(numbers, endIndex, numSubStrings-1);
                //System.out.println("after recursion " + numSubStrings);
                // if recursion worked then we're done
                if (recursiveValue) {
                    //System.out.println(" SOLUTION " + currentSubstring);
                    answers.add(currentSubstring);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        new prob13();
    
    }
}
