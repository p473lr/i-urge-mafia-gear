#!/usr/bin/env python

#CodeWars 2015
#
# No Disassemble

# You must write a program that reads a single number and writes a friendly message on the screen.
# Input
# The input is a single integer between one and ten, inclusive.
# Example 1:
# 5
# Example 2:
# 10
# Output
# The program must print a sentence in the format shown in the examples below, using the 
# input number written as a word.
# Example 1:
# Number five is alive!
# Example 2:
# Number ten is alive!


import sys

NumberWords = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"]
print ("Enter N.")
N = int(sys.stdin.readline())
print ("Number", NumberWords[N-1], "is alive!")
