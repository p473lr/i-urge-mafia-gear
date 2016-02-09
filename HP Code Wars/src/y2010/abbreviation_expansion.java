/*
 * Abbreviation Expansion solution.
 * Author: Don Brace
 * 
 * Keys to solving this problem:
 *     1. Build up list of words. (A Vector is a good choice).
 *     2. Read in each abbreviation. Strip off and save Punctuation mark.
 *        2a. Find punctuation mark by shifting each character to uppercase
 *            and check to see if it is below 'A' or above 'Z'.
 *     3. Look for the letter in each abbreviation in each word in the word
 *        list, in the same order. IE. stbry matches strawberry not storyboard.
 *     4. Apply rules:
 *            a. If two or more words can be formed by adding the same
 *               number of letters, then the abbreviation should be
 *               printed as-is.
 *            b. Use the shortest word in the match list.
 */
import java.util.*;

public class abbreviation_expansion {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Vector word_list = new Vector();
		Vector abb_list = new Vector();
		String word;
		boolean separator_found = false;

		/*
		 * Read in the word list and the abbreviations
		 */
		while (true) {
			word = scan.next();
			if (word.equals("~"))
				break;
			//System.out.println(word);
			if (word.equals("|")) {
				separator_found = true;
				continue;
			}

			/*
			 * I could have just built up the word list and then
			 * processed the abbreviations, but I like using
			 * Vectors.
			 */
			if (separator_found)
				abb_list.add(word);
			else
				word_list.add(word);
		} /* while */

		/*
		 * Decode the abbreviations accorging to the rules.
		 */
		DecodeJitter(word_list, abb_list);

	} /* main */

	static String LookupWord(Vector wl, String abv) {
		int i;
		int j;
		int k;
		int index;
		int found;
		int foundLen = 100;
		int[] lengths = new int[wl.size()];/* Track lengths of matches*/
		int nummatches = 0; /* How many matches were found */
		String foundString = null;

		/*
		 * Get the starting letter
		 * Strip off punctuation mark.
		 */
		String punctuation = FindPunctuation(abv);
		int len = abv.length();

		/*
		 * Do not look for punctuation mark in word list. It does
		 * not exist.
		 */
		if (punctuation != null)
			--len;

		/*
		 * For every abbreviation passed in, look thru the word list
		 * and select the best matches.
		 */
		for (i = 0; i < wl.size(); i++) {
			String word = (String) wl.get(i);

			/* 
			 * To narrow it down more:
			 * All letters in abbreviation must be in word
			 * in same order.
			 */
			found = 0;
			k = 0;
			for (j = 0; j < len; j++) {
				String l1 = abv.substring(j,j+1);
				index = word.indexOf(l1, j);
				/*
				 * Each successive letter in the abbreviation
				 * must be at a higher index to preserve
				 * ordering.
				 */
				if (index < k) {
					found = 0;
					break;
				}

				++found;
				k = index; /* Remember last found position */
			}

			if (found < len) /* Must find all abbv. letters */
				continue;


			/*
			 * Need to add letters from word to abv.
			 * Smallest number wins.
			 */

			if (word.length() < foundLen) {
				foundString = word;
				foundLen = word.length();
			}

			lengths[nummatches] = word.length();
			++nummatches;

		} /* for */

		/*
		 * If no match found, use abbreviation
		 */
		if (foundString == null)
			return abv;

		/*
		 * If two or more words can be formed by adding
		 * the same number of letters, use abv
		 */
		if (nummatches >= 2) {
			k = 0;
			for (i = 0; i < nummatches; i++) {
				if (foundString.length() == lengths[i])
					++k;
			}
			if (k >= 2)
				return abv;
		}

		/*
		 * Add in the punctuation mark
		 */
		if (punctuation != null)
			foundString += punctuation;

		return foundString;
	}

	/*
	 * Look for a punctuation mark.
	 */
	static String FindPunctuation(String s) {
		int i;

		for (i = 0; i < s.length(); i++) {
			String p = s.substring(i,i+1); /* Strip off a char */
			p = p.toUpperCase(); /* Convert it to upper case */
			Integer I = new Integer(p.charAt(0)); /* Ascii value */
			int c = I.intValue();
			/*
			 * If not within alphabet, then something else,
			 * for this problem it is a punctuation mark.
			 */
			if ((c > 'Z') || (c < 'A'))
				return s.substring(i,i+1);
		}

		/*
		 * No punctuation mark found.
		 */
		return null;
	}
	/*
	 * Need to lookup best fit word from abbrev. list
	 */
	static void DecodeJitter(Vector wl, Vector al) {
		int i;
		String s;
		String p;

		for (i = 0; i < al.size(); i++) {
			s = (String) al.get(i);

			p = LookupWord(wl, s);
			System.out.print(p + " ");
		} /* for */
		System.out.println();

	} /* DecodeJitter */

}; /* abbreviation_expansion */
