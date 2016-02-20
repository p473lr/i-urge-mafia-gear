package y2011;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package prob02;

import java.util.*;
import java.math.*;

/**
 *
 * @author Hazzard
 * Code wars 2011 Divide by 11
 */
public class Prob02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Scanner scan = new Scanner(System.in);
        int numberCount = 0;
        BigInteger number, original;
        BigInteger hundred = new BigInteger("100");
        BigInteger eleven = new BigInteger("11");
        numberCount = scan.nextInt(); // read in character count
        scan.nextLine();  // skip next line

        for (int i = 0; i < numberCount; i++) {
            original= number = new BigInteger(scan.nextLine());
            

            while (1 == 1) {
                System.out.println(number);

                if (number.compareTo(hundred) < 0)
                    break;
                BigInteger rem = number.remainder(BigInteger.TEN);
                number = number.divide(BigInteger.TEN);
                number = number.subtract(rem);

            }

            BigInteger rem = number.remainder(eleven);
            if (rem.compareTo(BigInteger.ZERO)== 0)
                System.out.println(
                        "The number "+ original+ " is divisible by 11.");
            else
                System.out.println(
                        "The number "+ original+ " is not divisible by 11.");
        }

    }
}
