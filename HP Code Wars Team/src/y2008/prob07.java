package y2008;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class prob07 {
	
	private static final Map rootLookup = new HashMap() {{
		put("a", new Integer(0));
		put("e", new Integer(1));
		put("o", new Integer(2));
	}};
	
	private static final Map suffixLookup = new HashMap() {{
		put("a",  new Integer(0));
		put("e",  new Integer(1));
		put("i",  new Integer(2));
		put("y",  new Integer(3));
		put("o",  new Integer(4));
		put("ou", new Integer(5));
		put("w",  new Integer(6));
	}};
	
	private static final String[][] table = new String[][] {
				/* a */  /* e */ /* i */ /* y */ /* o */ /* ou */ /* w */
		{/* a */  "a",     "a",    "ai",   "a",    "w",    "w",     "w"},
		{/* e */  "y",     "ei",   "ei",   "y",    "ou",   "ou",    "w"},
		{/* o */  "w",     "ou",   "oi",   "w",    "ou",   "ou",    "w"}
	};

	String root;
	String suffix;
	
	public ItsGreekToMe(String input) throws Exception {
		String[] split = input.split("-");
		if (split.length < 2) {
			throw new Exception("The input line didn't split on dash correctly");
		}
		this.root = split[0];
		this.suffix = split[1];
	}
	
	public String toString() {		
		String keepRoot = root.substring(0, root.length() - 1);
		String keepSuffix = suffix.substring(1);
		
		String lastRoot = root.substring(root.length() - 1);
		String firstSuffix = suffix.substring(0, 1);
		if (suffix.startsWith("ou"))
			firstSuffix = "ou";
		
		String contraction = table[((Integer)rootLookup.get(lastRoot)).intValue()]
		                          [((Integer)suffixLookup.get(firstSuffix)).intValue()];
		
		return keepRoot + contraction + keepSuffix;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		
		String line;
		while ((line = in.readLine()) != null) {
			if (line.equals("END")) break;
			System.out.println(new ItsGreekToMe(line));
		}
	}

}
