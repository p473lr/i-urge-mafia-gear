#!/usr/bin/env python

#CodeWars 2014
#
# Greek Acrophonic Numerals
#
# The earliest alphabet-related system of numerals used with Greek letters 
# was the set of acrophonic Attic numerals. These numerals operated much like 
# Roman numerals (which derived from this scheme), 
# with: ? = 1, ? = 5, ? = 10, ? = 100, ? = 1000, ? = 10000. 
# Also, 50, 500, 5000, and 50000 were represented by composites of ? and a 
# tiny version of the applicable power of ten.
#
# For this problem we'll use a numeral scheme that follows the pattern 
# of this ancient Greek system. We'll represent the numerals like this: 
# I = 1, P = 5, D = 10, H = 100, C = 1000, and M = 10000. 
# For values above ten, groups of five are represented by a P followed 
# by another letter. For example, 50 would be written PD and 475 HHHHPDDDP.
#
# Write a program to convert between Greek Acrophonic Numbers and our 
# familiar decimal number system.
#
# Input
#
# The first line of input will indicate the number of conversions the 
# program must perform. Each line after will contain either a Greek a
# crophonic number or a decimal number.
#
# 9
# 8
# 50
# 475
# CCPHHDDDDPII
# CCCPHHHHPII
# PMMCPDDDDDP
# 5678
# PMMPCPHDDDPI
# 8642
#
#
#
#
# Output
#
# For each input value the program must convert from decimal to Greek 
# acrophonic, or vice-versa.
#
# PIII
# PD
# HHHHPDDDP
# 2647
# 3807
# 61095
# PCPHHPDDDPIII
# 65536
# PCCCCPHHDDDDII
#

import sys

print ("Enter N, then N words/numbers to translate.")
count = int(sys.stdin.readline())
while (count > 0):
    count -= 1
    line=""
    line = sys.stdin.readline()
    charIn = line[0]
    if (charIn.isdigit()): # convert a number
        value = int(line)
        M = int(value / 10000)
        value -= M*10000
        C = int(value/1000)
        value -= C*1000
        H = int(value/100)
        value -= H*100
        D = int(value/10)
        I = value-D*10
        #print("M",M,"C",C,"H",H,"D",D,"I",I)
        if (M>=5):
            print ("PM", end="")
            M -= 5
        while (M>0):
            print ("M", end="")
            M-=1
        if (C>=5):
            print ("PC", end="")
            C -= 5
        while (C>0):
            print ("C", end="")
            C-=1
        if (H>=5):
            print ("PH", end="")
            H -= 5
        while (H>0):
            print ("H", end="")
            H-=1
        if (D>=5):
            print ("PD", end="")
            D -= 5
        while (D>0):
            print ("D", end="")
            D-=1
        if (I>=5):
            print ("P", end="") # For 1's digit, just "P" for 5.
            I -= 5
        while (I>0):
            print ("I", end="")
            I-=1
        print("")
    else: # Convert Greek to number
        value = 0
        multiple = 1
        length = len(line)

        for i in range(0, length):
            charIn=line[i]
            if (charIn == 'P'):
                multiple = 5
                charIn=line[i+1] # Check next character after P
                if ( (charIn!='M') and (charIn!='C') and (charIn!='H') and (charIn!='D')):
                    # Deal with final lone P, or P followed by I
                    value+=5
                    multiple=1
            else:
                newVal=0
                if (charIn == 'M'):
                    newVal=10000
                elif (charIn == 'C'):
                    newVal=1000
                elif (charIn == 'H'):
                    newVal=100
                elif (charIn == 'D'):
                    newVal=10
                elif (charIn == 'I'):
                    newVal=1
                value += multiple*newVal
                multiple = 1
        print (value)

