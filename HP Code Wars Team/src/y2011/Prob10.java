package y2011;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package prob10;

import java.util.*;

/**
 *
 * @author Hazzard
 * Hp CodeWars 2011 Reverse+add=Plaindrome
 */
public class Prob10 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //int inputNum = 195;
        //int inputNum = 304;
        //int inputNum = 628;
        //
        int inputNum = 259;
        Scanner scan = new Scanner(System.in);
        inputNum = scan.nextInt();


        while (1 == 1) {
            String palindrome = "" + inputNum;
            int len = palindrome.length();
            int half = len / 2;

            String frontHalf = palindrome.substring(0, half);
            StringBuffer bh = new StringBuffer(palindrome.substring((len - half)));
            String backHalfRev = bh.reverse().toString();

            if (frontHalf.equals(backHalfRev)) {
                break; // palindrome found
            }
            StringBuffer temp = new StringBuffer(palindrome);
            palindrome = temp.reverse().toString();
            inputNum += Integer.parseInt(palindrome);
        }
        System.out.println(inputNum);
    }
}
