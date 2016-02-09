package y2008;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class prob08 {
	
	private Map dictionary;
	private ArrayList textToTranslate;

	public LeetTranslator(InputStream stream) throws IOException, Exception {
		StringBuffer buf = new StringBuffer();
		
		// Suck the whole file into memory
		int b;
		while ((b = stream.read()) >= 0 ) {
			buf.append((char) b);
		}
		
		
		// Build the dictionary
		dictionary = new HashMap();
		String[] lines = buf.toString().split("\n");
		if (! lines[0].equals("[L3X1K0N]"))
			throw new Exception("Missing [L3X1K0N] marker at top of file");
		
		int i = 1;
		for (; ! lines[i].equals("[1337]") ; i++) {
			addDictionaryLine(lines[i]);
		}
		
		
		// Pull in the text to translate, but don't translate yet.
		textToTranslate = new ArrayList();
		// Increment i by one to skip past the [1337] line.
		for (i += 1; ! lines[i].equals("[3ND]"); i++ ) {
			String line = lines[i];
			String[] words = line.split(" ");
			for (int j = 0; j < words.length; j++) {
				textToTranslate.add(words[j]);
			}
			textToTranslate.add("\n");
		}
	}
	
	
	private void addDictionaryLine(String string) {
		String[] splitted = string.split(":");
		String key = splitted[0];
		String value = splitted[1];
		
		dictionary.put(key, value);
	}


	// Do the actual translation
	public String translate() throws Exception {
		StringBuffer translated = new StringBuffer();
		
		// Go over the words list and translate each word
		for (int i = 0; i < textToTranslate.size(); i++) {
			String l33t = (String) textToTranslate.get(i);
			String human = (String) dictionary.get(l33t);
			
			// If we didn't find it in the dictionary, use the raw untranslated l33t word.
			if (human == null) {
				human = l33t;
			}
			
			translated.append(human).append(" ");
		}
		
		return translated.toString();
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 0)
		{
			throw new Exception("Enter a file name as the only argument");
		}
		
		FileInputStream stream = new FileInputStream(args[0]);
		
		LeetTranslator leet = new LeetTranslator(stream);
		
		String translated = leet.translate();
		
		System.out.println(translated);
	}

}
