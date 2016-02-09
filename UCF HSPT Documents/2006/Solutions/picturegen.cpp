#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <ctime>

using namespace std;

int random(int a,int b)
{
	int r,m;
m=b-a+1;
	r=rand()%m;
return r+a;
}

bool find(string key,string tv[100],int n)
{
 int i;

for(i=0;i<n;i++)
if(tv[i]==key) return true;

return false;
}

string randStr(int len)
{
 string r;
r="";
int i;
for(i=0;i<len;i++) r+=(char)(random('a','z'));
return r;
}

string randStr(string tv[100],int n)
{
 int len;
 string r="";
 len=random(2,30);
 
 r=randStr(len);
 while(find(r,tv,n))
 {
   r=randStr(len);
 }
 return r;
}

int main()
{
	ofstream cout("picturegen.out");
srand(time(0));

	string  tv[100];
int n,sn,i,j,k,N;
cout.setf(ios::fixed|ios::showpoint);
cout.precision(3);

for(cin>>N;N;N--)
{
	n=random(1,100);
cout<<n<<endl;
for(i=0;i<n;i++)
{
tv[i]=randStr(tv,i);
if(tv[i]!="normal")
{
	cout<<tv[i]<<' '<<random(1,100)/10.0<<endl;
}
else
{
	cout<<tv[i]<<' '<<"1.0"<<endl;
}
}

sn=random(1,100);
cout<<sn<<endl;
for(i=0;i<sn;i++)
{
	cout<<tv[random(0,n-1)]<<' '<<random(2000,50000)/100.0<<endl;
}

cout<<random(1000,10000)/100.0<<' '<<random(1000,10000)/100.0<<endl;

}

}