import java.util.*;
public class sums {
public static void main(String[] args)
{
	Scanner input = new Scanner(System.in);
	int T = input.nextInt();
	for(int t = 0; t<T; t++)
	{
		int n = input.nextInt(), m = input.nextInt();
		int[] papers = new int[n];
		for(int i = 0; i<n; i++)
		{
			papers[i] = input.nextInt();
			papers[i] %= 2; // We only care about whether numbers are even or odd, so make all evens 0 and all odds 1.
		}
		int[] starts = new int[m], ends = new int[m];
		for(int i = 0; i<m; i++)
		{
			starts[i] = input.nextInt();
			ends[i] = input.nextInt();
		}
		/*
		 * We will represent the state of the board as string of 0's and 1's.
		 * The ith character of the string is 0 if player i has an even number and 1 otherwise.
		 */
		String state = "";
		for(int i = 0; i<n; i++) state += papers[i];
		/*
		 * We keep a map from a state string to how many laps around the table into the game we saw the state.
		 * If we see a state that's already in the map, then the state of 
		 * players will go through the sequence states that happened since we last saw the state.
		 * Then it will return to that state and repeat again and again.
		 */
		HashMap<String, Integer> stateLog = new HashMap<String, Integer>();
		HashMap<Integer, String> turnList = new HashMap<Integer, String>();
		HashMap<Integer, Integer> oddsSeen = new HashMap<Integer, Integer>();
		stateLog.put(state, 0);
		turnList.put(0, state);
		oddsSeen.put(0, 0);
		int turn = 0;
		int periodLength = 0;
		int periodStart = 0;
		int odds = 0;
		int periodOdds = 0;
		//System.out.println(Arrays.toString(papers));
		while(true)
		{
			turn++;
			for(int i = 0; i<n; i++)
			{
				int countOdds = 0;
				for(int j = 0; j<n; j++)
					if(i != j && papers[j] == 1)
						countOdds++;
				if(countOdds%2 == 0) papers[i] = 0;
				else papers[i] = 1;
				if(papers[i] == 1)
				{
					odds++;
				}
			}
			//System.out.println(turn+" "+odds);
			oddsSeen.put(turn, odds);
			String newState = "";
			for(int i = 0; i<n; i++) newState += papers[i];
			if(stateLog.containsKey(newState))
			{
				periodLength = turn - stateLog.get(newState);
				periodStart = turn;
				periodOdds = odds - oddsSeen.get(turn - periodLength);
				break;
			}
			else
			{
				stateLog.put(newState, turn);
				turnList.put(turn, newState);
			}
		}
		System.out.println("Game #" + (t+1) + ":");
		for(int i = 0; i<m; i++)
		{
			int ans = countOdd(ends[i], turnList, oddsSeen, periodLength, periodStart, periodOdds, n);
			ans -= countOdd(starts[i] - 1, turnList, oddsSeen, periodLength, periodStart, periodOdds, n);
			System.out.println(ans);
		}
		System.out.println();
	}
}
static int countOdd(int turnsPassed, HashMap<Integer, String> turnList, HashMap<Integer, Integer> oddsSeen, int periodLength, int periodStart, int periodOdds, int n)
{
	int rotations = turnsPassed / n;
	int partialRotation = turnsPassed % n;
	int ans = 0;
	if(rotations >= periodStart)
	{
		ans += (1 + (rotations - periodStart) / periodLength) * periodOdds;
		rotations = (periodStart - periodLength) + (rotations - periodStart)%periodLength;
	}
	ans += oddsSeen.get(rotations);
	int[] papers = stateFromString(turnList.get(rotations));
	for(int i = 0; i<partialRotation; i++)
	{
		int countOdds = 0;
		for(int j = 0; j<n; j++)
			if(i != j && papers[j] == 1)
				countOdds++;
		if(countOdds%2 == 0) papers[i] = 0;
		else papers[i] = 1;
		if(papers[i]%2 == 1) ans++;
	}
	return ans;
}
static int[] stateFromString(String state)
{
	int[] papers = new int[state.length()];
	for(int i = 0; i<state.length(); i++)
	{
		papers[i] = state.charAt(i) - '0';
	}
	return papers;
}
}
