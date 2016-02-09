#include <stdio.h>

int main(int argc, char **argv)
{
	int runs;
	scanf("%d", &runs);
	int z;

	for(z = 1; z <= runs; z++)
	{
		int rows, cols;
		scanf("%d %d\n", &rows, &cols);
		int r, c;
		int count = 0;
		char ch;

		for(r = 0; r < rows; r++)
		{
			for(c = 0; c < cols; c++)
			{
				scanf("%c", &ch);
				if(ch == '~' || ch == '<' || ch == '>')
					count++;
			}
			scanf("%c", &ch);
		}

		printf("Net #%d: %d Fish Nuggets\n", z, count);
	}
	return 0;
}
