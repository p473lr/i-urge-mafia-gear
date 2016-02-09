#include <stdio.h>
#define MAXCOL 60
#define MAXROW 20


int rows=MAXROW;
int cols=0;

char screen[MAXROW][MAXCOL];

void setup(){
int r,c;
for(r=0;r<MAXROW;r++)
 for(c=0;c<MAXCOL;c++)
  screen[r][c]=' ';
}

void printb(){
int r,c;

for(r=rows;r<MAXROW;r++) {
 for(c=0;c<=cols;c++)
  printf("%c",screen[r][c]);
 printf("\n");
 }

for(c=0;c<=cols/10;c++)
 for(r=1;r<=10;r++) 
  printf("%1d",r%10);
printf("\n");
}


void draw(x,w,h) int x,w,h; {
int i,j;

/* printf("drawings %dx%d @ %d\n",w,h,x); */

if(rows>(MAXROW-h)) rows=MAXROW-h;
if(cols<(x+w-1)) cols=x+w-1;
for(i=0;i<w;i++)
 screen[MAXROW-h][i+x-1]='-';
for(i=1;i<h;i++) {
 screen[MAXROW-h+i][x+w-2]='|';
 screen[MAXROW-h+i][x-1]='!';
 for(j=1;j<w-1;j++)
  screen[MAXROW-h+i][x+j-1]='#'; 
}


}
main(){
int b;
int i;
int x,w,h;

setup();
scanf("%d",&b);
/* printf("loading %d buildings\n",b); */
for(i=0;i<b;i++) {
 scanf("%d %d %d",&x,&w,&h);
 draw(x,w,h);
}
printb();
}
