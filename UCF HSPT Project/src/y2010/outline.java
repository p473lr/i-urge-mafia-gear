import java.util.*;
import java.io.*;


public class outline {

	//Instead of making my own tree structure, I decided to use a mapping of
	// topics to arrays of subtopics. Tree map documentation can be found on
	// the java website.
	TreeMap<String, ArrayList<String>> tree = new TreeMap<String, ArrayList<String>>();

	//This allows us to get the topmost nodes of the tree
	TreeSet<String> tops = new TreeSet<String>();
	TreeSet<String> subs = new TreeSet<String>();
	
	//A tab, for use for printing, exactly 4 spaces.
	String tab = "    ";
	
	public outline(Scanner in)
	{
		int n;
		
		//Case counter
		int cas = 1;
		
		while((n = in.nextInt()) != 0)
		{
			//Clear the tree, in case there is anything left over (there shouldn't be)
			tree.clear();
			tops.clear();
			subs.clear();
			
			for(int i = 1; i <= n; i++)
			{
				
				String joint;
				
				//This loop is probably not needed,
				// but if the input contains blank lines,
				// I can read them in.
				do 
				{
					joint = in.nextLine();
				}while(joint.trim().isEmpty());
				
				//Split the string into top and subtopic
				String[] split = joint.split("/");
				
				//If I've heard of the topic before, just add this
				// subtopic into the list
				if(tree.containsKey(split[1]))
				{
					tree.get(split[1]).add(split[0]);
					subs.add(split[0]);
					
				}
				else //if I haven't, create a new entry in the tree
				{
					ArrayList<String> newList = new ArrayList<String>();
					
					newList.add(split[0]);
			
					tree.put(split[1], newList);

					tops.add(split[1]);
					subs.add(split[0]);
				}
			}
			
			for(String s : subs)
				tops.remove(s);
									
			System.out.println("Outline #" + cas);
			
			//Go through the entries, and print them out!
			for(String t : tops)
			{						
				//Print out the top level
				System.out.println(t);
				
				//Print the subtopics
				recursivePrint(t,1);
			}
			
			//Blank line!
			System.out.println();
			cas++;
		}
	}
	
	void recursivePrint(String topic, int level)
	{
		//If this is a leaf node, return
		if(!tree.containsKey(topic))
			return;
		
		//Once again, get the list and sort it
		ArrayList<String> subtopics = tree.get(topic);
		Collections.sort(subtopics);
		
		//For each of the subtopics
		for(String subtopic : subtopics)
		{
			//Print a certain number of tabs
			for(int i = 0; i < level; i++)
				System.out.print(tab);
				
				//Followed by the topic name
			System.out.println(subtopic);
			
			//And keep printing, only using the new supertopic
			recursivePrint(subtopic, level+1);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new outline(new Scanner(new File("outline.in")));
	}
	
}
