import java.util.*;
public class hoo {
public static void main(String[] args)
{
	Scanner input = new Scanner(System.in);
	int cases = input.nextInt();
	for(int caseNum = 1; caseNum <= cases; caseNum++)
	{
		int n = input.nextInt();
		int licks = 0;
		while(n > 1)
		{
			// We will use a greedy algorithm, taking the smallest lick we can every time.
			// This way, after each lick we have the biggest lollipop possible.
			for(int i = 2; i<=n; i++)
			{
				if(n%i == 0)
				{
					// We found a divisor, so we divide n by this number and add one to our licks.
					licks++;
					n /= i;
					break;
				}
			}
		}
		if(licks <= 3)
		{
			System.out.printf("Pop #%d: %d licks? Your goose is cooked!\n", caseNum, licks);
		}
		else
		{
			System.out.printf("Pop #%d: A-one... A-two-HOO... A-%d.\n", caseNum, licks);
		}
	}
}
}
