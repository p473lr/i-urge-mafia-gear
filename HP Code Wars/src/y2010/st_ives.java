/*
 * St. Ives solution
 * Author: Don Brace
 *
 * Simple solution to the riddle.
 * Basically looking for "HAD" and parsing words before and after.
 *
 */

import java.util.*;

/*
 * Simple class to aid in the lookup of a string representing a number
 */
class numbers {
	numbers(String s, int v) {
		name = s;
		value = v;
	}
	String name;
	int	value;
}; /* numbers */

/*
 * Simple class to track how many an object had 
 */
class number_of_stuff {
	number_of_stuff(String n, String hn, int num) {
		name = n;
		had_name = hn;
		number = num;
	}
	String	name;
	String  had_name;
	int	number;
}; /* number_of_stuff */

public class st_ives {
	public static void main(String[] args) {
		Vector<numbers> numbers = new Vector<numbers>();
		Vector<number_of_stuff> nos = new Vector<number_of_stuff>();
		Scanner scan = new Scanner(System.in);
		String word;
		String last_word = null;
		String had_word = null;
		int value;

		/*
 		 * Initialize the number Vector
 		 */
		BuildNumbers(numbers);

		/*
 		 * Read in riddle
 		 */
		while (true) {
			word = scan.next();

			/*
 			 * Is this the question?
 			 */
			if (word.indexOf("?") > 0) {
				String how_many = 
					word.substring(0, word.length()-1);
				System.out.println(GetValue(nos, how_many) +
						" " + how_many);
				break;
			}

			if (word.equals("HAD")) 
				had_word = last_word;

			value = GetIntValue(numbers, word);

			if (value > 0) {
				word = scan.next();
				nos.add(new number_of_stuff(had_word, word, 
								value));
			}

			last_word = word;
		} /* while */

	} /* main */

	/*
 	 * Need to backtrack
 	 */
	static int GetValue(Vector<number_of_stuff> v, String s) {
		int i;
		int j;
		int result = 0;
		number_of_stuff nos = null;
		String word;

		/*
 		 * Look for the string with ?
 		 */
		for (i = 0; i < v.size(); i++) {
			nos = (number_of_stuff) v.get(i);
			if (nos.had_name.equals(s))
				break;
		}

		if (i > v.size())
			return -1;

		/*
 		 * Now search Vetor backwards
 		 */

		result = nos.number;
		word = nos.had_name;

		/*
 		 * No need to look at the strings.
 		 */
		for (j = i-1; j >= 0; j--) {
			nos = (number_of_stuff) v.get(j);
			result *= nos.number;
		}

		return result;
	}

	/*
 	 * Lookup the number stored as a string and return its numeric
 	 * value.
 	 */
	static int GetIntValue(Vector<numbers> v, String s) {
		int i;

		for (i = 0; i < v.size(); i++) {
			numbers n = (numbers) v.get(i);
			if (n.name.equals(s))
				return n.value;
		}
		return 0;
	} /* GetIntValue */

	static void BuildNumbers(Vector<numbers> v) {
		v.add(new numbers("TWO",2));
		v.add(new numbers("THREE",3));
		v.add(new numbers("FOUR",4));
		v.add(new numbers("FIVE",5));
		v.add(new numbers("SIX",6));
		v.add(new numbers("SEVEN",7));
		v.add(new numbers("EIGHT",8));
		v.add(new numbers("NINE",9));
		v.add(new numbers("TEN",10));
		v.add(new numbers("ELEVEN",11));
		v.add(new numbers("TWELVE",12));
		v.add(new numbers("THIRTEEN",13));
	} 
}; /* st_ives */
