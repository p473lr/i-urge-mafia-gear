import java.util.*;
import java.io.*;

public class batarang {
   private static String[] direction_already_thrown;
   private static int number_thrown;
   public static void main(String[] Args) throws Exception {
   
      //sc is our scanner that will read in the input
      Scanner sc = new Scanner(new File("batarang.in"));
      
      //number_of_cases will store the number of cases
      int number_of_cases = sc.nextInt();
      
      //this for loop loops through all the cases
      for(int cur_case = 0; cur_case < number_of_cases; cur_case++)
      {
         //number_of_enemies store the number of enemies we have to eliminate
         int number_of_enemies = sc.nextInt();
         
         //this stores the number of batarangs thrown
         number_thrown = 0;
         
         //this stores the direction we have thrown each batarang
         direction_already_thrown = new String[number_of_enemies];
         
         //loop through the enemies and check if we need to throw another batarang
         for(int enemy = 0; enemy < number_of_enemies; enemy++)
         {
            
            //read in the enemies x and y
            int enemy_x = sc.nextInt();
            int enemy_y = sc.nextInt();
            
            //reduce the x and y of the enemy
            int gcd = GCD(Math.abs(enemy_x), Math.abs(enemy_y));
            enemy_x = enemy_x / gcd;
            enemy_y = enemy_y / gcd;
            
            //convert x and y to a string direction
            String direction = enemy_x + ":" + enemy_y;
            
            //loop through the already thrown batarangs and see if we hit this enemy already
            boolean has_hit = false;
            for(int batarang = 0; batarang < number_thrown; batarang++)
            {
               
               //check to see if we hit him with this batarang
               if(direction.compareTo(direction_already_thrown[batarang]) == 0)
               {
                  
                  //we hit him
                  has_hit = true;
               }
            }
            
            //if we have not hit him
            if(!has_hit)
            {
               
               //set direction of the newest batarang equal to the current direction
               direction_already_thrown[number_thrown] = direction;
               
               //increment the number of batarangs thrown by 1
               number_thrown++;
            }
         }
         
         //print the number of batarangs thrown if he only needs 1
         if(number_thrown == 1)
         {
            System.out.printf("Room #%d: Leaping lizards, %d birds with one stone!%n",cur_case+1, number_of_enemies);
         }
         else
         {
            System.out.printf("Room #%d: Batman will only need %d batarangs!%n",cur_case+1,number_thrown);
         }
         System.out.println();
      }
   }
   public static int GCD(int a, int b)
   {
      if(a == 0)
      {
         return b;
      }
      if(b == 0)
      {
         return a;
      }
      
      return GCD(b, a % b);
   }
}
