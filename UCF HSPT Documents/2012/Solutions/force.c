#include<stdio.h>
#include<stdlib.h>
main()
{
   FILE *fp;
   fp = fopen("force.in", "r");
   if (fp == NULL) {
        printf("Can't open input file force.in!\n");
        exit(1);
   }

   int N=0, F=0, M=0, I=0;
   fscanf(fp, "%d",&N);   
   for(I=0;I<N;I++){
      fscanf(fp, "%d %d",&F,&M);
      printf("Force #%d: %d\n",(I+1),F*M);
   }
   exit(0);
   fclose(fp);
}
