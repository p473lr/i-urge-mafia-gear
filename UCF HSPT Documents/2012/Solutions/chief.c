/*
Chief
Michael Galletti
---
This problem asks whether or not it is possible to absorb all of the problems, given a starting radius.
The observation can be made that it is always beneficial to absorb whatever problems we currently can, as they can only make us stronger. With this consideration, it follows that we should always perform the easiest task: try to absorb the problem with the smallest height. If, when following this strategy, we find that there is a problem we cannot absorb, we'll know that there is absolutely no way we could have absorbed it. 

The approach we'll take, then, is to first sort the list of problems in ascending order by height, then iterate over the list and absorb problems as we go, until we run out of problems or cannot absorb the next one.
*/
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define PI 3.141592653589793

//A struct representing a problem, storing its volume and height.
typedef struct problem{
   double V;
   double H;
} Problem;

Problem makeProblem(double V, double H);
double getNextRadius(double r, double additionalVolume);
void sort(int low, int high, Problem *arr);
void merge(int low, int high, Problem *arr);

int main()
{
   // Open the file
   FILE *fin;
   fin = fopen("chief.in", "r");
   
   // Read in the number of problem sets
   int times, k;
   fscanf(fin, "%d", &times);
   
   // Loop through the problem sets
   for(k = 1; k <= times; k++)
   {
      // Read the number of problems and initial ball radius
      int problemCount, i;
      double radius;
      fscanf(fin, "%d %lf", &problemCount, &radius);
      
      // Allocate memory for your problem array
      Problem *p = (Problem*)malloc(problemCount * sizeof(Problem));;
      
      // Read in problems
      for(i = 0; i < problemCount; i++)
      {
         double l,w,h;
         fscanf(fin, "%lf %lf %lf", &l, &w, &h);
         p[i] = makeProblem(l*w*h, h/2.0);
      }
      
      // Sort the problems by height
      sort(0, problemCount, p);
      
      // Iterate over problems, checking to make sure you can absorb the current one
      for(i = 0; i < problemCount; i++)
      {
         if(radius > p[i].H)
            radius = getNextRadius(radius, p[i].V);
         else
            break;
      }
      
      // Output prefix header
      printf("Problem Set #%d: ",k);

      // If we were able to absorb all of the problems, then it's going to be a good set!
      if(i == problemCount)
         printf("It's going to be a good set!\n");
      else
         printf("We need to rebuild this!\n");
      
      //Free your allocated memory
      free(p);
   }
   return 0;
}

// Make a problem object
Problem makeProblem(double V, double H)
{
   Problem ret;
   ret.V = V;
   ret.H = H;
   return ret;
}

// Finds the radius of the new sphere made from adding additionalVolume to the volume of the original sphere with radius r
double getNextRadius(double r, double additionalVolume)
{
   double currentVolume = r*r*r*PI*4.0/3.0;
   double nextVolume = currentVolume + additionalVolume;
   double nextRadiusCubed = nextVolume*3.0/(4.0*PI);
   double nextRadius = pow(nextRadiusCubed, 1.0/3.0); //Alternatively, this step may be performed with a binary search
   return nextRadius;
}

/*
Note that this problem allows for large lists, and so an inefficient sorting algorithm may result in a Time Limit Exceeded judge response.
To combat this, we'll adapt what's known as a divide-and-conquer strategy to sort the list in O(nlog(n)) time, rather than a naive O(n^2). 

Merge sort
Our sorting algorithm will work in the following way:
1. If the size of our array is <= 1, then it is already sorted.
2. Otherwise, call sort again on the left and right halves of the array (sort each half as a sub-array).
3. Once both halves are sorted, we'll merge the two, sorting the original array in the process.

We can merge two sorted lists into one sorted list in linear time, and we'll make at most log(n) recursive calls, ultimately sorting the list in nlog(n) instructions.

low: The low index in the interval we're currently sorting
high: One plus the high index in our interval. We exclude high to make things easier when recursing.
arr: The array to be sorted
*/
void sort(int low, int high, Problem *arr)
{
   // An interval of size 0 or 1 is already sorted, so our work here is done.
   if(high-low <= 1)
      return;
   
   // Calculate the midpoint (the average of low and high)
   int mid = (low + high)/2;
   
   // Make our two recursive calls, sorting both the left and right intervals
   sort(low, mid, arr);
   sort(mid, high, arr);

   // Merge the two sorted intervals into one
   merge(low, high, arr);
}

// Merges two sorted lists, [low..mid-1] and [mid..high-1] in linear time
void merge(int low, int high, Problem *arr)
{
   int mid = (low + high)/2;
   
   // Allocate some space for a temporary array
   Problem *temp = (Problem*)malloc((high-low) * sizeof(Problem));

   // i will index us into the low list, j into the high list, and k into the temporary list
   int i = low, j = mid, k = 0;
   
   // While there are still elements to merge
   while(i < mid || j < high)
   {
     // If high list is empty and low list is non-empty and its current element is less than the current element of high list,
     // copy the current element of low list into temp[k] and increment i.
      if(j == high || (i < mid && arr[i].H < arr[j].H))
      {
         temp[k] = arr[i];
         i++;
      }
      else
      {
         temp[k] = arr[j];
         j++;
      }
      k++;
   }
   
   // Copy temp back into arr
   for(k = low; k < high; k++)
      arr[k] = temp[k-low];
      
   // Free your allocated memory
   free(temp);
}

