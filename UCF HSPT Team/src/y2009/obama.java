// Author: Stephen Fulwider

import java.io.File;
import java.util.Scanner;

public class obama
{

	public static void main(String[] args) throws Exception
	{
		new obama();
	}

	obama() throws Exception
	{
		Scanner in = new Scanner(new File("obama.in"));
		for (int N = in.nextInt(); N > 0; N--)
		{
			// get the name of the judge
			String name = in.next();

			// count the O's
			int count = 0;
			for (char c : name.toCharArray())
				if (c == 'O')
					count++;

			// print the result
			System.out.printf("Potential Judge %s's last name has %d O's in it.%n", name, count);
		}
	}

}
