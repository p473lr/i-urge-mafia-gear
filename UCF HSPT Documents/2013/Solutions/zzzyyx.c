
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Zzzyyx or I'm Out (zzzyyx)
//
// Author:        Mike Galletti
// Judge Data:    Aaron Teetor
// C Solution:    Nick Buelich
// Java Solution: Matt Fontaine
// Verifier:      Antony Stabile

#include <stdio.h>

main()
{
   //open the file
   FILE* fp = fopen("zzzyyx.in", "r");

   //read in number of cases
   int cases = 0;
   fscanf(fp, "%d",&cases);

   //loop over the cases
   int i = 0;
   for(i = 1;i<=cases;i++){
      //stores alphabet
      char alpha[28];
      //stores spilt numbers
      char spill[315];

      //fill spill array with null elements
      int k=0;
      for(k=0;k<315;k++)
         spill[k]='\0';

      //read in k, alphabet, and spill
      fscanf(fp,"%d %s %s\n",&k,alpha,spill);

      //bubble sort the elements of spill into lexographically minimal string
      int a=0,b=0,c=0;
      for(a=0;a<315;a++){
         //break if null character is reached
         if(spill[a]=='\0')
            break;

         for(b=a+1;b<315;b++){
            //break if null character is reached
            if(spill[b]=='\0')
               break;

            int va = 0;
            int vb = 0;
            //assign weight to Ath character and Bth character
            for(c=0;c<26;c++){
               if(alpha[c]==spill[a])va=c;
               if(alpha[c]==spill[b])vb=c;
            }

            //reverse
            if(va>vb){
               char temp = spill[a];
               spill[a]=spill[b];
               spill[b]=temp;
               a--;
               break;
            }
         }
      }

      //place null character at Kth position so string prints out to K places
      spill[k]='\0';
      printf("Week #%d: His name is %s!\n",i,spill);
   }

   //done
   fclose(fp);
   return 0;
}
