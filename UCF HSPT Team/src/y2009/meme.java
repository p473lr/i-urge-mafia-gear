// Author: Stephen Fulwider

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class meme
{

	public static void main(String[] args) throws Exception
	{
		new meme();
	}

	int Mn; // number of memes
	Phrase[] M = new Phrase[20]; // memes

	int Pn; // number of phrases
	Phrase[] P = new Phrase[20]; // phrases

	final String okay = "No memes detected! Let intellectual discourse continue!";
	final String symbols = "@#$%*&!";

	meme() throws Exception
	{
		Scanner in = new Scanner(new File("meme.in"));
		for (int T = Integer.parseInt(in.nextLine()), tc = 1; T > 0; T--, tc++)
		{
			// read in the memes and phrases
			Mn = Integer.parseInt(in.nextLine());
			for (int i = 0; i < Mn; i++)
				M[i] = new Phrase(in.nextLine());

			Pn = Integer.parseInt(in.nextLine());
			for (int i = 0; i < Pn; i++)
				P[i] = new Phrase(in.nextLine());

			System.out.printf("Comment thread #%d:%n", tc);

			// for each meme, try to match each phrase; as soon as a match is found print the meme and go to the next one
			boolean found = false;
			for (int i = 0; i < Mn; i++)
				for (int j = 0; j < Pn; j++)
					if (match(i, j))
					{
						found = true;
						System.out.println("   " + M[i]);
						break;
					}

			if (!found)
				System.out.println("   " + okay);

			System.out.println();

		}
	}

	// does M[m] match P[p]?
	boolean match(int m, int p)
	{
		// if the meme and phrase have different lengths, they can't match
		if (M[m].sortedWords.length != P[p].sortedWords.length)
			return false;

		// if the phrase contains more than one type of symbol, it can't match
		int symbolTypeCount = 0;
		for (char c : symbols.toCharArray())
			if (P[p].phrase.indexOf(c) > -1)
				symbolTypeCount++;
		if (symbolTypeCount > 1)
			return false;

		// if more than one word in the phrase contains a symbol, it can't match
		int symbolWordCount = 0;
		for (String w : P[p].sortedWords)
			for (char c : symbols.toCharArray())
				if (w.indexOf(c) > -1)
				{
					symbolWordCount++;
					break;
				}
		if (symbolWordCount > 1)
			return false;

		// notice that either 0,1,or 2 words can be changed for this to be a valid match
		// also notice that if we consider the words in sorted order, then at most 2 of the words
		// will be out of order in the meme, since at most 2 words can actually have characters
		// change. Thus, to make sure we hit every case, try every possible way to choose 2 words,
		// then try each possible location of those 2 words.
		String[] phrase = new String[P[p].sortedWords.length];
		for (int p1 = 0; p1 < P[p].sortedWords.length; p1++)
			for (int p2 = p1 + 1; p2 < P[p].sortedWords.length; p2++)
				for (int p1i = 0; p1i < P[p].sortedWords.length; p1i++)
					for (int p2i = 0; p2i < P[p].sortedWords.length; p2i++)
						if (p1i != p2i)
						{
							Arrays.fill(phrase, null);
							phrase[p1i] = P[p].sortedWords[p1];
							phrase[p2i] = P[p].sortedWords[p2];
							int pi = 0;
							for (int j = 0; j < P[p].sortedWords.length; j++)
							{
								// already used this word
								if (j == p1 || j == p2)
									continue;

								// get the next free spot
								while (phrase[pi] != null)
									pi++;

								// put this word in the next free spot
								phrase[pi++] = P[p].sortedWords[j];
							}
							if (match(M[m].sortedWords, phrase))
								return true;
						}

		// finally, just check the original order, to handle if there's only one word
		if (match(M[m].sortedWords, P[p].sortedWords))
			return true;

		// nothing matched, so no match
		return false;
	}

	// m can be converted to p?
	boolean match(String[] m, String[] p)
	{
		// get the indices which don't match
		ArrayList<Integer> notMatched = new ArrayList<Integer>();
		for (int i = 0; i < m.length; i++)
			if (!m[i].equals(p[i]))
				notMatched.add(i);

		// if more than 2 words don't match, this can't be a match
		if (notMatched.size() > 2)
			return false;

		// if 0 words don't match, we have a match
		if (notMatched.size() == 0)
			return true;

		// 1 word doesn't match
		// it can be rule 2, rule 3, or a combination
		if (notMatched.size() == 1)
		{
			int index = notMatched.get(0);
			return rule2(m[index], p[index]) || rule3(m[index], p[index]) || rule2and3(m[index], p[index]);
		}

		// 2 words match, one is rule 2 and other is rule 3
		int index0 = notMatched.get(0);
		int index1 = notMatched.get(1);
		return ((rule2(m[index0], p[index0]) && rule3(m[index1], p[index1])) 
				|| (rule2(m[index1], p[index1]) && rule3(m[index0], p[index0])));
	}

	// does m transform to p via rule 2? here rule 2 requires a character to be changed exactly once to a symbol
	boolean rule2(String m, String p)
	{
		// they have to be the same length for rule 2 to apply
		if (m.length() != p.length())
			return false;

		// any character can be switched, just consider them all
		for (int i = 0; i < m.length(); i++)
			if (rule2(m, p, i))
				return true;

		// nothing matched, so rule 2 doesn't apply
		return false;
	}

	// the character at location index has to be a symbol for this rule to apply
	boolean rule2(String m, String p, int index)
	{
		// check before and after index for match
		if (!m.substring(0, index).equals(p.substring(0, index)))
			return false;
		if (!m.substring(index + 1).equals(p.substring(index + 1)))
			return false;

		// make sure index is a symbol
		return symbols.indexOf(p.charAt(index)) > -1;
	}

	// does m transform to p via rule 3? here rule 3 requires a characters to be repeated at least once
	boolean rule3(String m, String p)
	{
		// since we insist a character is repeated at least once, p must be longer than m
		if (m.length() >= p.length())
			return false;

		// any character can be repeated, consider them all
		for (int i = 0; i < m.length(); i++)
			if (rule3(m, p, i))
				return true;

		// nothing matched, so rule 3 doesn't apply
		return false;
	}

	// the character at location index must be repeated at least once
	boolean rule3(String m, String p, int index)
	{
		int charFront = index;
		int charEnd = m.length() - index - 1;

		// check before and after index for match
		if (!m.substring(0, charFront).equals(p.substring(0, charFront)))
			return false;
		if (!m.substring(m.length() - charEnd).equals(p.substring(p.length() - charEnd)))
			return false;

		// everything in between better be the same character as m[index]
		return allMatch(p.substring(index, p.length() - charEnd), m.charAt(index));
	}

	// does m transform to p via rule 2 and 3? again, we require each rule to exist
	// rule 2 and rule 3 can be applied independently or together
	boolean rule2and3(String m, String p)
	{
		// since we insist a character is repeated at least once, p must be longer than m
		if (m.length() >= p.length())
			return false;

		// any character can be repeated, and any character can be changed to a symbol (note that it may be the same one!)
		for (int i = 0; i < m.length(); i++)
			for (int j = 0; j < m.length(); j++)
				if (rule2and3(m, p, i, j))
					return true;

		// nothing matched rule 2 and 3, so no match
		return false;
	}

	// the character at index2 must be replaced by a symbol and the character at index3 must be repeated
	// note that due to combination, index2 and index3 can be the same index
	boolean rule2and3(String m, String p, int index2, int index3)
	{
		// in this case, rule 2 is embedded within rule 3
		if (index2 == index3)
		{
			int charFront = index2;
			int charEnd = m.length() - index2 - 1;

			// check before and after index for match
			if (!m.substring(0, charFront).equals(p.substring(0, charFront)))
				return false;
			if (!m.substring(m.length() - charEnd).equals(p.substring(p.length() - charEnd)))
				return false;

			// to apply rule 2 and 3 on the same character one of 2 scenarios is possible:
			// 1) the character is repeated, and optionally exactly one of those characters is changed to a symbol
			// 2) the character is changed to a symbol and it is repeated (make sure all the symbols are the same)
			// Here I use regex's to check if either pattern is matched - Look at Java's Pattern class for how regex's are made
			String s = p.substring(index2, p.length() - charEnd);
			String regex1 = String.format("[%c]*[%s]?[%c]*", m.charAt(index2), symbols, m.charAt(index2));
			String regex2 = String.format("[%s]+", symbols);
			return s.matches(regex1) || (s.matches(regex2) && allMatch(s, s.charAt(0)));
		}

		// in this case the rules are applied disjointly
		else
		{
			// first get rule 2 out of the way
			if (index2 < index3)
			{
				// check before index2
				if (!m.substring(0, index2).equals(p.substring(0, index2)))
					return false;
				// check index2
				if (symbols.indexOf(p.charAt(index2)) == -1)
					return false;
				// check between index2 and index3
				if (!m.substring(index2 + 1, index3).equals(p.substring(index2 + 1, index3)))
					return false;
				// check after index3
				int charEnd = m.length() - index3 - 1;
				if (!m.substring(m.length() - charEnd).equals(p.substring(p.length() - charEnd)))
					return false;

				// reduce p down to just the repeated characters
				p = p.substring(index3, p.length() - charEnd);
			}
			else // index2 > index3
			{
				// check before index3
				if (!m.substring(0, index3).equals(p.substring(0, index3)))
					return false;
				// check after index2
				int charAfter2 = m.length() - index2 - 1;
				if (!m.substring(m.length() - charAfter2).equals(p.substring(p.length() - charAfter2)))
					return false;
				// check index2
				if (symbols.indexOf(p.charAt(p.length() - charAfter2 - 1)) == -1)
					return false;
				// check between index3 and index2
				int charAfter3 = m.length() - index3 - 1;
				int cnt3to2 = index2 - index3 - 1;
				if (!m.substring(m.length() - charAfter3, m.length() - charAfter3 + cnt3to2).equals(
						p.substring(p.length() - charAfter3, p.length() - charAfter3 + cnt3to2)))
					return false;

				// reduce p down to just the repeated characters
				p = p.substring(index3, p.length() - charAfter3);
			}

			// rule 2 is out of the way, just make sure rule 3 was applied
			return allMatch(p, m.charAt(index3));
		}
	}

	// is every character in p = c?
	boolean allMatch(String p, char c)
	{
		for (int i = 0; i < p.length(); i++)
			if (p.charAt(i) != c)
				return false;
		return true;
	}

}

// helper class to store the original unaltered phrase(or meme) and the chopped up sorted phrase(or meme)
class Phrase
{
	String phrase;
	String[] sortedWords;

	Phrase(String m)
	{
		phrase = m;

		// convert the sorted words to lower case to meet rule 1
		sortedWords = m.toLowerCase().split("[ ]");
		Arrays.sort(sortedWords);
	}

	public String toString()
	{
		return phrase;
	}
}
