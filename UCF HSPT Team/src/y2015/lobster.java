/*
 * In this problem, we are given an array of size n, and we can swap elements that
 * are k apart.
 * 
 * Consider splitting the array into k smaller arrays, where each of the smaller
 * arrays contains all the elements that were multiples of k apart in the bigger
 * array, in the same order as they were in the bigger array. For example, if n = 8
 * and k = 3, we can split the array into these groups:
 * 
 * 1 2 3 1 2 3 1 2
 * 
 * where each group is labeled with a number from 1 to 3. Now we can view the swap
 * lobster as sorting each of the smaller arrays independently and only swapping
 * adjacent elements (that is, k = 1). For each of the smaller arrays, this is
 * equivalent to performing bubble sort, so the swap lobster can always sort them.
 * 
 * The solution we end up with is to split the array into smaller arrays as described
 * above, sort each of the smaller arrays, combine them back into a big array, and
 * check if it is sorted.
 */

import java.util.*;
public class lobster
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		int s = in.nextInt();
		
		// Iterate through each test case
		for(int t = 0; t < s; t++)
		{
			int n = in.nextInt();
			int k = in.nextInt();
			
			// Initialize small arrays
			ArrayList<Integer>[] arrays = new ArrayList[k];
			for(int i = 0; i < k; i++)
			{
				arrays[i] = new ArrayList<Integer>();
			}
			
			// Split the array into smaller arrays
			for(int i = 0; i < n; i++)
			{
				arrays[i % k].add(in.nextInt());
			}
			
			// Sort each of the small arrays
			for(ArrayList<Integer> array : arrays)
			{
				Collections.sort(array);
			}
			
			// Determine if the big array is sorted by checking each pair of adjacent elements
			boolean sortable = true;
			for(int i = 0; i < n - 1; i++)
			{
				if(arrays[i % k].get(i / k) > arrays[(i + 1) % k].get((i + 1) / k))
				{
					sortable = false;
				}
			}
			
			// Output the answer
			System.out.print("Lobster #" + (t + 1) + ": ");
			if(sortable)
			{
				System.out.println("Sortable");
			}
			else
			{
				System.out.println("Unsortable");
			}
		}
	}
}
