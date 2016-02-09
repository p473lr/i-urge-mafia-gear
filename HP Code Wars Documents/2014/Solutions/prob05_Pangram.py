#!/usr/bin/env python

#CodeWars 2014
#
#Pangram
# A sentence that uses every letter of the alphabet is called a pangram. 
# A "perfect" pangram only uses each letter once. 
# Write a program to determine if a sentence is a pangram or a perfect pangram.


# Input
#
# Each line of input is a sentence that ends with a period. The input ends with a single period.
#
# THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG.
# ALL YOUR BASE ARE BELONG TO US; SOMEONE SET US UP THE BOMB.
# "NOW FAX A QUIZ TO JACK!" MY BRAVE GHOST PLED.
# QUICK HIJINX SWIFTLY REVAMPED THE GAZEBO.
# NEW JOB: FIX MR GLUCK'S HAZY TV, PDQ.
# LOREM IPSUM DOLOR SIT AMET CONSECTETUR ADIPISCING ELIT.
# PACK MY BOX WITH FIVE DOZEN LIQUOR JUGS.
# .
#
#
# Output
#
# For each sentence, the program must print the key word PERFECT if the sentence is a perfect pangram, PANGRAM if it is a non-perfect pangram, or NEITHER if it is neither one. After the key word, the program must also print a colon and the sentence.
#
# PANGRAM: THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG.
# NEITHER: ALL YOUR BASE ARE BELONG TO US; SOMEONE SET US UP THE BOMB.
# PANGRAM: "NOW FAX A QUIZ TO JACK!" MY BRAVE GHOST PLED.
# PANGRAM: QUICK HIJINX SWIFTLY REVAMPED THE GAZEBO.
# PERFECT: NEW JOB: FIX MR GLUCK'S HAZY TV, PDQ.
# NEITHER: LOREM IPSUM DOLOR SIT AMET CONSECTETUR ADIPISCING ELIT.
# PANGRAM: PACK MY BOX WITH FIVE DOZEN LIQUOR JUGS.
#

import sys
import string

print ("Enter sentences. Period to end.")
for line in sys.stdin:
    if (line[0]=='.'):
        break
    line = line.upper()             # Be sure we have uppercase letters
    label = "PERFECT:"              # Assume perfect to start
    for letter in string.ascii_uppercase: # Loop from A to Z
        letterCount = line.count(letter)
        if (letterCount == 0):
            label="NEITHER:"        # A letter is missing, quit looking
            break
        elif (letterCount > 1):
            label="PANGRAM:"        # A letter appears more than once
    print (label, line)
