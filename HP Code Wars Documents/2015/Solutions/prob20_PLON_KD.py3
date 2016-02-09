#!/usr/bin/env python

#CodeWars 2015
#
# Parenthetical List Object Notation

# The first part of the input will consist of a PLON string, 
# which may span multiple lines. You may assume that parenthesis, 
# hash signs, and words are separated by one or more spaces 
# and/or end-of-line characters. You may also assume the PLON string 
# is valid (you don't need to validate the syntax). 
# The next lines should be interpreted as object queries in dot notation. 
# The last line will contain a single period.
# ( # ( book ( # ( title MyBook ) ( authors ( MyName YourName ) ) ( date MMXIV )
# ( characters ( ( Mary Martha Larry ) ( WuYan SuLiu ) ) ) ( formatList (
# ( # ( format paperback ) ( price XXIV ) ( pages CCCIC ) ) ( # ( format audioCD )
# ( price XIX ) ) ( # ( format hardback ) ( price XXXII ) ) ) ) ) ) )
# book.date
# book.authors[0]
# book.characters[1][0]
# book.formatList[2].price
# .
# 
# Output
# For each object query, the program must print a line with the object 
# query followed by an equal sign and the correct property value. 
# You may assume that each output value will be a single word and not a list.
# book.date = MMXIV
# book.authors[0] = MyName
# book.characters[1][0] = WuYan
# book.formatList[2].price = XXXII

import sys

#Globals
words=[]
wordIndex=0

# Read a new PLON value (which is allowed to be any type of element)
# Every value can be one of three things:
#  1) A single word: "word"
#  2) A list: "( value value ... value )"
#  3) A dictionary: "( # ( name value ) ( name value ) ... ( name value ) )"
#       ... where every 'name' is a single "word" and
#                 every value can be one of the 3 above items
def newPlonValue ():
    global words
    global wordIndex
    token = words[wordIndex]
    wordIndex = wordIndex + 1
    if (token != '('): # It must just be a word, so return it
        return token

    # New element is either a dictionary or a list
    token = words[wordIndex]
    if (token == '#'):  # Dictionary
        wordIndex = wordIndex + 1
        newDict = {} # Empty Dictionary
        while (words[wordIndex] != ')'): # Haven't reached the end of dictionary
            wordIndex = wordIndex + 1 # skip opening paren of "( name value )"
            name = words[wordIndex]
            wordIndex = wordIndex + 1
            value = newPlonValue() # Recursive call to find a new value
            newDict[name] = value # Add to dictionary
            # Index is pointing at closing paren of "( name value )"
            # Advance once to see if dictionary is complete
            wordIndex = wordIndex + 1  
        # found the end of the dictionary.  Advance past close paren and return.
        wordIndex = wordIndex + 1
        return newDict

    else: # Not a dictionary, so we're starting a list
        newList = [] # Empty List
        while (words[wordIndex] != ')'):
            newList.append(newPlonValue()) # Recursive call to find a new value
        # found the end of the list.  Advance past close paren and return.
        wordIndex = wordIndex + 1
        return newList

#-----------------------
#Main program starts here.
# Read in the complete input PLON data into a single line
plonIn = sys.stdin.readline().rstrip('\n')
while ( plonIn.count('(') > plonIn.count(')')): # check if parentheses counts match
    # Add a space between lines to make sure every element has spaces between
    plonIn = plonIn + ' ' + sys.stdin.readline().rstrip('\n')
words = plonIn.split()
wordIndex = 0
# Analyze the line into a Python structure of lists and dictionaries
PLON = newPlonValue() 
#print("")
#print(PLON)

# Now start reading and analyzing each data element.
print ("") # Blank Line
for line in sys.stdin:
    line = line.rstrip('\n')
    if ((len(line) == 0) or (line[0]=='.')):
        exit()
    print (line, " = ", sep='', end='') # Begin output
    newPlon = PLON # New variable to walk the data
    while len(line) > 0:
        if (line[0] == '['): #Get the index
            lineIndex=1
            indexString=""
            while line[lineIndex] != ']':
                indexString = indexString+line[lineIndex]
                lineIndex = lineIndex+1
            line = line[lineIndex+1:]
            #print("Found index:", int(indexString),". New line:", line)
            newPlon = newPlon[int(indexString)]
            continue

        # Search for '.' or '['
        period = line.find(".")
        bracket = line.find("[")
        if (period >= 0):
            periodString = line[0:period]
            if ( (bracket < 0) or (bracket > period)):
                line = line[period+1:]
                #print("Found:",periodString,". New line:", line)
                if (len(periodString) > 0): # Deal with period immediately after bracket
                    newPlon = newPlon[periodString]
                continue #back to the top of the while Loop
        if (bracket >= 0):
            bracketString = line[0:bracket]
            newPlon = newPlon[bracketString]
            line = line[bracket:]
            #print("Found:",bracketString,". New line:", line)
            continue # back to the top of the while loop

        # There must be only text left if we reached this.
        lastString = line
        line = ''
        #print("Found:", lastString)
        newPlon = newPlon[lastString]

    print(newPlon)

