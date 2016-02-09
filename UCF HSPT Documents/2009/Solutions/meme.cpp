
#include <stdio.h>
#include <ctype.h>
#include <string.h>


typedef struct
{
   char   line[128];
   char   words[128][128];
   bool   used[128];
   int    numWords;
} Sentence;


bool repeatSeen;
bool debug=false;


bool isSymbol(char c)
{
   // Return whether this character is a symbol
   switch (c)
   {
      case '@':
      case '#':
      case '$':
      case '%':
      case '*':
      case '&':
      case '!':
         return true;
         break;

      default:
         return false;
         break;
   }
}


void capitalize(char * word)
{
   int   i;

   // Make each letter a capital
   for (i=0; i < strlen(word); i++)
      word[i] = toupper(word[i]);
}


void sortWords(Sentence * sen)
{
   char   tmp[256];
   int    i;
   int    j;

   // Sort the words in this sentence
   for (i=0; i < sen->numWords-1; i++)
   {
      for (j=i; j < sen->numWords; j++)
      {
         if (sen->words[i] < sen->words[j])
         {
            strcpy(tmp, sen->words[i]);
            strcpy(sen->words[i], sen->words[j]);
            strcpy(sen->words[j], tmp);
         }
      }
   }
}


int countSymbolTypes(Sentence sen)
{
   char   symbol;
   int    count;
   int    i;
   int    j;

   // Loop through the words' characters and count the symbols
   symbol = ' ';
   count = 0;
   for (i=0; i < sen.numWords; i++)
   {
      for (j=0; j < strlen(sen.words[i]); j++)
      {
         switch (sen.words[i][j])
         {
            case '@':
            case '#':
            case '$':
            case '%':
            case '*':
            case '&':
            case '!':
               // Count the "unique" symbols
               if (symbol == ' ')
               {
                  symbol = sen.words[i][j];
                  count++;
               }
               else if (symbol != sen.words[i][j])
                  count++;
               break;
            default:
               // If we see a letter, set our seen symbol back to
               // blank (this lets us count strings such as
               // "ABC@@@@DEF@@@GHI" as 2 and not 1)
               symbol = ' ';
               break;
         }
      }
   }

   // Return the count of symbols
   return count;
}


int countSymbols(char * word)
{
   int    count;
   int    i;

   // Loop through the words' characters and count the symbols
   count = 0;
   for (i=0; i < strlen(word); i++)
   {
      switch (word[i])
      {
         case '@':
         case '#':
         case '$':
         case '%':
         case '*':
         case '&':
         case '!':
            count++;
      }
   }

   // Return the count of symbols
   return count;
}

