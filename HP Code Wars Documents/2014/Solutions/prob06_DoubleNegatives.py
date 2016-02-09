#!/usr/bin/env python

#CodeWars 2014
#
#Don't Use No Double Negatives
# 
# Write a program to count the number of negatives in a sentence. 
# Use the list of negative words provided below. 
# The sample input will demonstrate that there may or may not be other 
# forms of negatives we won't be failing to ignore.
#
#     don't can't isn't haven't cannot wouldn't couldn't won't
#     no not never nobody nowhere neither ain't
#
#
#
#
# Input
#
# Each line of input is a single sentence that ends with a period. 
# Sentences may have one or more of the following punctuation marks:
#
# , ; : . ? ! "
#
# The input ends with a single period.
#
# THERE NEVER WAS NO MAN NOWHERE SO VIRTUOUS.
# AXE YA EX WHAT TALKS IN TONGUES, SAY "NEVER MO NEITHER ME".
# I HAVEN'T NEVER OWED NOTHING TO NO ONE.
# BADGES? WE AIN'T GOT NO BADGES.
# THIS IS A PERFECTLY POSITIVE SENTENCE.
# I CAN'T GET NO, NO, NO, NO, HEY, HEY, HEY, NO SATISFACTION!
# IT WOULDN'T BE INACCURATE TO ASSUME I COULDN'T SAY THAT I DON'T KNOW WHERE HE'S NOT.
# .
#
#
#
# Output
#
# The program should print the number of negatives in each sentence, followed by the sentence.
#
# 3: THERE NEVER WAS NO MAN NOWHERE SO VIRTUOUS.
# 2: AXE YA EX WHAT TALKS IN TONGUES, SAY "NEVER MO NEITHER ME".
# 4: I HAVEN'T NEVER OWED NOTHING TO NO ONE.
# 2: BADGES? WE AIN'T GOT NO BADGES.
# 0: THIS IS A PERFECTLY POSITIVE SENTENCE.
# 6: I CAN'T GET NO, NO, NO, NO, HEY, HEY, HEY, NO SATISFACTION!
# 4: IT WOULDN'T BE INACCURATE TO ASSUME I COULDN'T SAY THAT I DON'T KNOW WHERE HE'S NOT.
#

import sys
import re

negativeLine = "don't can't isn't haven't cannot wouldn't couldn't won't no not never nobody nowhere neither ain't"
negativeLine = negativeLine.upper()
negativeWords = re.findall(r"[\w']+", negativeLine) # Parse for words, keeping apostrophes

print ("Enter sentences. Period to end.")
for line in sys.stdin:
    if (line[0]=='.'):
        break
    upperLine = line.upper()             # Be sure we have uppercase letters
    negativeCount=0
    words = re.findall(r"[\w']+", upperLine)  # Parse for words, keeping apostrophes
    for word in words:
        if (word in negativeWords):
            negativeCount += 1
    print (negativeCount, ": ", line, sep="")
