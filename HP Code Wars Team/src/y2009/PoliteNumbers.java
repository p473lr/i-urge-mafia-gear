//********************************************************************
//  PoliteNumbers		Author: Don Brace
//********************************************************************
import java.io.*;
import java.util.*;

public class PoliteNumbers
{
	public static void main (String[] args) {

		try {
			StringTokenizer		tok = new StringTokenizer(" ");
			BufferedReader		br = new BufferedReader(new InputStreamReader(System.in));
			String			strInput;
			int			number;
	
			while (true) {
				strInput = br.readLine();
				if (strInput == null) break;
				tok = new StringTokenizer(strInput, " ");
				number = Integer.parseInt(tok.nextToken());

				// Poweer of 2 numbers are impolite.
				if ( (number % 2) == 0)
					System.out.println(number + " is impolite");
				else
					System.out.println(number + " is polite");
			}
		}
		catch (IOException e) {
		}
	} // main
}
