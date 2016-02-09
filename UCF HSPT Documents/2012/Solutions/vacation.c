// Arup Guha
// 3/30/2012
// Solution to 2012 UCF HS Contest Problem: Vacation

#include <stdio.h>
#include <math.h>

#define MAXRIDES 11 // Should be set to 10. I set it to 11 to deal with an invalid input case.

struct pt {
    double x;
    double y;
};

double solve(int perm[], int used[], int k, int n, struct pt rideloc[], int blocked[][MAXRIDES]);
double calcdist(int perm[], struct pt rideloc[], int n);
double getdist(const struct pt* pt1, const struct pt* pt2);

int main() {

    // Open the input file.
    FILE* ifp = fopen("vacation.in", "r");

    int numparks;
    fscanf(ifp, "%d", &numparks);

    // Go through each case.
    int loop;
    for (loop=1; loop<=numparks; loop++) {
        int numrides, numblockedpaths;
        fscanf(ifp, "%d%d", &numrides, &numblockedpaths);

        // Read in the location of each ride.
        struct pt rideloc[MAXRIDES];
        int i, j;
        for (i=0; i<numrides; i++)
            fscanf(ifp, "%lf%lf", &rideloc[i].x, &rideloc[i].y);

        // Store which paths are blocked in an adjacency matrix.
        // if blocked[i][j] = 1, it's blocked.
        int blocked[MAXRIDES][MAXRIDES];
        for (i=0; i<MAXRIDES; i++)
            for (j=0; j<MAXRIDES; j++)
                blocked[i][j] = 0;
        for (i=0; i<numblockedpaths; i++) {

            int e1, e2;
            fscanf(ifp, "%d%d", &e1, &e2);
            blocked[e1-1][e2-1] = 1;
            blocked[e2-1][e1-1] = 1;
        }

        int perm[MAXRIDES], used[MAXRIDES];

        // No ride has been visited yet.
        for (i=0; i<numrides; i++)
            used[i] = 0;

        double ans = solve(perm, used, 0, numrides, rideloc, blocked);

        printf("Vacation #%d:\n", loop);
        if (ans < 999999)
            printf("Jimmy can finish all of the rides in %.3lf seconds.\n\n", ans+numrides*120);
        else
            printf("Jimmy should plan this vacation a different day.\n\n");
    }

    fclose(ifp);

    return 0;
}

// Returns the length of the shortest path that hits each ride exactly
// once (a Hamiltonian Path). If none exists, 1000000 is returned. Note
// that for this problem, this value is sufficient because no single edge
// will be more than 2000, and at most there are 10 of them. I just picked
// a million because it's easily big enough an a nice round number.
double solve(int perm[], int used[], int k, int n, struct pt rideloc[], int blocked[][MAXRIDES]) {
    // Finished case!
    if (k == n) {
        return calcdist(perm, rideloc, n);
    }

    else {

        int i;
        double best = 1000000;
        for (i=0; i<n; i++) {

            // See if ride i is a valid place to go next.
            if (!used[i]) {
                if (k == 0 || blocked[perm[k-1]][i] == 0) {
                    perm[k] = i;
                    used[i] = 1;

                    // See if going this way helps us.
                    double thispath = solve(perm, used, k+1, n, rideloc, blocked);

                    if (thispath < best)
                        best = thispath;

                    // Need to mark this as unused, so a new ride can be put in spot k.
                    used[i] = 0;
                }
            }

        } // end for

        return best;

    }


}

double calcdist(int perm[], struct pt rideloc[], int n) {

    int i;

    // Add in the distance of the first segment.
    struct pt start;
    start.x = 0;
    start.y = 0;
    double dist = getdist(&start, &rideloc[perm[0]]);

    // Add in each subsequent edge in the path.
    // Note the use of the permutation array.
    //int i;
    for (i=1; i<n; i++)
        dist = dist + getdist(&rideloc[perm[i-1]], &rideloc[perm[i]]);

    return dist;
}

// Returns the distance betweeh the point pointed to by p1 and the
// point pointed to by p2.
double getdist(const struct pt* pt1, const struct pt* pt2) {
    return sqrt( pow(pt1->x - pt2->x, 2) + pow(pt1->y - pt2->y, 2) );
}
