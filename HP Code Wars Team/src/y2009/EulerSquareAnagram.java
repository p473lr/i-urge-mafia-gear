//********************************************************************
//  EulerSquareAnagram		Author: Don Brace
//
// Solved by reading in words, sorting the characters within each word
// then comparing to the other words. 
//
// Anagrams will have the same number of characters sorted in the 
// same order.
//
// Brute force approach, but effective
//********************************************************************
import java.io.*;
import java.util.*;

public class EulerSquareAnagram
{
	public static void main (String[] args) {

		try {
			InputStreamReader	isr = new InputStreamReader(System.in);
			BufferedReader		br = new BufferedReader(isr);
			String			data;
			String			word;
			HashMap			anagrams = new HashMap();
			int			count = 0;
			Vector			vAnagrams = new Vector();
			Vector			vPrinted = new Vector();
	
			data = br.readLine();

			StringTokenizer		tok = new StringTokenizer(data, ",\"");

			while(tok.hasMoreTokens()) {
				word = tok.nextToken();

				//System.out.println("Input:" + word);
				vAnagrams.add(findAnagrams(data, word, anagrams));
				++count;
			}

			//System.out.println("There are " + vAnagrams.size());
			for (count = 0; count < vAnagrams.size(); count++) {
				Anagrams a = (Anagrams)vAnagrams.elementAt(count);
				if (a.count <= 0) continue;
				if (FindPrintedWord(a.word, vPrinted) == 1) continue;
				vPrinted.add(a.word);
				System.out.println(a.GetList(vPrinted));
			}

		} // try
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		} // catch
	} // main

	public static int FindPrintedWord(String s, Vector v)
	{
		for (int count = 0; count < v.size(); count++) {
			String t = (String) v.elementAt(count);
			if (t.compareTo(s) == 0) return 1;
		}
		return 0;
	}
	public static Anagrams findAnagrams(String data, String word, HashMap anagrams) {
		String			ana_word;
		StringTokenizer		tok = new StringTokenizer(data, ",\"");
		char[] 			sorted_word_content = word.toCharArray();
		Anagrams		a = new Anagrams(word);
	
		Arrays.sort(sorted_word_content);
		String sorted_word = new String(sorted_word_content);

		while(tok.hasMoreTokens()) {
			ana_word = tok.nextToken();

			if (ana_word.compareTo(word) == 0) continue;

			char[] ana_content = ana_word.toCharArray();
			Arrays.sort(ana_content);
			String ana_sorted = new String(ana_content);

			if (ana_sorted.compareTo(sorted_word) == 0) {
				anagrams.put(word, ana_word);
				//System.out.println("Anagram " + word + ' ' 
					//+ ana_word);
				a.Add(ana_word);
			}
		}

		return a;
	} // findAnagrams
}

class Anagrams {
	Anagrams(String s) {
		vlist = new Vector();
		word = s;
		count = 0;
	}
	void Add(String s) {
		vlist.add(s);
		++count;
	}
	String GetList(Vector v) {
		int count = 0;
		String s = word + " ";
		String t;
		while(count < vlist.size()) {
			t = (String)vlist.elementAt(count);
			s += t + " ";
			v.add(t);
			count ++;
		} // while
		return s;
	}
	int FindWord(String s) {
		int count = 0;
		String t = "";
		for (count = 0; count < vlist.size(); count++) {
			t = (String)vlist.elementAt(count);
			if (t.compareTo(s) == 0) return 1;
		}
		return 0;
	} // FindWord
	String	word;
	Vector vlist;
	int	count;
};
