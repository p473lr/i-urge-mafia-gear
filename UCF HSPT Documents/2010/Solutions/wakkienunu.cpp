//Include the standard i/o stuff
#include <iostream>
#include <fstream>
using namespace std;
//The alternative to the namespace is
// just calling cin and cout as
// std::cin and std::cout


int main(void)
{
    //The number of appliances, the number of checks,
    // and the base cost, respectively
    ifstream myfile;
    int n, m, cost;

    // Open the input file
    myfile.open("wakkienunu.in");

    myfile >> n;

    // A standard for loop from 0 to n-1 (a total of n times)
    for(int i = 0; i < n; i++)
    {
        //get the name of the appliance
        string app;

        myfile >> app;

        //Get the cost of the appliance
        myfile >> cost;

        //Get m, the number of checks to make
        myfile >> m;

        //Print out the header required by the problem
        cout << "Appliance " << app << ":\n";

        //Now loop over the m checks
        for(int j = 0; j < m; j++)
        {
            //Get the retail vale from input
            int retail;
            myfile >> retail;

            //If we paid more than the base cost
            if(cost < retail)
                cout << "You paid too much!" << endl;
            else if(cost > retail) //else the retail was less than the base cost
                cout << "I love appliances!" << endl;
            else //If it's not greater, and not less, it's equal!
                cout << "Wakkie Nu Nu!" << endl;
            //I have no idea what Wakkie Nu Nu means
        }
        //Print out what extra blank line.
        cout << endl;
    }
    myfile.close();
    return 0;
}

