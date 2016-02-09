// Arup Guha
// 4/22/08
// C++ Solution to 2008 UCF High School Programming Contest Problem Hands

#include <iostream>
#include <fstream>
#include <math.h>

using namespace std;

int solveTriangle(double sides[]);
void sort(double sides[], int length);
void swap(double &a, double &b);

int main() {
     
    int numcases, i;
    ifstream fin("hands.in");

    // Get the number of cases.          
    fin >> numcases;
    
    // Read through these.
    for (i=1; i<=numcases; i++) {
        
        // Store the values as doubles so real-number operations are done.
        double tri1[3], tri2[3];
        
        // Read in the values.
        fin >> tri1[0] >> tri1[1] >> tri1[2];
        fin >> tri2[0] >> tri2[1] >> tri2[2];
        
        // Solve both triangles.
        int hrhand = solveTriangle(tri1);
        int minhand = solveTriangle(tri2);
        
        // We want the maximum length answer of the two.
        int answer = hrhand;
        if (minhand > answer)
            answer = minhand;
            
        // Output the result.
        cout << "Pair #" << i << " of hands requires a clock face at least ";
        cout << answer << " meters wide." << endl;
    } 
    
    fin.close();
    return 0;
}

// Pre-condition: sides is an array of length three storing side lengths that are
//                valid lengths for three sides of a triangle.
// Post-condition: The smallest possible integer length of a clock face is returned
//                 that contains the given triangle by at least 0.01 inches.
int solveTriangle(double sides[]) {
    
    // Sorts the sides of the triangle in non-decreasing order.
    sort(sides, 3);
    
    // The short side is hinged, and here we calculate the length from the
    // hinge to the end of that short side.
    double shortSide = sides[0]/2.0;
    
    // This is the middle length side.
    double middleSide = sides[1];
    
    // Here we calculate the largest angle in the original given triangle, using
    // the law of cosines.
    double includedAngle = 
      acos((sides[0]*sides[0]+sides[1]*sides[1]-sides[2]*sides[2])/(2*sides[0]*sides[1]));
    
    // To calculate the radius, we have to use the triangle that is formed with
    // shortSide, middleSide and their included angle. In the problem picture,
    // this corresponds to solving for the length of the dotted line. Once again,
    // the law of cosines correctly solves this.
    double radius = sqrt(shortSide*shortSide+middleSide*middleSide
                         -2*shortSide*middleSide*cos(includedAngle));
                         
    // This is a bit tricky, but what we need to do is add 0.01 to the radius,
    // due to the problem spec. THEN, we must return the diameter, so we 
    // multiply by 2. Finally we have to call the ceiling function to access
    // the next smallest integer.
    return (int)(ceil(2*(radius + 0.01)));
}

// Pre-condition: A is of length number of values.
// Post-condition: A will contain its previous values in non-decreasing sorted
//                 order.
void sort(double A[], int length) {
     
    int i,j;
    
    // This is a standard bubble sort.
    
    // Loop through each index in backwards order.
    for (i=length-1; i>0; i--) {
        
        // Go through each index until i, swapping adjacent elements that are
        // out of order. At the end of this loop, index i will store the 
        // largest unsorted element, which is its correct sorted position.
        for (j=0; j<i; j++) {
            if (A[j] > A[j+1])
                swap(A[j], A[j+1]);
        }
    }
}

// Pre-condition: none.
// Post-condition: The values located in the variables pointed to by a and b
//                 will have been swapped.
void swap(double &a, double &b) {
     
    double temp = a;
    a = b;
    b = temp;     
}
