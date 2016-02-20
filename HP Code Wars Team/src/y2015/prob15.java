// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class prob15 {

    private static final int DEBUG = 1;
    private List<String> input = null;
    String simpleOrComplex = null;
    String partialOrComplete = null;
    
    public prob15() {
        readInput();
        
        for (String line : input) {
            processAcronym(line);
        }
    }
    
    private void processAcronym(String line) {
        int firstSpace = line.indexOf(" ");
        String acronym = line.substring(0, firstSpace);
        String words = line.substring(firstSpace+1);
        if (DEBUG > 1) System.out.println("acro: " + acronym + " word " + words);

        simpleOrComplex = null;
        partialOrComplete = "COMPLETE";
        boolean match = findMatch(acronym, words);
        
        if(match) {
            System.out.println(acronym + " is a " + partialOrComplete + " " + simpleOrComplex + " acronym.");
        }
        else {
            System.out.println(acronym + " is NOT an acronym");
        }
    }
    
    private boolean findMatch(String acronym, String allWords) {
        
        if (DEBUG == 3) System.out.println("START [" + acronym + "]:" + allWords);
        String[] words = allWords.split("[ -]");
        
        boolean oneWordRemaing = false;
        if (words.length == 1 && false == words[0].isEmpty()) {
            oneWordRemaing = true;
        }
        // base conditions
        if (acronym.isEmpty() 
                && (allWords.isEmpty() || oneWordRemaing)) {
            if (oneWordRemaing) {
                if (DEBUG == 3) System.out.println("[" + allWords + "]");
                partialOrComplete = "PARTIAL"; // skipping last word
            }
            return true;
        }
        else if (acronym.isEmpty()) {
            if (DEBUG == 3) System.out.println("acro empty");
            return false;
        }
        else if (allWords.isEmpty()) {
            if (DEBUG == 3) System.out.println("words empty");
            return false;
        }
        
        // recurse condition
        // try to match 1st char of acronym with 1st char of words
        char acronymFirst = Character.toUpperCase(acronym.charAt(0));
        char wordsFirst = Character.toUpperCase(allWords.charAt(0));
        int nextWordIndex = (words[0].length()+1);
        
        String remainder = "";
        if (words.length > 1) {
            remainder = allWords.substring(nextWordIndex);
        }
        
        if (acronymFirst == wordsFirst) {
            boolean firstMatch = findMatch(acronym.substring(1), remainder);
            
            if (firstMatch) {
                if (DEBUG == 3) System.out.println("FIRST match true");
                if (null == simpleOrComplex) {
                    simpleOrComplex = "SIMPLE";
                }
                return true;
            }
            
            if (DEBUG == 3) System.out.println("try matching 2 chars");
            // try to match 2 char of acronym with 2 char of words
            if (acronym.length() > 1 && allWords.length() > 1) {
                char acronymSecond = Character.toUpperCase(acronym.charAt(1));
                char wordsSecond = Character.toUpperCase(allWords.charAt(1));
                
                if (acronymSecond == wordsSecond) {
                    boolean secondMatch = findMatch(acronym.substring(2), remainder);
                    
                    if (secondMatch) {
                        if (DEBUG == 3) System.out.println("SECOND match true");
                        simpleOrComplex = "COMPLEX";
                        return true;
                    }
                    
                    if (DEBUG == 3) System.out.println("second chars match, skip word");
                    boolean skipWord = findMatch(acronym.substring(2), remainder);
                    
                    if (skipWord) {
                        if (DEBUG == 3) System.out.println("second char match, SKIP word true");
                        partialOrComplete = "PARTIAL";
                        return true;
                    }
                }
                
                if (DEBUG == 3) System.out.println("second chars don't match, skip word");
                boolean skipWord = findMatch(acronym.substring(1), remainder);
                
                if (skipWord) {
                    if (DEBUG == 3) System.out.println("second char no match, SKIP word true");
                    partialOrComplete = "PARTIAL";
                    return true;
                }
            }
            
        }
        
        if (DEBUG == 3) System.out.println("first chars don't match, skip word");
        boolean skipWord = findMatch(acronym, remainder);
        
        if (skipWord) {
            if (DEBUG == 3) System.out.println("first char no match, SKIP word true");
            partialOrComplete = "PARTIAL";
            return true;
        }
        
        if (DEBUG == 3) System.out.println("END [" + acronym + "]:" + allWords);
        return false;
    }
    
    private void readInput() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
            
            int number = new Integer(line).intValue();
            
            input = new ArrayList<String>();
            
            for (int l = 0; l < number; l++) {
                line = stdIn.readLine();
                input.add(line);
            }
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        new prob15();
    }
}
