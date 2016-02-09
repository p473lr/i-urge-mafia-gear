import java.util.*;
public class flowers {
	public static void main(String[] args){
		Scanner br = new Scanner(System.in);
		int numberOfTestCases = br.nextInt();
		for(int i = 1;i<=numberOfTestCases;i++){
			int numberOfAnimals = br.nextInt();
			
			//The animal with the slowest throw speed will be the bottleneck 
			//for getting the flowers through the assembly line so we just need
			//to keep track of this value
			int minThrowSpeed = 1000;
			for(int j = 0;j<numberOfAnimals;j++){
				String name = br.next();
				int throwSpeed = br.nextInt();
				
				//The last animals throw speed doesn't matter since it does not need to throw to anyone
				if(j != numberOfAnimals-1){
					minThrowSpeed = Math.min(minThrowSpeed, throwSpeed);
				}
			}
			
			System.out.println("Assembly #"+i+": "+minThrowSpeed);
		}
	}
}
