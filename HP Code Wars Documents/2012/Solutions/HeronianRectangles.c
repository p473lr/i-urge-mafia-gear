#include <stdio.h>
#include <math.h>

main() {
int start,stop;
int a,b,c;
int s,t;
int l,w;
int A;
int area;

int isSquare(n) int n; { 
int s;
s=(int)sqrt((double)n);
return(n==s*s);
}
scanf("%d %d",&start,&stop);

for(c=start;c<=stop;c++)
 for(a=1;a<=c;a++)
  for(b=a;b<=c;b++) {
   s=a+b+c;
   if((a+b)<=c) continue;
   t=s*(b+c-a)*(a+c-b)*(a+b-c);
   if(!(t%16)&&(isSquare(t/16))) {
    A=(int)sqrt(t/16);
    for(l=1;l*l<A;l++)
     if(!(A%l)) {
      w=A/l;
      if(s==(w+w+l+l))
       printf("(%d, %d, %d)  (%d, %d)\n",a,b,c,(w<l)?w:l,(w<l?l:w));
      }
    }
    }
    }

