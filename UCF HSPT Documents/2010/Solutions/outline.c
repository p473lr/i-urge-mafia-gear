// Arup Guha
// 4/15/2010
// Solution to 2010 UCF High School Contest Problem: Outline

#include <stdio.h>

#define NAME_LENGTH 41
#define LINE_LENGTH 100
#define MAX_NAMES 300

struct pair {
    char title[NAME_LENGTH];
    char subtitle[NAME_LENGTH];
};

void sort(struct pair* list[], int length);
int pair_cmp(const struct pair* a, const struct pair* b);
void my_copy(char first[], char second[], char line[]);
void do_outline(struct pair* list[], int length);
void rec_outline(struct pair* list[], int length, char title[], int level);
int main_header(struct pair* list[], int length, char thistitle[]);
int is_title(struct pair* list[], int length, char thissub[]);

int main() {
    
    FILE* ifp = fopen("outline.in", "r");
    char line[LINE_LENGTH];
    
    // Read in the number of names in the first case.
    fgets(line, LINE_LENGTH, ifp);
    int size = atoi(line);
    int casenum = 1;
    
    // This is our terminating condition.
    while (size != 0) {
          
        int i;
        struct pair* list[MAX_NAMES];
        
        // Read each pair in the input.
        for (i=0; i<size; i++) {
            
            // Set up our string tokenizer.
            fgets(line, LINE_LENGTH, ifp);
            
            // Allocate the struct to store this info and copy it in.
            list[i] = (struct pair*)malloc(sizeof(struct pair));
            my_copy(list[i]->subtitle, list[i]->title, line);
        }
  
        // Sort the list so printing our output is easier.
        sort(list, size);
          
        // Print out the outline.
        printf("Outline #%d\n", casenum);
        do_outline(list, size);
        
        // free array memory
        for (i=0; i<size; i++)
            free(list[i]);
        
        // Get the number of items in the next case and update case number. 
        fgets(line, LINE_LENGTH, ifp);
        size = atoi(line);
        casenum++;
    }
        
    fclose(ifp);
    return 0;
}

// Runs a basic selection sort on this array of pointers. The order is
// lexicographical by title, breaking ties with subtitle.
void sort(struct pair* list[], int length) {
     
    int i,j;
    
    // Find the maximum value to put in index i.
    for (i=length-1; i>0; i--) {
    
        // Go through everything, marking the largest index.
        int maxIndex = 0;
        for (j=1; j<=i; j++)
            if (pair_cmp(list[j], list[maxIndex]) > 0)
                maxIndex = j;
                
        // Swap the largest value into the last slot of the array.
        struct pair* temp = list[i];
        list[i] = list[maxIndex];
        list[maxIndex] = temp;       
    }
}

// Returns a negative integer if the pair pointed to by a comes before
// the pair pointed to by b, 0 if they are the same, and a positive
// integer otherwise. 
int pair_cmp(const struct pair* a, const struct pair* b) {
    
    int ans = strcmp(a->title, b->title);
    
    // Here's the answer if the titles are different.
    if (ans != 0)
        return ans;
        
    // Tie is broken by subtitle.
    return strcmp(a->subtitle, b->subtitle);    
}

// Cuts off a string with a new line character in it and null terminates it,
// removing the newline character.
void my_copy(char first[], char second[], char line[]) {
     
    int i = 0;
    
    // Extract first string and null terminate.
    while (line[i] != '/') {
        first[i] = line[i];
        i++;
    }
    first[i] = '\0';
    i++; // Move on.
    
    // Extract second string and null terminate.
    int j=0;
    while ( (line[i] != '\n') && (line[i] != '\r') ) {
        second[j] = line[i];
        j++;
        i++;
    }
    second[j] = '\0';
}

// Prints out the outline of the pair listing list of length items.
void do_outline(struct pair* list[], int length) {

    // Loop through all potential headers.
    int i=0;
    while (i < length) {
          
        // We process main headers at this level.
        if (main_header(list, length, list[i]->title)) {
                  
            // Print out our header.                
            printf("%s\n", list[i]->title);
            
            // Print out all of its subtitles with a call to a recursive func.
            int j=i;
            while (j < length && strcmp(list[j]->title, list[i]->title) == 0) {
                  
                rec_outline(list, length, list[j]->subtitle, 1);
                j++;      
            }
            
            // Advance to the next distinct title.
            i = j;
        }
        
        // We will skip over this non-main header.
        else {
        
            // Skip over all names in the list that start with the same title.
            int j = i;
            while (j < length && strcmp(list[j]->title, list[i]->title) == 0)
                j++;
                
            // Advance i to the next distinct title.
            i = j;
        }      
    }
    printf("\n");
}

// Recursively prints an outline for title, using the database of pairs list
// which contains length pairs. Level indicates how deeply nested this title
// is.
void rec_outline(struct pair* list[], int length, char title[], int level) {
     
    int i;
    
    // Print out spaces for this level, then the word.
    for (i=0; i<4*level; i++)
        printf(" ");
    printf("%s\n", title);
    
    // Go through the whole list alphabetically, looking for subtitles
    // under this title. Since they are in order already, we'll recursively
    // explore them in the correct order.
    for (i=0; i<length; i++) {
    
        // We found a subtitle, recursively outline it.
        if (strcmp(title, list[i]->title) == 0)
            rec_outline(list, length, list[i]->subtitle, level+1);
    }
    
    // Note: If something was at the bottom level, that loop will finish
    //       without ever matching a title and the function will terminate 
    //       down here without a recursive call.
}

// Returns 1 iff thistitle is a MAIN title for the list.
int main_header(struct pair* list[], int length, char thistitle[]) {
    
    int i;
    
    // Search for this title as a subheader.
    for (i=0; i<length; i++)
        if (strcmp(thistitle, list[i]->subtitle) == 0)
            return 0;
            
    // If we never found it, it's a main title!
    return 1;
}

// Returns 1 iff thissub is a title for the list.
int is_title(struct pair* list[], int length, char thissub[]) {
    
    int i;
    
    // Search for thissub in the list.
    for (i=0; i<length; i++)
        if (strcmp(thissub, list[i]->title) == 0)
            return 1;
            
    // If we get here, we never found it.
    return 0;
}