bool compare(char * memeWord, char * phraseWord)
{
   bool     found;
   int      i;
   char     meme[256];
   char     phrase[256];
   int      j;
   char     symbol[256];
   char     prefixLetter;
   char     suffixLetter;
   char *   token;

   if (debug) 
      printf("Comparing %s and %s\n", memeWord, phraseWord);

   // If the meme word is longer than the phrase word, then it can't match
   if (strlen(memeWord) > strlen(phraseWord))
      return false;

   // If these words match, they will have some prefix and/or some
   // suffix that matches so find those first and get rid of them

   // Find the common prefix
   strcpy(symbol, " ");
   strcpy(meme, memeWord);
   strcpy(phrase, phraseWord);
   prefixLetter = ' ';
   suffixLetter = ' ';
   found = false;
   i = strlen(meme);
   while ( (found == false) && (i >= 0) )
   {
      meme[i] = '\0';
      if (strstr(phrase, meme) == phrase)
         found = true;
      else
         i--;
   }
   if (i < 0)
      i = 0;

   // Find the common suffix
   if (i >= 1)
      prefixLetter = memeWord[i-1];
   strcpy(meme, &memeWord[i]);
   strcpy(phrase, &phraseWord[i]);
   found = false;
   j = strlen(meme);
   while ( (found == false) && (j >= 0) )
   {
      if (strstr(&phrase[strlen(phrase)-j], &meme[strlen(meme)-j]) == 
          &phrase[strlen(phrase)-j])
         found = true;
      else
         j--;
   }

   // Get the leftover
   if (found == true)
   {
      phrase[strlen(phrase)-j] = '\0';
      if (suffixLetter == ' ')
         suffixLetter = meme[strlen(meme)-j];
      meme[strlen(meme)-j] = '\0';
   }
   
   if (debug) 
      printf("   last letter is *%c* *%c*\n", prefixLetter, suffixLetter);
   if (debug) 
      printf("   Core is *%s* and *%s*\n", meme, phrase);

   // If the core is empty, then the words matched perfectly
   if ( (strlen(meme) == 0) && (strlen(phrase) == 0) )
      return true;

   // Check the parts of the meme we can (single symbol)
   if (strlen(meme) > 0)
   {
      // Check if starts with symbol
      if (isSymbol(phrase[0]) == true)
      {
         // Store the symbol
         sprintf(symbol, "%c", phrase[0]);

         // Clear off the first character and check rest
         memmove(&meme[0], &meme[1], strlen(&meme[1])+1);
         memmove(&phrase[0], &phrase[1], strlen(&phrase[1])+1);

         // 
         while ( (strlen(meme) > 0) && (strlen(phrase) > 0) &&
                 (meme[0] == phrase[0]) )
         {
            memmove(&meme[0], &meme[1], strlen(&meme[1])+1);
            memmove(&phrase[0], &phrase[1], strlen(&phrase[1])+1);
         }
      }
      else if (isSymbol(phrase[strlen(phrase)-1]) == true)
      {
         // Store the symbol
         sprintf(symbol, "%c", phrase[strlen(phrase)-1]);

         // Clear off the last character and check rest
         meme[strlen(meme)-1] = '\0';
         phrase[strlen(phrase)-1] = '\0';

         while ( (strlen(meme) > 0) && (strlen(phrase) > 0) &&
                 (meme[strlen(meme)-1] == phrase[strlen(phrase)-1]) )
         {
            meme[strlen(meme)-1] = '\0';
            phrase[strlen(phrase)-1] = '\0';
         }
      }
   }
   if (debug) 
      printf("   Now core is *%s* and *%s*\n", meme, phrase);

   // See if we ate up everything
   if ( (strlen(meme) == 0) && (strlen(phrase) == 0) )
      return true;

   // Process based on meme core length
   if (strlen(meme) == 1)
   {
      // Meme core has a single character so it either mismatched
      // to a symbol or to a letter
      if ( (isSymbol(phrase[0]) == true) && (symbol[0] == ' ') )
      {
         // It is a symbol so remove the match
         sprintf(symbol, "%c", phrase[0]);
         meme[0] = '\0';
         memmove(&phrase[0], &phrase[1], strlen(&phrase[1])+1);

         // If we used up the phrase, we're done and matched
         if (strlen(phrase) == 0)
            return true;
      }
      else
      {
         // Two letters must have mismatched so return false
         return false;
      }
   }

   if (strlen(meme) == 0)
   {
      // If we already processed one repeat, then we fail
      if (repeatSeen == true)
         return false;

      // Otherwise, we must have a repeat (otherwise, the phrase
      // wouldn't have left over characters)
      repeatSeen = true;

      // Used up the meme so we just have to verify the
      // left over phrase
      if ( (symbol[0] != ' ') && (strspn(phrase, symbol) == strlen(phrase)) )
      {
         // All the characters are the same and the removed symbol
         return true;
      }
      else if ( (symbol[0] == ' ') && (isSymbol(phrase[0]) == true) &&
                (strspn(phrase, &phrase[strlen(phrase)-1]) == strlen(phrase)) )
      {
         // All the characters are the same and a new symbol
         if (strlen(phrase) == 1)
            return true;
         else
            return false;
      }
      else if (strspn(phrase, &phrase[strlen(phrase)-1]) == strlen(phrase))
      {
         // All the characters are the same letter (and match the
         // last one in the prefix or first one in the suffix)
         if ( (phrase[0] == prefixLetter) || (phrase[0] == suffixLetter) )
            return true;
         else
            return false;
      }
      else if (countSymbols(phrase) == 1)
      {
         // There are multiple characters and one symbol (the symbol
         // itself can't be repeated or we repeated two characters)
         i = 0;
         while (isSymbol(phrase[i]) == false)
            i++;
         memmove(&phrase[i], &phrase[i+1], strlen(&phrase[i+1])+1);

         if ( (strspn(phrase, &phrase[strlen(phrase)-1]) == strlen(phrase)) )
            return true;
         else
            return false;
      }
      else
      {
         // Multiple characters that aren't the same so can't match
         return false;
      }
   }
   else
   {
      // If the meme still has two characters left, it must not
      // match
      return false;
   }
}


