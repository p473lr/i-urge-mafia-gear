/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package prob01;

import java.util.*;

/**
 *
 * @author Hazzard
 * Dee Scramble
 */
public class Prob01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String encryptString = "";
        int charCount = 0;

        Scanner scan = new Scanner(System.in);
        int m = 2;
        String whileString = new String();

        charCount = scan.nextInt(); // read in character count
        scan.nextLine();  // skip next line
        encryptString = scan.nextLine(); // read in encrpted email
        do {

            StringBuffer outString = new StringBuffer();
            int pos = 0;
//            int len = encryptString.length();
            m += 1;
            outString.append(encryptString.charAt(pos));
            for (int i = 1; i < charCount; i++) {
                pos = (pos + m) % charCount;
                char c = encryptString.charAt(pos);
                if (c == '~') {
                    c = ' ';
                }
                outString.append(c);
            }

            whileString = outString.toString();

        } while (!whileString.endsWith("-DEE"));

        System.out.println(whileString);
    }
}
