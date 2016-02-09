// UCF High School Programming Tournament 2006
import java.io.*;

/** Solution to "Phish Finder".
 * The basic strategy is to convert each message into a single-line version
 * by substituting spaces for newlines, and then find and examine each link
 * in the message for "phishiness."
 */
public class phishy
{
    /** Finds out whether link text can be interpreted as a domain.
     * Verifies that all characters are either lower-case letters or dots, and
     * makes sure all dots are separated by at least one letter.
     */
    static boolean isDomain(String text)
    {
	boolean hasDot = false;
	for (int i = 0; i < text.length(); i++) {
	    char c = text.charAt(i);
	    if (Character.isLowerCase(c))
		continue; // lower-case letters are always ok in domains
	    if (c == '.')
	    {   // dots are okay UNLESS they're next to one another, so check:
		if ((i > 0 && text.charAt(i-1) == '.') || i != text.length() - 1)
		    // adjacent dots -- not a domain
		    return false;
		hasDot = true; // note that text contains a dot
	    }
	    else
	    {
		// 'c' is neither a lower case letter nor a dot, so
		// text can not be a domain
		return false;
	    }
	}
	// Every character is okay, so the text can be a domain if it has
	// at least one dot
	return hasDot;
    }
    /** Extracts the domain name from a link URL.
     * Strips the HTTP protocol prefix, and then returns everything before the
     * first slash.
     */
    static String getDomain(String link)
    {
	link = link.substring(7); // skip "http://"
	int slash = link.indexOf('/'); // check for a "page name" part
	if (slash == -1)
	    // no page name to strip
	    return link;
	else
	    // strip page name
	    return link.substring(0, slash);
    }
    /** Determines whether two domain names "match".
     * Shortens the longer string to the length of the shorter one.  If they
     * are equal at that point, returns true.  If not, then attempts to
     * further shorten to the "second-dot" point and compares again.
     * Example:
     * "www.go.ogle.com" and "www.google.com" -- Nope, shorten
     *  "ww.go.ogle.com" and "www.google.com" -- Nope, shorten again
     *       ".ogle.com" and      "oogle.com" -- Nope, return false
     */
    static boolean matchDomains(String dom1, String dom2)
    {
	if (dom1.length() > dom2.length()) // make dom1 the shorter one
	    return matchDomains(dom2, dom1);
	int lengthDiff = dom2.length() - dom1.length();
	dom2 = dom2.substring(lengthDiff); // shorten dom2 to length of dom1

	if (dom1.equals(dom2)) return true;
	
	// No match on whole length, try second-dot shortening
	int secondDot = dom1.lastIndexOf('.', dom1.lastIndexOf('.')-1);
	if (secondDot != -1)
	{   // Found a second dot in dom1.  Keep it in the substring so that it
	    // will fail if the dot is missing in dom2, as illustrated above!
	    dom1 = dom1.substring(secondDot);
	    dom2 = dom2.substring(secondDot);
	    return dom1.equals(dom2);
	}
	else
	{   // No second dot in dom1, and no match on whole length -- fail
	    return false;
	}
    }

    /** Main solution program.
     */
    public static void main(String[] args) throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader("phishy.in"));
	int numMessages = Integer.parseInt(in.readLine().trim());

	for (int messageNum = 0; messageNum < numMessages; messageNum++)
	{
	    // Take the second token from each of the first two lines as the
	    // important part.
	    String from = in.readLine().split(" ")[1];
	    int numLines = Integer.parseInt(in.readLine().split(" ")[1]);

	    // Read in the message, substituting spaces for newlines.
	    String message = "";
	    for (int i = 0; i < numLines; i++)
		message += in.readLine() + " ";
	    
	    // Find links, until all are found or we see a phishy one
	    boolean isPhishy = false;
	    int linkStart = message.indexOf('<', 0); // Find where the first link is
	    
	    while (linkStart != -1) // As long as we have more links...
	    {
		// Find out where the address is -- it's in quotes, after a <
		int addrStart = message.indexOf('"', linkStart)+1;
		int addrEnd   = message.indexOf('"', addrStart);
		// Find the link text, it's after the address and looks > like this <
		int textStart = message.indexOf('>', addrEnd)+1;
		int textEnd   = message.indexOf('<', textStart);

		// Extract the address and text from the link
		// The trim() on the link text is *IMPORTANT* because we check
		// whether it can be a domain, and domains can't contain spaces.
		String address = message.substring(addrStart, addrEnd);
		String text = message.substring(textStart, textEnd).trim();

		if (isDomain(text)) { // If the link text is a domain...
		    // ... it must "match" the domain in the link address...
		    if (!matchDomains(getDomain(address), text))
		    {
			// ... or we call the e-mail phishy.
			isPhishy = true;
			break; // message is already phishy, we can stop now!
		    }
		}

		// This link looks okay, move on by finding the next < after
		// the one that ended the link text this time.
		linkStart = message.indexOf('<', textEnd+1);
	    }
	    
	    // Now that we've categorized the message, print the result
	    if (isPhishy)
		System.out.printf("Mail from %s looks phishy!%n", from);
	    else
		System.out.printf("Mail from %s looks ok.%n", from);
	}
    }
}

