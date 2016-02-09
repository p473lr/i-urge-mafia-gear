import java.util.Scanner;
import java.io.*;
import java.util.*;

public class cloud
{
	public static void main(String[] args) throws IOException
	{
		// A file reader to read in the input file
		Scanner input = new Scanner(new File("cloud.in"));

		// Reads how many different cases are in the input file
		int numberOfCases = input.nextInt();

		// Runs through each input case in the file
		for(int i = 0; i < numberOfCases; i++)
		{
			// This stores and counts the different words
			HashMap<String, Integer> listOfWords = new HashMap<String, Integer>();
			// I use this to easily access the number of times each word appeared in the file. I only do this because
			// I don't like the Iterator class and prefer to use the get method of HashMap
			ArrayList<String> words = new ArrayList<String>();

			// Read how many words are in this input case
			int numberOfWords = input.nextInt();

			// Run through all the words for this input case
			for(int j = 0; j < numberOfWords; j++)
			{
				// Get the next word from the input
				String nextWord = input.next();

				// Determine if we have already seen this word
				if(listOfWords.containsKey(nextWord))
				{
					// Since we have seen the word increment the number of times it was seen
					int numberOfOccurences = listOfWords.remove(nextWord);

					listOfWords.put(nextWord, numberOfOccurences+1);
				}
				else
				{
					// Since the word was not in our list create a new location for it
					listOfWords.put(nextWord, 1);

					words.add(nextWord);
				}
			}

			// Create an array that we can sort
			WordElement[] sortedList = new WordElement[words.size()];

			// Run through each distinct word and add it to the array to sort
			for(int j = 0; j < words.size(); j++)
			{
				// Create a instance of the class for sorting
				WordElement nextElement = new WordElement(words.get(j), listOfWords.get(words.get(j)));

				sortedList[j] = nextElement;
			}

			// Sort the array
			Arrays.sort(sortedList);

			// Print the header line for each input case
			System.out.println("Article #" + (i+1) + ":");

			// Print the words in sorted order
			for(int j = 0; j < sortedList.length; j++)
			{
				System.out.println(sortedList[j].word + " " + sortedList[j].number_of_occurences);
			}

			// Add the blank line between cases
			System.out.println();
		}
	}

	// This class is used to allow Arrays.sort to work based on the number of times the word appeared in the file
	public static class WordElement implements Comparable<WordElement>
	{
		// Stores the word
		public String word;
		// Stores the number of times the word appeared in the file
		public int number_of_occurences;

		// Constructor
		public WordElement(String givenWord, int numberOfOccurences)
		{
			word = givenWord;
			number_of_occurences = numberOfOccurences;
		}

		// Implements the Comparable interface which allows Arrays.sort to work
		public int compareTo(WordElement otherWordElement)
		{
			return otherWordElement.number_of_occurences - number_of_occurences;
		}
	}
}
