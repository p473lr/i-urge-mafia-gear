/* 
 * Program to find out the numeric Palindromes for numbers in a specified
 * range
 * Author: Don Brace
 *
 * This program expects read the starting/ending numbers from standard in.
 *			starting number - The first number whose Palindrom is desired
 *			ending number   - The last number whose Palindrom is desired
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#ifdef DEBUG
#define D(a) a;
#else
#define D(a) ;
#endif

#define FMT "|%3u|%5u|%-14.14s|\n"
#define FMT1 "|%3u|%5u|%-14.14s\n"

void            reverse(char *, char *);

/* Data structure to hold the palindrom values for a number and the number
 * of steps required to calcluate them
 */
struct output {
	unsigned long   num;			/* Number whos Palindrome was desired */
	unsigned long   steps;			/* Number of steps required to calc.  */
	char            number[128];    /* Holds num's Palindrome */
};

main(argc, argv)
	int             argc;
	char          **argv;
{
	unsigned long   starting_num;
	unsigned long   ending_num;
	unsigned long	total_numbers = 0;
	unsigned long   num;
	unsigned long   num_reversed = 0.0;
	unsigned long   temp_num = 0.0;
	unsigned long   total = 0.0;
	unsigned long   num_steps = 0L;
	int		size = 0;
	char            cnum1[128] = { '\0' };
	char            cnum2[128] = { '\0' };

	struct output  *my_results, *current_result;

	/* Decode the command line arguements into numeric values */
	scanf("%u", &starting_num);
	scanf("%u", &ending_num);

	/* Obtain enough memory to hold the data structure */
	num = ending_num - starting_num;
	size = num * sizeof(struct output) + 1;
	my_results = (struct output *) malloc(size);
	if (my_results == (struct output *) NULL) {
		perror("Malloc:");
		printf("error = %d\n", errno);
		exit(1);
	}
	current_result = my_results;

	printf("Finding Palindromes for numbers from %u to %u\n",
	       starting_num, ending_num);

	total_numbers = num+1;
	/* Loop through the range of numbers and calclulate their Palindromes */
	for (num = starting_num; num <= ending_num; num++) {
		num_steps = 0L;
		sprintf(cnum1, "%u", num); /* Insert number into string */
		reverse(cnum1, cnum2);     /* Reverse the string for comparrison */
		if (!strcmp(cnum1, cnum2)) {  /* If equal, a simple Palindrome */
			current_result->num = num;
			current_result->steps = num_steps;
			strcpy(current_result->number, cnum1);
			++current_result;
			continue;
		}
		/* Must now work on number */
		D(printf("================== Number = %u ===========================\n",
			 num));
		D(printf("%-5s%-15s%-15s%-15s\n", "Step", "Number",
			 "Reversed", "Total"));
		D(printf("%-5s%-15s%-15s%-15s\n", "----", "------",
			 "--------", "-----"));
		/* Loop until a valid Palindrome is found */
		while (strcmp(cnum1, cnum2)) {
			sscanf(cnum1, "%ld", &temp_num);
			sscanf(cnum2, "%ld", &num_reversed);
			total = temp_num + num_reversed;
			sprintf(cnum1, "%.d", total);
			reverse(cnum1, cnum2);
			++num_steps;
			D(printf("%-5d%-15d%-15d%-15d %s %s\n",
				 num_steps, temp_num, num_reversed, total, cnum1, cnum2));
		}
		/* printf(FMT, num, num_steps, cnum1); */
		current_result->num = num;
		current_result->steps = num_steps;
		strcpy(current_result->number, cnum1);
		++current_result;
	}

	current_result->num = -1;

	print_results(my_results, total_numbers);
}

/* Function to reverse the characters in a string */
void
reverse(char *str1, char *str2)
{
	char           *c_pt1;
	char           *c_pt2;

	c_pt1 = str1 + strlen(str1) - 1;
	c_pt2 = str2;

	while (1) {
		*c_pt2 = *c_pt1;
		if (c_pt1 == str1)
			break;
		c_pt2++;
		c_pt1--;
	}

	*++c_pt2 = '\0';
}

/* Printout the results, this is a hardcoded hack, but if I had more time...*/
print_results(struct output * m, int num)
{

	int             i;

	printf("Number of Palendromes calculated %d\n", num);
	printf("%5s%6s%-14.14s\n", "|No.|", "Steps|", "Palindrome");
	printf("%5s%6s%-14.14s\n", "|---|", "-----|", "--------------");
	for(i = 0; i < num; i++) {
		printf(FMT1, m->num, m->steps, m->number);
		++m;
	} // while
}
