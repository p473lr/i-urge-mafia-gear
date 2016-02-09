#include <stdio.h>

int d[26];

main(){
int i,j,k;
char c;

int m;

for(i=0;i<26;i++) d[i]=0;

while((c=getchar())!=EOF) {
 c=toupper(c);
 if((c<'A')||(c>'Z')) continue;
 c-='A';
 d[c]++;
 }

for(m=i=0;i<26;i++) if(m<d[i]) m=d[i];
for(i=m;i>=0;i--) 
 for(j=0;j<26;j++)
  if(d[j]==i) {
   printf("%c ",j+'A');
   for(c=0;c<d[j];c++) printf("*");
   printf("\n");
   }
}

