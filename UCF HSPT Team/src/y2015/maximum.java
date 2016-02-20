import java.util.*;

public class maximum 
{
   public static void main(String[] args) 
   {
      Scanner in = new Scanner(System.in);

      int max = 0;
      int N = in.nextInt();
      for (int i=0; i<N; i++) {

         int x = in.nextInt();

         // If this is the highest number so far, output it and mark
         // it as our new max
         if (x > max)
         {
            System.out.printf("%d!%n", x);
            max = x;
         }
      }
   }
}
