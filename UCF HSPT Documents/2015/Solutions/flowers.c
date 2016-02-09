#include <stdio.h>
#include <stdlib.h>

/*
flowers.c

Find the minimum of the first N-1 animals.
The Nth animal does not pass flowers, it places them into the basket, thus the speed of that animal is irrelevant.

by Nick Buelich
*/

main()
{
	int lines, line, a; 
	scanf("%d",&lines);
	for(line = 1; line <= lines; line++){
		int N, min, temp;
      char name[11];
		scanf("%d",&N);
      min = 987654321;
		for(a=0;a<N-1;a++){
		    scanf("%s %d", &name, &temp);
          if(min>temp) min = temp;
      }
      scanf("%s %d",&name,&temp);
		printf("Assembly #%d: %d\n",line, min);
	}
	exit(0);
}
