import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class world {
	public static void main(String[] args) throws IOException
	{
		Scanner br=new Scanner(new File("world.in"));
		//Setting this format allows you to insert any string for the %s,
		//which helps us because we only care about what string we are reading in.
		String format="The end of the world is on %s.\n";
		int cases=br.nextInt();
		br.nextLine();	//Scanner reads from the end of the previous int to the
						//end of that line, and we don't need to print out an empty line to start with
		for(int i=0;i<cases;i++)
		{
			//We use printf instead of print or println so we can add
			//arguments after what we print. This way, it replaces each
			//%s (or %d, %f, etc.) with the next argument.
			System.out.printf(format,br.nextLine());
		}
	}
}
