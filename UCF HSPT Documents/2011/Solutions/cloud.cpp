#include<iostream>
#include<vector>
#include<map>
#include <fstream>   // file I/O
#include <iomanip>   // format manipulation
#include <string>

using namespace std;

int no_test_cases; 

int main(){
    int n; //number of words in a test case
    string word; //temporary variable to input each word
    ifstream fp_in;
    fp_in.open("cloud.in", ios::in);
    fp_in>>no_test_cases;
    for(int article = 1 ; article <= no_test_cases ; ++article){
        fp_in>>n;
        
        /*
        counter will store the frequency of each word
        */
        map<string,int> counter;
        while(n--){
            fp_in>>word;
            counter[word]++; //each time we input an word we increase its frequency by word
        }
        
        /*
        wordbyfrequency swaps the key-value pair of the map counter
        */
        
        map<int,string> wordbyfrequency;
        map<string,int>::iterator it1;
        for(it1 = counter.begin() ; it1!= counter.end() ; ++it1){
            wordbyfrequency[it1->second] = it1->first;
        }
        
        cout<<"Article #"<<article<<":\n";
        
        /*
        a map sorts its entries by key. So all the entries of wordbyfrequency  have already been sorted by their frequency.
        So just outputting the entries from the back to the front.
        */
        
        map<int,string>::iterator it2 = wordbyfrequency.end(); 
        while(it2!=wordbyfrequency.begin()){
            --it2;
            cout<<it2->second<<" "<<it2->first<<endl;
        }
        cout<<endl;
        
        
    }
    fp_in.close();
    return 0;
}


