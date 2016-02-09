#include <stdio.h>

#define BUF 1024
#define MAXPRINCES 100
#define DIM 1000
#define R 0
#define C 1
#define STATE 2
#define ALIVE 1
#define DEAD 0

char roster[MAXPRINCES][BUF];
int princes[MAXPRINCES][3];
int num;


setup(){
int i;
scanf("%d",&num);
for(i=0;i<num;i++) {
 scanf("%s %d %d",&roster[i],&princes[i][R],&princes[i][C]);
 princes[i][STATE]=ALIVE;
}
}

int countAlive() {
int s,i;
for(s=i=0;i<num;i++)
 s+=princes[i][STATE]==ALIVE;
return(s);
}

int dist(p,q) int p,q; {
 int dr,dc;
 return( 
  (dr=(princes[p][R]-princes[q][R]))*dr + 
  (dc=(princes[p][C]-princes[q][C]))*dc
  );
}

void printb(){
int p;
for(p=0;p<num;p++)
 printf("%s (%d, %d) %s\n",
  roster[p],
  princes[p][R],
  princes[p][C],
  (princes[p][STATE]==ALIVE)?"ALIVE":"DEAD");
printf("\n");
}

void play() {
int p,q,c,d,t,lr,lc;

while(countAlive()>1)
 for(p=0;p<num;p++) {

  if(princes[p][STATE]==ALIVE)  {

#ifdef DEBUG
    printb();
   Printf("#%d %s checking ...\n",p,roster[p]);
#endif
   for(c=2*DIM*DIM+1,q=0;q<num;q++)
    if((p!=q)&&(princes[q][STATE]==ALIVE)&&((d=dist(p,q))<c))
     c=d;
#ifdef DEBUG
   printf("min distance = %d\n",c);
#endif
   for(lr=lc=DIM,t=q=0;q<num;q++)
    if((p!=q)&&(princes[q][STATE]==ALIVE)&&(dist(p,q)==c)){
#ifdef DEBUG
    printf("#%d %s is %d away from #%d %s\n",
     q,roster[q],c,p,roster[p]);
#endif
     if
      (princes[q][R]*DIM+princes[q][C]<lr*DIM+lc) {
       lr=princes[q][R];
       lc=princes[q][C];
       t=q;
#ifdef DEBUG
       printf("\tlowest coord =%d\n",princes[q][R]*DIM+princes[q][C]);
#endif
       }
    }
   q=t;
#ifdef DEBUG
   printf("moving %s towards %s\n", roster[p], roster[q]);
#endif 
   if(princes[p][R]>princes[q][R]) princes[p][R]--;
   if(princes[p][R]<princes[q][R]) princes[p][R]++;
   if(princes[p][C]>princes[q][C]) princes[p][C]--;
   if(princes[p][C]<princes[q][C]) princes[p][C]++;

   if((princes[p][R]==princes[q][R]) && (princes[p][C]==princes[q][C])) {
    printf("(%d, %d) %s defeats %s\n",princes[p][R], princes[p][C],
     roster[p],roster[q]);
    princes[q][STATE]=DEAD;
   }
  }
 }
}

main(){
setup();
play();
}
