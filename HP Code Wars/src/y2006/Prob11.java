/*
 * Prob11.java
 * Codewars2006 - How Many Ways to Make Change?
 *
 * Created on February 26, 2006, 2:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;
/**
 *
 * @author skearney
 */
public class Prob11 {
   
    
    /** Creates a new instance of Prob11 */
    public Prob11() {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int amount=0;   // amount to make change for
        int count=0;
        
        System.out.println("Enter amount of change to make (in cents):");
        amount = in.nextInt();
        
        count = makeChange(amount,amount);
        System.out.print("There are "+count+" ways to make change for ");
        System.out.format("$%.2f\n", (double)amount/(double)100);
        
        in.close();
        System.exit(0);
    }
    
    private static int makeChange(int amount, int coin) {
        int count=0;
        
        if(amount >= 50 && coin >=50) {
            count +=  makeChange(amount-50,50);
            if(amount==50) count++;
        }
        
        if(amount >= 25 && coin >=25) {
            count +=  makeChange(amount-25,25);
            if(amount==25) count++;
        }
        if(amount >= 10 && coin >=10) {
            count +=  makeChange(amount-10,10);
            if(amount==10) count++;
        }
        if(amount >= 5 && coin >=5) {
            count +=  makeChange(amount-5,5);
            if(amount==5) count++;
        }
        
        if(amount > 0) count++;
              
        return count;
    }
    
}

