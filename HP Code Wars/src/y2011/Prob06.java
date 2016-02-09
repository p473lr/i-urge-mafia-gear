/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package prob06;
import java.util.*;
/**
 *
 * @author Hazzard
 * HP Codewar MakingWaves
 */
public class Prob06 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        int inputLen = input.length();
        int levels[] = new int[inputLen];
        int maxLevel=0, currLevel=0, minLevel=0;
        char prevChar = input.charAt(0);

        //Level everything out.

        for (int i=1; i<inputLen; i++) {
            char nextChar = input.charAt(i);
            if (nextChar < prevChar)
                currLevel--;
            else if(nextChar > prevChar)
                currLevel++;

            prevChar = nextChar;
            levels[i] = currLevel;

            if (currLevel > maxLevel)
                maxLevel = currLevel;
            else if (currLevel < minLevel)
                minLevel = currLevel;
        }

        // let's print it out...

        for (int level = maxLevel; level >= minLevel; level--) {
            for (int i=0; i < inputLen; i++) {
                if (level == levels[i])
                    System.out.print(input.charAt(i));
                else
                    System.out.print(" ");

            }
            System.out.println();

        }

    }

}
