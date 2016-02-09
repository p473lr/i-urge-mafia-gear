/*
 * Prob07.java
 *
 * Created on February 7, 2010, 9:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
//package prob07;

import java.util.*;

/**
 *
 * @author Hazzard
 * HP CodeWars NodeGraphMaze
 */
public class Prob07 {


    /**
     * Solve problem 3 on page 311
     * This method will output the spanning tree using the
     * breadth-firest search alorithm, if one exists
     * the number of degree for each Vertex
     *
     * input
     *      adjaceny matrix to be solved.
     * output
     *      none at this time
     */
    public static void solveProb3(boolean m[][], String[] labels, int startingVertex, int sink) {
        int numV = m.length;   /* number of vertix in this matrix */
        boolean visited[];     /* mark when a vertix has been visisted */
        String visitedLabel[];   /* save the label of the previous vertix */
        int visitedLevel[];    /* what is the level from the starting vertix */
        int currentLevel = 0;  /* What is the current level from the starting */
        boolean marked = false; /* check to see if a pass marked a vertix */
        int verticesMarked = 0; /* make sure all vertices have been marked */

        //visited = new int [numV];
        visitedLabel = new String[numV];
        visitedLevel = new int[numV];
        visited = new boolean[numV];
        for (int i = 0; i < numV; i++) {
            visitedLevel[i] = -1;      /* set all vertices as not visited */
            visited[i] = false;
        }


        /* mark the starting vertex as ground zero to start the search
         */
        visited[startingVertex] = true;
        visitedLevel[startingVertex] = 0;
        visitedLabel[startingVertex] = "-";
        verticesMarked++;

        /* double check to make sure the starting vertex has Adjacent vertex*/

        for (int i = 0; i < numV; i++) {
            if (isAdjacent(m, startingVertex, i)) {
                marked = true;
            }
        }

        /* can't reach all vertices */
        if (!marked) {
            System.out.println("spanning tree can not be determine.  Source doesn't have any adjacent nodes");
            return;
        }


        while (verticesMarked < numV) {
            marked = false;

            for (int i = 0; i < numV; i++) {

                /* find the vertices equal to the current scanning level */

                if (visitedLevel[i] == currentLevel) {

                    for (int k = 0; k < numV; k++) {
                        /* has it not been visited, and it is next to our
                         * currentLevel?
                         **/
                        if (!visited[k] && isAdjacent(m, i, k)) {
                            /* mark is as visited and save what vertex
                             * we came from
                             **/
                            marked = true;
                            verticesMarked++;
                            visited[k] = true;
                            visitedLevel[k] = currentLevel + 1;
                            visitedLabel[k] = labels[i];
                        }
                    }
                }

            }
            if (marked) {
                currentLevel++;
            } else { // didn't mark everyone, some vertex must not be connected
                //System.out.println("Complete spanning tree can not be determine");
                //return;
                // everything which can be reached is marked.
                break;
            }
        }

        /*
         * double check to see if sink was reached...
         */
        if (visitedLevel[sink] == -1) {
            System.out.println("error: Sink can not be reached from source");
            return;
        }

        /*
         * walk backwards from sink to source to path the path
         *
         */

        //System.out.println("    Tree has been found, the vertices are:\n");


        ArrayList<String> output = new ArrayList<String>();
        boolean startoutput = false;
        String lastMark = " ";
        for (int c = currentLevel; c >= 0; c--) {
            for (int i = 0; i < numV; i++) {
                if (visitedLevel[i] == c) {
                    if (i == sink) {
                        startoutput = true;
                        lastMark = labels[i];
                    }

                    if (startoutput && labels[i].equals(lastMark)) {
                        output.add(labels[i]);
                        //System.out.println("      {" + visitedLabel[i] + "," + labels[i] + "} " + c);
                        lastMark = visitedLabel[i];
                    }
                }
            }
        }

        for (int i = output.size() - 1; i >= 0; i--) {
            System.out.print(output.get(i));
            if (i != 0) {
                System.out.print(" ");
            }
        }
        System.out.println("");
    }

