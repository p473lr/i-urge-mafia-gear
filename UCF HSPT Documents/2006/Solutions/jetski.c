#include <stdio.h>

int main()
{
    int numcases;
    int casenum;
    int speed;
    double time;
    double radius;
    double area;
    
    // Open the file for reading.
    FILE *input = fopen("jetski.in", "r");
    
    // Read in the number of cases in the input.
    fscanf(input, "%d", &numcases);
    
    // Loop through each case
    for(casenum = 1; casenum <= numcases; casenum++)
    {
        // Read in the speed and time
        fscanf(input, "%d", &speed);
        fscanf(input, "%lf", &time);
        
        // Since the time is in minutes and the speed is in miles per hour,
        // divide the time by 60 to get the hours that Skippy is on his
        // jetski
        time /= 60;
        
        // Get the radius of the circle that Skippy inscribes with his crazy
        // jetski.
        radius = ((double)(speed)) * time;
        
        // The area of the whole circe is pi * radius squared, so get that
        area = 3.1415926535898 * radius * radius;
        
        // Since there's a beach blocking one side of it, we only need half
        // the area.
        area /= 2.0;
        
        // Print out our answer.
        printf("Data Set %d: %0.2lf\n", casenum, area);
    }    
    
    // Close the input file.
    fclose(input);
       
    
    system("PAUSE");
    return 0;
}    
    
