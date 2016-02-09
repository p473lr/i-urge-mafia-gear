import java.util.*;
public class bird {
	public static void main(String[] args) {
		Scanner br = new Scanner(System.in);
		int numberOfTestCases = br.nextInt();
		for(int i = 1;i<=numberOfTestCases;i++){
			int distance = br.nextInt();
			
			int numberOfPoints = br.nextInt();
			
			Point[] points = new Point[numberOfPoints];
			for(int j = 0;j<numberOfPoints;j++){
				points[j] = new Point(br.nextInt(), br.nextInt());
			}
			
			double smallestDistance = distance+1;
			
			//The bird is initially located at (0, 0)
			Point bird = new Point(0, 0);
			
			//Each pair of adjacent points forms a line segment which is one of
			//the fences that make up the boundary of the yard. We need to find
			//out how close the closest fence is to the bird's initial location.
			
			for(int j = 0;j<numberOfPoints;j++){
				Point p1 = points[j];
				//Get the next point after the current one wrapping back around to
				//zero if this is the last point
				Point p2 = points[(j+1)%numberOfPoints];
				
				//Checks the distance of the birds initial location from this fence segment
				smallestDistance = Math.min(smallestDistance, pointLineDistance(p1, p2, bird));
			}
			
			//If the smallest distance is less than or equal to the given distance the yard is not safe
			if(smallestDistance <= distance){
				System.out.println("Yard #"+i+": Better not risk it.");
			}
			else{
				System.out.println("Yard #"+i+": Fly away!");
			}
		}
	}
	public static class Point{
		double x, y;
		public Point(double _x, double _y){
			x = _x; y = _y;
		}
		public Point sub(Point p){
			return new Point(x-p.x, y-p.y);
		}
		public double cross(Point p){
			return x*p.y-y*p.x;
		}
		public double mag(){
			return Math.sqrt(x*x + y*y);
		}
	}
	public static double pointLineDistance(Point a, Point b, Point p){
		Point c = p.sub(a);
		Point d = b.sub(a);
		return Math.abs(c.cross(d))/d.mag();
	}
	

}
