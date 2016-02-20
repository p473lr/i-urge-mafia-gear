import java.util.*;
import java.io.*;
public class force {
    public static void main(String[] args)throws IOException{
        Scanner br = new Scanner(new File("force.in"));
        int t = br.nextInt();
        for(int i = 0;i<t;i++){
            int mass = br.nextInt();
            int acceleration = br.nextInt();
            int Force = mass*acceleration;
            System.out.println("Force #"+(i+1)+": "+Force);
        }
    }
}
