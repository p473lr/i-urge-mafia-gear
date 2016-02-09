/*
 * Prob07.java
 * Codewars2006 - Sudoku Judging
 *
 * Created on February 26, 2006, 1:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;
/**
 *
 * @author skearney
 */
public class Prob07 {
    
    /** Creates a new instance of Prob07 */
    public Prob07() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = null;
        String line=null;
        int i,j;
        int[][] puzzle = new int[9][9];
        
        // Use input from File, but default to STDIN if file is not found
        try {
            in = new Scanner(new File("Prob07.in"));
        } catch (FileNotFoundException e) {
            in = new Scanner(System.in);
        }
        // Create our puzzle matrix
        for(i=0;i<9;i++) {
            line = in.nextLine();
            for(j=0;j<9;j++) {
                puzzle[i][j] = Character.digit(line.charAt(j),10);
            }
        }
        
        Sudoku sudoku = new Sudoku(puzzle);
        if(sudoku.isCorrect())
            System.out.println("Correct");
        else
            System.out.println("Incorrect");
        
    }
    
}
class Sudoku {
    private int[][] puzzle;
    
    
    public Sudoku(int[][] puzzle) {
        this.puzzle=puzzle;
    }
    
    public boolean isCorrect(){
        int i,j,x,y;
        
        // Judge Rows
        for(i=0; i<9; i++) {
            Set<Integer> row = new HashSet<Integer>();
            // The add method will return false if there are any duplicates
            for(j=0; j<9; j++) {
                if(!row.add(puzzle[i][j]))
                    return false;
            }
        }
        
        // Judge Columns
        for(j=0; j<9; j++) {
            Set<Integer> col = new HashSet<Integer>();
            for(i=0; i<9; i++) {
                // The add method will return false if there are any duplicates
                if(!col.add(puzzle[i][j]))
                    return false;
            }
        }
        
        // Judge Each 3x3 Grid
        for(i=0;i<9;i+=3) {
            for(j=0;j<9;j+=3) {
                // Begin a new Matrix
                Set<Integer> matrix = new HashSet<Integer>();
                for(x=i;x<i+3;x++) {
                    for(y=j;y<j+3;y++) {
                        if(!matrix.add(puzzle[x][y]))
                            return false;
                    }
                }
            }
        }
        
        return true;
    }
}