#!/usr/bin/env python

#CodeWars 2015
#
# NetOS App

# Input
# Each line of the input is two integer values. The last line of input is two zeros.
# 15 9
# 11 8
# 0 0
# Output
# For each line of input, the program must multiply the two input numbers and print the result.
# 135
# 88

import sys

for line in sys.stdin:
    line = line.rstrip('\n')
    length, width = line.split(None, 1)
    area = int(length) * int(width)
    if (area > 0):
        print (area)
