#!/usr/bin/env python

#CodeWars 2015
#
# State Machine Decoder

# Input
# The first line of input indicates the number of transitions. 
# Each transition is listed on its own line and has three parts: 
# a state label, a trigger condition, and a target state label. 
# All parts are single characters and the initial condition is labelled with a hash tag. 
# The last line of the input is a text string to be decoded.
# 10
# # m s
# # e d
# - q a
# - m d
# a q d
# a m s
# d q -
# d v a
# s e a
# s v -
# meqqqmvmvq
# 
# Output
# The program must decode the last line of the input according 
# to the rules of the state machine. Each character of the text string should be 
# interpreted as a trigger condition for a state change and the new state should 
# be appended to the decoded string. The program must print the decoded string.
# sad-as-dad
# 

import sys

dict={}
transitionCount = int(sys.stdin.readline().rstrip('\n'))
for i in range(0,transitionCount):
    line = sys.stdin.readline().rstrip('\n')
    words = line.split();
    if words[0] in dict.keys():
        dict[words[0]][words[1]] = words[2]  # Add a new entry to existing lower level dictionary
    else:
        dict[words[0]] = {words[1]:words[2]} # Add a new entry to top dict, with value of a new lower-level dict
#print(dict)

# Travel the transitions and print each state.
state = '#'  #default first state
line = sys.stdin.readline().rstrip('\n')
lineLength = len(line)

print("") # Blank line
for i in range(0,lineLength):
    state = dict[state][line[i]]
    print(state, sep='', end='')
print("")
