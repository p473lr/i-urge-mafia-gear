#include <stdio.h>


#define LOOK(c,r)  (board[r-'0'-1][c-'a'])
#define MARK(c,r,p) board[r-'0'-1][c-'a']=p
#define DIM 8
#define EMPTY 0
#define WHITE 1
#define BLACK 2

char square[]=".WB";

int board[8][8];

void printb(){
int r,c;

for(r=0;r<DIM;r++)
 for(c=0;c<DIM;c++)
  printf("%c%s",square[board[r][c]],(c==(DIM-1))?"\n":"");
printf("\n");
}


play(c,r,p) char c,r; int p;
{
 char i;
 int f;
 MARK(c,r,p);
 
 for(f=0,i=c-1;i>='a';i--) {if(LOOK(i,r)==EMPTY) {f=0;break;} if(LOOK(i,r)==LOOK(c,r)) {f=1;break;}} 
  if(f)for(;i<c;i++) MARK(i,r,p);
 for(f=0,i=r-1;i>='0';i--) {if(LOOK(c,i)==EMPTY) {f=0;break;} if(LOOK(c,i)==LOOK(c,r)) {f=1;break;}}
  if(f)for(;i<r;i++) MARK(c,i,p);

 for(f=0,i=c+1;i<='h';i++) {if(LOOK(i,r)==EMPTY) {f=0;break;} if(LOOK(i,r)==LOOK(c,r)) {f=1;break;}}
  if(f)for(;i>c;i--) MARK(i,r,p);
 for(f=0,i=r+1;i<='8';i++) {if(LOOK(c,r)==EMPTY) {f=0;break;} if(LOOK(c,i)==LOOK(c,r)) {f=1;break;}}
  if(f)for(;i>r;i--) MARK(c,i,p);

 for(f=0,i=1;((c-i)>='a')&&((r-i)>='0');i++) {if(LOOK(c-i,r-i)==EMPTY) {f=0;break;} if(LOOK(c-i,r-i)==LOOK(c,r)) {f=1;break;}}
  if(f)for(;i>0;i--) MARK(c-i,r-i,p);
 for(f=0,i=1;((c+i)<='h')&&((r+i)<='8');i++) {if(LOOK(c+i,r+i)==EMPTY) {f=0;break;} if(LOOK(c+i,r+i)==LOOK(c,r)) {f=1;break;}}
 if(f)for(;i>0;i--) MARK(c+i,r+i,p); 

 for(f=0,i=1;((c-i)>='a')&&((r+i)<='8');i++) {if(LOOK(c-i,r+i)==EMPTY) {f=0;break;} if(LOOK(c-i,r+i)==LOOK(c,r)) {f=1;break;}}
  if(f)for(;i>0;i--) MARK(c-i,r+i,p);
 for(f=0,i=1;((c+i)<='h')&&((r-i)>='0');i++) {if(LOOK(c+i,r-i)==EMPTY) {f=0;break;} if(LOOK(c+i,r-i)==LOOK(c,r)) {f=1;break;}}
  if(f)for(;i>0;i--) MARK(c+i,r-i,p);

}

main(){
 char move[1024];
 int tomove=WHITE;
 int r,c;

 for(r=0;r<DIM;r++) for(c=0;c<DIM;c++) board[r][c]=EMPTY;

 MARK('d','4',WHITE);
 MARK('e','5',WHITE);
 MARK('d','5',BLACK);
 MARK('e','4',BLACK);
 printb();

 for(;;){
 scanf("%s",&move);
 if(move[1]=='N') break;
 play(move[0],move[1],tomove);
 tomove=(tomove==WHITE)?BLACK:WHITE;
 printb();
 }
}
