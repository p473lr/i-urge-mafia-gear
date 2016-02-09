#include <iostream>
#include <string.h>

#define MAX_NAME_LENGTH 20

//reserve a function for later use
void my_sort(char** names, int** numletters, int numstudents);

int main()
{
	//opening a file for reading
	FILE * file = fopen("sorting.in", "r");
	
	//reserving variables for use later
	int num_classes, num_students;
	int** num_letters;
	char** students;

	//get number of classes.
	fscanf(file, "%d", &num_classes);

	//this is c++, so I can declare variables in loops
	for(int curclass = 0; curclass < num_classes; curclass++)
	{
		fscanf(file, "%d", &num_students);

		/*
			resserving the space for the students strings. 
			Similar to the new command. This function returns a pointer to a
			block of data in memory that's been reserved with the size I asked
			for in the parameters
		*/
		students = (char**)(calloc(num_students, sizeof(char*)));
		for(int x = 0; x < num_students; x++)
			students[x] = (char*)(calloc(MAX_NAME_LENGTH + 1, sizeof(char)));

		num_letters = (int**)(calloc(num_students, sizeof(int*)));
		for(int x = 0; x < num_students; x++)
			num_letters[x] = (int*)(calloc(26, sizeof(int)));
		

		for(int curstudent = 0; curstudent < num_students; curstudent++)
		{
			//get the current student
			fscanf(file, "%s", students[curstudent]);
			
			//reserve its length
			int length = strlen(students[curstudent]);
			
			/*
				for every character in the string, increment the number of times
				that particular character has been found in that string
				
				'A' - 'A' = 0
				'B' - 'A' = 1
				'C' - 'A' = 2, etc.
			*/
			for(int curchar = 0; curchar < length; curchar++)
			{
				num_letters[curstudent][students[curstudent][curchar] - 'A']++;
			}
		}

		//sort the students based on the criteria
		my_sort(students, num_letters, num_students);

		/*
			output the results.
			Because curclass starts at 0 and the first class is "1", output
			one higher than curclass is, so output is consistent
		*/
		printf("Class #%d ordering\n", (curclass + 1));
		for(int curstudent = 0; curstudent < num_students; curstudent++)
		{
			printf("%s\n", students[curstudent]);
		}
		printf("\n");
		
		/*
			free up the space taken by students and num_letters 
			for use in the future. Remember that, for arrays of pointers,
			you should free each individual pointer before you free the array
			of pointers. Otherwise, if that's your only reference to those
			pointers, they'll never be deleted in your program
		*/
		for(int x = 0; x < num_students; x++)
		{
			free(students[x]);
			free(num_letters[x]);
		}
		free(students);
		free(num_letters);
			
	}
}

/*
	This function is a modified selection sort, it will sort the names
	based on the criteria of the problem.
	
*/
void my_sort(char** names, int** numletters, int numstudents)
{
			
	int highest = 0;
	for(int x = 0; x < numstudents; x++)
	{
		highest = x;
		for(int y = x + 1; y < numstudents; y++)
		{
			int index = 0;

			/*
				while each word has the same number of a given letter,
				increment the counter and keep searching for a difference.
			*/
			while(numletters[highest][index] == numletters[y][index])
			{
				index++;
			}
			if(index == 26) printf("%s is an anagram\n", names[highest]);
			
			/*
				if the current is string is higher than the current highest,
				set the current string to the highest.
			*/
			if(numletters[highest][index] < numletters[y][index])
			{
				highest = y;
			}
		}
		
		//swap the highest with the current index
		char* tempname = names[x];
		int* templetters = numletters[x];
				
		numletters[x] = numletters[highest];
		numletters[highest] = templetters;
				
		names[x] = names[highest];
		names[highest] = tempname;
	}
}
