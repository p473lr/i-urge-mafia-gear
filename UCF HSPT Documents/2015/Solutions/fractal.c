/**************************
*
* In this problem we are given a number represented as the sum of two infinite fractions
* and are asked to determine its value. The key to solving this problem is to observe that
* because the denominators of the two fractions continue infinitely, they are actually equivalent
* to x. Because of this, we can rewrite the equation given to us in the following way:
*
*	x   =  a / x + b / x
*	x   = (a + b) / x
*	x^2 = (a + b)
*	x   =  sqrt(a + b)
*
* So, through a little algebra, we've simplified the equation given to us into one we can easily
* evaluate. Since the square root of (a + b) will always be a real number if (a + b) > 0, we only
* need to output "DNE" if the opposite is true.
*
**************************/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(){
	//Declare variables
	int times = 0, i = 0, a = 0, b = 0;
	
	//Read number of cases
	scanf("%d", &times);
	
	//Loop through cases
	for(i = 1; i <= times; i++){
		//Read a and b
		scanf("%d %d", &a, &b);
		
		//Perform DNE check, as described above
		if(a + b <= 0){
			//x is not a real number!
			printf("Fraction #%d: DNE\n", i);
		}else{
			//x must be the square root of (a + b), as described above
			printf("Fraction #%d: %0.2lf\n", i, sqrt(a + b));
		}
	}
	
	return 0;
}
