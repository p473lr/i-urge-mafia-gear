// Sebastian Schagerer
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class prob19 {

    private static int DEBUG = 1;

    private int numShips[] = new int[5];
    private int gridSize = 0;
    private char[][] shipGrid = null;
    private List<Ship> ships = null;

    public prob19() {
        readInput();
        //printShipGrid();

        ships = new ArrayList<Ship>();
        findShips();
        //printShipGrid();
        removeSubsumedShips();
        verifyShipNumbers();

        int numTouching = noTouching();
        System.out.println(numTouching + " Ships are touching.");
    }

    private void findShips() {

        // Look for the top left corner of a ship in each location
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                if (shipGrid[r][c] == 'X'
                        && false == isPartOfShip(new Point(c, r))) {
                    Point topOfShip = new Point(c, r);
                    checkAllShipTypes(topOfShip);
                }
            }
        }
    }

    private boolean isPartOfShip(Point location) {
        
        boolean isPart = false;
        for (Ship s : ships) {
            if (s.partOfShip(location)) {
                isPart = true;
                break;
            }
        }
        return isPart;
    }
    private void checkAllShipTypes(Point topOfShip) {

        // Iterate over all ship types
        for (int type = 0; type < 5; type++) {

            // Iterate over all orientations of this type
            List<List<Point>> offsets = getShipOffsets(type);
            for (List<Point> offset : offsets) {

                if (true == isShip(topOfShip, offset)) {
                    Ship aShip = new Ship(type, topOfShip, offset);
                    ships.add(aShip);
                    //markShip(aShip);
                    if (DEBUG > 1)
                        System.out.println(Ship.typeToString(type) + " at "
                                + topOfShip.toString());
                    if (DEBUG > 1)
                        printShipGrid();
                    break;
                    // */
                }
            }
        }
    }

    private void removeSubsumedShips() {

        List<Ship> allShips = new ArrayList<Ship>();
        allShips.addAll(ships);

        for (Ship aShip : allShips) {
            for (Ship bShip : allShips) {
                if (aShip.type != bShip.type && true == aShip.contains(bShip)) {
                    ships.remove(bShip);
                }
            }
        }
    }

    private void verifyShipNumbers() {

        for (int shipType = 0; shipType < 5; shipType++) {
            numShips[shipType] = 0;
            for (Ship aShip : ships) {
                if (aShip.type == shipType) {
                    numShips[shipType]++;
                }
            }
        }
        for (int s = 0; s < 5; s++) {
            System.out.println(numShips[s] + " " + Ship.typeToString(s));
        }
    }

    private int noTouching() {

        int shipsTouching = 0;
        for (Ship aShip : ships) {
            if (true == isShipTouching(aShip)) {
                if (DEBUG > 1)
                    System.out.println("aShip touching" + aShip.toString());
                shipsTouching++;
                //break;
            }
        }
        return shipsTouching;
    }

    private boolean isShipTouching(Ship aShip) {

        boolean isTouching = false;
        Point topOfShip = aShip.topCoordinate;
        boolean debug = topOfShip.equals(new Point(11, 5));
        List<Point> offsets = aShip.offsets;

        isTouching = (isTouching || isShipPieceTouching(aShip, topOfShip));
        if (DEBUG > 1 && debug)
            System.out.println("top touching? " + isTouching);

        for (Point offset : offsets) {
            Point current = new Point(topOfShip);
            current.x += offset.x;
            current.y += offset.y;
            isTouching = (isTouching || isShipPieceTouching(aShip, current));
            if (DEBUG > 1 && debug)
                System.out.println("point " + current.toString()
                        + " touching? " + isTouching);
        }

        return isTouching;
    }

    private boolean isShipPieceTouching(Ship aShip, Point location) {

        boolean isPieceTouching = false;
        List<Point> pieceOffsets = Ship.pieceOffset();

        for (Point pOffset : pieceOffsets) {
            Point current = new Point(location);
            current.x += pOffset.x;
            current.y += pOffset.y;

            if (isInBounds(current) && shipGrid[current.y][current.x] != '-'
                    && false == aShip.partOfShip(current)) {
                isPieceTouching = true;
            }
        }
        return isPieceTouching;
    }

    private void markShip(Ship aShip) {

        Point topOfShip = aShip.topCoordinate;
        int type = aShip.type;
        List<Point> offsets = aShip.offsets;

        // Mark top left of ship
        shipGrid[topOfShip.y][topOfShip.x] = Ship.typeToChar(type);

        // Mark rest of the ship
        for (Point offset : offsets) {
            Point current = new Point(topOfShip);
            current.x += offset.x;
            current.y += offset.y;
            shipGrid[current.y][current.x] = Ship.typeToChar(type);
        }
    }

    private boolean isShip(Point topOfShip, List<Point> offsets) {

        boolean isShip = true;

        for (Point offset : offsets) {
            Point current = new Point(topOfShip);
            current.x += offset.x;
            current.y += offset.y;
            isShip = (isShip && isShipPiece(current));
        }
        return isShip;

    }

    private List<List<Point>> getShipOffsets(int type) {

        List<List<Point>> offsets = new ArrayList<List<Point>>();
        if (type == Ship.DESTROYER) {
            offsets.add(Ship.dOffset());
            offsets.add(Ship.d1Offset());
            offsets.add(Ship.d2Offset());
            offsets.add(Ship.d3Offset());
        } else if (type == Ship.FRIGATE) {
            offsets.add(Ship.fOffset());
            offsets.add(Ship.f1Offset());
        } else if (type == Ship.CARRIER) {
            offsets.add(Ship.cOffset());
            offsets.add(Ship.c1Offset());
        } else if (type == Ship.GUNBOAT) {
            offsets.add(Ship.gOffset());
            offsets.add(Ship.g1Offset());
        } else if (type == Ship.BATTLESIHP) {
            offsets.add(Ship.t1Offset());
            offsets.add(Ship.tOffset());
        }
        return offsets;
    }

    private boolean isShipPiece(Point aPoint) {

        boolean isPiece = false;
        if (true == isInBounds(aPoint)) {
            isPiece = ('X' == shipGrid[aPoint.y][aPoint.x]);
        }

        if (DEBUG > 1)
            System.out.println("is peice? " + isPiece);
        return isPiece;
    }

    private boolean isInBounds(Point aPoint) {
        return (aPoint.x >= 0 && aPoint.x < gridSize && aPoint.y >= 0 && aPoint.y < gridSize);
    }

    private void readInput() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                    System.in));

            String line = null;
            line = stdIn.readLine();
            gridSize = new Integer(line).intValue();

            shipGrid = new char[gridSize][gridSize];
            for (int row = 0; row < gridSize; row++) {
                line = stdIn.readLine();

                shipGrid[row] = new char[gridSize];
                for (int col = 0; col < gridSize; col++) {
                    shipGrid[row][col] = line.charAt(col);
                }
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void printShipGrid() {

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                System.out.print(shipGrid[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new prob19();
    }
}

class Ship {

    public static final int BATTLESIHP = 0;
    public static final int CARRIER = 1;
    public static final int DESTROYER = 2;
    public static final int FRIGATE = 3;
    public static final int GUNBOAT = 4;

    public int type = 0;
    public Point topCoordinate = null;
    public List<Point> offsets = null;

    private static List<Point> dOffset;
    private static List<Point> d1Offset;
    private static List<Point> d2Offset;
    private static List<Point> d3Offset;
    private static List<Point> fOffset;
    private static List<Point> f1Offset;
    private static List<Point> cOffset;
    private static List<Point> c1Offset;
    private static List<Point> gOffset;
    private static List<Point> g1Offset;
    private static List<Point> tOffset;
    private static List<Point> t1Offset;
    private static List<Point> pieceOffset;
    private static List<List<Point>> shipOffsets;

    public Ship(int type, Point topCoordinate, List<Point> offsets) {
        this.type = type;
        this.topCoordinate = new Point(topCoordinate);
        this.offsets = offsets;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(typeToString(type));
        sb.append(",");
        sb.append(topCoordinate.toString());
        sb.append("]");
        return sb.toString();
    }

    public boolean partOfShip(Point location) {

        boolean isPart = false;

        for (Point offset : offsets) {
            Point current = new Point(topCoordinate);
            current.x += offset.x;
            current.y += offset.y;

            if (location.equals(current)) {
                isPart = true;
                break;
            }
        }

        if (topCoordinate.equals(location)) {
            isPart = true;
        }
        
        // System.out.println("is " + location.toString() + " part of " +
        // this.topCoordinate.toString() + ":" + isPart);
        return isPart;
    }

    public static List<List<Point>> getShipOffsets() {
        if (null == shipOffsets) {
            shipOffsets = new ArrayList<List<Point>>();
            shipOffsets.add(cOffset);
            shipOffsets.add(dOffset);
            shipOffsets.add(fOffset);
            shipOffsets.add(gOffset);
            shipOffsets.add(g1Offset);
            shipOffsets.add(tOffset);
        }

        return shipOffsets;
    }

    private List<Point> getShipOffsets(int type) {

        List<Point> offsets = null;
        if (type == DESTROYER) {
            offsets = Ship.dOffset();
        } else if (type == FRIGATE) {
            offsets = Ship.fOffset();
        } else if (type == CARRIER) {
            offsets = Ship.cOffset();
        } else if (type == GUNBOAT) {
            offsets = Ship.gOffset();
        } else if (type == BATTLESIHP) {
            offsets = Ship.tOffset();
        }
        return offsets;
    }

    public boolean contains(Ship otherShip) {

        boolean contains = true;

        List<Point> otherShipOffsets = otherShip.offsets;
        Point otherShipTop = otherShip.topCoordinate;

        for (Point otherShipOffset : otherShipOffsets) {
            Point current = new Point(otherShipTop);
            current.x += otherShipOffset.x;
            current.y += otherShipOffset.y;

            if (false == partOfShip(current)) {
                contains = false;
            }
        }
        return contains;
    }

    public static char typeToChar(int type) {

        char cType = 'O';
        if (type == DESTROYER) {
            cType = 'D';
        } else if (type == FRIGATE) {
            cType = 'F';
        } else if (type == CARRIER) {
            cType = 'C';
        } else if (type == GUNBOAT) {
            cType = 'G';
        } else if (type == BATTLESIHP) {
            cType = 'B';
        }
        return cType;
    }

    public static String typeToString(int type) {

        String strType = null;
        if (type == DESTROYER) {
            strType = "Destroyer";
        } else if (type == FRIGATE) {
            strType = "Frigate";
        } else if (type == CARRIER) {
            strType = "Carrier";
        } else if (type == GUNBOAT) {
            strType = "Gunner";
        } else if (type == BATTLESIHP) {
            strType = "Battleship";
        }

        return strType;
    }

    public static List<Point> dOffset() {
        if (null == dOffset) {
            dOffset = new ArrayList<Point>();
            dOffset.add(new Point(0, 1));
            dOffset.add(new Point(0, 2));
            dOffset.add(new Point(0, 3));
            dOffset.add(new Point(-1, 2));
            dOffset.add(new Point(-1, 3));
            dOffset.add(new Point(1, 2));
            dOffset.add(new Point(1, 3));
        }
        return dOffset;
    }

    public static List<Point> d1Offset() {
        if (null == d1Offset) {
            d1Offset = new ArrayList<Point>();
            d1Offset.add(new Point(1, 0));
            d1Offset.add(new Point(0, 1));
            d1Offset.add(new Point(1, 1));
            d1Offset.add(new Point(2, 1));
            d1Offset.add(new Point(3, 1));
            d1Offset.add(new Point(0, 2));
            d1Offset.add(new Point(1, 2));
        }
        return d1Offset;
    }

    public static List<Point> d2Offset() {
        if (null == d2Offset) {
            d2Offset = new ArrayList<Point>();
            d2Offset.add(new Point(1, 0));
            d2Offset.add(new Point(2, 0));
            d2Offset.add(new Point(0, 1));
            d2Offset.add(new Point(1, 1));
            d2Offset.add(new Point(2, 1));
            d2Offset.add(new Point(1, 2));
            d2Offset.add(new Point(1, 3));
        }
        return d2Offset;
    }

    public static List<Point> d3Offset() {
        if (null == d3Offset) {
            d3Offset = new ArrayList<Point>();
            d3Offset.add(new Point(1, 0));
            d3Offset.add(new Point(-2, 1));
            d3Offset.add(new Point(-1, 1));
            d3Offset.add(new Point(0, 1));
            d3Offset.add(new Point(1, 1));
            d3Offset.add(new Point(0, 2));
            d3Offset.add(new Point(1, 2));
        }
        return d3Offset;
    }

    public static List<Point> fOffset() {
        if (null == fOffset) {
            fOffset = new ArrayList<Point>();
            fOffset.add(new Point(0, 1));
            fOffset.add(new Point(0, 2));
            fOffset.add(new Point(0, 3));
            fOffset.add(new Point(-1, 1));
            fOffset.add(new Point(-1, 2));
            fOffset.add(new Point(1, 1));
            fOffset.add(new Point(1, 2));
        }
        return fOffset;
    }

    public static List<Point> f1Offset() {
        if (null == f1Offset) {
            f1Offset = new ArrayList<Point>();
            f1Offset.add(new Point(1, 0));
            f1Offset.add(new Point(-1, 1));
            f1Offset.add(new Point(0, 1));
            f1Offset.add(new Point(1, 1));
            f1Offset.add(new Point(2, 1));
            f1Offset.add(new Point(0, 2));
            f1Offset.add(new Point(1, 2));
        }
        return f1Offset;
    }

    public static List<Point> cOffset() {
        if (null == cOffset) {
            cOffset = new ArrayList<Point>();
            cOffset.add(new Point(0, 1));
            cOffset.add(new Point(-1, 1));
            cOffset.add(new Point(-1, 2));
            cOffset.add(new Point(-2, 2));
            cOffset.add(new Point(-2, 3));
        }
        return cOffset;
    }

    public static List<Point> c1Offset() {
        if (null == c1Offset) {
            c1Offset = new ArrayList<Point>();
            c1Offset.add(new Point(1, 0));
            c1Offset.add(new Point(1, 1));
            c1Offset.add(new Point(2, 1));
            c1Offset.add(new Point(2, 2));
            c1Offset.add(new Point(3, 2));
        }
        return c1Offset;
    }

    public static List<Point> gOffset() {
        if (null == gOffset) {
            gOffset = new ArrayList<Point>();
            gOffset.add(new Point(1, 0));
            gOffset.add(new Point(0, 1));
            gOffset.add(new Point(1, 1));
            gOffset.add(new Point(0, 2));
            gOffset.add(new Point(1, 2));
            gOffset.add(new Point(0, 3));
            gOffset.add(new Point(1, 3));
        }
        return gOffset;
    }

    public static List<Point> g1Offset() {
        if (null == g1Offset) {
            g1Offset = new ArrayList<Point>();
            g1Offset.add(new Point(1, 0));
            g1Offset.add(new Point(2, 0));
            g1Offset.add(new Point(3, 0));
            g1Offset.add(new Point(0, 1));
            g1Offset.add(new Point(1, 1));
            g1Offset.add(new Point(2, 1));
            g1Offset.add(new Point(3, 1));
        }
        return g1Offset;
    }

    public static List<Point> tOffset() {
        if (null == tOffset) {
            tOffset = new ArrayList<Point>();
            tOffset.add(new Point(0, 1));
            tOffset.add(new Point(0, 2));
            tOffset.add(new Point(0, 3));
        }
        return tOffset;
    }

    public static List<Point> t1Offset() {
        if (null == t1Offset) {
            t1Offset = new ArrayList<Point>();
            t1Offset.add(new Point(1, 0));
            t1Offset.add(new Point(2, 0));
            t1Offset.add(new Point(3, 0));
        }
        return t1Offset;
    }

    public static List<Point> pieceOffset() {
        if (null == pieceOffset) {
            pieceOffset = new ArrayList<Point>();
            pieceOffset.add(new Point(-1, -1));
            pieceOffset.add(new Point(0, -1));
            pieceOffset.add(new Point(1, -1));
            pieceOffset.add(new Point(-1, 0));
            pieceOffset.add(new Point(1, 0));
            pieceOffset.add(new Point(-1, 1));
            pieceOffset.add(new Point(0, 1));
            pieceOffset.add(new Point(1, 1));
        }

        return pieceOffset;
    }
}
