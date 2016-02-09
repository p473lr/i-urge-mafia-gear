// Arup Guha
// Solution to 2006 UCF High School Programming Contest Problem: Picture
// 5/2/06

#include <iostream>
#include <fstream>

using namespace std;

// Stores information about one type of terrain.
struct terrain {
  char name[31];
  double factor;
};

// Stores information about one type of road segment. Although struct
// terrain could have been reused, a new struct is preferable for
// readability since the double stored in each struct is conceptually
// different. The double for this struct is a distance where as the
// double for struct terrain is a speed adjustment factor.
struct roadseg {
  char name[31];
  double distance;
};

double solve(struct terrain surfaces[], struct roadseg segments[], 
             int numsurfaces, int numsegments, double vel1, double vel2);
double getFactor(struct terrain surfaces[], int numsurfaces, char word[]);
void printArr(double vals[], int n);

int main() {

  int n, s, casenum = 1;

  // Open the input file.
  ifstream fin("picture.in");

  // Set up the output precision.
  cout.setf(ios::fixed);
  cout.setf(ios::showpoint);
  cout.precision(3);

  fin >> n;

  // Keep on going until the sentinel value is reached.
  while (n > 0) {

    // Read in all the different possible terrains.
    struct terrain surfaces[100];
    for (int i=0; i<n; i++) {
      fin >> surfaces[i].name;
      fin >> surfaces[i].factor;
    }

    // Read in all the road segments.
    fin >> s;
    struct roadseg segments[100];
    for (int i=0; i<s; i++) {
      fin >> segments[i].name;
      fin >> segments[i].distance;
    }

    // Read in the velocities of each car.
    double car1vel, car2vel;
    fin >> car1vel >> car2vel;

    // Solve the problem
    double timemeet = solve(surfaces, segments, n, s, car1vel, car2vel);
    cout << "Picture " << casenum << ": " << timemeet << endl;   

    // Go to the next case.
    casenum++;
    fin >> n;
  }
  fin.close();
  return 0;
}

// Given all the requisite input, this function solves the problem at 
// hand. 
double solve(struct terrain surfaces[], struct roadseg segments[], 
             int numsurfaces, int numsegments, double vel1, double vel2) {

  // car1 will store when the first car will begin each segment.
  // car2 will store when the second car will end each segment.
  double car1[100], car2[101];

  // Store all the beginning segment times for car 1.
  double time = 0;

  car1[0] = 0; // Car 1 begins the first segment at time t=0.

  // For each subsequent segment, add the time of the current segment to
  // the previous time spent and store that in the car1 array.
  for (int i=1; i<numsegments; i++) {

    // This is the time calculation assuming regular speed.
    double timeseg = segments[i-1].distance/vel1;

    // Change the time to account for our driving surface.
    timeseg /= getFactor(surfaces, numsurfaces, segments[i-1].name);

    // Add the time in for this segment into our accumulator variable.
    time += timeseg;
 
    // Store this in the car1 array.
    car1[i] = time;
  }

  // Store the ending segment times for car 2.
  time = 0;
  car2[numsegments] = 0;
  // We have to go backwards through the segments.
  for (int i=numsegments-1; i>=0; i--) {

    // Regular time calculation.
    double timeseg = segments[i].distance/vel2;

    // Adjusted for the driving surface.
    timeseg /= getFactor(surfaces, numsurfaces, segments[i].name);
   
    // Add to the total time driving.
    time += timeseg;

    // Store this in the car2 array.
    car2[i] = time;
  }  

  // Determine the road segment in which the two cars meet.
  int seginter;
  int i;
  for (i=1; i<numsegments; i++)
    if (car1[i] > car2[i])
      break;

  // The actual segment of intersection is equal to i-1, since the first
  // time car1's time exceeds car2's time it means that car 1 has crossed
  // car2's path, so we just have to go to the previous segment to find
  // where the crossing occurred.

  // This is the speed factor for the surface we care about.
  double factor = getFactor(surfaces, numsurfaces, segments[i-1].name);


  // Let t equal the amount of time car1 spends in this segment. Then we
  // set up the following equation:

  // t(c1) + (t-t12)(c2) = d

  // where c1 is the velocity of car1 on this surface, 
  //       t12 is the amount of a head start car1 had on car2 on this segment
  //       note that this could be negative, meaning car2 started first.
  //       c2 is the velocity of car2 on this surface,
  //       and d is the total distance of the road segment we are on.

  // Basically t(c1) represents the distance traveled by car1, and
  //           (t-t12)(c2) represents the distance traveled by car2,
  // since we defined t12 to be the amount of time less car2 drives on
  // this segment than car1.

  // Solving this equation for t, we get:

  // t(c1) + (t-t12)(c2) = d
  // t(c1+c2) = d + (c2)(t12)
  // t = [d + (t12)(c2)]/(c1+c2)

  // That is the formula implemented below. (The surface factor has to 
  // be used also.) We are guaranteed that c1+c2 isn't zero since each
  // factor is positive as is each normal car speed.

  double timeinseg = (segments[i-1].distance + (car2[i]-car1[i-1])*vel2*factor)/(vel1*factor+vel2*factor);

  // Now, just add the time of car1 in the given segment to the amount of 
  // time it took to get to that segment and return this value.

  return car1[i-1] + timeinseg;
}

// Just does a sequential linear search to find the factor associated with
// a particular terrain.
double getFactor(struct terrain surfaces[], int numsurfaces, char word[]) {

  // Go through all the surfaces.
  for (int i=0; i<numsurfaces; i++)

    // If we find the terrain, immediately return it's speed factor. 
    if (strcmp(word, surfaces[i].name) == 0)
      return surfaces[i].factor;

  // Should never get here. If it does, a normal terrain is assumed.
  return 1;
}

// For debugging purposes. Prints the first n values of vals on a single line.
void printArr(double vals[], int n) {

  for (int i=0; i<n; i++)
    cout << vals[i] << " ";
  cout << endl;
}
