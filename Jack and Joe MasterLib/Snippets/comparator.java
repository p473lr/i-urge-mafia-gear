package Snippets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class comparator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		/*
		 * Sorts the ArrayList arr in descending order
		 */
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Collections.sort(arr); 
		
		/* 
		 * Sorts the ArrayList arr in ascending order
		 */
		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		Comparator myComparator= (Comparator) Collections.reverseOrder();
		Collections.sort(arr2);
	}

}
