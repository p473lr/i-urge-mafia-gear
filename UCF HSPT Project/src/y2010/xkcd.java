// Stephen Fulwider
//	XKCD - 2010 High School Programming Tournament

// This solution exhaustively tries all possible strings matching the given criteria using
//	brute force with pruning - that is, trying all possibilities but breaking out early for
//	strings which are realized to be impossible before getting to the end

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class xkcd
{

	public static void main(String[] args) throws Exception
	{
		// I like to instantiate my classes this way so that I don't have to use static variables or methods
		 new xkcd();
	}
	
	// the length of names to generate
	int N;
	
	// the name being generated
	char[] A = new char[42];
	
	// open file for output
	PrintWriter out = new PrintWriter(System.out);
	
	xkcd() throws Exception
	{
		// open file for reading
		Scanner in = new Scanner(new File("xkcd.in"));
				
		// continue reading in N until 42 is read
		while ((N=in.nextInt()) != 42)
		{
			// print the header
			out.printf("XKCD-like name(s) of length: %d%n",N);
			
			// solve the problem
			boolean has = go(0,'z',0);
			
			// if no names found, output special text
			if (!has)
				out.println("Mostly Harmless");
		}
		
		// print the final string, as requested
		out.println("The answer to life, the universe and everything!");
		
		// close output file
		out.close();
	}

	// solve the problem starting @ position at, considering only characters <= c, with current ordinal sum
	//	returns true iff some xkcd-like name is possible
	boolean go(int at, char c, int sum)
	{
		// if only 2 characters left, try all possible increasing valid pairs of letters
		if (at+2==N)
		{
			boolean has=false;
			for (char x='a'; x<c; ++x) if (!isVowel(x))
				for (char y=(char)(x+1); y<=c; ++y) if (!isVowel(y))
					if (sum+ordinal(x)+ordinal(y)==42)
					{
						// A[0..N-3]xy is a valid xkcd name, print it
						for (int k=0; k<N-2; ++k)
							out.print(A[k]);
						out.println(x+""+y);
						has=true;
					}
			return has;
		}
		
		// no characters left to try, or the sum is too large, so no names possible here
		//	37 is because the smallest valid pair of letters is 'bc', with value 5, and 42-5=37
		if (c<'a' || sum>37)
			return false;
				
		// otherwise we have 2 choices, use this character or don't. Try the 2nd choice first
		//	to satisfy printing the strings in lexicographically smallest order, and only try
		//	the letter if it is not a vowel
		boolean has = go(at,(char)(c-1),sum);
		if (!isVowel(c))
		{
			A[at]=c;
			has |= go(at+1,(char)(c-1),sum+ordinal(c));
		}
		
		// return whether either choice found a valid name
		return has;
	}
	
	// get the ordinal value of lower-case character c
	int ordinal(char c)
	{
		return c-'a'+1;
	}
	
	// determine if lower-case character c is a vowel, by the definition of the problem
	String V="aeiouy";
	boolean isVowel(char c)
	{
		return V.indexOf(c)>=0;
	}
	
}
