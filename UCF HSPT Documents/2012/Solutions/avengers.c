#include <stdio.h>
int main()
{
    int villains;

    //max size for a villain is 40 characters, but we need the 41st to account for the null character
    char villain[41];
    int i,v;
    freopen("avengers.in", "r", stdin);
    scanf("%d",&villains);

    //Call gets before you begin reading because after we read in the number of villains,
    //the pointer hasn't found a new line character yet. If we had left this out, the
    //first call to gets would only put a blank line in villain because it read up until
    //a new line character (which was the next character from the pointer).
    gets(villain);
    for(v=0;v<villains;v++)
    {
        //We always reset the characters in villain to null just in case the next
        //villain has a name of different length
        for(i=0;i<40;i++)
            villain[i]=0;

        //Use gets(char[]) when you need to read an entire line in C
        gets(villain);

        //We are at the beginning of a third
        if(v%3==0)
        {
            printf("Captain America: I'll take care of %s!\n",villain);
        }
        //We are at the middle of a third
        else if(v%3==1)
        {
            printf("Thor: I shall deal with %s!\n",villain);
        }
        //We are at the end of a third
        else
        {
            printf("HULK SMASH!\n");
        }
        //Don't forget the extra line between villains!
        printf("\n");
    }
    return 0;
}
