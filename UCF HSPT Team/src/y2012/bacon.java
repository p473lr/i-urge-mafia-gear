/*
 * bacon.java
 * UCF HSPT 2012
 * written by Nick Buelich
 * 
 * Notes:
 * 
 * N is read in as a line, and then parsed into an integer 
 * that way the newline character that postcedes N will not be considered as the next sc.nextLine() 
 * 
 * For each line/sentence, 
 *       read in the corresponding line/sentence 
 *       while an occurrence of France exists in this line/sentence 
 *          replace only the FIRST occurrence of France with Bacon 
 *          and increment a counter
 *       output line number and counter
 */

import java.util.*;
import java.io.*;
public class bacon {
   public static void main(String[] args) throws Exception {
      Scanner sc = new Scanner(new File("bacon.in")); 
      int N = Integer.parseInt(sc.nextLine());
      for(int i=1;i<=N;i++){
         String line = sc.nextLine();
         int found = 0; 
         while(line.contains("France")){
            line=line.replaceFirst("France","Bacon");
            found++;
         }
         System.out.println("Sentence #"+i+": "+found);
      }
   }
}
