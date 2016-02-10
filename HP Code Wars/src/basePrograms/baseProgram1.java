/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basePrograms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author ryanh_000
 */
public class baseProgram1 {

    private void function(ArrayList<String[]> input) {
        //        Depending on the problem:
        input = removeEmptyStrings(input);
        //Actual Code:
        System.out.println("Hello world");
    }

    public baseProgram1() {
        //Specify Input Method:
        //read           Split/Single            Input           Set/Until/SetAtRun
        //or:
        //readNoInput();
        readSplitInputUntil("-","0 0");
    }

    public static void main(String[] args) {
        baseProgram1 instance = new baseProgram1();
    }

    private void readNoInput() {
        function(null);
    }

    private void readSplitInputUntil(String seperatedBy, String endString) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            ArrayList<String[]> input = new ArrayList<String[]>();

            while (!aLine.equals(endString)) {

                String[] line = aLine.split(seperatedBy);

                input.add(line);

                aLine = standardIn.readLine();
            }

            function(input);

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readSplitInputSet(String seperatedBy, int numLines) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();

            while (count <= numLines) {
                String[] line = aLine.split(seperatedBy);

                input.add(line);

                if (count != numLines) {
                    aLine = standardIn.readLine();
                }
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readSplitInputSetAtRun(String seperatedBy) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));

            int numLines = new Integer(standardIn.readLine()).intValue();

            String aLine = null;
            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();

            while (count <= numLines) {
                String[] line = aLine.split(seperatedBy);

                input.add(line);

                if (count != numLines) {
                    aLine = standardIn.readLine();
                }
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readSingleInputUntil(String endString) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            ArrayList<String[]> input = new ArrayList<String[]>();

            while (!aLine.equals(endString)) {

                String[] line = new String[1];
                line[0] = aLine;

                input.add(line);

                aLine = standardIn.readLine();
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readSingleInputSet(int numLines) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();

            while (count <= numLines) {
                String[] line = new String[1];
                line[0] = aLine;

                input.add(line);

                if (count != numLines) {
                    aLine = standardIn.readLine();
                }
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readSingleInputSetAtRun() {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));

            int numLines = new Integer(standardIn.readLine()).intValue();

            String aLine = null;
            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();

            while (count <= numLines) {
                String[] line = new String[1];
                line[0] = aLine;

                input.add(line);

                if (count != numLines) {
                    aLine = standardIn.readLine();
                }
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

//    private void readPartialSplitInputUntil(String seperatedBy, String endString) {
//
//        try {
//
//            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
//            String aLine = null;
//
//            aLine = standardIn.readLine();
//
//            ArrayList<String[]> input = new ArrayList<String[]>();
//
//            while (!aLine.equals(endString)) {
//                String[] line = aLine.split(seperatedBy);
//                input.add(line);
//                function(input);
//                aLine = standardIn.readLine();
//            }
//
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//        }
//    }
//
//    private void readPartialSplitInputSet(String seperatedBy, int numLines) {
//
//        try {
//
//            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
//            String aLine = null;
//
//            aLine = standardIn.readLine();
//
//            int count = 1;
//
//            ArrayList<String[]> input = new ArrayList<String[]>();
//
//            while (count <= numLines) {
//                String[] line = aLine.split(seperatedBy);
//                input.add(line);
//
//                function(input);
//
//                if (count != numLines) {
//                    aLine = standardIn.readLine();
//                }
//                count++;
//            }
//
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//        }
//    }
//
//    private void readPartialSplitInputSetAtRun(String seperatedBy) {
//
//        try {
//
//            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
//
//            int numLines = new Integer(standardIn.readLine()).intValue();
//
//            String aLine = null;
//            aLine = standardIn.readLine();
//
//            int count = 1;
//
//            ArrayList<String[]> input = new ArrayList<String[]>();
//
//            while (count <= numLines) {
//                String[] line = aLine.split(seperatedBy);
//                input.add(line);
//
//                if (count != numLines) {
//                    aLine = standardIn.readLine();
//                }
//                count++;
//            }
//
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//        }
//    }
//
//    private void readPartialSingleInputUntil(String endString) {
//
//        try {
//
//            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
//            String aLine = null;
//
//            aLine = standardIn.readLine();
//
//            ArrayList<String[]> input = new ArrayList<String[]>();
//
//            while (!aLine.equals(endString)) {
//
//                String[] line = new String[1];
//                line[0] = aLine;
//
//                function(input);
//                input.clear();
//
//                aLine = standardIn.readLine();
//            }
//
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//        }
//    }
//
//    private void readPartialSingleInputSet(int numLines) {
//
//        try {
//
//            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
//            String aLine = null;
//
//            aLine = standardIn.readLine();
//
//            int count = 1;
//
//            ArrayList<String[]> input = new ArrayList<String[]>();
//
//            while (count <= numLines) {
//                String[] input = new String[1];
//                input[0] = aLine;
//
//                aLine = standardIn.readLine();
//                if (count != numLines) {
//                    aLine = standardIn.readLine();
//                }
//            }
//
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//        }
//    }
//
//    private void readPartialSingleInputSetAtRun() {
//
//        try {
//
//            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
//
//            int numLines = new Integer(standardIn.readLine()).intValue();
//
//            String aLine = null;
//            aLine = standardIn.readLine();
//
//            int count = 1;
//
//            ArrayList<String[]> input = new ArrayList<String[]>();
//
//            while (count <= numLines) {
//                String[] input = new String[1];
//                input[0] = aLine;
//
//                if (count != numLines) {
//                    aLine = standardIn.readLine();
//                }
//                count++;
//            }
//
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//        }
//    }

    private ArrayList<String[]> removeEmptyStrings(ArrayList<String[]> inputList) {
        ArrayList<String[]> outputList = new ArrayList<String[]>();
        for (String[] input : inputList) {
            ArrayList<String> listData = new ArrayList<String>();
            for (int x = 0; x < input.length; x++) {
                if (!input[x].isEmpty()) {
                    listData.add(input[x]);
                }
            }
            String[] output = new String[listData.size()];
            for (int x = 0; x < listData.size(); x++) {
                output[x] = listData.get(x);
            }
            outputList.add(output);
        }
        return outputList;
    }

}
