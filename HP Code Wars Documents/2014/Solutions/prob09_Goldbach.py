#!/usr/bin/env python

#CodeWars 2014
#
#Goldbach's Conjecture

# Goldbach's conjecture says that every positive even number greater than 2 is 
# the sum of two prime numbers. This conjecture has never been proven in the 
# general case, but it has been confirmed for numbers much larger than 
# your programming environment's native data type supports.
#
# Write a program to print the two prime numbers that sum to a given even integer.
# Find the two primes with the smallest difference
#
# Input
#
# Each line of input will be a positive, even integer greater than two, 
# except the last line, which will be zero. The maximum input value will be 1000.
#
# 28
# 992
# 16
# 0
# 

import sys
from math import sqrt

def isprime(value):
    for divisor in range (2, int(sqrt(value))+1):
        if (value/divisor == int(value/divisor)):
            return 0
    return 1

print ("Enter even numbers.  0 to end.")
for line in sys.stdin:
    total = int(line)
    lowestDiff=total
    if (total == 0):
        break
    for low in range(3, int((total/2)+1)):
        if (isprime(low)==1):
            high = total-low
            if (isprime(high)==1):
                # Found an answer.  Update if lowest answer is found.
                if (high-low < lowestDiff):
                    lowestDiff = high-low
                    bestLow = low
                    bestHigh = high
    print (bestLow, "+", bestHigh, "=", total)

