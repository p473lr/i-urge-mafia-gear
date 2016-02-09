
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


class Atom
{
private:

   char  symbol[8];
   int   subscript;

public:

   Atom(char *sym, int subscr);

   const char  *getSymbol();
   int         getSubscript();

   void        print();
};


Atom::Atom(char *sym, int subscr)
{
   strcpy(symbol, sym);
   subscript = subscr;
}


const char *Atom::getSymbol()
{
   return symbol;
}


int Atom::getSubscript()
{
   return subscript;
}


void Atom::print()
{
   printf("%s %d ", symbol, subscript);
}



class Molecule
{
private:

   unsigned long   coefficient;
   int    numAtoms;
   Atom   *atoms[16];

public:

   Molecule(char * str);

   void   setCoefficient(unsigned long newCoeff);
   unsigned long   getCoefficient();

   int    getNumAtoms();
   Atom   *getAtom(int index);
   Atom   *getAtom(const char *symbol);

   void   print();
};


Molecule::Molecule(char * str)
{
   char   *token;
   char   symbol[8];
   int    subscript;

   // Start with no atoms
   numAtoms = 0;
   memset(atoms, 0, sizeof(atoms));

   // We always start with a coefficient of 1
   coefficient = 1;

   // Parse the molecule from the input string
   token = strtok(str, " \n\r");
   while (token != NULL)
   {
      // Get the symbol and subscript from this atom
      strcpy(symbol, token);
      token = strtok(NULL, " \n\r");
      subscript = atoi(token);

      // Create a new atom and add it to our list
      atoms[numAtoms] = new Atom(symbol, subscript);
      numAtoms++;

      // Try to parse another atom
      token = strtok(NULL, " \n\r");
   }  
}


void Molecule::setCoefficient(unsigned long newCoeff)
{
   coefficient = newCoeff;
}


unsigned long Molecule::getCoefficient()
{
   return coefficient;
}


int Molecule::getNumAtoms()
{
   return numAtoms;
}


Atom *Molecule::getAtom(int index)
{
   if ((index < 0) || (index >= numAtoms))
      return NULL;
   else
      return atoms[index];
}


Atom *Molecule::getAtom(const char *symbol)
{
   int i;

   // Search our list of atoms for the one that matches the given symbol
   for (i = 0; i < numAtoms; i++)
   {
      // If this atom's symbol matches the given one, return it
      if (strcmp(symbol, atoms[i]->getSymbol()) == 0)
         return atoms[i];
   }

   // We didn't find the atom
   return NULL;
}


void Molecule::print()
{
   int i;

   // Print the molecule, start with the coefficient
   printf("%lu ", coefficient);

   // Then, print each atom in our list
   for (i = 0; i < numAtoms; i++)
      atoms[i]->print();
}


unsigned long gcd(unsigned long a, unsigned long b)
{
   unsigned long r;

   // Compute and return the Greatest Common Divisor of a and b
   r = a % b;
   while (r > 0)
   {
      a = b;
      b = r;
      r = a % b;
   }  

   return b;
}


unsigned long lcm(unsigned long a, unsigned long b)
{
   // Compute and return the Least Common Multiple of a and b
   // NOTE:  It's important to divide before multiplying here, otherwise
   //        the product might overflow
   return a * (b / gcd(a, b));
}


