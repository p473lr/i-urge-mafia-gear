import java.util.*;
import java.io.*;
public class zombie {
   public static void main(String[] args) throws IOException {
      Scanner in = new Scanner(new File("zombie.in"));
      int cases = in.nextInt();
      for(int caseOn = 1; caseOn <= cases; caseOn++) {
         int dictionarysize = in.nextInt();
         word[] dictionary = new word[dictionarysize];
         for(int i = 0; i < dictionarysize; i++) {
            dictionary[i] = new word(in.next());
         }
         Arrays.sort(dictionary);
         System.out.printf("Scenario #%d:\n",caseOn);
         int zombieWords = in.nextInt();
         for(int i = 0; i < zombieWords; i++) {
            LinkedList<word> possibilities = new LinkedList<word>();
            word r = new word(in.next());
            for(word w : dictionary) {
               if(w.canBeFormed(r)) {
                  possibilities.add(w);
               }
            }
            if(possibilities.isEmpty()) {
               System.out.printf("No matches found.\n");
            } else {
               System.out.printf("Did you mean:\n");
               for(word w : possibilities) {
                  System.out.printf("%s?\n",w.word);
               }
            }
            System.out.println();
         }
      }
   }
   static class word implements Comparable<word> {
      String word;
      int[] runs;
      int sum;
      public word(String word) {
         this.word = word.toUpperCase();
         sum=0;
         getRuns();
      }
      public void getRuns() {
         //parse out the runs of the word
         runs = new int[26];
         runs[(int)(word.charAt(0)-'A')]++;
         sum++;
         for(int i = 1; i < word.length(); i++) {
            int letter = word.charAt(i)-'A';
            if(word.charAt(i)!=word.charAt(i-1)) {
               runs[letter]++;
               sum++;
            }
         }
      }
      
      //sees if the word W could possibly be a scrambled for of this word
      public boolean canBeFormed(word w) {
         for(int i = 0; i < 26; i++) {
            if(w.runs[i]!=runs[i])
               return false;
         }
         return true;
      }
      public int compareTo(word o) {
         return this.word.compareTo(o.word);
      }
   }
}
