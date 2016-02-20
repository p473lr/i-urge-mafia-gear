
/*
 * CodeWars 2008
 * Problem -- Redundant Acronym Syndrome Syndrome
 * 
 * Task Description:
 *  Write a program that generates RAS syndrome acronyms. 
 * 
 * Input:
 *  Each line of the input contains one or more words. The line of text will be
 *  no longer than 80 letters, spaces, and/or punctuation. The last line of the
 *  input is the word END.
 *  
 * Output:
 *  The program must print the acronyms in RAS syndrome format. For this 
 *  program you can assume that the repeated word is always the last word of 
 *  the input line. Acronyms must be upper-case, regardless of the case of the
 *  input.
 */

import java.io.*;
import java.util.*;

/**
 * Generate RAS syndrome acronyms.
 * 
 * @author kolson
 *
 */
public class prob04 {
    
    /**
     * The main method, used to generate RAS syndrome acronyms.
     * 
     * @param arg            (not used).
     * @throws IOException    if an input or output exception occurs.
     * 
     */
    public static void main(String[] arg) throws IOException {    
        List rasList = new ArrayList();
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Get input from the user until they enter "END"
        while (true) {
            input = br.readLine();
            
            // last line is the word END
            if (input.compareTo("END") != 0) {
                
                // input must be no longer than 80 letters, spaces, and/or punctuation
                if (input.length() <= 80) {
                    rasList.add(input);
                }
            }
            else {
                break;
            }
        }
        
        // print all valid input lines in RAS syndrome format
        for (Iterator rasIterator = rasList.iterator(); rasIterator.hasNext();) {
            
            String[] words = ((String) rasIterator.next()).split("\\s+");
            
            // get the first letter of each word
            String acronym = "";
            for (int i = 0; i < words.length; i++) {
                acronym += words[i].charAt(0);
            }

            System.out.println(acronym.toUpperCase() + " " + words[words.length - 1]);
        }
    }
}
