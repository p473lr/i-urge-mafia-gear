#include <stdio.h>
#include <strings.h>

#define MAX 1024

struct node {
 int q;
 int p;
 char word[MAX];
 } x[MAX];

char buf[MAX];

int cmd[MAX];

main(){
int i,j,k,m;
int l,e;
int last=0;
char queue[10];

for(i=0;i<(MAX-1);i++) buf[i]=' ';
buf[i]='\0';
scanf("%d %d",&l,&e);
for(i=0;i<e;i++)  {
 scanf("%s %d %s",
  &queue,&x[i].p,&x[i].word);
 x[i].q=queue[1]-'0';
}

for(i=0;i<e;i++) {
 scanf("%s ",&queue);
 cmd[i]=queue[1]-'0'; 
 }

for(i=0;i<e;i++) {
 for(j=0;j<e;j++)
  if(x[j].q==cmd[i]) {
   for(m=0;x[j].word[m];m++)
    buf[x[j].p+m]=x[j].word[m];
   if(last<(x[j].p+m)) last=x[j].p+m;
   x[j].q=-1;
   break;
   }
}

 buf[last]='\0'; 
printf("%s\n",buf);

}
