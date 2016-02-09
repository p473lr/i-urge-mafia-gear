import java.util.*;
import java.io.*;
public class census 
{
   private static boolean oldest, youngest, twins, triplets, middle;
   private static int number_of_children;
   
   private static boolean possible;
   private static int num_possible;
   private static ArrayList<Integer> correct_family;
   public static void main(String[] Args) throws IOException
   {
      //the input
      Scanner sc = new Scanner(new File("census.in"));
      
      //the number of cases
      int number_of_cases = sc.nextInt();
      
      //loop through all the cases
      for(int case_number = 0; case_number < number_of_cases; case_number++)
      {
         //Initialize the variables
         initializeVariables();
         
         //read in sum product and number of restrictions
         int sum = sc.nextInt();
         int product = sc.nextInt();
         int number_of_restrictions = sc.nextInt();
         
         for(int restriction = 0; restriction < number_of_restrictions; restriction++)
         {
            //remove the word has
            sc.next();
            
            //get the next word
            String next_word = sc.next().toUpperCase();

            //check if we have twins
            if(next_word.compareTo("TWINS") == 0)
            {
               
               //mark that we have twins
               twins = true;
               continue;
            }
            
            //check if we have triplets
            if(next_word.compareTo("TRIPLETS") == 0)
            {
               
               //mark that we have triplets
               triplets = true;
               continue;
            }
            
            //check if the word is oldest
            if(next_word.compareTo("OLDEST") == 0)
            {
               
               //we have an oldest child so mark it
               oldest = true;
               
               //read the last word
               sc.next();
               continue;
            }
            
            //check if the word is middle
            if(next_word.compareTo("MIDDLE") == 0)
            {
               
               //we have a middle child so mark it
               middle = true;
               
               //read the last word
               sc.next();
               continue;
            }
            
            //check to see if the word is youngest
            if(next_word.compareTo("YOUNGEST") == 0)
            {
               
               //we have an youngest child so mark it
               youngest = true;
               
               //read the last word
               sc.next();
               continue;
            }
            
            //last case is the restriction limits the number of children
            //get the number of children
            int temp_number_of_children = Integer.parseInt(next_word);
            
            //if it we already know how many children there are
            //check to see if our information is matches
            if(number_of_children != temp_number_of_children && number_of_children != -1)
            {
               
               //if it doesn't match label this case as impossible
               possible = false;
            }
            
            number_of_children = temp_number_of_children;
            sc.next();
         }
         
         //generate and check all the possible families
         generatePossibility(sum, product, new ArrayList<Integer>());
         
         //print the case number
         System.out.printf("House #%d:%n", case_number+1);
         
         //if the information is not possible then tell them
         if(!possible || (num_possible == 0))
         {
            
            //print impossible
            System.out.println("Contradictory Information");
         }
         
         //if we found exactly one solution
         if(num_possible == 1 && possible)
         {
            
            //print the family
            for(int child = 0; child < correct_family.size(); child++)
            {
               System.out.printf("%d ",correct_family.get(child));
            }
            
            System.out.println();
         }
         
         //if we found multiple solutions
         if(possible && num_possible > 1)
         {
            System.out.println("Not Enough Information");
         }

         System.out.println();
      }
   }


   private static void generatePossibility(int sum, int product, ArrayList<Integer> family) 
   {
      //if our sum is satisfied check the family
      if(sum == 0 && product == 1)
      {
         //checking
         check(family);
         return;
      }
      
      //if we can't have anymore kids and our product is incorrect return
      if(sum == 0 && product != 1)
      {
         return;
      }
      
      //can't have a child older than the sum
      int max_age = sum;
      
      //limit the next child's to the youngest child's age 
      if(family.size() != 0)
      {
         //change the max
         max_age = Math.min(max_age, family.get(family.size()-1));
      }
      
      //loop through possible ages
      for(int age = 1; age <= max_age; age++)
      {
         //check if this divides our product
         if(product % age == 0)
         {
            //add the possible age
            family.add(age);
            
            //recurse
            generatePossibility(sum - age, product / age, family);
            
            //remove that age
            for(int child = 0; child < family.size(); child++)
            {
               if(family.get(child) == age)
               {
                  family.remove(child);
               }
            }
         }
      }
   }


   private static void check(ArrayList<Integer> family) 
   {
      //sort children based on age
      Collections.sort(family);
      
      //check if we need a middle child
      if(middle)
      {
         //check if we can have a middle child
         if(family.size() % 2 == 0)
         {
            //if we can't this is not a valid family so return 
            return;
         }
         
         //check if our family size is greater than 1
         if(family.size() != 1)
         {
            //check if there is a unique middle child
            if(family.get(family.size()/2) == family.get((family.size()/2) - 1))
            {
               return;
            }
            if(family.get(family.size()/2) == family.get((family.size()/2) + 1))
            {
               return;
            }
         }
      }
      
      //check if there is an oldest child
      if(oldest)
      {
         //check if there is more than 1 child
         if(family.size() != 1)
         {
            //check if there is a unique oldest child
            if(family.get(family.size() - 1) == family.get(family.size() - 2))
            {
               return;
            }
         }
      }
      
      //check if there is a youngest child
      if(youngest)
      {
         //check if there is more than 1 child
         if(family.size() != 1)
         {
            
            //check if there is a unique youngest child
            if(family.get(0) == family.get(1))
            {
               return;
            }
         }
      }
      
      //check if there is a twins
      if(twins)
      {
         boolean has_twins = false;
         
         //go through the family and check for twins
         for(int child = 0; child < family.size(); child++)
         {
            //Initialize that we have not found any child with the current age group
            int number_of_children_with_current_age = 0;
            
            //go through each child and check if they match the current age group
            for(int other_child = 0; other_child < family.size(); other_child++)
            {
               
               if(family.get(child) == family.get(other_child))
               {
                  number_of_children_with_current_age++;
               }
            }
            
            //if we have found the correct number we have twins
            if(number_of_children_with_current_age == 2)
            {
               has_twins = true;
            }
         }
         
         //if we found no twins 
         if(!has_twins)
         {
            return;
         }
      }

      //check if there is a twins
      if(triplets)
      {
         boolean has_triplets = false;
         
         //go through the family and check for triplets
         for(int child = 0; child < family.size(); child++)
         {
            
            //Initialize that we have not found any child with the current age group
            int number_of_children_with_current_age = 0;
            
            //go through each child and check if they match the current age group
            for(int other_child = 0; other_child < family.size(); other_child++)
            {
               
               if(family.get(child) == family.get(other_child))
               {
                  number_of_children_with_current_age++;
               }
            }
            
            //if we have found the correct number we have triplets
            if(number_of_children_with_current_age == 3)
            {
               has_triplets = true;
            }
         }
         
         //if we found no triplets
         if(!has_triplets)
         {
            return;
         }
      }

      //make sure there is the correct number of children
      if(number_of_children != -1)
      {
         //verify the number of children
         if(number_of_children != family.size())
         {
            return;
         }
      }
      
      //our family has passed the check add one to the number of possibilities
      num_possible++;
      correct_family = (ArrayList<Integer>) family.clone();
   }
   
   //initialize all the things
   private static void initializeVariables() 
   {
      oldest = false;
      youngest = false;
      twins = false;
      triplets = false;
      middle = false;
      number_of_children = -1;
      num_possible = 0;
      possible = true;
   }
}
