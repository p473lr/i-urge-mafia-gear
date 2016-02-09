
#include <stdio.h>

int main() {
    // Open the input file for reading
    FILE* ifp = fopen("absolute.in", "r");

    // Get the number of values to check
    int n;
    fscanf(ifp, "%d", &n);

    // Loop over those "n" values
    int i;
    for (i = 0; i < n; i++) {
        // Read this value
        int v;
        fscanf(ifp, "%d", &v);

        // Output it accordingly (making positive numbers negative)
        if (v < 0)
            printf("Integer #%d: %d\n", i+1, v);
        else
            printf("Integer #%d: %d\n", i+1, -v);
    }

    // Close the file and return
    fclose(ifp);
    return 0;
}
