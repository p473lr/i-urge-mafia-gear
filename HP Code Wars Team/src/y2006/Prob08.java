/*
 * Prob08.java
 * Codewars2006 - Simply Sets
 *
 * Created on February 22, 2006, 12:16 AM
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
public class Prob08 {
    
    /** Creates a new instance of Prob08 */
    public Prob08() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = null;
        
        // Use input from File, but default to STDIN if file is not found
        try {
            in = new Scanner(new File("Prob08.in"));
        } catch (FileNotFoundException e) {
            in = new Scanner(System.in);
        }
        
        while(in.hasNextLine()) {
            Set<String> setA = new LinkedHashSet<String>();
            Set<String> setB = new LinkedHashSet<String>();
            Set<String> setC = new LinkedHashSet<String>();    //our new set
            String operation=null;                          //Op to perform on our sets
            Iterator it=null;
            
            StringTokenizer token = new StringTokenizer(in.nextLine(),",");
            // Build Set A
            while (token.hasMoreTokens()) {
                setA.add(token.nextToken());
            }
            // Build Set B
            token = new StringTokenizer(in.nextLine(),",");
            while (token.hasMoreTokens()) {
                setB.add(token.nextToken());
            }
            
            // Get operation and perform
            operation = in.nextLine();
            
            if(operation.compareToIgnoreCase("Union") == 0) {
                // build a new set comprised of elements from A followed by elements from B
                setC = setUnion(setA, setB);
            } else if (operation.compareToIgnoreCase("Intersection") == 0) {
                //build a new set comprised of elements that are members of both A and B
                setC = setIntersection(setA, setB);
            } else if (operation.compareToIgnoreCase("Complement") == 0) {
                //build a new set comprised of elements from B that are not in A
                setC = setComplement(setA, setB);
            } else {
                System.out.println("Invalid Operation");
                System.exit(1);
            }
            
            // Output our new Set
            if(setC.isEmpty()) {
                System.out.println("Null");
            } else {
                it = setC.iterator();
                System.out.print(it.next().toString());
                while(it.hasNext()) {
                    System.out.print(",");
                    System.out.print(it.next().toString());
                }
                System.out.println();
            }
        }
        in.close();
        System.exit(0);
    }
    private static Set setUnion(Set setA, Set setB) {
        Set<String> setC = new LinkedHashSet<String>();    //our new set
        Iterator it=null;
        
        // Add members of setA
        it = setA.iterator();
        while(it.hasNext()) {
            setC.add(it.next().toString());
        }
        
        // Add members of setB, the class will take care of duplicates.
        it = setB.iterator();
        while(it.hasNext()) {
            setC.add(it.next().toString());
        }
        return setC;
    }
    
    private static Set setIntersection(Set setA, Set setB) {
        Set<String> setC = new LinkedHashSet<String>();    //our new set
        Iterator it=null;
        Object element=null;
        
        // Add members of setA if they are in setB
        it = setA.iterator();
        while(it.hasNext()) {
            element = it.next();
            if(setB.contains(element))
                setC.add(element.toString());
        }
        return setC;
    }
    
    private static Set setComplement(Set setA, Set setB) {
        Set<String> setC = new LinkedHashSet<String>();    //our new set
        Iterator it=null;
        Object element=null;
        
        // Add members of setB that are NOT in setA
        it = setB.iterator();
        while(it.hasNext()) {
            element = it.next();
            if(!setA.contains(element))
                setC.add(element.toString());
        }
        return setC;
    }
}

