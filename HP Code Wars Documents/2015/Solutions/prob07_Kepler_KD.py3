#!/usr/bin/env python

#CodeWars 2015
#
# Kepler's Third Law

# P^2 = R^3
# 
# Input
# Each line of input is the orbital period, in years, of a body orbiting the sun. 
# The input ends with a value of zero.
# 1.8808
# 4.60
# 0.615198
# 0
# Output
# For each period, the program must print the semi-major axis in AU. 
# The answer must be accurate to within +/- 0.01 AU.
# 1.523679
# 2.7668
# 0.723327

import sys

for line in sys.stdin:
    P = float(line.strip())
    if (P==0):
        exit()
    R = (P*P)**(1/3)
    print(R)
