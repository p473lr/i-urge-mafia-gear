import java.util.*;
public class fishytoo {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);		
		int t = in.nextInt();
		
		//This array contains the characters that can be used to create nuggets
		char[] useful = {'<', '>', '~'};
		
		for(int z = 1; z <= t; z ++){
			
			int height = in.nextInt();
			int width = in.nextInt();
			int nuggets = 0;
			
			//This loop goes through each row 
			for(int i = 0; i < height; i ++){
				String row = in.next();
				
				//This loop goes through each character of the current row
				for(int j = 0; j < width; j ++){
					
					//This loops checks if the current character is one of the useful characters
					for(int k = 0; k < 3; k ++){
						if(row.charAt(j) == useful[k])
							nuggets++;
					}
				}
			}
			System.out.println("Net #" + z + ": " + nuggets + " Fish Nuggets");
		}
	}
}
