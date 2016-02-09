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
        //read          Full/Partial            Split/Single            Input           Set/Until/SetAtRun
        //or:
        //readNoInput();
        readNoInput();
    }

    public static void main(String[] args) {
        baseProgram1 instance = new baseProgram1();
    }

    private void readNoInput() {
        function(null);
    }

    private void readFullSplitInputUntil(String seperatedBy, String endString) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            ArrayList<String[]> input = new ArrayList<String[]>();

            while (null != aLine && false == aLine.equals(endString)) {

                String[] line = aLine.split(seperatedBy);
                
                input.add(line);
                
                aLine = standardIn.readLine();
            }

            function(input);

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readFullSplitInputSet(String seperatedBy, int numLines) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = aLine.split(seperatedBy);

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readFullSplitInputSetAtRun(String seperatedBy) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));

            int numLines = new Integer(standardIn.readLine()).intValue();

            String aLine = null;
            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = aLine.split(seperatedBy);

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readFullSingleInputUntil(String endString) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && false == aLine.equals(endString)) {

                String[] input = new String[1];
                input[0] = aLine;
                aLine = standardIn.readLine();
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readFullSingleInputSet(int numLines) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = new String[1];
                input[0] = aLine;

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readFullSingleInputSetAtRun() {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));

            int numLines = new Integer(standardIn.readLine()).intValue();

            String aLine = null;
            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = new String[1];
                input[0] = aLine;

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readPartialSplitInputUntil(String seperatedBy, String endString) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && false == aLine.equals(endString)) {

                String[] input = aLine.split(seperatedBy);
                aLine = standardIn.readLine();
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readPartialSplitInputSet(String seperatedBy, int numLines) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = aLine.split(seperatedBy);

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readPartialSplitInputSetAtRun(String seperatedBy) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));

            int numLines = new Integer(standardIn.readLine()).intValue();

            String aLine = null;
            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = aLine.split(seperatedBy);

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readPartialSingleInputUntil(String endString) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && false == aLine.equals(endString)) {

                String[] input = new String[1];
                input[0] = aLine;
                aLine = standardIn.readLine();
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readPartialSingleInputSet(int numLines) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = new String[1];
                input[0] = aLine;

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    private void readPartialSingleInputSetAtRun() {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));

            int numLines = new Integer(standardIn.readLine()).intValue();

            String aLine = null;
            aLine = standardIn.readLine();

            int count = 1;

            ArrayList<String[]> input = new ArrayList<String[]>();
            
            while (null != aLine && count <= numLines) {
                String[] input = new String[1];
                input[0] = aLine;

                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

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
