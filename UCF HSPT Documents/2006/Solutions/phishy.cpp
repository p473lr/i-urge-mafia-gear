#include <fstream>
#include <iostream>
#include <cstring>
#include <cctype>

using namespace std;

const int L=256;
const int ML=1024;

int dotCnt(char *txt,int i,int j)
{
	int x,cnt;
	cnt=0;
	for(x=i;x<=j;x++)
	if(txt[x]=='.') cnt++;
	return cnt;
}

bool validDom(char *txt)
{
	int j,len,x;
	len=strlen(txt);
	j=len-1;
	while(0<=j && isspace(txt[j])) j--;
	txt[j+1]=0;
	if(0<=j)
	{
		if(dotCnt(txt,0,j)>=1)
		{
			for(x=0;x<=j;x++)
			if(txt[x]=='.')
			{
				if(!islower(txt[x-1]) || !islower(txt[x+1])) 
					return false;
			}
			else if(!islower(txt[x])) return false;

			return true;
		}
	}
	return false;
}

char* getDom(char *s)
{
	int i,j,len,dc;
	len=strlen(s);
	j=len-1;
	dc=0;
	while(j>=0 && dc<2)
	{
		if(s[j]=='.') dc++;
		if(dc<2) j--;
	}
	return s+j+1;
}

int main()
{
	ifstream cin("phishy.in");
	char line[L],msg[ML],sender[L],tmp[L];
	char *lnk,*txt,*txtDom,*lnkDom,*p;
	int N,cc,n,x,y,k,len,i;
	bool phishy;
	cin>>N;
	for(cc=1;cc<=N;cc++)
	{
		cin>>tmp>>sender;
		cin>>tmp>>n;
		cin.getline(line,L);
		msg[0]=0;
		for(x=0;x<n;x++)
		{
			cin.getline(line,L);
			strcat(msg,line);
			strcat(msg,"\n");
		}
		len=strlen(msg);
		phishy=false;
		i=0;
		while(i<len && !phishy)
		{
			if(msg[i]=='<')
			{
				lnk=strchr(msg+i,'"')+1;
				txt=strchr(msg+i,'>')+1;
				while(isspace(txt[0])) txt++;

				i=strchr(txt,'>')-msg;

				*strchr(lnk,'"')=0;
				*strchr(txt,'<')=0;
				if(validDom(txt))
				{
					txtDom=getDom(txt);

					p=strchr(lnk+7,'/');
					if(p) *p=0;
					lnkDom=getDom(lnk+7);

					if(strcmp(txtDom,lnkDom)!=0)
					{
						phishy=true;
					}
				}
			}
			i++;
		}
		if(phishy)
		{
			cout<<"Mail from "<<sender<<" looks phishy!"<<endl;
		}
		else
		{
			cout<<"Mail from "<<sender<<" looks ok."<<endl;
		}
	}
	return 0;
}