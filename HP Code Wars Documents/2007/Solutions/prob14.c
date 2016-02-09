#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

#define MAX_LENGTH 100

struct position {
        int x, y;
};

// These are the various moves the knight can make
struct position knight_moves[] = {
        { -2, -1 },
        { -1, -2 },
        {  1, -2 },
        {  2, -1 },
        { -2,  1 },
        { -1,  2 },
        {  1,  2 },
        {  2,  1 },
};

struct position start;
struct position destination;

int nr_blocked;
struct position blocked[MAX_LENGTH];

struct position moves[MAX_LENGTH];

int shortest_move_length;
struct position shortest_move[MAX_LENGTH];

// Function to print out the moves
void print_moves(int len)
{
        int i;

        printf("%c%d ", start.x+'@', start.y);
        for(i=0; i<len;i++) {
                printf("%c%d ", moves[i].x+'@', moves[i].y);
        }
        printf("\n");
}

// Function to test if two positions are equal
int position_eq(struct position *a, struct position *b)
{
        if (a->x == b->x && a->y == b->y)
                return 1;
        return 0;
}

// Function to compute the next move
int compute_move(struct position current, int move, int length, int maxlen)
{
        struct position next;
        int i;
        int solutions = 0;

        // Compute the next position
        next.x = current.x + knight_moves[move].x;
        next.y = current.y + knight_moves[move].y;

        // If out of bounds, then not a solution
        if (next.x < 1 || next.x > 8 || next.y < 1 || next.y > 8)
                return 0;

        // If it's a blocked square, then not a solution
        for(i=0; i<nr_blocked; i++) {
                if (position_eq(&blocked[i], &next))
                        return 0;
        }

        // If it's a postion we've already been in, then not a solution
        for(i=0; i<length; i++) {
                if (position_eq(&moves[i], &next))
                        return 0;
        }

        // If we've exceeded the maximum path length, then not a
        // solution
        if (length == maxlen)
                return 0;


        // Store the position
        moves[length++] = next;

        // If equal to the destination, then we have a solution
        if (position_eq(&next, &destination)) {
                printf("Solution: ");
                print_moves(length);
                if (length < shortest_move_length) {
                        shortest_move_length = length;
                        memcpy(shortest_move, moves, length*sizeof(struct position));
                }
                return 1;
        }

        // Otherwise, try each of the eight combinations from here
        for(i=0; i<8; i++) {
                solutions += compute_move(next, i, length, maxlen);
        }

        return solutions;
}

// This wrapper function calls the compute_move function for each
// of the eight starting moves
int compute_moves(struct position start, int maxlen)
{
        int i;
        int solutions = 0;

        for(i=0; i<8; i++) {
                solutions += compute_move(start, i, 0, maxlen);
        }
        return solutions;
}

// This draws the chessboard with the starting, ending and blocked
// positions shown.  This is not necessary to solve the problem, but
// it helps make sure the data is parsed correctly.
void draw_chessboard(void)
{
        struct position d;
        int i;
        char ch;

        printf("+---+---+---+---+---+---+---+---+\n");
        for(d.y=8; d.y>=1; d.y--) {
                for(d.x=1; d.x<=8; d.x++) {
                        ch = ' ';
                        if (position_eq(&d, &start)) ch = 'S';
                        if (position_eq(&d, &destination)) ch = 'E';
                        for(i=0; i<nr_blocked; i++) {
                                if (position_eq(&d, &blocked[i])) {
                                        ch = 'X';
                                        break;
                                }
                        }
                        for(i=0; i<shortest_move_length; i++) {
                                if (position_eq(&d, &shortest_move[i])) {
                                        ch = '*';
                                        break;
                                }
                        }
                        printf("| %c ", ch);
                }
                printf("| %d\n", d.y);
                printf("+---+---+---+---+---+---+---+---+\n");
        }
        printf("  A   B   C   D   E   F   G   H\n\n");
}

// The main program starts here
int main(int argc, char *argv[])
{
        char buf[256];
        int maxlen=0, solutions=0;
        int i;

        for(i=0; i<argc; i++) {
                // Find all solutions up to max length
                if (!strcmp(argv[i], "-max")) {
                        maxlen = atoi(argv[++i]);
                        continue;
                }
        }

        // Read user input
        // Start position
        printf("Enter start position:  ");
        gets(buf);
        start.x = toupper(buf[0]) - 'A' + 1;
        start.y = buf[1] - '0';
        if (start.x < 1 || start.x > 8 || start.y < 1 || start.y > 8) {
                printf("Bad position\n");
                return 0;
        }

        // End position
        printf("Enter end position:  ");
        gets(buf);
        destination.x = toupper(buf[0]) - 'A' + 1;
        destination.y = buf[1] - '0';
        if (destination.x < 1 || destination.x > 8 || destination.y < 1 || destination.y > 8) {
                printf("Bad position\n");
                return 0;
        }


        // Block positions
        for(i=0;;i++) {
                printf("Enter a blocked position (XX to quit):  ");
                gets(buf);
                if (strlen(buf) == 0 || toupper(buf[0]) == 'X')
                        break;
                blocked[i].x = toupper(buf[0]) - 'A' + 1;
                blocked[i].y = buf[1] - '0';
                nr_blocked++;
                if (blocked[i].x < 1 || blocked[i].x > 8 || blocked[i].y < 1 || blocked[i].y > 8) {
                        printf("Bad position\n");
                        return 0;
                }
        }
        printf("\n\n");

        // Set this to a very high number (100 in this case)
        shortest_move_length = MAX_LENGTH;
        draw_chessboard();

        // If maxlen is set to something other than zero,
        // then find all solutions up to that length
        if (maxlen) {
                solutions += compute_moves(start, maxlen);
        } else {
                // Otherwise, find the shortest solution
                while(!solutions) {
                        maxlen++;
                        solutions += compute_moves(start, maxlen);
                }
        }

        // Print the results
        printf("\nThere are %d solutions of %d or fewer moves.\n", solutions, maxlen);

        printf("\nThe shortest solution is %d moves:\n", shortest_move_length);
        printf("%c%d ", start.x+'@', start.y);
        for(i=0; i<shortest_move_length; i++) {
                printf("%c%d ", shortest_move[i].x+'@', shortest_move[i].y);
        }
        printf("\n\n");
        draw_chessboard();

        return 0;

}
