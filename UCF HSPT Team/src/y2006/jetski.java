import java.io.*;
import java.lang.*;
import java.util.*;

public class jetski{

        public static void main(String[] args)throws Exception{

                BufferedReader In = new BufferedReader(new FileReader("jetski.in"));
                String input;
                StringTokenizer token;
                int numSet, set, speed, time;
                double area, PI = 3.1415926535898;

// Reads in the number of sets we're calculating
                input = In.readLine();
                numSet = Integer.parseInt(input);

                for(set = 1;set <= numSet;set++){

// reads in the data, then tokenizes it
                        input = In.readLine();
                        token = new StringTokenizer(input);

// gets the speed and time
                        speed = Integer.parseInt(token.nextToken());
                        time = Integer.parseInt(token.nextToken());

// The area the jetski can be in, is a semi-circle which has an area of  pi * r^2
// The radius is the total distance the jetski can cover in the time alloted.
// Because the time is in minutes and the speed is per hour, the time must be divided
// by 60.0 in order to get a decimal fraction in hours unit.
                        area = Math.pow( (time / 60.0) * speed, 2) * PI * .5;
                        System.out.printf("Data Set %d: %.2f\n", set, area);

                }


        }

}
