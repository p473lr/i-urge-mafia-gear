#include <stdio.h>
int main()
{
    int dates,d,i;

    //The date can be up to 20 characters long, but we need to leave the last one for the null character
    char date[21];
    freopen("world.in", "r", stdin);
    scanf("%d\n",&dates);

    //Go through the dates
    for(d=0;d<dates;d++)
    {
        //We always want to reset our date so we don't use leftover characters
        for(i=0;i<21;i++)
            date[i]=0;
        gets(date);
        printf("The end of the world is on %s.\n",date);
    }
    return 0;
}
