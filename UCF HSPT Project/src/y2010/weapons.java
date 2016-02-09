import java.io.*;
import java.util.*;

public class weapons
{
	static double EP = 1e-7;
	public static void main (String [] args) throws IOException
	{
		Scanner scan = new Scanner(new File("weapons.in"));
		
		//Store the base armor 
		int ba = scan.nextInt();
		
		//Read the 
		while (ba > 0)
		{
			//Get the weapon info
			Weapon w1 = new Weapon(scan.next(), scan.nextInt(), scan.next());
			Weapon w2 = new Weapon(scan.next(), scan.nextInt(), scan.next());
			
			//Calculate the damages
			double damage1 = GetAverageDamage(w1, ba);
			double damage2 = GetAverageDamage(w2, ba);
			
			//Print out the answer
			if (Math.abs(damage1-damage2) < EP)
				System.out.println("They're both the same.");
			else if (damage1 > damage2)
				System.out.printf("%s is better than %s.%n", w1.name, w2.name);
			else
				System.out.printf("%s is better than %s.%n", w2.name, w1.name);
			
			ba = scan.nextInt();
		}
	}
	
	public static double GetAverageDamage(Weapon w, int ba)
	{
		//Calculate the probability to hit, not counting critical hits
		double probHit = Math.max(0, Math.min(18, 20-ba+w.attackBonus))/20.0;
		
		//On average, we'll roll the median damage value of the dice (so 1d6 has an average damage of 3.5)
		double averageDamage = (w.damageDice+1) / 2.0 * w.numDice + w.damageBonus;
		
		//Get the damage contribution from critical hits. It has a 5% chance of happening,
		//so we multiply the maximium weapon damage by 5%.
		double critDamage = (w.damageDice * w.numDice + w.damageBonus) * 0.05;
		
		return probHit * averageDamage + critDamage;
	}
}

class Weapon
{
	String name;
	int attackBonus;
	double damageDice;
	double numDice;
	double damageBonus;
	
	public Weapon(String n, int ab, String dmg)
	{
		name = n;
		attackBonus = ab;
		
		//Parse the damage string to get the appropriate parts
		String [] parts = dmg.split("d");
		numDice = Integer.parseInt(parts[0]);
		
		parts = parts[1].split("\\+");
		damageDice = Integer.parseInt(parts[0]);
		damageBonus = Integer.parseInt(parts[1]);
	}
}
