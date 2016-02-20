import java.io.*;
import java.util.*;
public class stapler {
	public static int MAX_STACK = 30;
	public static void main(String[] Args) throws IOException {
		Scanner input = new Scanner(new File("stapler.in"));
		
		//read in the total number of cases
		int num_cases = input.nextInt();
		
		//loop through all cases
		for(int count = 1; count <= num_cases; count++) {
			
			//read in the amount of stacks
			int num_amounts = input.nextInt();
			
			//initialize the stack size
			int stack_size = 0;
			
			//count up all the stack sizes
			for(int amount_index = 0; amount_index < num_amounts; amount_index++) {
				
				//read in stack size and add it
				int current_amount = input.nextInt();
				stack_size = stack_size+ current_amount;
			}
			
			//output result
			if(stack_size > MAX_STACK){
				
				//case where there are too many pages
				System.out.printf("Battle #%d:%n", count);
				System.out.printf("The Stapler must face %d pages%n", stack_size);
				System.out.printf("Foiled again!%n");
			}
			else {

				//case where the stapler was successful
				System.out.printf("Battle #%d:%n", count);
				System.out.printf("The Stapler must face %d pages%n", stack_size);
				System.out.printf("The Stapler saves the day!%n");
			}
			
			//output new line if we are not on the last case
			if(count != num_cases) {
				
				System.out.printf("%n");
			}
		}
	}
}
