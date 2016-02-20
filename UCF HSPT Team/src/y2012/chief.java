import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;


public class chief 
{
   public static final double PI = 3.141592653589793;

   public static void main(String[] args)throws IOException
   {
      // Open the input file
      Scanner br=new Scanner(new File("chief.in"));

      // Read in number of problem sets to process
      int cases=br.nextInt();
      for(int c=1;c<=cases;c++)
      {
         // Read in the number of problems and initial ball radius
         int probs=br.nextInt();
         double radius=br.nextDouble();
         
         // We want to calculate our next radius based on our current volume
         double volume=radius*radius*radius*4/3.0*PI;
         
         // Read the problem (block) specifications
         Problem[] problems=new Problem[probs];
         for(int i=0;i<probs;i++)
            problems[i]=new Problem(br.nextDouble(),br.nextDouble(),br.nextDouble());
         
         // If we sort the problems by increasing center of mass, we can greedily
         // choose the next one in the array to roll over (if it is possible)
         // If it isn't possible to roll over the next one, then it will be impossible
         // to roll over any later ones since the later ones have a higher center of mass
         // than our current one.
         Arrays.sort(problems);
         boolean good=true;
         for(int i=0;i<probs&&good;i++)
         {
            // Can we add this one?
            if(radius>problems[i].center)
            {
               // We simply add the volume of the box, then find our new radius
               volume+=problems[i].volume;
               
               // V=r^3*4pi/3, so r=cube root of ((3/4)*V/pi)
               radius=Math.cbrt(volume*(3.0/4)/PI);
            }
            // If we couldn't roll over it, the set is unsolvable
            else
               good=false;
         }
         if(good)
            System.out.println("Problem Set #"+c+": It's going to be a good set!");
         else
            System.out.println("Problem Set #"+c+": We need to rebuild this!");
      }
   }
   
   //If we make a new class that implements Comparable, we don't need to write our own
   //sorting method, Arrays.sort takes care of that for us.
   public static class Problem implements Comparable<Problem>
   {
      double length,width,height;
      double volume;
      double center;
      public Problem(double l,double w,double h)
      {
         length=l;
         width=w;
         height=h;
         volume=1.0*l*w*h;
         center=h/2.0;
      }
      
      //All classes that implement Comparable must have a compareTo function which
      //returns a negative number if this object comes before the parameter object,
      //positive if this comes after the parameter, or 0 if they are equal. Since we
      //only care about sign, we take the sign of the difference between this and the
      //parameter's center of masses, if we are smaller then the difference will be
      //negative (and therefore, this comes first, like we want it to).
      public int compareTo(Problem o)
      {
         return Double.compare(center, o.center);
      }
   }
}
