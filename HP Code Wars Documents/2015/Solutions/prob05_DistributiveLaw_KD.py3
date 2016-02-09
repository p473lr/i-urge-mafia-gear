#!/usr/bin/env python

#CodeWars 2015
#
# Distributive Law

#Input
# There will be three lines of input. 
# The first line has the value for A, the second for B, and the third for C. All values will be positive integers.
# 11
# 5
# 4
# Output
# The program must print three lines demonstrating the Distributive Law with the input values. 
# The first line must substitute the values into the equation. 
# The second line must show the result of the first level of evaluation. 
# The third line must show the result of the final evaluation. Follow the pattern shown below.
# 11 x (5 + 4) = 11 x 5 + 11 x 4
# 11 x 9 = 55 + 44 
# 99 = 99


import sys

A = int(sys.stdin.readline())
B = int(sys.stdin.readline())
C = int(sys.stdin.readline())
print (A," x (", B," + ",C,") = ",A," x ",B," + ",A," x ",C, sep='')
print (A," x ",B+C," = ",A*B," + ",A*C, sep='')
print (A*(B+C)," = ",A*(B+C),sep='')

