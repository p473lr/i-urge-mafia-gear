// Analysis of Acronyms
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

var DEBUG = false;

(function AnalysisOfAcronyms() {

   var counters = { };

   function run() {
      var count  = parseInt( stdin.readLine() );
      var acronymData = '';
      var line = '';
      for( var i=0; i<count; ++i ) {
         line = stdin.readLine().trim();
         analyze( line );
      }
   };

   function initCounters() {
      counters = {
         skip: 0,
         multi: 0
      };
   };

   function analyze( line ) {
      var outputData = line.split(/\s+/);
      var acronymData = line.split(/[\s-]+/);
      for( var i=0; i<acronymData.length; ++i ) {
         acronymData[i] = acronymData[i].toLowerCase();
      }
      var acronym = acronymData[0];
      var position = 0;
      var index = 1;
      initCounters();
      var result = subAnalyze( acronym, acronymData, position, index );
      var article = "a ";
      var scope = "";
      var plurality = "";
      if( !result ) {
         article = "NOT an ";
      }
      else {
         if( counters.skip ) {
            scope = "PARTIAL ";
         }
         else {
            scope = "COMPLETE ";
         }
         if( counters.multi ) {
            plurality = "COMPLEX ";
         }
         else {
            plurality = "SIMPLE ";
         }
      }
      stdout.write( outputData[0] + " is " + article + scope + plurality + "acronym for" );
      for( var i=1; i<outputData.length; ++i ) {
         stdout.write( " " + outputData[i] );
      }
      stdout.writeLine('');
   };

   function subAnalyze( acronym, acronymData, position, index ) {
      // if we're at the end of both the acronym and the word list, then
      // we have found an acronym
      if( position === acronym.length && index === acronymData.length ) {
         if(DEBUG) stdout.writeLine( "COMPLETE MATCH!" );
         return true;
      }
      if( position === acronym.length && index < acronymData.length ) {
         if(DEBUG) stdout.writeLine( "THIS IS OK TOO I GUESS" );
         ++counters.skip;
         return true;
      }
      if( position < acronym.length && index === acronymData.length ) {
         if(DEBUG) stdout.writeLine( "NOT EVEN CLOSE" );
         return false;
      }
      // if we're not at the end of either one, then keep looking
      for( var charCount=0; charCount < 3 && charCount + position <= acronym.length; ++charCount ) {
         if(DEBUG) stdout.writeLine( "at position " + position + ", comparing '" + acronym.substr( position, charCount ) + "' with " + acronymData[index] );
         if( acronym.substr( position, charCount ) === acronymData[index].substr( 0, charCount ) ) {
            if(DEBUG) stdout.writeLine( "SUB-MATCHED!" );
            if( charCount === 0 )
               ++counters.skip;
            else if( charCount > 1 )
               ++counters.multi;
            if( subAnalyze( acronym, acronymData, position+charCount, index+1 ) ) {
               return true;
            }
            if( charCount === 0 )
               --counters.skip;
            else if( charCount > 1 )
               --counters.multi;
         }
         else {
            break; // if the short sub-string doesn't match, longer sub-strings won't match either
         }
      }
      if(DEBUG) stdout.writeLine( "out of char counts to try, backing out . . ." );
      return false;
   };

   run();

}) ();
