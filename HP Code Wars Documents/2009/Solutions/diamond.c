// Diamond
// Author: Don Brace
//
#include <stdio.h>
#include <stdlib.h>

// A bit of recursive trickery.
//
char           *
pr(char *ch, int scale, int topscale, int margin)
{
	printf("%*.*s%*.*s%.*s",
	       margin + 1 - scale, scale, ch,
	       scale > 1 ? 2 * scale - 2 : 0, scale - 1, ch,
	     	scale, topscale > scale ? pr(ch, scale + 1, topscale, margin) :
	       scale > 1 ? pr(ch, scale - 1, scale - 1, margin) : "\n");
	return "\n";
}

int
main(int argc, char *argv[])
{
	char c;
	int scale;

	scanf("%c %d", &c, &scale);
	//printf("char %c, scale %d\n", c, scale);
	pr(&c, 1, scale, scale);
	//printf("Done\n");
	return 0;
}
