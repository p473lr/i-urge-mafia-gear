/*
 * Prob09.java
 * Codewars2006 - RandomSort
 *
 * Created on February 20, 2006, 11:06 PM
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
public class Prob09 {
    
    /** Creates a new instance of Prob09 */
    public Prob09() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = null;
        String alphabet=null;   // Our random alphabet
        List<String> wordlist= new ArrayList<String>();
        
        // Use input from File, but default to STDIN if file is not found
        try {
            in = new Scanner(new File("Prob09.in"));
        } catch (FileNotFoundException e) {
            in = new Scanner(System.in);
        }
        
        // Get our alphabet
        alphabet = in.next();
        
        // Build our list of words to sort
        while(in.hasNext()) {
            wordlist.add(in.next());
        }
        Collections.sort(wordlist , new RandomSortComparator(alphabet));
        
        // Print our sorted list
        Iterator it = wordlist.listIterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
        
        in.close();
        System.exit(0);
    }
}
class RandomSortComparator implements Comparator {
    private String alphabet = null;
    
    // Constructor to initialize our alphabet
    public RandomSortComparator(String alphabet) {
        this.alphabet=alphabet;
    }
    
    public int compare(Object o1, Object o2) {
        String s1 = o1.toString().toUpperCase();
        String s2 = o2.toString().toUpperCase();
        int i=0;
        
        while(i < s1.length() && i < s2.length()) {
            // If the letters match, move to next letter
            if(alphabet.indexOf(s1.charAt(i)) == alphabet.indexOf(s2.charAt(i))){
                i++;
                continue;
            } else {
                // Return neg if o1 < o2, or positive if o1 > o2
                return alphabet.indexOf(s1.charAt(i)) - alphabet.indexOf(s2.charAt(i));
            }
        }
        // If we are here, then the first x letters of both words match.
        // Return neg if o1 is shorter than o2, positive if o1 is longer than o2, and 0 of they are equal
        return s1.length() - s2.length();
    }
}
