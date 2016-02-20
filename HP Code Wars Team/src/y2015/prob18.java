// Sebastian Schagerer
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class prob18 {

    private static final int DEBUG = 1;
    private int rows = 0;
    private int cols = 0;
    private Point start = null;
    private Point end = null;
    private List<Point> visited = null;
    private char[][] output = null;
    private int count;
    public prob18() {
        int[][] input = readInput();
        
        start = find(input, 10);
        end = find(input, -1);
        
        if (DEBUG > 1) System.out.println("start " + start.toString());
        if (DEBUG > 1) System.out.println("end " + end.toString());
        
        if (DEBUG > 1) System.out.println("r" + rows + " c" + cols);
        visited = new ArrayList<>();
        visited.add(start);
        
        output = new char[rows][cols];
        for (int r=0;r<rows;r++) {
            for (int c=0;c<cols;c++) {
                output[r][c] = '#';
            }
        }
        count =0;
        rollBoulder(input, new Point(start));
        output[start.y][start.x] = 'S';
        output[end.y][end.x] = 'X';
        
        for (int r=0;r<rows;r++) {
            for (int c=0;c<cols;c++) {
                System.out.print(output[r][c] + " ");
            }
            System.out.println();
        }
    }
    
    private boolean rollBoulder(int[][] grid, Point location) {
        
        if (DEBUG > 1) System.out.println("rollBoulder " + location.toString());
        if (location.equals(end)) {
            return true;
        }
        
        // roll east
        if (DEBUG > 1) System.out.println("roll east from " + location.toString());
        if (true == roll(grid, location, new Point(1, 0))) {
            return true;
        }
        
        // roll west
        if (DEBUG > 1) System.out.println("roll west from " + location.toString());
        if (true == roll(grid, location, new Point(-1, 0))) {
            return true;
        }
        
        // roll south
        if (DEBUG > 1) System.out.println("roll south from " + location.toString());
        if (true == roll(grid, location, new Point(0, 1))) {
            return true;
        }
        
        // roll north
        if (DEBUG > 1) System.out.println("roll north from " + location.toString());
        if (true == roll(grid, location, new Point(0, -1))) {
            return true;
        }
        return false;
    }
    
    private boolean roll(int[][] grid, Point location, Point offset) {
        boolean rolled = false;
        int height = grid[location.y][location.x];
        if (DEBUG > 1) System.out.println("height " + height);
        Point current = new Point(location);
        current.x += offset.x;
        current.y += offset.y;
        if (true == isInBounds(current)) {
            boolean v = visited.contains(current);
            if (DEBUG > 1) System.out.println("try " + current.toString() + "visited " + v);
            if (false == v
                    && true == isDownhill(grid, height, current)) {
                if (DEBUG > 1) System.out.println("roll to > " + location.toString());
                visited.add(current);
                rolled = rollBoulder(grid, new Point(current));
                if (false == rolled) {
                    visited.remove(current);
                }
                else {
                    //grid[current.y][current.x] = 10;
                    output[current.y][current.x] = '.';
                }
            }
        }
        else {
            if (DEBUG > 1) System.out.println("out of bounds" + current.toString());
        }
        
        return rolled;
    }
    
    private boolean isDownhill(int[][] grid, int height, Point location) {
        
        boolean isDown = false;
        int newHeight = grid[location.y][location.x];
        if (DEBUG > 1) System.out.println("compare h " + height + " to n " + newHeight);
        if (newHeight <= height) {
            isDown = true;
        }
        
        return isDown;
    }
    private boolean isInBounds(Point location) {
        boolean inBounds = false;
        if (location.x >= 0 && location.x < cols
                && location.y >= 0 && location.y < rows) {
            inBounds = true;
        }
        return inBounds;
    }
    
    private Point find(int[][] grid, int find) {
        Point location = null;
        for (int r=0;r<grid.length;r++) {
            for(int c=0;c<grid[r].length;c++) {
                if (grid[r][c] == find) {
                    location = new Point(c, r);
                } 
            }
        }
        return location;
    }
    private int[][] readInput() {
        
        int[][] input = null;
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
            String[] parts = line.split(" ");
            rows = new Integer(parts[0]).intValue();
            cols = new Integer(parts[1]).intValue();
            
            input = new int[rows][cols];
            
            for(int r = 0; r<rows; r++) {
                line = stdIn.readLine();
                parts = line.split(" ");
                for (int c = 0; c<cols; c++) {
                    if (parts[c].equals("X")) {
                        input[r][c] = -1;
                    }
                    else if (parts[c].equals("S")) {
                        input[r][c] = 10;
                    } else {
                        input[r][c] = new Integer(parts[c]).intValue();
                    }
                }
            }
            
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
        
        return input;
    }
    public static void main(String[] args) {
        new prob18();
    }
}
