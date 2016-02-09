/*
 * Sample solution for HP CodeWars 2012
 * Problem 15
 * "Queueing Theory"
 */

#include <stdio.h>
#include <ctype.h>

#define Q_COUNT 9

struct request
{
    int position;
    int size;
    char word[50];
};

struct queue
{
    int next_enq;
    int next_deq;
    struct request req[50];
};

int main(void)
{
    struct queue Q[Q_COUNT] = {{0}};
    char data[50];
    char input[50];
    int length, lines, dq_count;
    int q_num, q_pos, in_pos;
    int request_id, position;
    int x, count;
    char ch;
    int dequeue_num;

    // Initialize output buffer
    for(x=0;x<sizeof(data);x++)
    {
        data[x] = ' ';
    }

    // Read first line
    if(fgets(input, sizeof(input), stdin) == NULL)
    {
        printf("no input\n");
        return 0;
    }

    sscanf(input, "%d%d", &length, &lines);
    if(length>sizeof(data))
    {
        printf("data too small (have %ld need %d)\n", sizeof(data), length);
        return 0;
    }

    dq_count = lines;

    do{
        if(fgets(input, sizeof(input), stdin) == NULL)
        {
            printf("couldn't get the next line\n");
            return 0;
        }

        /* Parse input:
         * Two char for queue ID, followed by
         * space, followed by
         * position (one or two digits), followed by
         * word
         */
        q_num = input[1] - '1';
        q_pos = 0;
        q_pos = input[3] - '0';
        in_pos = 5;

        if(isdigit(input[4]))
        {
            q_pos = (q_pos*10) + (input[4] - '0');
            in_pos = 6;
        }

        request_id = Q[q_num].next_enq;

        Q[q_num].req[request_id].position = q_pos;

        x = 0;
        while(isalpha(input[in_pos]))
        {
            Q[q_num].req[request_id].word[x] = input[in_pos];
            x++;
            in_pos++;
        }

        Q[q_num].req[request_id].size = x;
        Q[q_num].next_enq += 1;

    } while(--lines);

    // Read last line
    if(fgets(input , sizeof(input),stdin) == NULL)
    {
        printf("could not get last line\n");
        return 0;
    }

    /*
     * Parse last line and dequeue
     * Format is two char for queue ID, followed by space(s), repeating
     */
    in_pos = 1;
    do{
        dequeue_num = input[in_pos] - '1';
        in_pos += 3;

        request_id = Q[dequeue_num].next_deq;
        position = Q[dequeue_num].req[request_id].position;
        count = Q[dequeue_num].req[request_id].size;
        for(x=0; x < count; x++)
        {
            ch = Q[dequeue_num].req[request_id].word[x];
            data[position] = ch;
            position++;
        }
        Q[dequeue_num].next_deq += 1;
        
    } while(--dq_count);

    // Print result
    for(x=0;x<50;x++)
    {
        printf("%c", data[x]);
    }
    printf("\n");

    return 0;
}
