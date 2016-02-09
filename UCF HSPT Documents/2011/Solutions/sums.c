/*
Program: sums
Language: C
*/

#include <stdio.h>

int main() {
	//open the input file
	FILE* fin = fopen("sums.in", "r");

	//declaration of needed variables
	int num_cases,iter,input;

	//input begins with the number of test cases
	fscanf(fin,"%d", &num_cases);
	
	//run the loop once for each test case
	for(iter = 0; iter<num_cases; iter++) {
		//the digit sum begins at 0
		int digit_sum = 0;

		//read in the number to be tested
		fscanf(fin, "%d", &input);
		
		//while the number is greater than zero (i.e. has digits remaining),
		//mod the number by 10 to extract its last digit and add it to the sum,
		//then divide the number by 10 to remove the last digit.
		while(input > 0) {
			digit_sum += input % 10;
			input /= 10;
		}

		//if the sum is divisible by 25, the success message is displayed,
		//otherwise, the failure message is displayed
		if(digit_sum % 25 == 0)
			printf("Yes, it's 25!!!\n");
		else
			printf("Bummer, no 25.\n");
	}
	
	return 0;
}
