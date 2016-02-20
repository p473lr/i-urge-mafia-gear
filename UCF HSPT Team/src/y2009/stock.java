// Author: Stephen Fulwider
// This solution implements two methods of solving the problem, a naive O(n^2) brute
//	force approach, and an O(n) dynamic programming approach.

import java.io.File;
import java.util.Scanner;

public class stock
{

	public static void main(String[] args) throws Exception
	{
		new stock();
	}

	final int oo = (int) 1e9;
	final int MAXN = 1000;

	int N; // number of stocks
	int[] S = new int[MAXN]; // stock prices

	stock() throws Exception
	{
		Scanner in = new Scanner(new File("stock.in"));
		for (int T = in.nextInt(), tc = 1; T > 0; T--, tc++)
		{
			// read in the stocks - multiply everything by 8 so we can just work in integers
			N = in.nextInt();
			for (int i = 0; i < N; i++)
			{
				int stockPrice = 0;

				String price = in.next();

				// if this is a whole number, go ahead and get that value, then read the fractional part
				if (price.indexOf('/') == -1)
				{
					stockPrice = Integer.parseInt(price) * 8;
					price = in.next();
				}

				// get the numerator and denominator
				int n = price.charAt(0) - '0';
				int d = price.charAt(2) - '0';
				
				// convert the fraction to an integer
				stockPrice += (n * 8 / d);
				S[i] = stockPrice;
			}

			// solveBruteForce(tc);
			solveDynamicProgramming(tc);
			
		}
	}

	// Here is an O(n^2) algorithm which solves the problem using brute force
	//	Note that this is okay to use in this problem, since n is at most 1000, and 1000^2 is okay for contest time.
	//	But, a linear time solution (that is, O(n)) exists for this problem, and can be found below.
	void solveBruteForce(int tc)
	{
		int maxGain = -oo;
		int startDay = 0;
		int endDay = 0;
		
		// simply consider each pair (i,j) where i<j of days and remember the maximum among them all
		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
			{
				int gain = S[j] - S[i];
				if (gain > maxGain) // notice that the order in which we explore days satisfies the tie breaking rule
				{
					maxGain = gain;
					startDay = i;
					endDay = j;
				}
			}

		String gain = formatNumber(maxGain);
		System.out.printf("Period %d: The biggest gain was from day %d to day %d of %s.%n", tc, startDay + 1, endDay + 1, gain);
	}

	// Here is an O(n) algorithm which solves the problem using dynamic programming
	// Let F[x] be defined as the the maximum gain achievable by ending at day x 
	//	Clearly, if we can calculate F[x] for all days, then our maximum gain is just the largest value in F
	// To calculate F[x] efficiently, notice that for each day we can either start a new gain between day x-1 and day x,
	//	or we can take the longest gain ending at day x-1 and extend it to day x.
	// This leads us to the following recurrence relation:
	//	F[x] = max(S[i]-S[i-1], S[i]-S[SD[i-1]]), where SD[x] is the optimal starting day by ending at day x,
	//	and S[x] is the price of the stock on day x
	// Using this, we can calculate each F[x] in O(1) time, resulting in an O(n) algorithm, which means we could solve the problem
	//	for much larger values of N that would become intractable with an O(n^2) solution.
	
	int[] F = new int[MAXN]; // F, as describe above
	int[] SD = new int[MAXN]; // Starting Day of the gain ending at day x
	
	void solveDynamicProgramming(int tc)
	{
		F[0] = -oo;
		SD[0] = 0;

		for (int i = 1; i < N; i++)
		{
			F[i] = -oo;
			SD[i] = oo;
			
			// consider starting a new gain
			int gain = S[i] - S[i - 1];
			if (gain > F[i] || (gain == F[i] && i - 1 < SD[i]))
			{
				F[i] = gain;
				SD[i] = i - 1;
			}

			// consider extending the old gain
			gain = S[i] - S[SD[i - 1]];
			if (gain > F[i] || (gain == F[i] && SD[i - 1] < SD[i]))
			{
				F[i] = gain;
				SD[i] = SD[i - 1];
			}
		}

		// get the largest gain across all ending days
		int maxEnd = 0;
		for (int i = 1; i < N; i++)
			if (F[i] > F[maxEnd] || (F[i] == F[maxEnd] && SD[i] < SD[maxEnd]))
				maxEnd = i;

		String gain = formatNumber(F[maxEnd]);
		System.out.printf("Period %d: The biggest gain was from day %d to day %d of %s.%n", tc, SD[maxEnd] + 1, maxEnd + 1, gain);
	}
	
	String formatNumber(int num)
	{
		// get the whole and fractional amount
		int whole = Math.abs(num) / 8;
		int n = Math.abs(num) % 8;
		int d = 8;
		
		// reduce the fraction to lowest terms
		while (n % 2 == 0 && d % 2 == 0)
		{
			n /= 2;
			d /= 2;
		}

		// format the gain as specified
		String gain = "";
		if (num < 0)
			gain = "-";
		if (whole != 0)
			gain += whole + " ";
		gain += n + "/" + d;
		return gain;
	}

}
