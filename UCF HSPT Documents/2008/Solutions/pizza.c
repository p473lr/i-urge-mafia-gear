#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main()
{
    //file pointer that will open the input file
    FILE *ifp;
    
    //opens the input file
    ifp = fopen("pizza.in", "r");
    
    //will hold the number of test cases that will be executed
    int cases = 0;
    
    //reads in how test cases there are
    fscanf(ifp, "%d", &cases);
    
    int i = 0;
    
    //for loop to run through every test case in the input file
    for(i = 1; i <= cases; i++)
    {
          int p = 0;
          int t = 0;
          int c = 0;
          
          //reads in a single test case
          fscanf(ifp, "%d", &p);
          fscanf(ifp, "%d", &t);
          fscanf(ifp, "%d", &c);
          
          //using Circumference = 2 * pi * radius and Area = pi * radius ^ 2 
          //the equation becomes Circumference ^ 2 / (4 * pi) this is then
          //divided by the number of slices and then multiplied by the number
          //of slices that Perry ate this is multiplied by 10 for the calories
          //and then use the ceil function in the math header file to round 
          //the final answer up and finally cast the number to an integer
          int ans = ((int)ceil((((p * p)/(4 * 3.14159265))/t) * c * 10));
          
          //prints out the output for the file
          printf("Perfectly Popular Pizza %d: Perry consumed %d calories.\n", i, ans);
    }
}
