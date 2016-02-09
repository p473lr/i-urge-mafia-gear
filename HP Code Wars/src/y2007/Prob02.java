/*
 * Prob02.java
 *
 */


import java.io.*;

/**
 *
 * @author James
 */
public class Prob02 {
    
    private static final int MAX_NAMES = 16;
    
    //  We can't have more than 16 names.
    private static String teamNames [] = new String[MAX_NAMES];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        String input = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        //  Gather input from the user until they enter "END"
        for (int i=0 ; i<MAX_NAMES ; i++){
            
            input = br.readLine();
            if (input.compareTo("END") != 0){
                teamNames[i] = input;
            }
            else{
                break;
            }
            
        }
        
        printTeamNames();
        
    }
    
    /**
     * Prints the team names from the array in a vertical fashion.
     */
    private static void printTeamNames(){
        
        int maxTeamLength = getMaxNameLength();
        String lineString;
        
        //  Since we're going line by line we need to loop through
        //  the longest team name.
        for (int i=0 ; i<maxTeamLength ; i++){
            
            lineString = "";
            
            for (int j=0 ; j<teamNames.length ; j++){
                
                if (teamNames[j] != null && (i < teamNames[j].toCharArray().length)){
                    lineString += teamNames[j].toCharArray()[i] + "  ";
                }
                else{
                    lineString += "   ";
                }
                
            }
            
            //  Print the current line with a single char from each array
            //  separated by two spaces.
            System.out.println(lineString);
            
        }
        
    }
    
    /**
     * Finds the length of the longest team name in the array.
     */
    private static int getMaxNameLength(){
        
        int max = 0;
        
        for (int i=0 ; i<teamNames.length ; i++){
            
            if (teamNames[i] != null && teamNames[i].toCharArray().length > max){
                max = teamNames[i].toCharArray().length;
            }
            
        }
        
        return max;
        
    }
    
}
