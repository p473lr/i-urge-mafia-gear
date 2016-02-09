import java.io.*;
import java.util.*;

public class square
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader in = new BufferedReader(new FileReader("square.in"));
		
		// Read in the number of games in the input file.
		int numgames = Integer.parseInt(in.readLine());
		
		// Simulate each individual game by looping through each one.
		for(int gamenum = 1; gamenum <= numgames; gamenum++)
		{
			// Read in the number of players.
			int numplayers = Integer.parseInt(in.readLine());
			
			// Create the array of Player objects for us to use.
			Player players[] = new Player[numplayers];
			
			// Record the index of the Kefka player so we can find it easily.
			int kefkaIndex = -1;
			
			// Read in each player.
			for(int i = 0; i < numplayers; i++)
			{
				// Create this player
				players[i] = new Player();
				
				// Read in the line from the input file so we can get this
				// player's data.
				String input = in.readLine();
				
				// Create the StringTokenizer so we can separate the input
				// values.
				StringTokenizer st = new StringTokenizer(input);
				
				// Read in the player's values.
				players[i].name = st.nextToken();
				players[i].hp = Integer.parseInt(st.nextToken());
				players[i].dmg = Integer.parseInt(st.nextToken());
				
				// See if this player is Kefka
				if(players[i].name.equals("Kefka"))
				{
					kefkaIndex = i;
				}
			}
			
			// Print out the simulation header.
			System.out.println("Game #"+gamenum+":");
			
			// Simulate the game with the players we've been given.
			int curPlayer = 0;
			while(true)
			{
				int targetIndex = -1;
				
				// If this player is Kefka, then we'll need to find the first
				// friend of Celes to smack.
				if(players[curPlayer].name.equals("Kefka"))
				{
					targetIndex = findKefkasTarget(players);
				}
				// If this is not Kefka, then just target Kefka.
				else
				{
					targetIndex = kefkaIndex;
				}
				
				// Strike the target.
				players[targetIndex].hp -= players[curPlayer].dmg;
				
				System.out.println(players[curPlayer].name+" struck "
					+players[targetIndex].name+" for "+players[curPlayer].dmg+
					" points of hurt.");
				
				// See if the target died.
				if(players[targetIndex].hp <= 0)
				{
					System.out.println(players[targetIndex].name+" has been "+
					"removed from the game.");
				}
				
				// If our target was Kefka, then the world might have been 
				// saved, so check for that.
				if(targetIndex == kefkaIndex)
				{
					if(players[kefkaIndex].hp <= 0)
					{
						// Print out that the world was saved and break the 
						// loop, so we go onto the next simulation.
						System.out.println("The world has been saved!\n");
						break;
					}
				}
				// Otherwise, this was Kefka striking someone else, so the world
				// might've been cast into ruin for all time. So, check for that.
				else if(isWorldCastIntoRuin(players))
				{
					// The good guys lost, so print that out and move onto the
					// next simulation.
					System.out.println("The world will be cast into perpetual "+
						"ruin.\n");
					break;
				}
				
				// Now that we're done simulating this step, move onto the next
				// player (and handle wraparound)
				curPlayer++;
				if(curPlayer >= numplayers)
				{
					curPlayer = 0;
				}
				
				// While the player at the current index has less than or equal
				// to zero Hurt Points, then we need to move on to the next one
				// (because He's Dead, Jim)
				while(players[curPlayer].hp <= 0)
				{
					// Move onto the next player and handle wraparound.
					curPlayer++;
					if(curPlayer >= numplayers)
					{
						curPlayer = 0;
					}
				}
			}
		}
	}
	
	// This function finds Kefka's target by finding the first Player object
	// in the array that is still alive (hp > 0) and is not Kefka himself.
	public static int findKefkasTarget(Player players[])
	{
		// Loop through all the players.
		for(int i = 0; i < players.length; i++)
		{
			// If this player is still alive and is not Kefka, return it.
			if(players[i].hp > 0 && !players[i].name.equals("Kefka"))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	// This function check to see if the world has been cast into ruin or not. 
	// It works by checking to see if any players not named Kefka are still
	// alive.
	public static boolean isWorldCastIntoRuin(Player players[])
	{
		// This is a very pessimistic function, it starts by assuming the world
		// is doomed and then tries to prove itself wrong. All it takes is one
		// Player left alive to keep the fight going, so as soon as we find that
		// Player we quit.
		boolean isWorldDoomed = true;
		
		// Loop through all players
		for(int i = 0; i < players.length && isWorldDoomed; i++)
		{
			if(players[i].hp > 0 && !players[i].name.equals("Kefka"))
			{
				// The world isn't doomed, horray!
				isWorldDoomed = false;
			}
		}
		
		return isWorldDoomed;
	}
}

// A rather simple storage class that represents a Player in the simulation.
class Player
{
	String name;
	int hp;
	int dmg;
}
					
				