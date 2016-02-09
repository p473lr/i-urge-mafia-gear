/********************************************************************
 * UCF HSPC
 * decisions.java
 * judge solution prepared by Nick Beato
 *
 * The idea to solve this problem is as follows:
 *
 * Since all locations are either positive integers less than 1000 or
 * the hotel, -1, we start off saying that we can get absolutely no
 * where (hence, make an array size 1000 initialized to 0).  The
 * problem states that once a location is defined, it never appears
 * again.  This means that we can assume no following definitions
 * can "add" a chance to find a location, and that the current chance
 * of getting to the location is already determined.  Since each
 * location has exactly 2 paths leading from it, we need to add 50%
 * of the chance to get there to each of its destinations.  If
 * we do this fow all definitions, we eventually get to hotel.
 * After this, we simply query the array and print the value, as a
 * rounded percentage.
 * 
 * Throughout this entire problem, I chose to add 1 to all of the
 * locations.  This allows the values -1 to 999 to index an array
 * at locations 0 to 1000
 ********************************************************************/

/*
issues:
can a query be out of range?
*/

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class decisions {
	public static void main(String []args) throws IOException {
		// set up the input and output objects
		Scanner inFile = new Scanner(new File("decisions.in"));
		DecimalFormat formatter = new DecimalFormat("0.00");

		// store the current scenario number
		int scenarioId = 1;

		// indicates the 2 destinations from a defined location
		int [] destinations = new int[2];

		// indicates the source, or location definition
		int source;

		// first, read in where we can get to from the restaurant
		destinations[0] = inFile.nextInt() + 1;
		destinations[1] = inFile.nextInt() + 1;

		// if both indicate the hotel, we are done
		while(destinations[0] != 0 || destinations[1] != 0) {

			// initialize an array for locations 0 to 1000
			// to the value 0.0
			double [] data = new double[1001];
			Arrays.fill(data, 0.0);
			
			// print a header
			System.out.println("Scenario #" + scenarioId + ":");
			scenarioId++;

			// we have a 50% chance of taking either path from the
			// restaurant
			data[destinations[0]] += 0.5;
			data[destinations[1]] += 0.5;

			// no read in location definitions. each one has
			// a source and 2 destinations
			source = inFile.nextInt() + 1;
			destinations[0] = inFile.nextInt() + 1;
			destinations[1] = inFile.nextInt() + 1;

			// while they are not all -1, keep processing
			while(source != 0 || destinations[0] != 0 || destinations[1] != 0) {

				// each destination has a 50% chance on top of 
				// the other definitions that have gone there.
				// we have to factor in the chance to get to the source
				data[destinations[0]] += data[source] * 0.5;
				data[destinations[1]] += data[source] * 0.5;

				// read the next case
				source = inFile.nextInt() + 1;
				destinations[0] = inFile.nextInt() + 1;
				destinations[1] = inFile.nextInt() + 1;
			}
			
			// now we are querying locations until the hotel is queried
			source = inFile.nextInt() + 1;
			while(source != 0) {
				// print the result, note that source had 1 added, so subtract 1
				System.out.println("Location " + (source - 1) + ": " + formatter.format(round(data[source])) + " %");
				source = inFile.nextInt() + 1;
			}

			// finally print the hotel
			System.out.println("Hotal: " + formatter.format(round(data[source])) + " %");
			System.out.println();

			// read in the next restaurant locations
			destinations[0] = inFile.nextInt() + 1;
			destinations[1] = inFile.nextInt() + 1;
		}

		// always close a file!
		inFile.close();
	}

	// round to 2 decimals and make sure to conver to a percent!
	private static double round(double x) {
		return Math.floor(x * 10000.0 + 0.5000001) / 100.0;
	}
}
