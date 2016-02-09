#include <stdio.h>

#define MAXMOVE 30
#define MAXDIM 1024
char steps[MAXMOVE][MAXDIM];
int r,c,depth;

void printb(d)int d;{
 int rr,cc;
 for(rr=0;rr<r;rr++) {
  for(cc=0;cc<c;cc++)
   printf("%c",steps[d][rr*c+cc]);
  printf("\n");
  }
  }

void play(d,h)
int d;
char *h;
{
 int i;
 int f;
 int valid;
 int rr,cc;
 char hist[1024];
 char tomove;
 int move;
 int n;

 /* printf("hist=%s\n",h); */
/*   printb(d);  */

 if(d<depth) {
  for(f=1,i=0;f&&(i<d);i++)
   f=f&&strcmp(steps[i],steps[d]);
/*  if(!f) printf("repeat position, skipping ...\n");  */
  if(f) {
   for(move=-1;move<2;move+=2)  {

    for(tomove='a';tomove<='j';tomove++) {
     for(n=0,valid=1,rr=0;valid&&(rr<r);rr++)
      for(cc=0;valid&&(cc<c);cc++)
       if(tomove==steps[d][rr*c+cc])
        n++,valid=valid&&((rr+move)>=0)&&((rr+move)<r)&&
         (
	  (steps[d][(rr+move)*c+cc]==tomove) ||
	  (steps[d][(rr+move)*c+cc]=='.') ||
	  (steps[d][(rr+move)*c+cc]=='X')
	 );
     if(valid&&(n>0)) { 
      /* printf("%c can move %s\n",tomove,(move<0)?"up":"down"); */
      sprintf(hist,"%s%c%c ",h,tomove,(move<0)?'U':'D');
      for(i=0;i<r*c;i++) steps[d+1][i]=steps[d][i];
      for(rr=((move<0)?1:(r-2));(rr>=0)&&(rr<r);rr-=move)
       for(cc=0;cc<c;cc++)
        if(steps[d+1][rr*c+cc]==tomove) {
         steps[d+1][(rr+move)*c+cc]=tomove;
 	 steps[d+1][rr*c+cc]='.';
	}
      play(d+1,hist);
      }
      }
     
    for(tomove='1';tomove<='9';tomove++) {
     for(n=0,valid=1,rr=0;valid&&(rr<r);rr++)
      for(cc=0;valid&&(cc<c);cc++)
       if(tomove==steps[d][rr*c+cc])
        n++,valid=valid&&((cc+move)>=0)&&((cc+move)<c)&&
         (
	  (steps[d][rr*c+cc+move]==tomove) ||
	  (steps[d][rr*c+cc+move]=='.') ||
	  (steps[d][rr*c+cc+move]=='X')
	 );
     if(valid&&(n>0)) { 
       /* printf("%c can move %s\n",tomove,(move<0)?"left":"right");  */
      sprintf(hist,"%s%c%c ",h,tomove,(move<0)?'L':'R');
      for(i=0;i<r*c;i++) steps[d+1][i]=steps[d][i];
      for(rr=0;rr<r;rr++)
       for(cc=((move<0)?1:(c-2));(cc>=0)&&(cc<c);cc-=move)
        if(steps[d+1][rr*c+cc]==tomove) {
         steps[d+1][rr*c+cc+move]=tomove;
 	 steps[d+1][rr*c+cc]='.';
	}
      play(d+1,hist);
      }
     }
   }
  }
 }
  else {
   for(f=1,i=0;f&&(i<r*c);i++)
    f=f&&(steps[d][i]!='X');
   if(f) {
    printf("%s\n\n",h);
    printb(d);
    }
  }
}

main(){
int i,j;
char m;

scanf("%d %d %d\n", &r, &c, &depth);
for(i=0;i<r*c;i++) {
 m=getchar(); if(m=='\n') m=getchar();
 steps[0][i]=m;
}

play(0,"");
}
