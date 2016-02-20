import java.util.*;
public class eggs {
	public static void main(String[] args){
		Scanner br = new Scanner(System.in);
		int numberOfTestCases = br.nextInt();
		for(int i = 1;i<=numberOfTestCases;i++){
			int age = br.nextInt();
			int weight = br.nextInt();
			
			//If he is already as large as a barge he eats no eggs
			if(weight == 350000){
				System.out.println("Breakfast #"+i+": 0");
			}
			//If he is less than 18 years old he eats 4 dozen eggs
			else if(age < 18){
				System.out.println("Breakfast #"+i+": 4");
			}
			//Otherwise he eats 5 dozen eggs
			else{
				System.out.println("Breakfast #"+i+": 5");
			}
		}
	}
}
