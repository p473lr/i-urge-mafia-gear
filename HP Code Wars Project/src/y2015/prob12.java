package y2015;

// Sebastian Schagerer
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class prob12 {

    private static final int DEBUG = 1;
    private double grid[][];
    private int size;
    private Point steep1;
    private Point steep2;
    private double steepest = 0;
    private static List<Point> straights;
    private static List<Point> diagonals;
    
    public prob12() {
        readInput();
        checkAdjacent();
        
        System.out.println(steepest);
        if (DEBUG > 1) {
            System.out.println(steep1);
            System.out.println(steep2);
        }
    }
    
    private void checkAdjacent() {
        
        List<Point> grid = prob12.straightOffsets();
        List<Point> diagonals = prob12.diagonalOffsets();
        for(int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                
                Point location = new Point(c, r);
                for (Point straight : grid) {
                    updateAngle(location, straight, false);
                }

                for (Point diagonal : diagonals) {
                    updateAngle(location, diagonal, true);
                }
            }
        }
    }

    private void updateAngle(Point location, Point offset, boolean isDiagonal) {

        Point adjacent = new Point(location);
        adjacent.x += offset.x;
        adjacent.y += offset.y;
        
        if (true == isInBounds(adjacent)) {
            double angle = calcAngle(grid[location.y][location.x], grid[adjacent.y][adjacent.x], isDiagonal);
            if (angle > steepest) {
                steepest = angle;
                steep1 = new Point(location);
                steep2 = new Point(adjacent);
            }
            if (DEBUG > 1) System.out.println("angle " + angle);
        }
    }
    
    private boolean isInBounds(Point aPoint) {
        
        boolean inBounds = false;
        if (aPoint.x > 0 && aPoint.x < size
                && aPoint.y > 0 && aPoint.y < size) {
            inBounds = true;
        }
        return inBounds;
    }
    
    private static List<Point> straightOffsets() {
        
        if (straights == null) {
            straights = new ArrayList<Point>();
            straights.add(new Point(0, -1));
            straights.add(new Point(-1, 0));
            straights.add(new Point(1, 0));
            straights.add(new Point(0, 1));
            
        }
        return straights;
    }

    private static List<Point> diagonalOffsets() {

        if (diagonals == null) {
            diagonals = new ArrayList<Point>();
            diagonals.add(new Point(-1, -1));
            diagonals.add(new Point(1, -1));
            diagonals.add(new Point(-1, 1));
            diagonals.add(new Point(1, 1));
        }
        return diagonals;
    }

    private double calcAngle(double h1, double h2, boolean isDiagonal) {
        
        double angle = 0;
        double max = Math.max(h1, h2);
        double min = Math.min(h1, h2);
        
        //double a = (max - min);
        double a = (h1 - h2);
        double b = 1;

        if (isDiagonal) {
            b = Math.sqrt(2);
        }
        double c = Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2)));
        
        double sinTheta = (a/c);
        double thetaRadians = Math.asin(sinTheta);

        angle = Math.toDegrees(thetaRadians);
        
        if (DEBUG > 1) {
            System.out.println("h0 " + h1);
            System.out.println("h1 " + h2);
            System.out.println("a " + a);
            System.out.println("b " + b);
            System.out.println("c " + c);
            System.out.println("sin theta " + sinTheta);
            System.out.println("theta r" + thetaRadians);
            System.out.println("theta d" + angle);
        }
        return angle;
    }
    
    public static void main(String[] args) {
        new prob12();
    }
    
    private void readInput() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
        
            size = new Integer(line).intValue();
            grid = new double[size][size];
            
            for (int r = 0; r < size; r++) {
                line = stdIn.readLine();
                String[] parts = line.split("\\s+");
                for (int c = 0; c < size; c++) {
                    grid[r][c] = new Double(parts[c]).doubleValue();
                }
            }
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
    
}
