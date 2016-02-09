
import java.util.*;
import java.io.*;

// In this problem we must convert cups to tablespoons
// it is given that there are 16 tablespoons in 1 cup so
// therefore we must convert all cups to tablespoons by
// multiplying the number of cups by 16. There can't possibly
// be an easier problem! can there? I mean I guess there could
// be a problem about converting cups to tablespoons back to cups
// where you would then just have to print out the input, but
// that would be silly and we here at Sample Problem's Inc. will 
// not take part in such sillyness.
public class pizza
{
   public static void main(String[] args) throws Exception
   {
      new pizza(new Scanner(new File("pizza.in")));
   }

   public pizza(Scanner in)
   {
      int n = in.nextInt();
      while (n-->0) 
         System.out.printf("%d%n", in.nextInt()*16);
   }
}
