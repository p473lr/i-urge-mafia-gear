
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

/*
 * The solution in this class is to first convert the maze into a two 
 * dementional integer array where open spaces are represented by 0, the 
 * starting place by -1, fire by -2 and walls by -3.  Then the simulation 
 * starts by putting a 1 in the starting location, and then a 2 in any open or 
 * fire places next to that and then a 3 in any place next to those, and so 
 * on, until the number is equal to the length of the fire hose.  Finally it 
 * converts the maze back to its string representation.
 */
public class prob12
{
    public static final int OPEN = 0;
    public static final int START = -1;
    public static final int FIRE = -2;
    public static final int WALL = -3;

    /**
     * Run the simulation.  The results will be stored in the given maze.
     * @param maze The maze in integer form to run the simulation on.
     * @param hoseLength The length of the fire hose.
     */
    public static final void runSimulation(int[][] maze, int hoseLength)
    {
        if (hoseLength < 1) return;

        int[] start = findStart(maze);
        maze[start[0]][start[1]] = 1;
 
        LinkedList points = new LinkedList();
        points.add(start);
        while (!points.isEmpty())
        {
            int[] point = (int[])points.removeFirst();
            int length = maze[point[0]][point[1]] + 1;

            if (length > hoseLength) continue;

            ArrayList connectedPoints = connectedPoints(point, maze);
            for (Iterator iter = connectedPoints.iterator(); iter.hasNext(); )
            {
                int[] p = (int[])iter.next();
                int value = maze[p[0]][p[1]];
                if (value == FIRE || value == OPEN)
                {
                    maze[p[0]][p[1]] = length;
                    points.add(p);
                }
            }
        }
    }

    /**
     * Returns all the points that are directly adjecent to the given point and
     * are in the maze.
     *
     * @param point The point in the map to return the connecting points for.
     * @param maze The maze.
     */
    public static final ArrayList connectedPoints(int[] point, int[][] maze)
    {
        int xlen = maze.length;
        int ylen = maze[0].length;

        ArrayList points = new ArrayList();
        int x = point[0];
        int y = point[1];
        if (x+1 < xlen) points.add(new int[] {x+1, y});
        if (x-1 > -1) points.add(new int[] {x-1, y});
        if (y+1 < ylen) points.add(new int[] {x, y+1});
        if (y-1 > -1) points.add(new int[] {x, y-1});
        return points;
    }

    /**
     * Returns the point for the start location in the maze.
     */
    public static final int[] findStart(int[][] maze)
    {
        for (int x = 0; x < maze.length; ++x)
        {
           for (int y = 0; y < maze[x].length; ++y)
           {
               if (maze[x][y] == START) return new int[] {x, y};
           }
        }
        throw new IllegalArgumentException("Could not find the start in the maze.");
    }

    /**
     * Returns true if there are not fires still in the maze.
     */
    public static final boolean isFireOut(int[][] maze)
    {
        for (int x = 0; x < maze.length; ++x)
        {
            for (int y = 0; y < maze[x].length; ++y)
            {
                if (maze[x][y] == FIRE) return false;
            }
        }
        return true;
    }
    
    /**
     * Parses the input and returns the length of the fire hose.
     */
    public static final int getHoseLength(ArrayList lines)
    {
        String[] firstLine = ((String)lines.get(0)).split(" ");
        return Integer.parseInt(firstLine[2]);
    }

    /**
     * Creates an interger representation of the maze.
     * @param lines The input from the user that contains the maze in 
     * charactor form.
     */
    public static final int[][] createMaze(ArrayList lines)
    {
        String[] firstLine = ((String)lines.get(0)).split(" ");
        int xlen = Integer.parseInt(firstLine[0]);
        int ylen = Integer.parseInt(firstLine[1]);

        int[][] maze = new int[xlen][ylen];
        for (int y = 0, n = lines.size()-1; y < n; ++y)
        {
            char[] chars = ((String)lines.get(y+1)).toCharArray();
            for (int x = 0; x < chars.length; ++x)
            {
                switch (chars[x])
                {
                    case '#':
                        maze[x][y] = WALL;
                        break;
                    case ' ':
                        maze[x][y] = OPEN;
                        break;
                    case 'F':
                        maze[x][y] = FIRE;
                        break;
                    case 'S':
                        maze[x][y] = START;
                        break;
                    default:
                        throw new IllegalArgumentException("Did not reconize maze element " + chars[x]);
                }
            }
        }
        return maze;
    }

    /**
     * Returns the string representation of the the given maze.
     */
    public static final String toString(int[][] maze)
    {
        StringBuffer str = new StringBuffer();
        for (int y = 0; y < maze[0].length; ++y)
        {
            for (int x = 0; x < maze.length; ++x)
            {
                switch (maze[x][y])
                {
                    case WALL:
                        str.append('#');
                        break;
                    case OPEN:
                        str.append(' ');
                        break;
                    case FIRE:
                        str.append('F');
                        break;
                    case START:
                        str.append(' ');
                        break;
                    default:
                        str.append('.');
                }
            }
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Reads the input from the user and returns the input in a list where
     * each element is one line of input.
     */
    public static ArrayList input() throws IOException
    {
        BufferedReader in = 
            new BufferedReader(new InputStreamReader(System.in));

        ArrayList lines = new ArrayList();
        String line = in.readLine();
        while (line != null)
        {
            lines.add(line);
            line = in.readLine();
        }
        return lines;
    }

    public static void main(String args[]) throws IOException
    {
        ArrayList lines = input();
        int hoseLength = getHoseLength(lines);
        int[][] maze = createMaze(lines);

        runSimulation(maze, hoseLength);

        System.out.print(toString(maze));
        if (isFireOut(maze)) System.out.println("All Fires Extinguished!");
    }
}
