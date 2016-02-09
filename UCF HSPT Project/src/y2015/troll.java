import java.util.*;
public class troll {
	public static void main(String[] args){
		Scanner br = new Scanner(System.in);
		int numberOfTestCases = br.nextInt();
		for(int i = 1;i<=numberOfTestCases;i++){
			long strength = br.nextLong();
			long shipmentSize = br.nextLong();
			
			
			long ans = 0;
			//Once his strength is reduced to zero it will stay zero and he won't
			//be able to stop anymore shipments
			while(strength > 0){
				
				//If his strength is odd he stops the current shipment
				//so shipmentSize is added to answer
				if(strength % 2 == 1){
					ans+=shipmentSize;
				}
				
				//Decrease strength and increase shipment size as described in the problem
				strength/=2;
				shipmentSize*=2;
			}
			
			//Note - this problem can also be solved by just multiplying the two original
			//input numbers as the process above is just a different way of multiplying numbers
			
			System.out.println("Situation #"+i+": "+ans);
			
		}	
			
	}
}
