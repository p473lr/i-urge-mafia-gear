/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basePrograms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author ryanh_000
 */
public class baseProgram1 {

    private void function(String[] input) {
        //Actual Code
        
    }
    
    public baseProgram1() {
        //Specify Input Method:
        
    }

    public static void main(String[] args) {
        baseProgram1 instance = new baseProgram1();
    }

    private void noInput(){
        function(null);
    }
    
    private void readSplitInputUntil(String seperatedBy, String endString) {

        try {

            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;

            aLine = standardIn.readLine();

            while (null != aLine && false == aLine.equals(endString)) {

                String[] input = aLine.split(seperatedBy);
                function(input);
                aLine = standardIn.readLine();
            }

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
            
            while(null != aLine && count<=numLines) {
                String[] input = aLine.split(seperatedBy);
                function(input);
                
                aLine = standardIn.readLine();
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
            
            while(null != aLine && count<=numLines) {
                String[] input = aLine.split(seperatedBy);
                function(input);
                
                aLine = standardIn.readLine();
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

            while (null != aLine && false == aLine.equals(endString)) {

                String[] input = new String[1];
                input[0] = aLine;
                function(input);
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
            
            while(null != aLine && count<=numLines) {
                String[] input = new String[1];
                input[0] = aLine;
                function(input);
                
                aLine = standardIn.readLine();
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
            
            while(null != aLine && count<=numLines) {
                String[] input = new String[1];
                input[0] = aLine;
                function(input);
                
                aLine = standardIn.readLine();
                count++;
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

}
