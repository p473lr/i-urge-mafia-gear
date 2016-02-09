#include<iostream>
#include<fstream>
#include<cctype>
#include<cstdlib>
using namespace std;

char input[16]; //variable to store each line of input

/*
Array to manipulate the integer to genetic character conversion.
For any character its array index is the corresponding value. For example 'G' has value of 2
For for any integer the corresponding character at that integer is the genetic character. 
*/
char base4chars[5] = "ACGT";


/*
A linear search to find the corresponding integer value. just search through the array base4chars for the index.
*/
int getbase4charvalue(char ch){
    for(int i=0;i<4;++i){
        if(base4chars[i]==ch){
            return i;
        }
    }
    return -1; //this line will never be executed
}


/*
recursively printing the base 4 representation of the integer.
*/
void base10to4(int n){
    if(n==0) return;
    base10to4(n/4); //before printing the last digit we need to print the bese-4 representation of n/4
    cout<<base4chars[n%4];
}

/*
base 4 to base 10 conversion.
*/
int base4to10(char input[]){
    int ret = 0;
    for(int i=0;input[i];++i){
        ret*=4;
        ret+=getbase4charvalue(input[i]);
    }
    return ret;
}



int main(){
	//Added to make the program read from file
	ifstream inputFile("E:\genetics.in");

    int test_cases;
    //cin>>test_cases;
	inputFile>>test_cases;
    for(int sequence = 1 ; sequence <= test_cases ; ++sequence){
        //cin>>input;
		inputFile>>input;
		cout<<"Sequence #"<<sequence<<": ";
        
        /*
        If the first symbol is a digit then the input is given as a base 10 integer.
        */
        
        if(isdigit(input[0])){
            int value = atoi(input); //converting the input string to an integer
            base10to4(value); //outputting the base-10 input to base-4 recursively
            cout<<endl;
        }
        /*
        If the first symbol is not a digit then the input is given in base 4.
        */
        else{
            
            cout<<base4to10(input)<<endl; 
        }
    }
    return 0;
}
