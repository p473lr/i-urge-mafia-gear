// Arup Guha
// 4/12/2010
// Solution to 2010 UCF High School Contest Problem: Automata

#include <stdio.h>

#define SIZE 100
#define NUMRULES 8

void printAutomata(char state[], int length);
void computeNextGen(char curstate[], char nextstate[], int width, char rules[]);

int main() {
    
    FILE* ifp;
    
    // Open the input file.
    ifp = fopen("automata.in", "r");
    
    int i, numcases;
    fscanf(ifp, "%d", &numcases);
    
    // Go through each case.
    for (i=1; i<=numcases; i++) {
        
        // Read in the size of the grid to print out.
        int gen, width;
        fscanf(ifp, "%d%d", &gen, &width);
        
        char curstate[SIZE+1];
        char nextstate[SIZE+1];        
        char rules[NUMRULES+1];
        
        // Read in the initial state and rule set.
        fscanf(ifp, "%s%s", curstate, rules);
        
        // Print header
        printf("Automata #%d:\n", i);
        
        int day;
        for (day = 0; day<=gen; day++) {
            
            // Print out this day.
            printAutomata(curstate, width);
            
            // Calculate the next day.
            computeNextGen(curstate, nextstate, width, rules);
            
            // Copy this back into the current state.
            strcpy(curstate, nextstate);
        }
        printf("\n");
    }
    
    fclose(ifp);
    return 0;    
}

// Prints out state (which stores 0's and 1's only) in the format
// specified by the problem.
void printAutomata(char state[], int length) {
     
    int i;
    
    // For each cell print either a . or # depending on whether it's
    // dead or alive.
    for (i=0; i<length; i++) {
        if (state[i] == '0')
            printf(".");
        else
            printf("#");
    }
    printf("\n");
            
}

// Given that curstate stores the current state of the automata, width
// stores the width of it and rules stores the 8 rules for it, the next
// state of the automata is calculated and stored in nextstate.
void computeNextGen(char curstate[], char nextstate[], int width, char rules[]) {
     
    int i;
    
    // Update each cell, one by one.
    for (i=0; i<width; i++) {
    
        // Get the values of the three cells that affect cell i.
        // Convert to ints.
        int left = (int)(curstate[(i-1+width)%width] - '0');
        int center = (int)(curstate[i]-'0');
        int right = (int)(curstate[(i+1)%width]-'0');
        
        // The index into the rule is really a binary number based on the
        // state of each cell, with the left being most significant. If a 
        // cell is off, that counts as a 1 in the binary encoding.
        int ruleNum = 0;
        if (left == 0) ruleNum += 4;
        if (center == 0) ruleNum += 2;
        if (right == 0) ruleNum += 1;
        
        // Copy in this rule now.
        nextstate[i] = rules[ruleNum];
    }
    
    // Copy in the null character so we can treat this as a string later.
    nextstate[width] = '\0';
     
}
