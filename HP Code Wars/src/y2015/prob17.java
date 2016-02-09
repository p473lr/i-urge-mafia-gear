package y2015;

// Sebastian Schagerer
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class prob17 {

    private static final int DEBUG = 1;
    private List<String> input = null;
    private int turns;
    private List<Point> points = null;
    private int lowY;
    private int lowX;

    public prob17() {
        readInput();

        for (String line : input) {
            String[] parts = line.split(" ");
            String direction = parts[0];
            String endPoint = parts[1];
            char cEndPoint = endPoint.charAt(0);
            int iEndPoint = 0;
            if (cEndPoint > 'A') {
                iEndPoint = (cEndPoint - 'A') + 10;
            } else {
                iEndPoint = new Integer(endPoint).intValue();
            }

            if (DEBUG > 1) System.out.println("start dir " + direction + " end point "
                    + iEndPoint);
            turns = 0;
            points = new ArrayList<Point>();
            lowX = 0;
            lowY = 0;
            String path = printPath(direction, iEndPoint);
            if (DEBUG > 1) System.out.println(points.size());
            int height = calculateHeight();
            int width = calculateWidth();
            if (DEBUG > 1) System.out.println(path + " turns " + turns + " h " + height
                    + " w " + width);
            if (DEBUG > 1) System.out.println();

            char grid[][] = new char[height][width];

            // TODO create grid of size height x width
            for (int r = 0; r < height; r++) {
                grid[r] = new char[width];
                for (int c = 0; c < width; c++) {
                    grid[r][c] = '.';
                }
            }

            // start point is top + lowY, and left + lowX
            Point start = new Point((-1 * lowX), (-1 * lowY));
            if (DEBUG > 1) System.out.println(start.toString());
            if (DEBUG > 1) System.out.println(points.get(0).toString());
            grid[start.y][start.x] = '0';
            // printGrid(grid);

            Point current = new Point(start);
            for (int p = 1; p < path.length(); p++) {
                char c = path.charAt(p);

                if (c == '\\') {
                    current.x += 1;
                    current.y += 1;
                    grid[current.y][current.x] = c;
                    current.x += 1;
                    current.y += 1;

                } else if (c == '-') {
                    current.x -= 1;
                    grid[current.y][current.x] = c;
                    current.x -= 1;
                    grid[current.y][current.x] = c;
                    current.x -= 1;
                    grid[current.y][current.x] = c;
                    current.x -= 1;
                    p += 2;
                } else if (c == '/') {
                    current.y -= 1;
                    current.x += 1;
                    grid[current.y][current.x] = c;
                    current.y -= 1;
                    current.x += 1;
                } else {
                    grid[current.y][current.x] = c;
                }

                // System.out.println();
            }
            printGrid(grid);
            System.out.println();
        }
    }

    private void printGrid(char grid[][]) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                System.out.print(grid[r][c]);
            }
            System.out.println();
        }
    }

    private String printPath(String startDir, int end) {

        String direction = startDir;
        int changeDirectionCount = 0;
        int changeDirectionIncr = 2;
        StringBuilder sb = new StringBuilder();
        Point currentLocation = new Point(0, 0);

        for (int index = 0; index < end; index++) {
            // sb.append("[");
            // sb.append(currentLocation.x);
            // sb.append(",");
            // sb.append(currentLocation.y);
            // sb.append("]");
            points.add(currentLocation);
            currentLocation = new Point(currentLocation);

            if (index > 9) {
                char c = (char) ('A' + (index - 10));
                sb.append(c);
            } else {
                sb.append(index);
            }
            if (direction.equals("-")) {
                sb.append(direction);
                sb.append(direction);
                sb.append(direction);
                currentLocation.x -= 4;

            } else {
                sb.append(direction);

                if (direction.equals("/")) {
                    currentLocation.x += 2;
                    currentLocation.y -= 2;
                } else if (direction.equals("\\")) {
                    currentLocation.x += 2;
                    currentLocation.y += 2;
                }
            }
            // sb.append(" ");

            if (index == changeDirectionCount) {
                direction = getNextDirection(direction);
                changeDirectionCount += changeDirectionIncr;
                changeDirectionIncr++;
                turns++;
            }

        }
        points.add(currentLocation);
        // sb.append("[");
        // sb.append(currentLocation.x);
        // sb.append(",");
        // sb.append(currentLocation.y);
        // sb.append("]");

        if (end > 9) {
            char c = (char) ('A' + (end - 10));
            sb.append(c);
        } else {
            sb.append(end);
        }
        return sb.toString();
    }

    private int calculateHeight() {

        int height = 0;
        int highY = 0;

        for (Point p : points) {
            if (p.y < lowY) {
                lowY = p.y;
            }

            if (p.y > highY) {
                highY = p.y;
            }
        }
        height = (-1 * lowY) + highY + 1;
        return height;
    }

    private int calculateWidth() {

        int width = 0;
        int highX = 0;

        for (Point p : points) {
            if (p.x < lowX) {
                lowX = p.x;
            }

            if (p.x > highX) {
                highX = p.x;
            }
        }

        width = (-1 * lowX) + highX + 1;
        return width;
    }

    private String getNextDirection(String direction) {

        String nextDir = null;
        if (direction.equals("/")) {
            nextDir = "\\";
        } else if (direction.equals("\\")) {
            nextDir = "-";
        } else if (direction.equals("-")) {
            nextDir = "/";
        }

        return nextDir;
    }

    private void readInput() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                    System.in));
            String line = stdIn.readLine();

            input = new ArrayList<String>();
            while (false == line.equals("# #")) {
                input.add(line);
                line = stdIn.readLine();
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new prob17();
    }
}
