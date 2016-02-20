/*
 * Prob06.java
 * Codewars2006 - Roman Numeral
 *
 * Created on February 20, 2006, 7:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author skearney
 */
public class Prob06 {

    /** Creates a new instance of Prob06 */
    public Prob06() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String op1=null,op2=null,oper=null,result=null;
        int i,j;

        System.out.println("Input Operand 1: ");
        op1 = in.next();
        System.out.println("Input Operator: ");
        oper = in.next();
        System.out.println("Input Operand 2: ");
        op2 = in.next();
        
        in.close();
        i = RomanToArabic(op1);
        j = RomanToArabic(op2);

        switch(oper.charAt(0)) {
            case '+':
                result = ArabicToRoman(i+j);
                break;
            case '-':
                result = ArabicToRoman(i-j);
                break;
            case '*':
                result = ArabicToRoman(i*j);
                break;
            case '/':
                result = ArabicToRoman(i/j);
                break;
            default:
                System.out.println("Illegal Operator");
                System.exit(1);
        }

        System.out.println("The result is: "+result);
        return;
    }
    private static int RomanToArabic(String roman) {
        // Convert Roman Numeral to Arabic (Integer).
        // Behavior is not defined for invalid Roman Numeral
        int i = 0;       // A position in the string, roman;
        int arabic = 0;  // Arabic numeral equivalent of the part of the string that has been converted so far.

        while (i < roman.length()) {
            char letter = roman.charAt(i);        // Letter at current position in string.
            int number = letterToNumber(letter);  // Numerical equivalent of letter.
            i++;
            if (i == roman.length()) {
                // There is no letter in the string following the one we have just processed.
                // So just add the number corresponding to the single letter to arabic.
                arabic += number;
            } else {
                // Look at the next letter in the string.  If it has a larger Roman numeral
                // equivalent than number, then the two letters are counted together as
                // a Roman numeral with value (nextNumber - number).
                int nextNumber = letterToNumber(roman.charAt(i));
                if (nextNumber > number) {
                    // Combine the two letters to get one value, and move on to next position in string.
                    arabic += (nextNumber - number);
                    i++;
                } else {
                    // Don't combine the letters.  Just add the value of the one letter onto the number.
                    arabic += number;
                }
            }
        }

        return arabic;
    }

    private static String ArabicToRoman(int num) {
        // Convert Roman Numeral to Arabic
        int[]    numbers = { 1000,  900,  500,  400,  100,   90,
                              50,   40,   10,    9,    5,    4,    1 };
        String[] letters = { "M",  "CM",  "D",  "CD", "C",  "XC",
                             "L",  "XL",  "X",  "IX", "V",  "IV", "I" };

        String roman = "";
        for (int i = 0; i < numbers.length; i++) {
            while (num >= numbers[i]) {
                roman += letters[i];
                num -= numbers[i];
            }
        }
        return roman;
    }
    private static int letterToNumber(char letter) {
        // Find the integer value of letter considered as a Roman numeral.  Return
        // -1 if letter is not a legal Roman numeral.  The letter must be upper case.
        switch (letter) {
            case 'I':  return 1;
            case 'V':  return 5;
            case 'X':  return 10;
            case 'L':  return 50;
            case 'C':  return 100;
            case 'D':  return 500;
            case 'M':  return 1000;
            default:   return -1;
        }
    }
}

