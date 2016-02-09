#!/usr/bin/env python

#CodeWars 2015
#
# Analysis of Acronyms

# Input
# The first line of input provides the number of phrases to be analyzed. 
# Each line after consists of an acronym (candidate) followed by a phrase. 
# The acronyms and phrases may be mixed (upper and lower) case. 
# Words may be hyphenated.
# 7
# SPBSG slowly pulsating B super-giants
# GNU GNU is not Unix
# COSTAR corrective optics space telescope axial replacement
# MOST Microvariability and Oscillation of Stars
# BOVINE Binary Obscure Voters Network
# SoHo SOUTH OF HOUSTON STREET
# BREAD Brian Edwards Analytical Design
# 
# Output
# For each input line, the program must analyze the phrase and determine 
# if the candidate is really an acronym for the phrase that follows, 
# and if it is, what its properties are. 
# The judges will expect the output to follow the patterns and wording 
# shown in the examples below. The key words are complete vs. partial 
# and simple vs. complex, or the use of the word not. 
# Some acronyms could be parsed either simple or complex. 
# In such cases the preferred analysis is simple.
# SPBSG is a COMPLETE SIMPLE acronym for slowly pulsating B super-giants
# GNU is a PARTIAL SIMPLE acronym for GNU is not Unix
# COSTAR is a COMPLETE SIMPLE acronym for corrective optics space telescope axial replacement
# MOST is a PARTIAL COMPLEX acronym for Microvariability and Oscillation of Stars
# BOVINE is NOT an acronym for Binary Obscure Voters Network
# SoHo is a PARTIAL COMPLEX acronym for SOUTH OF HOUSTON STREET
# BREAD is a COMPLETE COMPLEX acronym for Brian Edwards Analytical Design

import sys

# Recursively search for zero/one/two letters of acronym in the current word
# Return FOUND (1) if every letter of acronym is used
# Return COMPLETE (1) if every word is used, else return PARTIAL (0)
# Return SIMPLE (1) if only first letter is used, else return COMPLEX (0)
def findAcronym (newAcro, wordList):
    # First check end conditions (newAcro or wordList empty)
    found = 0
    complete = 0
    simple = 1 # Assume all single letters.  Recursive response will set to zero if needed.
    if (len(newAcro) == 0):
        found = 1
        if (len(wordList) == 0): # all words used
            complete = 1
        return found, complete, simple
    if (len(wordList) == 0): # no more words to search, so must not have found it.
        return 0, 0, 0
    
    # First try one letter of current word
    if (newAcro[0] != wordList[0][0]): # Didn't match first letter
        found, complete, simple = findAcronym(newAcro, wordList[1:]) # Skip this word
        return found,0, simple # Found or not, since a word was skipped, we're PARTIAL
            
    # First letter DOES match, so try the next letter and word
    found, complete, simple = findAcronym(newAcro[1:], wordList[1:])
    if (found==1): # Found the acronym
        return 1, complete, simple

    # Try two letters
    if ((len(newAcro) >1) and (len(wordList[0]) > 1)):
        if (newAcro[1] == wordList[0][1]): # Second letter matches
            found, complete, simple = findAcronym(newAcro[2:], wordList[1:])
            if (found==1): # Found the acronym
                return 1, complete, 0 # Used 2 letters here, so COMPLEX

    # Didn't match this word or later words
    return 0,0,0 # Return a no-match

#-----------------------
#Main program starts here.
lineCount = int(sys.stdin.readline().rstrip('\n'))
for i in range(lineCount):
    line = sys.stdin.readline().rstrip('\n')
    acronymOriginal = line[:line.find(" ")] # Store the Acronym
    acronym = acronymOriginal.upper() # Change to upper case for comparisons
    line = line[line.find(" ")+1:]
    lineRemaining = line  # Keep for final re-printing of line
    # Change any hyphens to spaces
    while (line.find("-")>0):
        line = line[:line.find("-")] + ' ' + line[line.find("-")+1:]
    words = line.upper().split()

    # Recursive search for the acronym in first 2 letters of words
    found, complete, simple = findAcronym(acronym, words)

    kind = "is NOT an" 
    if (found==1):
        kind = "is a "
        if (complete==1):
            kind = kind+"COMPLETE "
        else:
            kind = kind+"PARTIAL "
        if (simple==1):
            kind = kind+"SIMPLE"
        else:
            kind = kind+"COMPLEX"
    
    print (acronymOriginal, kind, "acronym of", lineRemaining)
