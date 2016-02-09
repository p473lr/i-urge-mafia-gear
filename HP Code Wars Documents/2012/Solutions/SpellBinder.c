#include <stdio.h>
#include <strings.h>

#define MAX 1024

char input[MAX], from[MAX], to[MAX];
int fl,tl;


main(){
int i;
scanf("%s %s %s",&input, &from, &to);

fl=strlen(from);
tl=strlen(to);

for(i=0;input[i];i++) {
 if(strncmp(input+i,from,fl)==0) {
  printf("%s",to);
  i+=fl-1;
  }
 else
  printf("%c",input[i]);
}
printf("\n");

} 