bool check(Sentence phrase, Sentence meme)
{
   bool   matchedAll;
   int    i;
   bool   match;
   int    j;
   bool   rs;

   // First, perform a simple reject (if the phrase has multiple
   // symbols, then it can't be a meme)
   if (countSymbolTypes(phrase) > 1)
      return false;

   // Then, reject if the phrase and meme have a different number of words
   if (phrase.numWords != meme.numWords)
      return false;

   // Clear the used flags
   for (j=0; j < phrase.numWords; j++)
      phrase.used[j] = false;

   // Note that Rules 2 and 3 each can change only a single word (if
   // used correctly to transform a meme) so least n-2 words will
   // match correctly (ignoring case)
   matchedAll = true;
   i = 0;
   repeatSeen = false;
   while ( (matchedAll == true) && (i < meme.numWords) )
   {
      // Loop through the phrase words and look for a match
      match = false;
      j = 0;
      while ( (match == false) && (j < phrase.numWords) )
      {
         // Only compare if this word hasn't been used yet
         rs = repeatSeen;
         if (phrase.used[j] == false)
         {
            // See if the two words match
            if (compare(meme.words[i], phrase.words[j]) == true)
            {
               // They do
               if (debug) 
                  printf("   TRUE\n");
               phrase.used[j] = true;
               match = true;
            }
            else
               repeatSeen = rs;
         }

         // Go to the next phrase word
         j++;
      }

      // Check to see if we ever matched this word
      if (match == false)
         matchedAll = false;

      // Go to the next meme word
      i++;
   }

   // Return if we matched all words
   return matchedAll;
}


int main()
{
   FILE *     infile;
   char       line[256];
   int        numThreads;
   int        i;
   int        numMemes;
   int        j;
   Sentence   memes[32];
   char *     token;
   int        numPhrases;
   Sentence   phrases[32];
   bool       foundMeme;
   bool       reject;
   int        idx;

   // Open the input file
   infile = fopen("meme.in", "r");

   // Read the number of comment threads
   fgets(line, sizeof(line), infile);
   sscanf(line, "%d", &numThreads);

   // Loop through the comment threads
   for (i=0; i < numThreads; i++)
   {
      // Output the header
      printf("Comment thread #%d:\n", i+1);

      // Get the number of memes
      fgets(line, sizeof(line), infile);
      sscanf(line, "%d", &numMemes);

      // Read in and split the memes
      for (j=0; j < numMemes; j++)
      {
         // Read in the line
         fgets(line, sizeof(line), infile);
         if (line[strlen(line)-1] == '\n')
            line[strlen(line)-1] = '\0';

         // Tokenize and store each word in the meme
         memes[j].numWords = 0;
         strcpy(memes[j].line, line);
         token = strtok(line, " ");
         while (token != NULL)
         {
            // Store this word and increment the count
            strcpy(memes[j].words[memes[j].numWords], token);
            capitalize(memes[j].words[memes[j].numWords]);
            memes[j].used[memes[j].numWords] = false;
            memes[j].numWords++;

            // Try to get the next word
            token = strtok(NULL, " ");
         }
      }

      // Get the number of phrases
      fgets(line, sizeof(line), infile);
      sscanf(line, "%d", &numPhrases);

      // Read in and split the phrases
      for (j=0; j < numPhrases; j++)
      {
         // Read in the line
         fgets(line, sizeof(line), infile);
         if (line[strlen(line)-1] == '\n')
            line[strlen(line)-1] = '\0';

         // Tokenize and store each word in the meme
         phrases[j].numWords = 0;
         strcpy(phrases[j].line, line);
         token = strtok(line, " ");
         while (token != NULL)
         {
            // Store this word and increment the count
            strcpy(phrases[j].words[phrases[j].numWords], token);
            capitalize(phrases[j].words[phrases[j].numWords]);
            phrases[j].used[phrases[j].numWords] = false;
            phrases[j].numWords++;

            // Try to get the next word
            token = strtok(NULL, " ");
         }
      }

      // Sort the words within the meme
      for (j=0; j < numMemes; j++)
         sortWords(&memes[j]);
      for (j=0; j < numPhrases; j++)
         sortWords(&phrases[j]);

      // Loop through the phrases
      foundMeme = false;
      for (j=0; j < numMemes; j++)
      {
          // Check this meme against the phrases
          reject = false;
          idx = 0;
          while ( (reject == false) && (idx < numPhrases) )
          {
             // See if this phrase matches the idx-th meme
             if (check(phrases[idx], memes[j]) == true)
             {
                // It's a meme!  Warning!
                reject = true;
             }
             else
             {
                // Try the next phrase
                idx++;
             }
          }

          // If the meme was found, output it and note that we found one
          if (reject == true)
          {
             // Output the meme
             printf("   %s\n", memes[j].line);

             // Mark that we found at least one meme
             foundMeme = true;
          }
      }

      // If we found no memes, output accordingly
      if (foundMeme == false)
         printf("   No memes detected! Let intellectual discourse continue!\n");

      // Leave a blank line after each set
      printf("\n");
   }

   // Close the input file
   fclose(infile);
}