    /**
     * boolean isAdjacnt(boolean m[][], int V1, V2)
     * Return if two vectors are adjacent or not.
     *  Input
     *      adjaceny matrix double dim boolean array
     *      int first vertix
     *      int second vertix
     *
     */
    public static boolean isAdjacent(boolean m[][], int V1, int V2) {
        return m[V1][V2];
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String testLabel[] = null;

        int sourceVertix=0;
        int sinkVertix=0;
        String source;
        String sink;
        String defaultVertix = null;
        boolean testMatrix[][] = null;

        ArrayList<String> consoleInput = new ArrayList<String>();
        Scanner scan = new Scanner(System.in);

        // build the list, so we can scan it a few times.
        while (scan.hasNext()) {
            String li = scan.nextLine();
            consoleInput.add(li);
            if (li.startsWith("EX")) {
                break;
            }
        }

        // build the labels
        String alreadyFound = " IN EX";
        ArrayList<String> buildLabels = new ArrayList<String>();
        for (int i = 0; i < consoleInput.size(); i++) {
            String[] words = consoleInput.get(i).split(" ");
            if (words.length != 2) {
                System.out.println("invalid input, aborting");
                return;
            }
            for (int k = 0; k < words.length; k++) {
                if (alreadyFound.lastIndexOf(words[k]) == -1) {
                    buildLabels.add(words[k]);
                    alreadyFound += " " + words[k];
                }
            }
        }

        int portNum = buildLabels.size();
        //System.out.println(" count is " + portNum);

        // ok we have the label
        testLabel = new String[portNum];
        for (int i = 0; i < portNum; i++) {
            testLabel[i] = buildLabels.get(i);
        }

        // now let's build the array
        testMatrix = new boolean[portNum][portNum];

        // setup the matrix
        for (int i = 0; i < portNum; i++) {
            String[] words = consoleInput.get(i).split(" ");

            for (int r = 0; r < portNum; r++) {
                for (int c = 0; c < portNum; c++) {
                    if ((testLabel[r].equals(words[0])
                            && testLabel[c].equals(words[1]))
                            || (testLabel[r].equals(words[1])
                            && testLabel[c].equals(words[0]))) {
                        testMatrix[r][c] = true;
                    }
                }
            }

        }

        /**
         *
         * let's get the source and sink.
         *
         */
        String[] inex = consoleInput.get(consoleInput.size()-2).split(" ");
        source = inex[1];
        inex = consoleInput.get(consoleInput.size()-1).split(" ");
        sink = inex[1];
        for (int i=0; i < portNum; i++) {
            if (source.equals(testLabel[i]))
                sourceVertix = i;
            if (sink.equals(testLabel[i]))
                sinkVertix = i;

        }
        // TODO code application logic here
        /*
        System.out.println("");
        System.out.println("    Input matrix is:");

        */
        /*for (int i = 0; i < testLabel.length; i++) {
        if (testLabel[i] == defaultVertix) {
        startingVertix = i;
        }
        }*/

        /* output the input matrix
         */
/*
        System.out.print("           ");
        for (int i = 0; i < testMatrix.length; i++) {
            System.out.print(" " + testLabel[i]);
        }
        System.out.println("");

        for (int i = 0; i < testMatrix.length; i++) {
            boolean comma = false;
            System.out.print("          " + testLabel[i]);
            for (int k = 0; k < testMatrix[i].length; k++) {
                if (isAdjacent(testMatrix, i, k)) {
                    System.out.print(" 1");
                } else {
                    System.out.print(" 0");
                }
            }
            System.out.println("");
        }
*/

        
        /*
         * Solve problem 1
         **/

        solveProb3(testMatrix, testLabel, sourceVertix, sinkVertix);
    }
}
