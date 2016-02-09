package com.hp.codewars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A solution to the Diffraction Grating problem.
 */
public class DiffractionGrating
{
    /**
     * The number of nanometers in a millimeter
     */
    public static final double NM_IN_MM = 1000000;
    
    public static final List<String> input() throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<String>();
        String line = in.readLine();
        while (line != null)
        {
            lines.add(line);
            line = in.readLine();
        }
        return lines;
    }
    
    /**
     * This returns the doubles that in the given string.  It assumes that 
     * there are exactly three doubles in the given string separated by spaces.
     */
    public static final double[] toDoubles(String line)
    {
        String[] strings = line.split(" ");
        double[] doubles = new double[3];
        doubles[0] = Double.parseDouble(strings[0]);
        doubles[1] = Double.parseDouble(strings[1]);
        doubles[2] = Double.parseDouble(strings[2]);
        return doubles;
    }

    /**
     * We know from the problem statement that sin t = lamda / d (assuming that 
     * m = 1).  By the definition of sine and cosine we know the following is 
     * true for the given triangle.
     * 
     *   sin t = lamda / d
     *   cos t = x / d
     * 
     *         /|
     *        / |
     *    d  /  | lamda
     *      /   |
     *     /    |
     *    /     |
     *   --------
     *      x
     * 
     * where t is the angle between side h and b.
     * 
     * The pythagorean theorem tells us that x ^ 2 + lamda ^ 2 = d ^ 2.  
     * Which follows that x = squareRoot(d ^ 2 - lamda ^ 2).  
     * 
     * Finally:
     * 
     * cos t = squareRoot(d ^ 2 - lamda ^ 2) / d
     * 
     */
    public static final double cosine(double lamda, double d)
    {
        return Math.sqrt((d * d) - (lamda * lamda)) / d;
    }
    
    public static final double caculateY(double lamda, double distance, double linesPerMm)
    {
        double d = NM_IN_MM / linesPerMm;
        double cosine = cosine(lamda, d);
        return (lamda * distance) / (d * cosine);
    }
    
    public static void main(String[] args) throws IOException
    {
        List<String> lines = input();
        for (String line : lines)
        {
            double[] doubles = toDoubles(line);
            double y = caculateY(doubles[0], doubles[1], doubles[2]);
            System.out.printf("%.3f\n", y);
        }
    }
}
