
/*
 * CodeWars 2008
 * Problem -- Checker Board
 * 
 * Task Description:
 *  Write a program to print multiple checker board patterns.
 * 
 * Input:
 *  Each line of input will contain a positive integer representing the size of
 *  an individual checker square. The largest possible input value is 9. The 
 *  last line of the input is the number zero.
 *  
 * Output:
 *  The program prints an 8x8 checker board corresponding to each input value. 
 *  The # character is used for black squares and the . character for white
 *  squares. Each checker board is separated by a blank line. 
 */

import java.io.*;
import java.util.*;

/**
 * Prints multiple checker board patterns based on user-defined sizes.
 * 
 * @author kolson
 * 
 */
public class prob03 {
    
    /**
     * The main method, used to draw checker board patterns.
     * 
     * @param arg            (not used).
     * @throws IOException    if an input or output exception occurs.
     * 
     */
    public static void main(String[] arg) throws IOException {    
        List squareSizes = new ArrayList();
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Get input from the user until they enter "0"
        while (true) {
            input = br.readLine();
            int size = Integer.parseInt(input);

            if (size == 0) {
                break;
            }
            else if ((size >= 1) && (size <= 9)) {
                squareSizes.add(input);
            }
        }
        
        // Iterate through each checker board size specified by the user
        for (Iterator sizeIterator = squareSizes.iterator(); sizeIterator.hasNext();) {
            int size = Integer.parseInt((String) sizeIterator.next());

            String rowText;
            
            for (int row = 0; row < 8; row++) {
                rowText = "";
                
                for (int col = 0; col < 8; col++) {
                    if ((row % 2) == (col % 2)) {
                        rowText += repeatCharacter(size, '#');
                    }
                    else {
                        rowText += repeatCharacter(size, '.');
                    }
                }
                
                for (int height = 0; height < size; height++) {
                    System.out.println(rowText);
                }
            }
            
            System.out.println();  // blank line between boards
        }
    }
    
    /**
     * Returns a string of specified size filled with a specified character.
     * 
     * @param    size        the size of the desired string.
     * @param    character    the character used to fill the string.
     * @return    String        a string filled with the specified character.
     * 
     */
    private static String repeatCharacter (int size, char character) {
        char[] chars = new char[size];
        Arrays.fill(chars, character);
        return (new String(chars));
    }
}
