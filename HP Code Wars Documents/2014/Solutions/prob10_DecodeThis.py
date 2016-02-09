#!/usr/bin/env python

#CodeWars 2014
#
#Decode This
#
#This program will also create the Encoded output.  Instead of providing the
#number of bytes, simply use "e Text to Encode".
#
#Original problem text:
#
#The following text is encoded thusly:
#    Each successive character is placed based on the value of the last character.
#DECODE THIS!  will become  DO TEE!ISCDH
#There are 12 characters in both the original and encoded strings. The original first character is placed in the first encoded position. 
#Dxxxxxxxxxxx. D=4, so the next character, E, is placed in the 4th empty position after D:
#DxxxExxxxxxx. E=5, so the next character, C, is placed in the 5th empty position after E:
#DxxxExxxxCxx. C=3, so the next character, O, is placed in the 3rd empty position after C, wrapping:
#DOxxExxxxCxx. O=15.
#DOxxExxxxCDx. D=4.
#DOxxEExxxCDx. E=5.
#DO#xEExxxCDx. The space = 1.
#DO#TEExxxCDx. T=20.
#DO#TEExxxCDH. H=8.
#DO#TEExIxCDH. I=9.
#DO#TEExISCDH. S=19
#DO#TEE!ISCDH
# 
#Values:
#A-Z = 1-26.
#a-z = 1-26.
#Any other characters (punctuation, numbers, spaces) have a value of 1.
#


import sys
import string

def decodeThis (codeLength, code):
    decodedString = ['@'] * codeLength
    usedArray = [0] * codeLength      # mark if location filled
    currentLoc=0
    charactersPlaced = 0
    while charactersPlaced < codeLength: #loop through each character in the code to fill decodedString
        char = code[currentLoc]
        decodedString[charactersPlaced] = char
        usedArray[currentLoc] = 1
        charactersPlaced += 1
        if charactersPlaced < codeLength:  # Wasn't the last character, keep going
            if char.isalpha():
                charVal = ord(char.upper()) - ord('A') + 1  # convert letter to value 1-26
            else:
                charVal = 1
            while charVal > 0:
                currentLoc = (currentLoc+1) % codeLength  # move through array
                if usedArray[currentLoc] == 0:
                    charVal -= 1

    return ''.join(decodedString)


def encodeThis (codeLength, code):
    encodedString = ['@'] * codeLength
    usedArray = [0] * codeLength      # mark if location filled
    currentLoc=0
    charactersPlaced = 0
    while charactersPlaced < codeLength: #loop through each character in the code to fill encodedString
        char = code[charactersPlaced]
        encodedString[currentLoc] = char
        usedArray[currentLoc] = 1
        charactersPlaced += 1
        if charactersPlaced < codeLength:  # Wasn't the last character, keep going
            if char.isalpha():
                charVal = ord(char.upper()) - ord('A') + 1  # convert letter to value 1-26
            else:
                charVal = 1
            while charVal > 0:
                currentLoc = (currentLoc+1) % codeLength  # move through array
                if usedArray[currentLoc] == 0:
                    charVal -= 1

    return ''.join(encodedString)

if __name__ == '__main__':
    print ("For each line, enter #/e then string. 0 to end. ")
    for line in sys.stdin:
        if len(line) < 4: # Any short line will force an exit
            sys.exit()
        # Split line into first token and rest of line
        first, code = line.split(None, 1)
        # Check first if being used to encode a string
        if first[0].upper() == 'E':
            codeLength = len(code)-1 #remove End-of-Line character
            outputLine = str(codeLength) + " " + encodeThis(codeLength, code)
        else:
            codeLength = int(first)
            outputLine = decodeThis(codeLength, code)

        print (outputLine)


