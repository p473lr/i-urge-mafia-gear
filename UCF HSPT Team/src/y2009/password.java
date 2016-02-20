// Author: Stephen Fulwider

import java.io.File;
import java.util.Scanner;

public class password
{

	public static void main(String[] args) throws Exception
	{
		new password();
	}

	final String okay = "Probably okay.";
	final String check = "Check password!";

	password() throws Exception
	{
		Scanner in = new Scanner(new File("password.in"));
		for (int N = in.nextInt(), tc = 1; N > 0; N--, tc++)
		{
			// read in the password
			String password = in.next();

			// get the last character
			char last = password.charAt(password.length() - 1);

			System.out.printf("Password #%d: ", tc);

			// check if the last character is a digit
			if (Character.isDigit(last))
				System.out.println(check);
			else
				System.out.println(okay);
		}
	}

}
