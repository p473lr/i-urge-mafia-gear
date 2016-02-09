/*
 * Sample solution for HP CodeWars 2012
 * Problem 10
 * "Card Counting"
 */

#include <stdio.h>
#include <string.h>

/*
 * All the ranks and suits conveniently fit into one char, except for the tens.
 * For tracking the cards, the placeholder of 'x' is used.  This then requires
 * some special handling in the loops that print out all the found and missing
 * cards.
 */
char rank_list[] = {'2', '3', '4', '5', '6', '7', '8', '9', 'x', 'J', 'Q', 'K', 'A'};
char suit_list[] = {'S', 'H', 'D', 'C'};

char deck[13][4] = {{0}};

void print_results(void);

int main(int argc, char *argv[])
{
	int r_index = 0;
	int s_index = 0;
	char suit, rank;
    char input[255];

    // First entry is the line count.  This solution ignores it.
    scanf("%s", input);
    
    // Process all the cards
    while(scanf("%s", input) == 1)
    {

        // All cards are digits, except for the tens
	    if(strlen(input)==3)
	    {
		    rank='x';
		    suit = input[2];
	    }
	    else
        {
		    rank = input[0];
		    suit = input[1];
	    }

	    switch(suit)
	    {
		case 'S': s_index = 0; break;
		case 'H': s_index = 1; break;
		case 'D': s_index = 2; break;
		case 'C': s_index = 3; break;
	    }

	    switch(rank)
	    {
		case '2': r_index = 0; break;
		case '3': r_index = 1; break;
		case '4': r_index = 2; break;
		case '5': r_index = 3; break;
		case '6': r_index = 4; break;
		case '7': r_index = 5; break;
		case '8': r_index = 6; break;
		case '9': r_index = 7; break;
		case 'x': r_index = 8; break;
		case 'J': r_index = 9; break;
		case 'Q': r_index = 10; break;
		case 'K': r_index = 11; break;
		case 'A': r_index = 12; break;
	    }

	    deck[r_index][s_index]++;
    } 

    print_results();

    return 0;
}

void print_results(void)
{
    int rank, suit;
    int missing_header_printed, duplicate_header_printed;
    int count;

	missing_header_printed = 0;
	for(rank=0;rank<13;rank++)
	{
		for(suit=0;suit<4;suit++)
		{
			if(deck[rank][suit] == 0)
			{
				if(!missing_header_printed)
				{
					missing_header_printed++;
					printf("Missing cards:\n");
				}

                if(rank_list[rank] == 'x')
                {
                    printf("10%c ", suit_list[suit]);

                }
                else
                {
				    printf("%c%c ", rank_list[rank], suit_list[suit]);
                }
			}
		}
	}
	printf("\n");

	duplicate_header_printed = 0;
	for(rank=0;rank<13;rank++)
	{
		for(suit=0;suit<4;suit++)
		{
            count = deck[rank][suit];

			if(count > 1)
			{
				if(!duplicate_header_printed)
				{
					duplicate_header_printed++;
					printf("Extra cards:\n");
				}

                if(rank_list[rank] == 'x')
                {
				    printf("10%c (%d) ", suit_list[suit], count - 1 );
                }
                else
                {
				    printf("%c%c (%d) ", rank_list[rank], suit_list[suit], count - 1);
                }
			}
		}
	}
	printf("\n");
}
