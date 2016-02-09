import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class sudokode
{
	public static void main(String[] args)
	{
		// Try to open the input file for reading
		Scanner fin;
		try{
			fin = new Scanner(new File("sudokode.in"));
		}catch(IOException e)
		{
			return;
		}
		
		// Read in the number of Sudokus
		int sets = fin.nextInt();
		
		// For each Sudoku, read it in and determine whether or not it's valid
		for(int set = 1; set <= sets; set++)
		{
			int[][] sudoku = new int[9][9];
			
			// Read the Sudoku from the file
			for(int row=0; row<9; row++)
			{
				String line = fin.next();
				for(int col=0; col<9; col++)
					sudoku[row][col] = line.charAt(col)-'0'; 
			}
			
			// Check the Sudoku and print whether or not it's corret
			System.out.print("Sudoku #"+set+":  ");
			if(checkSudoku(sudoku))
				System.out.println("Dave's the man!");
			else
				System.out.println("Try again, Dave!");
			System.out.println();
		}
		
		
	}
	
	// This checks a sudoku to see if it is correctly solved
	// A sudoku is only valid if all its row, columns, and 3x3 subgrids
	// are valid
	public static boolean checkSudoku(int[][] sudoku)
	{
		// Verify the rows
		for(int row = 0; row < 9; row++)
			if(!checkRow(sudoku, row))
				return false;
		
		// Verify the columns
		for(int col = 0; col < 9; col++)
			if(!checkCol(sudoku,col))
				return false;
		
		// Verify the 3x3 subgrids
		for(int row = 0; row < 9; row += 3)
			for(int col = 0; col < 9; col += 3)
				if(!checkBox(sudoku,row,col))
					return false;
		
		// If all the tests pass, then the sudoku is valid
		return true;
	}
	
	// This checks the specified row to make sure that each digit
	// appears once and only once
	public static boolean checkRow(int[][] sudoku, int row)
	{
		boolean[] hit = new boolean[10];
		
		for(int col = 0; col < 9; col++)
		{
			// If a number has been reused then the Sudoku is invalid
			if(hit[sudoku[row][col]])
				return false;
			
			// Mark a number as having been used
			hit[sudoku[row][col]] = true;
		}
		// Input is restricted in such a way that only digits 1 - 9 will appear
		// As such, if 9 digits have been seen without duplicates, then the row
		// must have all 9 digits once and only once
		return true;
	}
	
	// This checks the specified column to make sure that each digit
	// appears once and only once
	public static boolean checkCol(int[][] sudoku, int col)
	{
		boolean[] hit = new boolean[10];
		
		for(int row = 0; row < 9; row++)
		{
			// If a number has been reused then the Sudoku is invalid
			if(hit[sudoku[row][col]])
				return false;
			
			// Mark a number as having been used
			hit[sudoku[row][col]] = true;
		}
		// Input is restricted in such a way that only digits 1 - 9 will appear
		// As such, if 9 digits have been seen without duplicates, then the
		// column must have all 9 digits once and only once
		return true;
	}
	
	// This checks the specified 3x3 sub-grid(box) to make sure that each digit
	// appears once and only once
	public static boolean checkBox(int[][] sudoku, int row, int col)
	{
		boolean[] hit = new boolean[10];
		
		// Loop through each cell in a box
		for(int r = row; r < row+3; r++)
		{
			for(int c = col; c < col+3; c++)
			{				
				// If a number has been reused then the Sudoku is invalid
				if(hit[sudoku[r][c]])
					return false;
				
				// Mark a number as having been used
				hit[sudoku[r][c]] = true;
			}
		}
		// Input is restricted in such a way that only digits 1 - 9 will appear
		// As such, if 9 digits have been seen without duplicates, then the
		// box must have all 9 digits once and only once
		return true;
	}
}
