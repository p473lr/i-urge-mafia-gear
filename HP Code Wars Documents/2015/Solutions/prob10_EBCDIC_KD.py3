#!/usr/bin/env python

#CodeWars 2015
#
# EBCDIC

# EBCDIC Hexadecimal values
# a=81 b=82 c=83 d=84 e=85 f=86 g=87 h=88 i=89 j=91 k=92 l=93 m=94
# n=95 o=96 p=97 q=98 r=99 s=A2 t=A3 u=A4 v=A5 w=A6 x=A7 y=A8 z=A9
# A=C1 B=C2 C=C3 D=C4 E=C5 F=C6 G=C7 H=C8 I=C9 J=D1 K=D2 L=D3 M=D4
# N=D5 O=D6 P=D7 Q=D8 R=D9 S=E2 T=E3 U=E4 V=E5 W=E6 X=E7 Y=E8 Z=E9
# space=40 period=4B comma=6B exclamation=5A
#
# Write a program to convert text from EBCDIC to ASCII.
# Input
# The first line of input will indicate the number of lines of EBCDIC. 
# Each line begins with a decimal number of EBCDIC codes, followed by that number of EBCDIC codes.
# 3
# 12 C8 D7 40 C3 96 84 85 E6 81 99 A2 5A
# 17 E5 85 95 89 6B 40 A5 89 84 89 6B 40 A5 89 83 89 4B
# 21 E6 85 40 81 99 85 40 A3 88 85 40 83 88 81 94 97 89 96 95 A2 4B
# Output
# The program must print the text represented by the EBCDIC input. 
# Each line of input should correspond to a line of output.
# HP Code Wars!
# Veni, vidi, vici.
# We are the champions.

import sys

ebcdic = dict([
('81','a'), ('82','b'), ('83','c'),('84','d'), ('85','e'), ('86','f'),('87','g'), ('88','h'), 
('89','i'), ('91','j'), ('92','k'),('93','l'), ('94','m'), ('95','n'),('96','o'), ('97','p'), 
('98','q'), ('99','r'), ('A2','s'),('A3','t'), ('A4','u'), ('A5','v'),('A6','w'), ('A7','x'), 
('A8','y'), ('A9','z'), 

('C1','A'), ('C2','B'), ('C3','C'),('C4','D'), ('C5','E'), ('C6','F'),('C7','G'), ('C8','H'), 
('C9','I'), ('D1','J'), ('D2','K'),('D3','L'), ('D4','M'), ('D5','N'),('D6','O'), ('D7','P'), 
('D8','Q'), ('D9','R'), ('E2','S'),('E3','T'), ('E4','U'), ('E5','V'),('E6','W'), ('E7','X'), 
('E8','Y'), ('E9','Z'), 

('40',' '), ('4B','.'), ('6B',','),('5A','!')
])

#print ("Enter N and N codes.")
linecount = sys.stdin.readline() # Not used
for line in sys.stdin:
    line = line.rstrip('\n')
    words = line.split()
    N = int(words[0])
    for i in range(1,N+1):
        print (ebcdic[words[i]], sep='', end='')
    print("")

