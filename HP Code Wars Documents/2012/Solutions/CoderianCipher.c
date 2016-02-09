#include <strings.h>
#include <stdio.h>

char buf[1024];
char nospace[1024];
char key[5];

char shift(c,n) {

if(n>0) return((c+n)>'Z'?(c+n-26):(c+n));
if(n<0) return((c+n)<'A'?(c+n+26):(c+n));
}

main(){

char *c,i,j;
int s;

gets(buf);
strcpy(key,(c=strstr(buf,"KEY"))+4);
c--;
*c='\0';

for(j=i=0;buf[i];i++)
 if(buf[i]!=' ') 
  nospace[j++]=buf[i];
nospace[j]='\0';

while(!(c=strstr(nospace,key))) {
 for(i=0;i<4;i++) key[i]=shift(key[i],1);
 s--;
}

for(i=0;buf[i];i++) 
 if(buf[i]!=' ') buf[i]=shift(buf[i],s);

printf("%s\n",buf);

}
