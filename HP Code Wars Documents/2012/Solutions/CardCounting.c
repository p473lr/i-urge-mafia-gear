#include <stdio.h>
#define SUITS 4
#define RANKS 13

char suitn[]="HDCS";
char rankn[]="A23456789JQK";

int suit(c)
char c;
{
 switch(c) { 
  case 'H' : return 0; break;
  case 'D' : return 1; break;
  case 'C' : return 2; break;
  case 'S' : return 3; break;
  default  : return -1; break;
  }
}

int rank(c)
char c;
{
 switch(c) { 
  case 'A' : return 0; break;
  case '1' : return 1; break;
  case '2' : return 2; break;
  case '3' : return 3; break;
  case '4' : return 4; break;
  case '5' : return 5; break;
  case '6' : return 6; break;
  case '7' : return 7; break;
  case '8' : return 8; break;
  case '9' : return 9; break;
  case 'J' : return 10; break;
  case 'Q' : return 11; break;
  case 'K' : return 12; break;
  default  : return -1; break;
  }
}

int cards[SUITS][RANKS];

main(){
int s,r;
int c;
char p;
int i;

char buf[1024];

for(s=0;s<SUITS;s++) for(r=0;r<RANKS;r++) cards[s][r]=0;
scanf("%d",&c);
for(i=0;i<=c;i++){
 while((p=getchar())!='\n') {
  if(p==' ') continue;
  r=rank(p);s=-2; 
  p=getchar(); if(p=='0') p=getchar();
  s=suit(p); cards[s][r]++;
  }
}

printf("Missing cards:\n");

for(s=0;s<SUITS;s++) for(r=0;r<RANKS;r++) 
 if(cards[s][r]==0) 
  printf("%c%c ",rankn[r],suitn[s]);

printf("\n\nExtra cards:\n");

for(s=0;s<SUITS;s++) for(r=0;r<RANKS;r++) 
 if(cards[s][r]>1) 
  printf("%c%c (%d) ",rankn[r],suitn[s],cards[s][r]-1);
printf("\n");

}
