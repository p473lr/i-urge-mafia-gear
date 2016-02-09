/*
 * List Maker solution.
 * Author: Don Brace
 *
 * Key to solving this problem: Using Java Vector class.
 *      Use:
 *           Vector.add(String) - Append to end;
 *           Vector.add(index, String) - Insert at index
 *           Vector.remove(index) - Remove at index
 */

import java.util.*;

public class list_maker {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int i;
		String cmd;
		String word1;
		String word2;
		Vector<String> sentence = new Vector<String>();

		/*
		 * Read in commands and modifiers.
		 */

		while (true) {

			cmd = scan.next();

			//System.out.println("Cmd= " + cmd);

			if (cmd.equals("SHOW"))
				break;

			if (cmd.equals("ADD")) {
				word1 = scan.next();
				sentence.add(word1);
				continue;
			}

			if (cmd.equals("INSERT")) {
				word1 = scan.next();
				word2 = scan.next();
				InsertWord(sentence, word1, word2);
				continue;
			}

			if (cmd.equals("REMOVE")) {
				word1 = scan.next();
				RemoveWord(sentence, word1);
				continue;
			}
		} /* while */

		for (i = 0; i < sentence.size(); i++) {
			String w = sentence.get(i);
			System.out.print(w + ' ');
		}
		System.out.println();
	} /* main */

	static void InsertWord(Vector<String> v, String w1, String w2) {
		int i;
		
		//System.out.println("Insert:" + w1 + ' ' + w2);

		if (v.size() == 0) {
			v.add(w2);
		}
			
		for (i = 0; i < v.size(); i++) {

			String w = v.get(i);

			if (w.equals(w2)) {
				v.add(i, w1);
				break;
			}
		} /* for */
			
	} /* InsertWord */

	static void RemoveWord(Vector v, String w) {
		int i;

		//System.out.println("Remove:" + w);

		if (v.size() == 0)
			return;

		for (i = 0; i < v.size(); i++) {
			String word = (String) v.get(i);

			if (word.equals(w)) {
				v.remove(i);
				break;
			}
		} /* for */
	} /* RemoveWord */

}; /* list_maker */
