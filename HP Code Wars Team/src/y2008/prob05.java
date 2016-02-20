package y2008;


/*
 * CodeWars 2008
 * Problem -- Houston Skyline
 * 
 * Task Description:
 *  Write a program that will produce a silhouette of the Houston skyline, 
 *  given the height and placement of a series of buildings. 
 * 
 * Input:
 *  Prompt the user for the start location, the width, and height of each 
 *  building. Continue prompting for more buildings until a zero is entered 
 *  for the start location. There will be no more than 16 buildings.
 *  
 * Output:
 *  The outline of the resulting silhouette is output to the screen. Smaller
 *  buildings may be completely hidden by larger ones. 
 *
 * Author:
 *  Jeff Autor (with framework "borrowed" rom Karl Olson)
 * 
 */

import java.io.*;

/**
 * Generate a Houston skyline.
 * 
 * @author jautor
 *
 */
public class prob05 {

    /**
     * The main method, used to generate the Houston skyline.
     * 
     * @param arg            (not used).
     * @throws IOException    if an input or output exception occurs.
     * 
     */
    public static void main(String[] arg) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 50 char width, with padding of 1
        int[] skyline = new int[50+1+1]; 

        
        // Get input until user enters "0" or until 16 buildings are entered
        for (int i = 0; i < 16; i++) {
        	System.out.print("Enter building #" + (i + 1) + "'s start:   ");
            int start = Integer.parseInt(br.readLine());
            
            // user enters "0" to stop
            if (start == 0) {
            	break;
            }
            
        	System.out.print("Enter building #" + (i + 1) + "'s width:   ");
            int width = Integer.parseInt(br.readLine());
        	System.out.print("Enter building #" + (i + 1) + "'s height:  ");
            int height = Integer.parseInt(br.readLine());
            
            // check for valid building size
            if (	(start < 1) || (start > 50 - 2) || 
            		(width < 1) || (width > 40) || 
            		(height < 1) || (height > 20 - 1) ||
            		(start + width > 50 - 1)) {
            			System.out.println("Building #" + (i + 1) + " is invalid size.");
           	}
            else {
            	// define the silhouette of each building

		for (int x = start; x < (start + width + 2); x++) {
			if (skyline[x] < height + 1) {
				skyline[x] = height + 1; 
				}
			}
		}

           
            System.out.println();  // blank line between inputs
        }

	// print out the silhouette using the building's max height as a guide

        for (int y = 20; y >= 0; y--) {
        	for (int x = 1; x <= 50; x++) {
        		if (skyline[x] < y) {
				System.out.print(' ');  // sky
				}
			
			if (skyline[x] == y) {
				// check for outside corners
				if ((skyline[x-1] < y) || (skyline[x+1] < y))
					System.out.print('+');
				else
					System.out.print('-');	// roofline
				}

			if (skyline[x] > y) {
				if (((skyline[x-1] < y) && (skyline[x+1] > y)) ||
				((skyline[x-1] > y) && (skyline[x+1] < y))) {
					System.out.print('|');	// wall
					}
				else if ((skyline[x-1] == y) || (skyline[x+1] == y)) {
					System.out.print('+');  // inside corner
					}
				else					
					{
					System.out.print(' '); // interior
					}
				}
			}
		System.out.print('\n');
		}

        // horizontal index
	    for (int i = 1; i <= 50; i++) {
	    	System.out.print(Integer.toString(i % 10).charAt(0));
	    }
   }    
}
