/***********************************
Michael Galletti
04/02/2011
Ice Mice
***********************************/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define PI 3.1415926535897932384626433832795

//Data structure representing a coordinate
struct Point{
   double x;
   double y;
};

double toDegrees(double rads);
double toRadians(double degs);
struct Point* cIntersect(double r, struct Point c, struct Point a, struct Point b);
struct Point nP(double x, double y);
struct Point sub(struct Point a, struct Point b);
struct Point add(struct Point a, struct Point b);
double cross(struct Point a, struct Point b);
double dis(struct Point a, struct Point b);
double solveTriangle(double a, double A, double B);
void independence(); //declaration of independence

int main(){
   //Open the input file
   FILE *fin;
   fin = fopen("mice.in", "r");

   int times, k;

   //Scan in the number of ponds to process
   fscanf(fin, "%d\n", &times);

   //Begin processing input
   for(k = 1; k <= times; k++){
      //Read in case data
      double rp, rh, x, y;
      int irp, irh, ix, iy;
      fscanf(fin, "%d %d %d %d", &irp, &irh, &ix, &iy);
      rp = (double)irp;
      rh = (double)irh;
      x = (double)ix;
      y = (double)iy;

      /* The largest rectangle that fits the constraints of the problem, one side being twice the length of the other,
         and fits inside of the circle, will be inscribed in the circle. That is, its four points will all touch the circle's edge and its center
         will be the origin

         We'll assume the short side of the rectangle is length L, and the long side length 2L. Therefore, its area is 2*L^2.

         If we break the rectangle up into 8 right triangles by drawing lines from the origin to its corners and perpendicular lines bisecting its sides,
         we find that one of these triangles has sides L, (1/2)*L, and rp. Using the pythagorean theorem, we find that

         rp^2 = L^2 + (1/4)*L^2.
         rp^2 = (5/4)*L^2
         (4/5)*rp^2 = L^2

         Therefore, using the area we came up with earlier, we can replace L^2 and find that the area of the largest possible rectangle is in fact

         A = 2*((4/5)*rp^2) */

      double L = sqrt(rp*rp*4./5.); //Length of the shorter side
      double area = 2.*L*L; //Area of the original rectangle
      double narea = 0; //Area of the new rectangle

      struct Point o = nP(0, 0); //Point representing the origin
      struct Point holeCenter = nP(x, y); //Point representing the center of the hole

      //Find the two points in the hole at which the line extending from the origin to the hole center intersects with the hole
      struct Point *consider = cIntersect(rh, holeCenter, o, holeCenter);
      struct Point i; //We will place the closer of the two in this struct

      //If the hole is centered on the origin, we will need to choose two arbitrary points on the hole's circumference, as there is no line
      if(x==0&&y==0){
         consider[0] = nP(0,rh);
         consider[1] = nP(0,-rh);
      }

      //Choose the point which is closer to the origin
      if(dis(consider[0], o) < dis(consider[1], o)){
         i = consider[0];
      }else{
         i = consider[1];
      }

      //printf("Considering (%.2f %.2f) and (%.2f %.2f)\n", consider[0].x, consider[0].y, consider[1].x, consider[1].y);

      /*  Once we have this point, we will attempt to calculate the area of the largest rectangle that could be placed adjacent to it.
          Consider the placement of the rectangle. Its top side would have to be tangent to this point, touching at the midpoint to maximize its area.

          As such, imagine extending a line from this point at a 45 degree angle downward, until touching the edge of the pond. This line represents
          the diagonal of the left half of the rectangle. Finding the length of this line will allow us to find the area of the rectangle, using the following
          reasoning:

               The diagonal is of length c.
               It forms two isoscoles triangles.
               a^2 + b^2 = c^2
               In an isoscoles triangle, a = b
               a^2 + a^2 = c^2
               2*a^2 = c^2
               If one of the sides is of length a, the area of the square is a^2. Because the rectangle is composed of two of these squares, the rectangle has area 2*a^2.
               Therefore, A_rectangle = 2*a^2 = c^2

          We know that the angle will be extended at a 45 degree angle, but not much else. We do, however, have the circle to work with. By forming a triangle
          using point i, the origin, and the point on the edge of the hole, we can use the law of sines to solve the triangle for c. The length of one of the
          sides is rp, the length of another side is the distance from point i to the origin, and we have the angle of the line we extended, 45 degrees.

          What we find in solving the triangle is that it comes down to three cases. */

      //Three cases
      if(i.x==0&&i.y==0){
         //Case 1: Point i is (0,0)
         //The solution is rp*rp, as the length of the line is the radius of the circle
         narea = rp*rp;
      }else if(dis(o, holeCenter) > rh){
         //Case 2: The origin is outside of the hole, c is the hypotenuse of the triangle
         //Solve for c with sin(45)/r=sin(tb)/p.d((0,0))=sin(tc)/c
         //Solution is c^2
         narea = solveTriangle(45, rp, dis(i, o));
      }else if(dis(o, holeCenter) < rh){
         //Case 3: The origin is inside of the hole, rp is the hypotenuse
         //Solve for c with sin(135)/r=sin(tb)/p.d((0,0))=sin(tc)/c
         //Solution is c^2
         narea = solveTriangle(135, rp, dis(i, o));
      }

      //printf("Before: %.2f After: %.2f\n\n",area, narea);

      printf("Pond #%d:\n",k);
      //If the new area exceeds the original area, there is space for the original area to exist (and the new area actually goes out of bounds!)
      if(narea > area){
         //ICE CLEAR!!!
         printf("ICE CLEAR!!! %.2f\n\n", area);
      }else{
         //OBSTRUCTION!
         printf("OBSTRUCTION! %.2f\n\n", narea);
      }

      //Free your allocated memory
      free(consider);
   }

   return 0;
}

