// Take as input a series of encoded words, then "KEY" then some decoded text.
//  Then decode all the words.
/* Example Input:
XYVOIC ERH FSCWIRFIVVC SR VCI AMXL E WMHI SJ XYRE WEPEH
KEY YONR

Example Output:
TURKEY AND BOYSENBERRY ON RYE WITH A SIDE OF TUNA SALAD
*/


#include <string>
#include <iostream>

using namespace std;

#define MAXLINE 250

//Globals
char words[50][50]; //50 words of 50 chars each
int numWords;

// rotateWord
void rotateWord(char * word, int rotation)
{
	int i,j;

	i=strlen(word);
	for (j=0; j<i; j++)
	{
		word[j] += rotation;
		if (word[j] > 'Z')
			word[j] -= 26; // Account for wrapping
	}
}

void rotateAllAndPrint(int rotation)
{
	int i;
	for(i=0;i<numWords;i++)
	{
		rotateWord(words[i], rotation);
		cout << words[i] << " "; // Print the word and a space
	}
	cout << endl;
}

int main( int argc, char* argv[] )
{
   char sentence[MAXLINE]; //sentence, without spaces, for searching
   int numLetters;

   char newWord[MAXLINE];
   char keyText[MAXLINE];
   int keyRotation;

   while(true) // Program loop
   {
       keyRotation=numWords=numLetters=0;
	   memset(keyText, 0, MAXLINE);

       cout << endl << "Enter encoded words, followed by KEY <keytext> (or END): " << endl;

       while( true ) // Read in each word
       {
		   memset(newWord, 0, MAXLINE);
	       cin >> newWord;
	       if (!strcmp(newWord, "END")) // Out for full program loop
	    	   return 0;
           if( !strcmp(newWord, "KEY") )
	       {
	    	   cin >> keyText;
	    	   break;
	       }
	       else if (!strcmp(newWord, "KEYROT"))
	       {
	    	   cin >> keyRotation; // Backdoor to just rotate the input
	    	   break;
	       }
	       else
	       {
			   strcpy(words[numWords++], newWord);
			   strcpy(&(sentence[numLetters]), newWord);
			   numLetters += strlen(newWord);
	       }
       }

       if (keyRotation > 0) // Backdoor used.  Rotate, print, and exit.
       {
			rotateAllAndPrint(keyRotation);
       }
	   else
	   {
		    // Need to search for keyText in sentence
			for (keyRotation=0; keyRotation<26; keyRotation++)
			{
				if(strstr(sentence, keyText)) // Found keyText at this rotation
				{
					cout << "Coded Rotation: " << (26-keyRotation) << endl;
					rotateAllAndPrint(keyRotation);
					break;
				}
				else
				{
					rotateWord(keyText, 25); // Rotate keyText back one
				}
			}
	   }
   }
}