#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int main()
{
	double degrees = 0;//keeps the degree of the turn from the xaxis
	double x = 0;//the x location of the sled
	double y = 0;//the y location of the sled
	char command[10];//string to get the command from the file
	int cases = 0;//holds the number of test cases in the file
	int i;//counter
	
	FILE *in;//file pointer
	
	in = fopen("dogsled.in", "r");//open the file
	
	fscanf(in, "%d", &cases);//read in the number of cases
	
	for(i = 1; i <= cases; i++)//loop through the cases
	{
		fscanf(in, "%s", command);//read in the commands
			
		while(strcmp(command, "Whoa!") != 0)//loop until the dogs are told to stop or until end of a case
		{
			if(strcmp(command, "Haw!") == 0)//turn 10 degrees to the left
			{
				
				if(degrees == 350)
				{
					degrees = 0;
				}
				else
				{
					degrees += 10;
				}
			}
			else if(strcmp(command, "Gee!") == 0)//turn ten degrees to the right
			{
				
				if(degrees == 0)
				{
					degrees = 350;
				}
				else
				{
					degrees -= 10;
				}
			}
			else//move 50 feet in that direction
			{
				
				x += 50 * cos((double)degrees/180*3.1415926535);
				y += 50 * sin((double)degrees/180*3.1415926535);
			}
			fscanf(in, "%s", command);//read in the next command
			
		}
		
		double EPS = 1e-5;
		if(x+EPS >= 485 && x <= 515+EPS && y+EPS >= -15 && y <= 15+EPS)//if the sled ends in the camp
		{
			printf("Simulation #%d: Made it!\n", i);
		}
		else//the sled doesnt end inside the camp
		{
			printf("Simulation #%d: We're lost, eh?\n", i);
		}
		
		//reset the variables for the next case
		x = 0;
		y = 0;
		degrees = 0;
	}
	
	fclose(in);//close the file
	
	return 0;
}
