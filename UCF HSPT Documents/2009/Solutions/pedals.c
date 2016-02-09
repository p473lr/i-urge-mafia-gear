// Arup Guha
// 4/29/09
// Solution for 2009 UCF High School Programming Contest Problem: Pedals

#include <stdio.h>

int main() {
    
    FILE *fin;
    
    // Open the input file and read in the number of cases.
    fin = fopen("pedals.in", "r");
    
    int numCases;
    fscanf(fin, "%d", &numCases);
    
    int i;
    
    // Go through all the input cases.
    for (i=1; i<=numCases; i++) {
        
        // Read in the number of commands for this case.
        int numCommands;
        fscanf(fin, "%d", &numCommands);
        
        int j;
        
        // Print out the header for this course.
        printf("Course %d:\n", i);
        
        // Go through each command.
        for (j=0; j<numCommands; j++) {
            
            char command[10];
            int number;
            fscanf(fin, "%s", command);
            
            // Go through the first 4 cases of possible commands.
            if (strcmp(command, "forward") == 0)
                number = 1;
            else if (strcmp(command, "backward") == 0)
                number = 2;
            else if (strcmp(command, "left") == 0)
                number = 3;
            else if (strcmp(command, "right") == 0)
                number = 4;
                
            // Whatever the command is, it must be a rotation.
            else {
                 
                // Now we find out which direction and set number. 
                fscanf(fin, "%s", command);
                
                if (strcmp(command, "left") == 0)     
                    number = 5;
                else
                    number = 6; 
            }
            
            // Just print out the number here!
            printf("%d\n", number);
        
        } // end numCommands
        
        printf("\n"); // Blank line between cases.
    
    } // end numCases
    
    fclose(fin);
} // end main
        
