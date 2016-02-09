#include<stdio.h>
int main()
{
   FILE* input = fopen("shell.in","r");
   //n = number of test cases, i,j = iterator, s = size, a = area
   int n,i,s;
   long long j;
   fscanf(input," %d", &n);
   for(i = 0; i < n; i++){
      fscanf(input,"%d ", &s);
      double a = 1.0;
      for(j = 2; j <= s; j++){
         a += (1ll<<(j-1ll)) * 3.0 / 4.0;
         if(j >= 8){ // at 8 shell starts overlapping so subtract out the difference
            a -= (1ll<<(j-8ll)) / 2.0;
         }
      }
      printf("Shell #%d: %.3f\n", i+1, a);
   }
}
