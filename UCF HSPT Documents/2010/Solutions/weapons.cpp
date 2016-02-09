
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


struct Weapon
{
   char   name[32];
   int    hitBonus;
   int    numDmgDice;
   int    dmgDie;
   int    dmgBonus;
};


double calcAvgDamage(int ba, Weapon weapon)
{
   double   damageRollTotal;
   double   avgDamageRoll;
   double   maxDamageRoll;
   int      numHits;
   double   avgDamage;

   // Calculate the base damage (NOTE: instead of actually adding all of the
   // possible rolls and averaging, we'll use the n * (n+1) / 2 rule
   // that gives us the total immediately)
   damageRollTotal = weapon.dmgDie * (weapon.dmgDie+1) / 2.0;
   avgDamageRoll = damageRollTotal / (double) weapon.dmgDie;
   avgDamageRoll *= weapon.numDmgDice;
   avgDamageRoll += weapon.dmgBonus;

   // Calculate the maximum damage (used for hit rolls of 20)
   maxDamageRoll = weapon.numDmgDice * weapon.dmgDie + weapon.dmgBonus;

   // Now, figure out how many hit rolls on a 20-sided die would hit the target
   // (the extra +1 comes from the fact that we only have to match the BA,
   // not beat it).  Note that we're leaving out 20, since we'll handle this
   // as a special case later
   numHits = (19 - ba) + 1 + weapon.hitBonus;

   // Since we're omitting 20 and 1, we can't have more hits than 18 on a 
   // d20 (no matter how good our to-hit bonus is)
   if (numHits > 18)
      numHits = 18;

   // We also can't have fewer than zero hits
   if (numHits < 0)
      numHits = 0;

   // Finally, calculate the average damage per swing
   avgDamage = (numHits * avgDamageRoll + maxDamageRoll) / 20.0;

   return avgDamage;
}


int getBA(FILE * fp)
{
   char     line[256];
   int      ba;

   // Read the line
   fgets(line, sizeof(line), fp);

   // Parse the ba and return it
   ba = atoi(line);
   return ba;
}


Weapon getWeapon(FILE * fp)
{
   char     line[256];
   char *   token;
   Weapon   weapon;

   // Read the line
   fgets(line, sizeof(line), fp);

   // Parse the weapon's name
   token = strtok(line, " \r\n");  
   strcpy(weapon.name, token);

   // Parse the hit bonus
   token = strtok(NULL, " \r\n");
   weapon.hitBonus = atoi(token);

   // Parse the number of damage dice
   token = strtok(NULL, "d");
   weapon.numDmgDice = atoi(token);

   // Parse the damage die type
   token = strtok(NULL, "+");
   weapon.dmgDie = atoi(token);

   // Parse the damage bonus
   token = strtok(NULL, " \r\n");
   weapon.dmgBonus = atoi(token);

   // Return the weapon
   return weapon;
}


int main(void)
{
   FILE *   fp;
   char     line[256];
   int      ba;
   Weapon   weapon1;
   double   avg1;
   Weapon   weapon2;
   double   avg2;
 

   // Open the input file
   fp = fopen("weapons.in", "r");

   // Get the ba for the first monster
   ba = getBA(fp);

   // Repeat until we see a BA of 0
   while (ba != 0)
   {
      // Get the two weapons
      weapon1 = getWeapon(fp);
      weapon2 = getWeapon(fp);

      // Calculate the average damage per swing for each weapon
      avg1 = calcAvgDamage(ba, weapon1);
      avg2 = calcAvgDamage(ba, weapon2);

      // Compare the average damage values and print the result
      if (avg1 > avg2)
         printf("%s is better than %s.\n", weapon1.name, weapon2.name);
      else if (avg1 < avg2)
         printf("%s is better than %s.\n", weapon2.name, weapon1.name);
      else
         printf("They're both the same.\n");

      // Get the ba for the next monster
      ba = getBA(fp);
   }

   // Close the file
   fclose(fp);

   return 0;
}

