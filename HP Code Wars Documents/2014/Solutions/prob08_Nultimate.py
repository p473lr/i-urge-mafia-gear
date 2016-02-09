#!/usr/bin/env python

#CodeWars 2014
#
# N-ultimate Element

#The last element in a series may be called the ultimate element. 
# The penultimate element is next-to last. So, by extension, 
# the N-ultimate element is the Nth element from the end.
#
#Write a program to find the N-ultimate element in a series.
#
#
#Input
#
#Each line of input starts with an integer N, followed by a series of N 
# or more words/numbers/strings, terminated with a $ symbol. 
# The input ends with the number zero and a $ symbol.
#
#4 PROXIMATE DISTANT EXTREME FARTHEST ULTIMATE $
#6 999 0 426 123 1337 31415 1414 5 321 $
#2 WHO WHAT WHEN WHERE WHY HOW $
#3 RED GREEN BLUE YELLOW ORANGE PURPLE BLACK WHITE $
#7 GARCIA WANG ZHANG LI SMITH MULLER GONZALEZ SMIRNOV NGUYEN HERNANDEZ $
#0 $
#
#
#Output
#
#For each line of input the program must print the N-ultimate word.
#
#DISTANT
#123
#WHY
#PURPLE
#  LI
#

import sys

print ("Enter N, words, $. 0 to end.")
for line in sys.stdin:
    words = line.split()
    N = int(words[0])
    if (N==0):
        break
    print (words[len(words)-N-1])
