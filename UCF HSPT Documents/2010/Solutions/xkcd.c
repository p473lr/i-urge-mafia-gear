// Arup Guha
// 4/12/2010
// Solution to 2010 UCF High School Contest Problem: XKCD

#include <stdio.h>

void solve(int n);
void print(char word[], int spot, int length, int used[], int sumleft, int* gotone);

int main() {
    
    FILE *ifp;
    
    // Open the file.
    ifp = fopen("xkcd.in", "r");
    
    int n;
    
    fscanf(ifp, "%d", &n);
    
    // Go through each case.
    while (n != 42) {
        solve(n);
        fscanf(ifp, "%d", &n);
    }
    
    // This always prints at the end.
    printf("The answer to life, the universe and everything!\n");
    fclose(ifp);
    return 0;
}

// Wrapper function for recursive function print.
void solve(int n) {
     
    // Output header.
    printf("XKCD-like name(s) of length: %d\n", n);
    
    // Special case because of how my recursive code works.
    if (n == 2) {
        printf("pz\nrx\nsw\ntv\n");
    }
    
    // Regular case.
    else {
    
        // Fill in the null character at the beginning because we know how
        // long each of these will be.
        char answer[43];
        answer[n] = '\0';
        
        // Set up array of letters storing what has been used.
        // a = index 0, z = index 25.
        int used[26];
        int i;
        for (i=0; i<26; i++)
            used[i] = 0;
        
        // Just mark all the vowels as used.
        used[0] = 1;
        used[4] = 1;
        used[8] = 1;
        used[14] = 1;
        used[20] = 1;
        used[24] = 1;
        
        // Make the recursive call with the initial conditions.
        int gotone = 0;
        print(answer, 0, n, used, 42, &gotone);
        
        // This is what we print when nothing printed.
        if (!gotone)
            printf("Mostly Harmless\n");
        
    }
     
}

// Here is the meaning of all the input parameters:
        
// word represents the current word we are building. spot is the index in
// word that we need to fill in next. length represents the length of the
// solution we are trying to find. used stores a zero in each index 
// representing a letter that can still be placed in the word. sumleft 
// represents what the rest of the letters that get added to the word must
// add up to, and gotone is just a pointer to a variable that will indicate
// if any solutions were found.

// This function prints all the words that satisfy the given constraints, with
// spot number of spots in word already filled in the way they are.
void print(char word[], int spot, int length, int used[], int sumleft, int* gotone) {
     
    // This will never work.
    if (sumleft < 0)
        return;
        
    // This one did work.
    if (spot == length && sumleft == 0) {
        
        // Mark that we've printed one and print.
        *gotone = 1;
        printf("%s\n", word);
        return;
    }
    
    // This can't work.
    else if (spot == length)
        return;
    
    // Try each letter.
    int i;
    for (i=0; i<26; i++) {
        
        // We haven't used this one yet.
        if (!used[i]) {
                                        
            // Filling in the first or last slot. To be in the last slot,
            // there are two restrictions (sandwiched between the two
            // previous letters).
            if (spot == 0 ||
               (spot == length-1 && i > (int)(word[length-2] - 'a') &&
                                    i < (int)(word[length-3] - 'a'))) {
                used[i] = 1;
                word[spot] = (char)(i+'a');
                print(word, spot+1, length, used, sumleft-i-1, gotone);
                used[i] = 0;
            }
            
            // Filling in a previous slot - one restriction is that the
            // letter must come before the last letter filled in.
            else if (spot != length-1 && i < (int)(word[spot-1] - 'a')) {
                used[i] = 1;
                word[spot] = (char)(i+'a');
                print(word, spot+1, length, used, sumleft-i-1, gotone);
                used[i] = 0;
            }
        }
    }
                                        
    
}