void balance(Molecule *reactants[], int numReactants,
             Molecule *products[], int numProducts)
{
   bool balanced;
   int  i, j, k;
   Atom *reactantAtom;
   unsigned long numReactantAtoms;
   Atom *productAtom;
   unsigned long numProductAtoms;
   unsigned long lcmCount;
   unsigned long newReactantCoeff;
   unsigned long newProductCoeff;
   unsigned long gcdCoeff;

   // We're not balanced yet
   balanced = false;

   // Keep iterating until we're done balancing
   while (!balanced)
   {
      // Start this iteration assuming we're balanced
      balanced = true;

      // Iterate over the reactants
      for (i = 0; i < numReactants; i++)
      {
         // Iterate over the atoms in this reactant
         for (j = 0; j < reactants[i]->getNumAtoms(); j++)
         {
            // Get the j'th atom from the i'th reactant molecule
            reactantAtom = reactants[i]->getAtom(j);

            // Get the total count for this reactant atom
            numReactantAtoms = 
               reactants[i]->getCoefficient() * reactantAtom->getSubscript();

            // Find the corresponding product atom, and get it's count
            k = 0;
            productAtom = NULL;
            while (productAtom == NULL)
            {
               // Look for the product atom in the k'th product
               productAtom = products[k]->getAtom(reactantAtom->getSymbol());

               // Increment the counter and try again if we didn't find it
               if (productAtom == NULL)
                  k++;
            }
               
            // Get the total count of the product atom
            numProductAtoms =
               products[k]->getCoefficient() * productAtom->getSubscript();

            // If the counts don't match, we need to balance them
            if (numReactantAtoms != numProductAtoms)
            {
               // We're not balanced yet (we need to iterate at least once
               // more)
               balanced = false;

               // Find the LCM of the atom counts
               lcmCount = lcm(numReactantAtoms, numProductAtoms);

               // Compute new coefficients for the reactant and product
               newReactantCoeff = lcmCount / reactantAtom->getSubscript();
               newProductCoeff = lcmCount / productAtom->getSubscript();

               // Update the coefficients
               reactants[i]->setCoefficient(newReactantCoeff);
               products[k]->setCoefficient(newProductCoeff);
            }
         }
      }

      // Find the GCD of all coefficients before looping again.  By doing this
      // on every loop, we limit the chances of an integer overflow.
      if (numReactants > 1)
         gcdCoeff = gcd(reactants[0]->getCoefficient(),
            reactants[1]->getCoefficient());
      else
         gcdCoeff = gcd(reactants[0]->getCoefficient(),
            products[0]->getCoefficient());
      for (i = 1; i < numReactants; i++)
      {
         gcdCoeff = gcd(gcdCoeff, reactants[i]->getCoefficient());
      }
      for (i = 0; i < numProducts; i++)
      {
         gcdCoeff = gcd(gcdCoeff, products[i]->getCoefficient());
      }

      // Divide all coefficients by the GCD
      for (i = 0; i < numReactants; i++)
      {
         reactants[i]->
            setCoefficient(reactants[i]->getCoefficient() / gcdCoeff);
      }
      for (i = 0; i < numProducts; i++)
      {
         products[i]->
            setCoefficient(products[i]->getCoefficient() / gcdCoeff);
      }
   }
}


int main(void)
{
   FILE      *fp;
   char      line[256];
   char      *moleculeStr;
   int       numEquations;
   char      reactantStr[16][32];
   char      productStr[16][32];
   Molecule  *reactants[16];
   int       numReactants;
   Molecule  *products[16];
   int       numProducts;
   int       i, j;


   // Open the input file
   fp = fopen("balance.in", "r");

   // Read the number of equations to balance
   fgets(line, sizeof(line), fp);
   sscanf(line, "%d", &numEquations);

   // Read and balance each of the equations
   for (i = 0; i < numEquations; i++)
   {
      // Get the number of reactants and products
      fgets(line, sizeof(line), fp);
      sscanf(line, "%d %d\n", &numReactants, &numProducts);

      // Get the equation
      fgets(line, sizeof(line), fp);

      // Initialize the arrays
      memset(reactantStr, 0, sizeof(reactantStr));
      memset(reactants, 0, sizeof(reactants));
      memset(productStr, 0, sizeof(productStr));
      memset(products, 0, sizeof(products));

      // Parse the reactants and products from the line.  Note that we
      // have to separate the input into tokens and store them because the
      // strtok() function can only work on one string at a time, and the
      // Molecule constructor also uses strtok().  Otherwise, we could
      // immediately create a Molecule object for each string token.
      //
      // Also note here that we tokenize on both '+', as well as '-' and '>',
      // because the "->" symbol will be what terminates the last token on
      // the reactants side.
      moleculeStr = strtok(line, "+->");
      for (j = 0; j < numReactants; j++)
      {
         // Copy the reactant string
         strcpy(reactantStr[j], moleculeStr);

         // Get the next token
         moleculeStr = strtok(NULL, "+->");
      }

      // Now, parse out the product strings.  Note that we're parsing on
      // '+' and '\n' here, because the fgets() function leaves the newline
      // character on the line, and we don't want it in our string
      for (j = 0; j < numProducts; j++)
      {
         // Copy the product string
         strcpy(productStr[j], moleculeStr);

         // Get the next token
         moleculeStr = strtok(NULL, "+\n");
      }

      // Now that we've parsed the input into separate strings, we need
      // to create Molecule objects from them.  Start with the reactants...
      for (j = 0; j < numReactants; j++)
         reactants[j] = new Molecule(reactantStr[j]);

      // Now, do the same for the products...
      for (j = 0; j < numProducts; j++)
         products[j] = new Molecule(productStr[j]);

      // Finally, balance the equation
      balance(reactants, numReactants, products, numProducts);

      // Print the balanced equation
      printf("Equation %d: ", i+1);
      for (j = 0; j < numReactants; j++)
      {
         // Print the reactant
         reactants[j]->print();
         
         // If this isn't the last reactant, print a '+'
         if (j < (numReactants - 1))
            printf("+ ");
      }
      printf("-> ");
      for (j = 0; j < numProducts; j++)
      {
         // Print the product
         products[j]->print();
         
         // If this isn't the last product, print a '+'
         if (j < (numProducts - 1))
            printf("+ ");
      }

      // Terminate the output line
      printf("\n");
   }

   // Close the input file
   fclose(fp);
}
