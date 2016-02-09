import java.io.*;
import java.util.*;

public class radio
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader in = new BufferedReader(new FileReader("radio.in"));
		
		int numStudySessions = Integer.parseInt(in.readLine());
		for(int studySession = 1; studySession <= numStudySessions; studySession++)
		{
			int numStations = Integer.parseInt(in.readLine());
			
			// Create a matrix, with one dimension representing a station and the
			// other dimension representing that stations' programming at a given minute,
			// starting at 6:00PM.
			int stations[][] = new int[numStations][360];
			
			// Read in the values for each individual station.
			for(int station = 0; station < numStations; station++)
			{
				StringTokenizer st = new StringTokenizer(in.readLine());
				
				int musicMinutes = Integer.parseInt(st.nextToken());
				int commercialMinutes = Integer.parseInt(st.nextToken());
				
				// isProgramming = 0: means that in this minute, this station has music playing.
				// isProgramming = 1: means that in this minute, this station has commercials playing.
				int isProgramming = 0;
				
				// Minute count represents the amount of minutes we're in our current "phase", meaning,
				// how long we've been playing music or commercials since the last switch.
				int minuteCount = 0;
				for(int minute = 0; minute < 360; minute++)
				{
					stations[station][minute] = isProgramming;
					
					minuteCount++;
					
					// If we're programming and this should be our last minute of programming,
					// reset the counter and change isProgramming.
					if(isProgramming == 0 && minuteCount == musicMinutes)
					{
                                                minuteCount = 0;
						isProgramming = 1;
					}
					// If we're in commercials and this should be our last minute of commercials,
					// reset the counter and change isProgramming.
					else if(isProgramming == 1 && minuteCount == commercialMinutes)
					{
						minuteCount = 0;
						isProgramming = 0;
					}
				}
			}
			
			// We start off with zero cramming time, and loop through each minute. Since we already have
			// the full schedule planned out in the stations matrix, we simply need to loop through, minute by
			// minute, and see which minutes have two or more stations playing commercials.
			int crammingTime = 0;
			for(int minute = 0; minute < 360; minute++)
			{
				// The index value will be 1 if there's a commercial playing, or zero if there is music.
				// We simply need to add up all the values for this given minute to figure out how many
				// stations are playing music.
				int numCommercials = 0;
				for(int station = 0; station < numStations; station++)
				{
					numCommercials += stations[station][minute];
				}
				
				// If we have more than two commercials playing, this is pure cramming time!
				if(numCommercials >= 2)
				{
					crammingTime++;
				}
			}
			
			System.out.println("Study session #"+studySession+" has "+crammingTime+" minute(s) of pure cramming.  Excellent!");
		}
	}	
}
