#include <stdio.h>
#include <math.h>

#define PI 3.1415926
#define SA 196.935e6

main(){
double r,w;
double a;

scanf("%lf%lf",&r,&w);
a=2*PI*r*w;
printf("%.0f EARTHS\n",a/SA);
}

