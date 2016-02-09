// Angle of Repose
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

(function AngleOfRepose() {

   var size = 0;
   var grid = [ ];
   var maxAngle = 0.0;

   function run() {
      readGrid();
      // printGrid();
      scanGrid();
      stdout.writeLine( "Max angle is " + maxAngle * 180 / Math.PI );
   };

   function readGrid() {
      size = parseInt( stdin.readLine() );
      for( var row=0; row<size; row++ ) {
         grid.push( stdin.readLine().trim().split(/\s+/) );
      }
   };

   function printGrid() { // used for debugging
      for( var row=0; row<grid.length; row++ ) {
         var data = grid[row];
         for( var col=0; col<data.length; col++ ) {
            stdout.write( data[col] + " " );
         }
         stdout.writeLine('');
      }
   };

   function scanGrid() {
      for( var row=0; row<grid.length; row++ ) {
         var data = grid[row];
         for( var col=0; col<data.length; col++ ) {
            examineCell( row, col );
         }
      }
   };

   function examineCell( row, col ) {
      for( var dr=0; dr<2; ++dr ) {
         for( var dc=-1; dc<2; ++dc ) {
            if( dr === 0 && dc === 0 )
               continue;
            var row2 = row + dr;
            var col2 = col + dc;
            if( row2 >= size || col2 >= size )
               continue;
            var B = Math.sqrt( dr*dr + dc*dc );
            var A = Math.abs( grid[row][col] - grid[row2][col2] );
            var C = Math.sqrt( A*A + B*B );
            var theta = Math.asin( A / C );
            if( theta > maxAngle ) {
               maxAngle = theta;
            }
         }
      }
   };

   run();

}) ();