//Uses law of sines to solve a triangle, with the user providing angle-side-side
double solveTriangle(double _a, double _A, double _B){
   double c;
   double b;
   double a = _a;
   double C;
   double B = _B;
   double A = _A;

   double ae = sin(toRadians(a))/A;
   double be;
   double ce;

   b = toDegrees(asin(ae*B));
   be = sin(toRadians(b))/B;

   c = 180-a-b;

   C = sin(toRadians(c))/be;

   //C*C is the area of the rectangle for whichever triangle was being solved
   return C*C;
}

//Convert radians to degrees
double toDegrees(double rads){
   double convert = 360/(2*PI); //360 degrees for every 2*PI radians
   return rads * convert;
}

//Convert degrees to radians
double toRadians(double degs){
   double convert = (2*PI)/360; //2*PI radians for every 360 degrees
   return degs * convert;
}

//Create a new point
struct Point nP(double x, double y){
   struct Point out;
   out.x = x;
   out.y = y;
   return out;
}

//Subtract one point from another
struct Point sub(struct Point a, struct Point b){
   return nP(a.x-b.x, a.y-b.y);
}

//Add one point to another
struct Point add(struct Point a, struct Point b){
   return nP(a.x+b.x, a.y+b.y);
}

//Take the cross product of two points
double cross(struct Point a, struct Point b){
   return a.x*b.y-a.y*b.x;
}

//Determine the distance between two points
double dis(struct Point a, struct Point b){
   struct Point d = sub(a,b);
   return sqrt(d.x*d.x+d.y*d.y);
}

//Used in circle intersection
double sgn(double x){
   return (x < 0)?-1:1;
}

//Finds the two points at which the line AB passes through the circle at C.
//The discriminant d can be used to determine whether the line is secant, tangent, or there is no intersection
//d < 0 - no intersection
//d > 0 - secant
//d = 0 - tangent
//Input: radius, center of circle, line defined by AB
struct Point* cIntersect(double r, struct Point c, struct Point a, struct Point b){
   a = sub(a, c);
   b = sub(a, c);
   double dx = b.x-a.x;
   double dy = b.y-a.y;
   double dr = sqrt(dx*dx + dy*dy);
   double D = cross(a, b);
   double d = r*r*dr*dr-D*D;

   double x1 = (D*dy+sgn(dy)*dx*sqrt(r*r*dr*dr-D*D))/(dr*dr);
   double y1 = (-D*dx+sgn(dy)*dy*sqrt(r*r*dr*dr-D*D))/(dr*dr);
   double x2 = (D*dy-sgn(dy)*dx*sqrt(r*r*dr*dr-D*D))/(dr*dr);
   double y2 = (-D*dx-sgn(dy)*dy*sqrt(r*r*dr*dr-D*D))/(dr*dr);

   struct Point i1 = nP(x1, y1);
   struct Point i2 = nP(x2, y2);
   //The two points of intersection
   i1 = add(i1,c);
   i2 = add(i2,c);
   struct Point *ret = malloc(2*sizeof(struct Point));

   ret[0] = i1;
   ret[1] = i2;
   return ret;
}
