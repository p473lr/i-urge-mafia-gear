package y2008;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedList;

public class prob13 {
	private int hits = 0;
	private int misses = 0;
	private LinkedList cache = new LinkedList();
	
	public void getPage(int page) {
		// It's in the cache
		Iterator iter = cache.iterator();
		Integer searchedPage = new Integer(page);
		while (iter.hasNext()) {
			Integer cachedPage = (Integer)iter.next();
			// Move to the front of the list.
			if (cachedPage.equals(searchedPage)) {
				hits++;
				iter.remove();
				cache.addFirst(searchedPage);
				return;
			}
		}
		
		
		// It's not in the cache
		misses++;
		
		cache.addFirst(new Integer(page));
		if (cache.size() > 16) {
			cache.removeLast();
		}
	}
	
	public String toString() {
		NumberFormat f = NumberFormat.getInstance();
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		f.setMinimumIntegerDigits(1);
		
		double percent = (double)hits / ((double)misses + (double)hits) * (double)100;
		return "cache hit rate: " + f.format(percent) + "%";
	}

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		
		//LeastRecentlyUsed lru = new LeastRecentlyUsed();
		String line;
		while((line = in.readLine()) != null) {
			
			String[] pages = line.split(" ");
			for (int i = 0; i < pages.length; i++) {
				//lru.getPage(Integer.parseInt(pages[i]));
			}
			
			//System.out.println(lru);
		}
	}

}
