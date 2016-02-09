import java.io.*;
import java.util.*;

public class sorting
{
	public static void main (String [] args) throws IOException
	{
		//Declare a Scanner object to read in input from the file "sorting.in"
		Scanner scan = new Scanner(new File("sorting.in"));
		
		//Read in the number of inputs to be processed
		int numInputs = scan.nextInt();
		
		//Loop once for every input to be processed
		for (int i = 0; i < numInputs; i++)
		{
			//Read in the number of students in the particular class
			int numStudents = scan.nextInt();
			
			//Create an array to store all of the names of the students
			String [] students = new String[numStudents];
			
			//Read in and store the names of all of the students in the class
			for (int j = 0; j < numStudents; j++)
			{
				students[j] = scan.next();
			}
			
			//Perform a bubble sort on the array of student
			boolean sorted = false;
			while (!sorted)
			{
				sorted = true;
				for (int j = 0; j < numStudents-1; j++)
				{
					//if the current student belongers later in the list than the
					//next one...
					if (CompareStudents(students[j], students[j+1]) > 0)
					{
						//Swap the two students
						String temp = students[j];
						students[j] = students[j+1];
						students[j+1] = temp;
						
						sorted = false;
					}
				}
			}
			
			//Print out the output, as specified
			System.out.println("Class #"+(i+1)+" ordering");
			for (int j = 0; j < numStudents; j++)
			{
				System.out.println(students[j]);
			}
			System.out.println();		
		}
	}
	//This function compares two students and returns -1 if student1 < student2, 
	//0 if student = student2, and 1 if student1 > student2
	public static int CompareStudents(String student1, String student2)
	{
		//Since we need to count the number of different types of letters
		//each name contains, create two arrays of size 26 (one for each letter in the
		//alphabet) where A is index 0 and Z is index 25, one for each student.
		int [] letters1 = new int[26];
		int [] letters2 = new int[26];
		
		//Initialize each array to zero
		Arrays.fill(letters1, 0);
		Arrays.fill(letters2, 0);
		
		//Iterate through each character in each name, and keep a running total of the number
		//of letters found.
		for (int i = 0; i < student1.length(); i++)
		{
			//This works because:
			//		'A' - 'A' == 0 
			//    'B' - 'A' == 1  
			//    'C' - 'A' == 2 and so on. 
			letters1[student1.charAt(i)-'A']++;
		}
		for (int i = 0; i < student2.length(); i++)
		{
			letters2[student2.charAt(i)-'A']++;
		}
		
		//Now, we compare the two names. Start by comparing to see who has more As
		//then Bs, then Cs, and so forth;
		for (int i = 0; i < 26; i++)
		{
			if (letters1[i] != letters2[i])
			{
				return letters2[i] - letters1[i];
			}
		}
		
		//Return zero if they are equal. Due to the problem specification, this should never happen.
		return 0;
	}
}