#!/usr/bin/env python

#CodeWars 2014
#
#Check Digits
#
# There are many situations where we exchange a number with someone. In some cases we need 
# to be sure that the number we gave them was received correctly. This is especially 
# important for credit cards, serial numbers, and product bar code numbers. 
# A check digit is used to ensure that a sequence of numbers was transmitted or 
# entered correctly without human error. This extra digit helps verify that a tired 
# programmer didn't switch numbers (ex. 12 -> 15), reverse a pair of numbers 
# (ex. 34 -> 43) or otherwise alter the sequence. The different algorithms used 
# to calculate a check digit determine what types of errors it will catch.
#
# For UPC there's a specific algorithm that's used to catch 100% of single digit errors 
# and 89% of transposition errors. Your task is to calculate the missing check digit for 
# the given list of UPCs.
#
# First, add all the digits in the odd-numbered positions together and multiply the 
# result by three. Then add the digits in the even-numbered positions to the result. 
# Next, find the modulo 10 of the sum. The modulo operation calculates the remainder 
# after dividing the sum by 10. Finally subtract if from 10 to obtain the check digit.
#
# The first line of the input will contain the number of partial UPCs that follow. 
# Each UPC will be on it's own line with spaces between all the digits.
#
# 7
# 0 3 6 0 0 0 2 9 1 4 5
# 0 7 3 8 5 2 0 0 9 3 8
# 0 4 1 2 2 0 1 8 9 0 4
# 0 3 7 0 0 0 2 0 2 1 4
# 7 6 5 6 6 8 2 0 2 0 2
# 0 4 1 2 2 0 6 7 0 4 0
# 0 4 1 2 2 0 6 7 0 0 0
# 
# 
# 0 3 6 0 0 0 2 9 1 4 5 2
# 0 7 3 8 5 2 0 0 9 3 8 5
# 0 4 1 2 2 0 1 8 9 0 4 5
# 0 3 7 0 0 0 2 0 2 1 4 1
# 7 6 5 6 6 8 2 0 2 0 2 8
# 0 4 1 2 2 0 6 7 0 4 0 6
# 0 4 1 2 2 0 6 7 0 0 0 0
# 


import sys

print ("Enter number of lines. Then 11 digits for each line.")
count = int(sys.stdin.readline())
while (count > 0):
    count -= 1
    line = sys.stdin.readline().rstrip('\n')
    currentDigit=1
    checkDigit=0
    for c in line:
        if (c.isdigit()):
            value = int(c)
            checkDigit += value
            if (currentDigit % 2 == 1):
                checkDigit += value+value # Add odd positions a total of 3 times.
            currentDigit += 1
    checkDigit = checkDigit % 10
    print (line, (10-checkDigit)%10)
