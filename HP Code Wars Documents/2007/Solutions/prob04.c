#include <stdio.h>
#include <string.h>
#include <ctype.h>

int analyze(const char *pw)
{
        int criteria=0;
        int upper=0, lower=0, other=0;
        const char *s;

        // At least 8 characters long
        if (strlen(pw) >= 8)
                criteria++;

        // Now, count up uppercase, lowercase and other characters in
        // the password
        for(s=pw; *s; s++) {
                if (isupper(*s)) {
                        upper++;
                } else if (islower(*s)) {
                        lower++;
                } else {
                        other++;
                }
        }

        // Includes both upper and lower case letters
        if (upper && lower)
                criteria++;

        // Includes at least one numeric chracter or symbol
        // (and at least some other characters as well, which is implied,
        // but not specifically stated in the criteria)
        if ((upper || lower) && other)
                criteria++;

        return criteria;
}

char *pwtypes[] = {
        "WEAK", "ACCEPTABLE", "GOOD", "STRONG"
};
int main(int argc, char *argv[])
{
        char buf[31];
        int strength;

        printf("Enter your password:  ");
        gets(buf);

        strength = analyze(buf);
        printf("Your password is %s\n\n", pwtypes[strength]);

        return 0;
}

