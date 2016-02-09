// Diamond-in-the-Rough
// Code Wars program written in JavaScript for the RingoJS environment
//
// The MIT License (MIT)
//
// Copyright (c) 2015 Lee Jenkins
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

var stdin = require("system").stdin;
var stdout = require("system").stdout;

"use strict";

(function SpiralTriangles() {

   function run() {
      var inputData = readDiamondInfo();
      while( inputData.size > 0 ) {
         printDiamonds( inputData );
         inputData = readDiamondInfo();
      }
   };

   function printDiamonds( inputData ) {
      var midSize = inputData.size / 2;
      for( var gridRow=0; gridRow<inputData.rows; ++gridRow ) {
         for( var diamondRow=0; diamondRow<inputData.size; ++diamondRow ) {
            var line = "";
            for( var gridCol=0; gridCol<inputData.cols; ++gridCol ) {
               for( var diamondCol=0; diamondCol<inputData.size; ++diamondCol ) {
                  var c = "#";
                  if( diamondRow < midSize ) { // top half
                     if( diamondCol >= (midSize-(diamondRow+1)) && diamondCol < midSize ) {
                        c = "/";
                     }
                     else if( diamondCol >= midSize && diamondCol <= (midSize+diamondRow) ) {
                        c = "\\";
                     }
                  }
                  else { // bottom half
                     if( diamondCol >= (diamondRow-midSize) && diamondCol < midSize ) {
                        c = "\\";
                     }
                     else if( diamondCol >= midSize && diamondCol < (inputData.size+midSize-diamondRow) ) {
                        c = "/";
                     }
                  }
                  line += c;
               }
            }
            print( line );
         }
      }
   };

   function readDiamondInfo() {
	  var tokens = stdin.readLine().split(/\s+/);
      return {
         size: parseInt( tokens[0] ),
         rows: parseInt( tokens[1] ),
         cols: parseInt( tokens[2] )
      };
   };

   run();

}) ();
