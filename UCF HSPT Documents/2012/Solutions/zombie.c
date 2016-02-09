#include <ctype.h>

#include <stdio.h>
#include <stdlib.h>

#include <string.h>

/* builds a histogram of maximal runs */
void build(char* str, int* hist) {
   char ch = -1;
   
   while(*str) {
      /* capitalize just in case... */
      if(toupper(*str) != ch) {
         ch = toupper(*str);
         
         hist[ch-'A']++;
      }
      
      ++str;
   }
}

/* comparison function for qsort */
int cmp(const void* a, const void* b) {
   return strcmp((char*)a, (char*)b);
}

int main() {
   int T, D, q, i, j, k, g, bad;
   int hist[1000+100][26];
   char buf[1000][31], chk[100][101], print[1024*64], *temp;
   
        freopen("zombie.in","r",stdin); //open the input file;
   scanf("%d", &T);
   
   g = 0;
   
   while(T--) {
      memset(chk, 0, sizeof(chk));
      memset(buf, 0, sizeof(buf));
      memset(print, 0, sizeof(print));
      memset(hist, 0, sizeof(hist));
      
      temp = NULL;
      D = q = i = j = k = bad = 0;
      
      scanf("%d", &D);
      
      /* read & capitalize the dictionary */
      for(i = 0; i < D; ++i) {
         scanf("%s", buf[i]);
         
         for(j = 0; j < strlen(buf[i]); ++j) {
            buf[i][j] = toupper(buf[i][j]);
         }
      }
      
      /* sort the words alphabetically */
      qsort(buf, D, 31, cmp);
      
      /* construct a histogram for each word in the dictionary */
      for(i = 0; i < D; ++i) {
         build(buf[i], hist[i]);
      }
      
      scanf("%d", &q);
      
      /* read & construct a histogram for each zombie word */
      for(i = 0; i < q; ++i) {
         scanf("%s", chk[i]);
         
         build(chk[i], hist[i + 1000]);
      }
      
      printf("Scenario #%d:\n", ++g);
      
      /* for each zombie word attempt to match it with a dictionary word */
      for(i = 0; i < q; ++i) {
         memset(print, 0, sizeof(print));
         
         temp = print;
         
         for(j = 0; j < D; ++j) {
            bad = 0;
            
            for(k = 0; k < 26; ++k) {
               /* a word only matches if it has the same number of maximal runs */
               if(hist[j][k] != hist[i+1000][k]) {                  
                  bad = 1;
               }
            }
            
            if(!bad) {
               /* this is a possible match */
               temp += sprintf(temp, "%s?\n", buf[j]);
            }
         }
         
         /* if the buffer ptr has changed then a match has been found */
         if(print != temp) {
            printf("Did you mean:\n%s\n", print);
         } else {
            printf("No matches found.\n\n");
         }
      }
   }
}
