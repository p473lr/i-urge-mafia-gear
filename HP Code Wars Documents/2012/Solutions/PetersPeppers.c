#include <stdio.h>

int packs[]= {13,11,6,0};
int counts[]= {0,0,0,0};

void pick(t,r,l) int t,r,l;
{
 int i,s;
 if(r==0) {
  printf("%d peppers can be packed most economically in:\n",t);
  for(i=s=0;packs[i];i++){
  if(counts[i]) printf("%d package%s of %d\n",counts[i],(counts[i]>1)?"s":"",packs[i]);
  s+=counts[i];}
  printf("%d totat packages.\n",s);
  exit(0);
 } else {
  if(r>=packs[l]) {counts[l]++; pick(t,r-packs[l],l); counts[l]--;}
  if(packs[l+1]) pick(t,r,l+1);
 }
}

main(){
int p;
scanf("%d",&p);
pick(p,p,0,0,0);
printf("%d peppers cannot be packed.\n");
}
