#include <stdio.h>
#include <stdlib.h>

// Store the data for the spiral in this array
int data[20][20];

// Control variables: position, current value of the spiral, and
// the size of the spiral
int x, y;
int value;
int n;

// Four simple funtions for each movement in the spiral
int right(void)
{
        int done = 0;
        data[++x][y] = ++value;

        if (value == (n*n)-1)
                done = 1;
        return done;
}

int up(void)
{
        int done = 0;
        data[x][--y] = ++value;

        if (value == (n*n)-1)
                done = 1;
        return done;
}

int left(void)
{
        int done = 0;
        data[--x][y] = ++value;

        if (value == (n*n)-1)
                done = 1;
        return done;
}

int down(void)
{
        int done = 0;
        data[x][++y] = ++value;

        if (value == (n*n)-1)
                done = 1;
        return done;
}

// Program starts here
int main(int argc, char *argv[])
{
        int i, j, k;
        int direction;
        int done;
        char buf[20];

        // Prompt user
        printf("Enter the sizeof the number spiral:  ");
        fgets(buf, sizeof(buf), stdin);

        // Validate input
        n = atoi(buf);
        if ((n&1) == 0 || n < 1 || n > 19) {
                printf("Number must be odd and between 1 and 19!\n");
                return -1;
        }

        // Compute the initial starting position
        value = 0;
        x = n/2;
        y = n/2;
        data[x][y] = value;

        // Set up the control variables
        i = 0;
        direction = 0;

        // Compute the spiral
        // You move each "length" twice to go around the spiral
        // right 1, up 1, left 2,down 2, right 3, up 3, left 4, down 4, etc
        while(n!=1) {
                i++;
                for(j=0; j<2; j++) {
                        for(k=0; k<i; k++) {
                                switch(direction) {
                                case 0:
                                        done = right();
                                        break;
                                case 1:
                                        done = up();
                                        break;
                                case 2:
                                        done = left();
                                        break;
                                case 3:
                                        done = down();
                                        break;
                                default:
                                        printf("Unknown direction!\n");
                                        done = 1;
                                        break;
                                }
                                if (done) break;
                        }
                        if (done) break;
                        direction = (direction + 1) % 4;
                }
                if (done) break;
        }

        // Then print it out
        // x and y happen to be located in the lower right hand
        // corner of the array (e.g. the last position)
        for(i=0; i<=y; i++) {
                for(j=0; j<=x; j++) {
                        printf("%4d", data[j][i]);
                }
                printf("\n");
        }

        return 0;
}
