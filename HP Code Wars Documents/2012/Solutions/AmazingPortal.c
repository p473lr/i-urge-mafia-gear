#include <stdio.h>
#define MAXROW 25
#define MAXCOL 75

#define START 'S'
#define FINISH 'F'
#define PORTA 'A'
#define PORTB 'B'
#define PORTC 'C'
#define PORTD 'D'
#define EMPTY '.'
#define PATH '-'
#define WALL '#'

#define S 0
#define F 1
#define A1 2
#define A2 3
#define B1 4
#define B2 5
#define C1 6
#define C2 7
#define D1 8
#define D2 9
#define ROW 0
#define COL 1

int coords[10][2] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
int used[4]={0,0,0,0};
int usedCount=0;

char maze[MAXROW][MAXCOL];
int dimr,dimc;
int best;


void dump(){
int r,c;

for(r=0;r<dimr;r++) { 
 for(c=0;c<dimc;c++)
  printf("%c",maze[r][c]);
 printf("\n");
}
}

void printb(){
int r,c;

for(r=0;r<dimr;r++) {
 for(c=0;c<dimc;c++) {
       if((r==coords[S][ROW]) &&(c==coords[S][COL])) printf("S");
  else if((r==coords[F][ROW]) &&(c==coords[F][COL])) printf("F");
  else if((r==coords[A1][ROW])&&(c==coords[A1][COL])&&used[0]) printf("A");
  else if((r==coords[A2][ROW])&&(c==coords[A2][COL])&&used[0]) printf("A");
  else if((r==coords[B1][ROW])&&(c==coords[B1][COL])&&used[1]) printf("B");
  else if((r==coords[B2][ROW])&&(c==coords[B2][COL])&&used[1]) printf("B");
  else if((r==coords[C1][ROW])&&(c==coords[C1][COL])&&used[2]) printf("C");
  else if((r==coords[C2][ROW])&&(c==coords[C2][COL])&&used[2]) printf("C");
  else if((r==coords[D1][ROW])&&(c==coords[D1][COL])&&used[3]) printf("D");
  else if((r==coords[D2][ROW])&&(c==coords[D2][COL])&&used[3]) printf("D");
  else if(maze[r][c]==PATH) printf(".");
  else if(maze[r][c]==EMPTY) printf(" ");
  else printf("%c",maze[r][c]);
 }
 printf("\n");
}
}

  

void setup(){
int r,c;
char i;
scanf("%d %d",&dimr,&dimc);
best=dimr*dimc;

for(r=0;r<dimr;r++)
 for(c=0;c<dimc;c++)  {
  i=getchar();maze[r][c]=(i=='\n')?getchar():i;
  switch(maze[r][c]) {
   case START :  {coords[S][ROW]=r;coords[S][COL]=c;break;}
   case FINISH : {coords[F][ROW]=r;coords[F][COL]=c;break;}
   case PORTA : {if(coords[A1][ROW]==-1) 
     {coords[A1][ROW]=r;coords[A1][COL]=c;break;} else
     {coords[A2][ROW]=r;coords[A2][COL]=c;break;}}
   case PORTB : {if(coords[B1][ROW]==-1) 
     {coords[B1][ROW]=r;coords[B1][COL]=c;break;} else
     {coords[B2][ROW]=r;coords[B2][COL]=c;break;}}
   case PORTC : {if(coords[C1][ROW]==-1) 
     {coords[C1][ROW]=r;coords[C1][COL]=c;break;} else
     {coords[C2][ROW]=r;coords[C2][COL]=c;break;}}  
   case PORTD : {if(coords[D1][ROW]==-1) 
     {coords[D1][ROW]=r;coords[D1][COL]=c;break;} else
     {coords[D2][ROW]=r;coords[D2][COL]=c;break;}}  
   default:
    break;
   }
}
}

void play(d,r,c) int d,r,c; {
int i,j;
int p;
int dr,dc;
int pr,pc;
char t;

/* printf("===== d=%d, rc=%d,%d\n",d,r,c);
dump();
printf("\n");
*/
 if(maze[r][c]==FINISH) {
 if(d<=best) { 
 best=d;
 printf("Distance: %d squares\nPortals Used: ",d+2);
 for(i=0;i<4;i++)
  for(j=0;j<4;j++)
   if(used[j]==i+1) printf("%c",j+'A');
 printf("\n");
 printb();
 }}
  else {

 for(dr=-1;dr<2;dr++)
  for(dc=-1;dc<2;dc++) {
   
   if(((dr==-1)||(dr==1))&&(dc!=0)) continue;
   if(((dc==-1)||(dc==1))&&(dr!=0)) continue;

   if( 
      ((dr+r)<0)||((dr+r)==dimr) ||
      ((dc+c)<0)||((dc+c)==dimc) ||
      (maze[dr+r][dc+c]==WALL) ||
      (maze[dr+r][dc+c]==PATH)
     ) continue;
      
   switch(maze[dr+r][dc+c]) {
    
    case FINISH: 
     play(d+1,dr+r,dc+c);
     break;

    case EMPTY : 
     maze[dr+r][dc+c]=PATH;
     play(d+1,dr+r,dc+c);
     maze[dr+r][dc+c]=EMPTY;
     break;

    case PORTA:
    case PORTB:
    case PORTC:
    case PORTD:
     t=maze[dr+r][dc+c];
     for(p=A1;p<=D2;p++)
      if(((dr+r)==coords[p][ROW])&&((dc+c)==coords[p][COL])) 
       break;
     pr=((p-A1)%2)?coords[p-1][ROW]:coords[p+1][ROW];
     pc=((p-A1)%2)?coords[p-1][COL]:coords[p+1][COL];
     maze[dr+r][dc+c]=PATH;
     maze[pr][pc]=PATH;
     usedCount++;
     used[(p-A1)/2]=usedCount;
     play(d+1,pr,pc);
     used[(p-A1)/2]=0;
     usedCount--;
     maze[pr][pc]=t;
     maze[dr+r][dc+c]=t;
     break;
    default:
     break;
    }
}
}
}
  

main(){
setup();
play(0,coords[S][ROW],coords[S][COL]);
}
