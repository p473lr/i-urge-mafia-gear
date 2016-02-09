//********************************************************************
//  Robert the constructor
//	Author: Don Brace
//
//	Input: Number describing number of recycle words to follow, recycle words.
//	Output: constructed word from recycled words.
//	Solution is straight forward, but done brute force.
//	You can uncomment the print statements to follow the logic as program runx.
//********************************************************************
import java.io.*;

public class Robert_the_constructor
{
	public static void main (String[] args) {

		try {
			BufferedReader		br = new BufferedReader(new InputStreamReader(System.in));
			String			stringInt;
			String			goalWord;
			String[]		strWord;
			int			numWords = 0;
			int 			i;
			int			j;
			int			done = 0;
			int			found = 0;
	
			while (done == 0) {

				// Read entire string from console.
				stringInt = br.readLine();
				String[] result = stringInt.split(" "); // Split line by spaces.
				numWords = Integer.parseInt(result[0]);
				if (numWords == 0)
					break;

				//System.out.println("NumWords = " + numWords);
				// Parse out words from result array.
				strWord = new String[numWords];
				for (i = 0; i < numWords; i++) {
					strWord[i] = result[i+1];
					//System.out.print(strWord[i] + ' ');
				} // for
				//System.out.println();
				goalWord = strWord[0]; // First word in input is goal word.

				// Build up the word
				// Pass down one word from outer loop, then loop thru other words looking for a match.
				for (i = 1; i < numWords; i++) {

					found = 0;
					for (j = 1; j < numWords; j++) {
						//System.out.println("Goal:" + goalWord + " strWord1:" + strWord[i] + " strWord2:" + strWord[j]);
						stringInt = FindGoalWord(goalWord, strWord[i], strWord[j]);
						if (stringInt != null) {
							System.out.println(goalWord + " " + stringInt);
							found = 1;
							break;
						}
					} // for
					if (found == 1)
						break;
				}
			}
		} // try
		catch (IOException e) {
		}
	} // main

	// Simple algorighm to split up the goal word info left and right hand sub words.
	// Then look for those subwords in the two recycled words.
	// Use indexOf() and substring() member functions of the String class.
	static String FindGoalWord(String goal, String word1, String word2)
	{
		int i;
		//int j;
		int index;
		int len;
		String subWordL;
		String subWordR;
		String sub1;
		String sub2;
		String sub3;
		String sub4;

		// Loop over the goal word length breaking it up into left and right substrings.
		//System.out.println(goal + ' ' + word1 + ' ' + word2);
		for (i = 0; i <= goal.length(); i++) {
			subWordL = goal.substring(0, i); // Left substring.
			subWordR = goal.substring(i, goal.length()); // Right substring.

			// Now look for the left and right substrings in the recycled words.
			sub1 = "";
			index = word1.indexOf(subWordL);
			len = subWordL.length();
			if ((index >= 0) && (len > 0))
				sub1 = word1.substring(index, index + len);
			sub2 = "";
			index = word1.indexOf(subWordR);
			len = subWordR.length();
			if ((index >= 0) && (len > 0))
				sub2 = word1.substring(index, index + len);
			sub3 = "";
			index = word2.indexOf(subWordL);
			len = subWordL.length();
			if (index >= 0 && len > 0)
				sub3 = word2.substring(index, index + len);
			sub4 = "";
			index = word2.indexOf(subWordR);
			len = subWordR.length();
			if (index >= 0 && len > 0)
				sub4 = word2.substring(index, index + len);

			//System.out.println("subword:" + subWordL + '-' + subWordR + ':' + word1 + '/' + word2 + ':' + sub1 + ' ' + sub2 + ' ' + sub3 + ' ' + sub4);

			// Now just combine the substrings back into a string and compere the result to the goal word.
			// To preserve ordering, return the words in the proper ordeer as a return value.
			String s = sub1 + sub3;
			if (s.compareTo(goal) == 0)
				return word1 + " " + word2;
			s = sub3 + sub1;
			if (s.compareTo(goal) == 0)
				return word2 + " " + word1;

			s = sub1 + sub4;
			if (s.compareTo(goal) == 0)
				return word1 + " " + word2;
			s = sub4 + sub1;
			if (s.compareTo(goal) == 0)
				return word2 + " " + word1;

			s = sub2 + sub3;
			if (s.compareTo(goal) == 0)
				return word1 + " " + word2;
			s = sub3 + sub2;
			if (s.compareTo(goal) == 0)
				return word2 + " " + word1;

			s = sub2 + sub4;
			if (s.compareTo(goal) == 0)
				return word1 + " " + word2;
			s = sub4 + sub2;
			if (s.compareTo(goal) == 0)
				return word2 + " " + word1;
		} // for
		
		return null;
	} // FindGoalWord

} // Robert_the_constructor
