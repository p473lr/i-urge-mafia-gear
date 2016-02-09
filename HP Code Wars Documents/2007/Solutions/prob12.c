#include <stdio.h>

struct domino {
        int bone;
        int left, right;
};

struct domino standard[29];
struct domino user[1000][30];

int debug;

void create_standard_set(void)
{
        int i, j;
        int n;

        n = 1;
        // ID zero is invalid
        standard[0].bone = 0;
        standard[0].left = -1;
        standard[0].right = -1;

        // Create the 28 dominoes
        for(i=0; i<=6; i++) {
                for(j=i; j<=6; j++) {
                        standard[n].bone = n;
                        standard[n].left = i;
                        standard[n].right = j;
                        n++;
                }
        }

}

void print_set(struct domino *s)
{
        while(s->bone != 0) {
                printf("[ %d | %d ]", s->left, s->right);
                s++;
        }
        printf("\n");
}

// Perform eliminations
void eliminate(struct domino *set)
{
        struct domino *s = set;

        if (debug) print_set(set);
        while(s->bone !=0 ) {
                // If two bones match, eliminate them
                if (s[0].right == s[1].left) {
                        // Pull in the remaining bones
                        while(s->bone != 0) {
                                s[0] = s[2];
                                s++;
                        }
                        // Start again on the left
                        s = set;
                        if (debug) print_set(set);
                        continue;
                }
                // Continue to the right
                s++;
        }
}

int main(int argc, char *argv[])
{
        char buf[30];
        int bone;
        char d;
        int set, n;
        int i;
        struct domino *z;
        FILE *fp;
        char *file = "prob12.in";

        for(i=1; i<argc; i++) {
                if (!strcmp(argv[i], "-d")) {
                        debug = 1;
                        continue;
                }
                if (!strcmp(argv[i], "-f")) {
                        file = argv[++i];
                }
        }

        create_standard_set();

        set = 0; n = 0;
        if ((fp=fopen(file, "rt")) == NULL) {
                printf("Can't open %s!\n", file);
                return 0;
        }

        for(;;) {
                // Get user input
                fgets(buf, sizeof(buf), fp);
                if (feof(fp) || !strlen(buf))
                        break;

                // Scan it and validate it
                sscanf(buf, "%d %c", &bone, &d);
                if (bone<0 || bone>28) {
                        printf("Invalid bone number\n");
                        return 0;
                }
                if (d != 'F' && d != 'B') {
                        printf("Invalid bone orientation\n");
                        return 0;
                }

                // Add it to our data set, either forwards or backwards
                if (d == 'F') {
                        user[set][n].bone = bone;
                        user[set][n].left = standard[bone].left;
                        user[set][n].right = standard[bone].right;
                } else {
                        user[set][n].bone = bone;
                        user[set][n].left = standard[bone].right;
                        user[set][n].right = standard[bone].left;
                }
                // Next bone
                n++;
                // If it was bone zero, next set
                if (bone == 0) {
                        n = 0;
                        set++;
                }
        }
        fclose(fp);

        // Print the results
        printf("RESULTS OF ELIMINATIONS\n\n\n");
        for(i=0; i<set; i++) {
                eliminate(user[i]);
                z = user[i];
                if (z->bone) {
                        while(z->bone) {
                                printf("%d ", z->bone);
                                z++;
                        }
                        printf("\n");
                } else {
                        printf("DATASET CLEARED\n");
                }
                printf("\n");
        }

        return 0;
}
