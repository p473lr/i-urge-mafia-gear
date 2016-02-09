import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class avengers {
	public static void main(String[] args) throws IOException
	{
		Scanner br=new Scanner(new File("avengers.in"));
		int villains=br.nextInt();
		//When you use Scanner, after you read in a token (instead of a line), it leaves
		//its reader right after the last token. Then when you read an entire line, it
		//only goes to the new line character, which is usually where your reader is.
		//So we just skip that by calling nextLine() before we actually want to read.
		br.nextLine();
		for(int i=0;i<villains;i++)
		{
			String villain=br.nextLine();
			//If we have started a new third
			if(i%3==0)
				System.out.println("Captain America: I'll take care of "+villain+"!\n");
			//If we are in the middle of a third
			else if(i%3==1)
				System.out.println("Thor: I shall deal with "+villain+"!\n");
			//If we are at the end of a third
			else
				System.out.println("HULK SMASH!\n");
		}
	}
}
