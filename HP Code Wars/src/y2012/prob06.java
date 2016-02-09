import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class prob06
{
    public static final int A = 2;
    public static final int B = 1;
    public static final int C = 0;
    public static final int AREA = 3;
    public static final int PERIMETER = 4;
    public static final int SQR_B = 3;
    public static final int SQR_A = 4;
    public static final int AREA_NOT_AN_INTEGER = -1;
    
    /**
     * Reads the input from standard input.
     * @return A list of all non-empty lines in the input.
     * @throws IOException
     */
    public static final List<String> input() throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<String>();
        String line = in.readLine();
        while (line != null)
        {
            if (!line.trim().isEmpty()) lines.add(line);
            line = in.readLine();
        }
        return lines;
    }
    
    /**
     * Calculates the area of a triangle.
     * @param a One of the triangle's sides.
     * @param b One of the triangle's sides.
     * @param c One of the triangle's sides.
     * @return The area of the triangle or AREA_NOT_AN_INTEGER, if the area is 
     * not a whole integer.
     */
    public static final long area(long a, long b, long c)
    {
        long sNumerator = a + b + c;
        if ((sNumerator % 2) != 0) return AREA_NOT_AN_INTEGER;
        
        long s = sNumerator / 2;
        long square = s * (s - a) * (s - b) * (s - c);
        double area = Math.sqrt(square);
        if (Math.floor(area) == area)
        {
            return (long) area;
        }
        return AREA_NOT_AN_INTEGER;
    }
    
    /**
     * Takes a range for the triangles and returns a list for all the longest 
     * sides in that range.
     * @param smallest The smallest side in the range.
     * @param largest The largest side in the range.
     * @return
     */
    public static final List<Long> toLongestSides(long smallest, long largest)
    {
        List<Long> sides = new ArrayList<Long>();
        for(long side = smallest; side <= largest; ++side)
        {
            sides.add(side);
        }
        return sides;
    }
    
    /**
     * Converts the input string into two numbers.  The first number is the 
     * longest side for the smallest triangle.  The second number is the 
     * longest side for the largest triangle.
     * @param line The input string.
     * @return An array containing two values.
     */
    public static final long[] toRange(String line)
    {
        String[] numbers = line.split("\\s+");
        return 
            new long[] 
            {
                Long.valueOf(numbers[0].trim()),
                Long.valueOf(numbers[1].trim()) 
            };
    }
    
    /**
     * Returns all the triangles with the given longest side.
     * @param longestSide The longest side of the triangles.
     * @return The triangles.  Each array in the list contains the three sides
     * of the given triangle.  The sides are listed in the array from shortest 
     * to longest.
     */
    public static final List<long[]> toTriangles(long longestSide)
    {
        List<long[]> triangles = new ArrayList<long[]>();
        long b = longestSide - 1;
        long c = b;
        while ((b + c) > longestSide)
        {
            while ((b + c) > longestSide)
            {
                long[] triangle = new long[3];
                triangle[C] = c;
                triangle[B] = b;
                triangle[A] = longestSide;
                triangles.add(triangle);
                --c;
            }
            --b;
            c = b;
        }
        return triangles;
    }
    
    /**
     * Takes in a list of triangles and returns only the heronian triangles 
     * from the initial list.
     * @param triangles The triangles.  Each array in the list contains the 
     * three sides of the triangle.
     * @return The heronian triangles.  Each array in the list contains the 
     * three sides of the triangle followed by the area and perimeter of the 
     * triangle.  Only the heronian triangles are returned.
     */
    public static final List<long[]> toHeronianTriangles(List<long[]> triangles)
    {
        List<long[]> heronians = new ArrayList<long[]>();
        for (long[] triangle : triangles)
        {
            long c = triangle[C];
            long b = triangle[B];
            long a = triangle[A];
            long area = area(a, b, c);
            if (area != AREA_NOT_AN_INTEGER)
            {
                long perimeter = a + b + c;
                long[] heronian = new long[5];
                heronian[C] = c;
                heronian[B] = b;
                heronian[A] = a;
                heronian[AREA] = area;
                heronian[PERIMETER] = perimeter;
                heronians.add(heronian);
            }
        }
        return heronians;
    }
    
    /**
     * Returns the heronian triangle and associated rectangle for the given 
     * triangle.  The rectangle will have the same area and perimeter as the
     * triangle.
     * @param heronianTriangles The heronian triangles and rectangles.  Each 
     * array in the list contains the three sides of the triangle followed by
     * the sides of the rectangle.
     * @return The heronain triangles with associated rectangle. Each array in 
     * the list contains the sides of the triangle followed by the sides of 
     * the rectangle.
     */
    public static final List<long[]> toHeronianPairs(List<long[]> heronianTriangles)
    {
        List<long[]> pairs = new ArrayList<long[]>();
        for (long[] triangle : heronianTriangles)
        {
            long longSide = triangle[A];
            long area = triangle[AREA];
            long perimeter = triangle[PERIMETER];
            while ((area / longSide) < longSide)
            {
                if ((area % longSide) == 0)
                {
                    long shortSide = area / longSide;
                    if ((2 * (longSide + shortSide)) == perimeter)
                    {
                        long[] pair = new long[5];
                        pair[C] = triangle[C];
                        pair[B] = triangle[B];
                        pair[A] = triangle[A];
                        pair[SQR_B] = shortSide;
                        pair[SQR_A] = longSide;
                        pairs.add(pair);
                    }
                }
                --longSide;
            }
        }
        return pairs;
    }
    
    public static String heronianPairToString(long[] pair)
    {
        return String.format("(%d, %d, %d) (%d, %d)", pair[C], pair[B], pair[A], pair[SQR_B], pair[SQR_A]);
    }
    
    public static void main(String[] args) throws IOException
    {
        List<String> lines = input();
        
        long[] longestSideRange = toRange(lines.get(0));
        List<Long> longestSides = toLongestSides(longestSideRange[0], longestSideRange[1]);
        
        for (Long longestSide : longestSides)
        {
            List<long[]> triangles = toTriangles(longestSide);
            List<long[]> heronianTriangles = toHeronianTriangles(triangles);
            List<long[]> heronianPairs = toHeronianPairs(heronianTriangles);
            for (long[] heronianPair : heronianPairs)
            {
                System.out.println(heronianPairToString(heronianPair));
            }
        }
    }
}
