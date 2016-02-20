
import java.util.*;
import java.io.*;


// In this problem we just need to count and print out the number of 
// p's and q's in each string. (All capital letters) The obvious solution
// is just to loop through each character counting each match. This is java
// and therefore we have other means of doing the counting. In this solution
// I will demonstrate a regex solution to the problem. If you want to see
// the more straightforward looping solution please refer to the c++ solution
public class pandq
{
   public static void main(String[] args) throws Exception
   {
      new pandq(new Scanner(new File("pandq.in")));
   }

   public pandq(Scanner in)
   {
      while (in.hasNextLine())
      {
         String s = in.nextLine();

         // We should make sure the input is valid since this program
         // will be testing the judge data as well
         if (s.length() > 70) System.out.printf("BAD JUDGE DATA\n");

         // We can count the number of p's and q's by replacing each 
         // occurance with an empty string. We will make use of String's
         // replaceAll. This method will return a new string with each 
         // occurance of the passed regex replaces with the second parameter.
         // We will not use such fancy regex and only pass it the letter we 
         // want to replace. However learning the regex can be valuable 
         // for solving other problems so I recommend reading up on it.
         // After using the replaceAll the answer is just the original
         // string length minus the string with the character removed.
         int nP = s.length() - s.replaceAll("P","").length();
         int nQ = s.length() - s.replaceAll("Q","").length();
         System.out.printf("%d P's, %d Q's%n", nP, nQ);
      }
   }
}
