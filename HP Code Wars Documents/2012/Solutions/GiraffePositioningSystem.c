#include <stdio.h>
#include <strings.h>

#define MAXGIRAFFES 1024
#define MAXNAME 1024
#define MAXDIM 100

char giraffeN[MAXGIRAFFES][MAXNAME];
int  giraffeH[MAXGIRAFFES];
int  giraffes;

int dimr;
int dimc;

int startr;
int startc;

int finr;
int finc;

int field[MAXDIM][MAXDIM];
int visited[MAXGIRAFFES][MAXDIM][MAXDIM];

int best;
char bestH[MAXNAME];

void printb(r,c,g,d,h) int r,c,g,d; char *h;{
int rr,cc;
printf("depth=%d giraffe=%d=%s, height=%d rc=%d,%d\nhist=%s\n",d,g,giraffeN[g],giraffeH[g],r,c,h);
for(rr=0;rr<dimr;rr++) {
 for(cc=0;cc<dimc;cc++) 
  printf("%d ",field[rr][cc]);
 printf("\t");
 for(cc=0;cc<dimc;cc++)
  printf("%d ",visited[g][rr][cc]);
 printf("\n");
 }
 printf("\n");
}


void play(r,c,g,d,h) int r,c,g,d; char *h;
{

 char hist[MAXNAME];
 int t;
 int rr,cc;

 /* printb(r,c,g,d,h); */

 if(d<best) {
 if((r==finr)&&(c==finc)){
  if(g==(giraffes-1)) {
   if(d<best)  {
    best=d;
    strcpy(bestH,h);
/*    printf("BEST = %d steps\n",best);
    printf("Done!\n%s\n",h); */
    }
   }
   else {
   visited[g+1][startr][startc]=1;
   sprintf(hist,"%s\n%s",h,giraffeN[g+1]);
    for(cc=0;cc<dimc;cc++)
   play(startr,startc,g+1,d,hist);
  }
 } else {
   if(field[r][c]<giraffeH[g]) {
    t=field[r][c];
    field[r][c]++;
    sprintf(hist,"%s E",h);
    play(r,c,g,d+1,hist);
    field[r][c]=t;
   } else {

    if((r>0)&&(!visited[g][r-1][c])) {
     visited[g][r-1][c]=1; sprintf(hist,"%s U",h); play(r-1,c,g,d+1,hist); visited[g][r-1][c]=0; } 
    if((r<(dimr-1))&&(!visited[g][r+1][c])) {
     visited[g][r+1][c]=1; sprintf(hist,"%s D",h); play(r+1,c,g,d+1,hist); visited[g][r+1][c]=0; } 
    if((c>0)&&(!visited[g][r][c-1])) { 
     visited[g][r][c-1]=1; sprintf(hist,"%s L",h); play(r,c-1,g,d+1,hist); visited[g][r][c-1]=0; } 
    if((c<(dimc-1))&&(!visited[g][r][c+1])) {
     visited[g][r][c+1]=1; sprintf(hist,"%s R",h); play(r,c+1,g,d+1,hist); visited[g][r][c+1]=0; } 
   }
 } 
 }
} 

main(){
int i;
int t=0;
int r,c;
int buf[MAXNAME];

scanf("%d\n",&giraffes);
for(i=0;i<giraffes;i++) {
 scanf("%s %d",&giraffeN[i],&giraffeH[i]);
 if(t<giraffeH[i]) t=giraffeH[i];
}

for(i=0;i<MAXGIRAFFES;i++)
 for(r=0;r<MAXDIM;r++)
  for(c=0;c<MAXDIM;c++)
   visited[i][r][c]=0;
scanf("%d %d",&dimr,&dimc);
best=giraffes*2*dimr*dimc;

for(r=0;r<dimr;r++) 
 for(c=0;c<dimc;c++) {
  scanf("%s",&buf);
   switch(buf[0]) {
   case 'S' : startr=r;startc=c;field[r][c]=t+1;break;
   case 'F' : finr=r;finc=c;    field[r][c]=t+1;break;
   default: field[r][c]=atoi(buf);
   }
   }

visited[0][startr][startc]=1;
play(startr,startc,0,0,giraffeN[0]);
printf("%s\n",bestH);

}
