import java.util.*;
import java.io.*;
public class price {
    public static void main(String[] args)throws IOException{
        Scanner br = new Scanner(new File("price.in"));
        int t = br.nextInt();
        for(int i = 0;i<t;i++){
            int n = br.nextInt();
            //count the number of times each price occurs
            int[] freq = new int[1000001];
            for(int j = 0;j<n;j++){
                freq[br.nextInt()]++;
            }
            //find max amount of times any price occurs
            int max = 0;
            for(int j = 0;j<1000001;j++){
                if(freq[j] > max){
                    max = freq[j];
                }
            }
            //print out the price that occurs the max number of times
            for(int j = 0;j<1000001;j++){
                if(freq[j] == max){
                    System.out.println("Item #"+(i+1)+": Most likely price is "+j+" cents.");
                    System.out.println();
                }
            }
            
        }
    }
}
