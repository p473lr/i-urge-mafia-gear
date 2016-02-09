#include<iostream>
#include<vector>
#include<string>
#include<fstream>
using namespace std;

typedef vector<string> VS;
typedef vector<VS> VVS;

VS current;
VVS X,O,tie;


bool checkX(){
    int i;
    for(i=0;i<3;++i){
        if(current[i][0]=='X'&&current[i][1]=='X'&&current[i][2]=='X') return true;
        if(current[0][i]=='X'&&current[1][i]=='X'&&current[2][i]=='X') return true;
    }
    if(current[0][0]=='X'&&current[1][1]=='X'&&current[2][2]=='X') return true;
    if(current[2][0]=='X'&&current[1][1]=='X'&&current[0][2]=='X') return true;
    return false;
}


bool checkO(){
    int i;
    for(i=0;i<3;++i){
        if(current[i][0]=='O'&&current[i][1]=='O'&&current[i][2]=='O') return true;
        if(current[0][i]=='O'&&current[1][i]=='O'&&current[2][i]=='O') return true;
    }
    if(current[0][0]=='O'&&current[1][1]=='O'&&current[2][2]=='O') return true;
    if(current[2][0]=='O'&&current[1][1]=='O'&&current[0][2]=='O') return true;
    return false;
}


void go1(){
    bool fx = checkX();
    bool fo = checkO();
    if(fx&&fo) return;
    if(fx){
        X.push_back(current);
    }
    else if(fo){
        O.push_back(current);
    }
    else{
        tie.push_back(current);
    }
}

void go(int h){
    if(h==9) {go1();return;}
    current[h/3][h%3]='#';
    go(h+1);
    current[h/3][h%3]='X';
    go(h+1);
    current[h/3][h%3]='O';
    go(h+1);
}




int main(){
    current.resize(3,"   ");
    go(0);
    ofstream fin("circle.in");
    ofstream fout("circle.out");
    fin<<50<<endl;
    for(int i=1;i<=50;++i){
        fout<<"Game #"<<i<<":\n";
        int t1 = rand()%3;
        if(t1==0){
            current = tie[rand()%tie.size()];
            fout<<"Lolcat's game!\n";
        }
        else if(t1==1){
            current = X[rand()%X.size()];
            fout<<"Eureka! X wins!\n";
        }
        else if(t1==2){
            current = O[rand()%O.size()];
            fout<<"Gadzooks! O wins!\n";
        }
        fout<<endl;
        for(int j=0;j<3;++j){
            fin<<current[j]<<endl;
        }
        
    }
    
    return 0;
    
}

