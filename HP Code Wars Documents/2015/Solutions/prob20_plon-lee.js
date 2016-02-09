// plon - parenthetical list object notation
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
var system = require("system");

var JSON = null; // only used for debugging

var DEBUG = false;
if(DEBUG) JSON = require("./json2.js");

"use strict";

// this function will buffer an entire line at a time, so
// it is possible to miss input -- be very careful!!!
var readToken = ( function readTokenWrapper() {
    var tokenList = [ ]; // tokenList and tokenIndex are not visible outside readTokenWrapper,
    var tokenIndex = 0;  // and their values persist between calls to readTokenWorker.
    return function readTokenWorker() {
        // this function assumes that there will be another token
        // it does not handle end of input!!!!
        while( tokenIndex >= tokenList.length ) {
            tokenList = stdin.readLine().trim().split(/\s+/);
            tokenIndex = 0;
        }
        var token = tokenList[ tokenIndex++ ];
        if(DEBUG) system.print( "### read token " + token );
        return token;
    }
})();

function Property( name, value )
{
    this.name = name;
    this.value = value;
}

function PLON()
{
}

PLON.prototype.run = function()
{
    this.list = this.readPLON();
    if(DEBUG) system.print( JSON.stringify(this.list) );

    var query = stdin.readLine().trim();
    var count = 0;

    while( query !== '.' )
    {
        this.executeQuery( query );
        query = stdin.readLine().trim();
        if( ++count > 9 ) break;
    }
}

PLON.prototype.readPLON = function()
{
    var temp = { };
    var result = this.readItem();
    if( result instanceof Property )
    {
        temp[ result.name ] = result.value;
        result = temp;
    }
    return result;
}

PLON.prototype.readItem = function()
{
    var item; // undefined
    var token = readToken();
    if( token === "(" )
    {
        item = this.readList();
    }
    else if( token === ")" )
    {
        item = null;
    }
    else
    {
        item = token;
    }
    return item;
}

PLON.prototype.readPropertyList = function()
{
    var result = { };
    var token, propertyName, propertyValue;
    // we're cheating here because we assume the syntax of
    // the input is correct. this is fine for the contest,
    // but in the real world, not so much.
    token = readToken(); // read the opening parenthesis
    while( token !== ")" ) {
        propertyName = readToken();
        propertyValue = this.readItem();
        result[ propertyName ] = propertyValue;
        token = readToken(); // read the closing parenthesis

        token = readToken(); // read next parenthesis
           // if token is an open-paren, we'll read another property
           // if token is a closed-paren, we've read the end of the list
    }
    return result;
}

PLON.prototype.readList = function()
{
    var result = [ ];
    // read the first item of the list
    var item = this.readItem();
    if( item !== null )
    {
        // a property-list
        if( item === "#" )
        {
            result = this.readPropertyList();
        }
        else
        {
            while( item !== null )
            {
                result.push( item );
                item = this.readItem();
            }
        }
    }
    return result;
}

PLON.prototype.executeQuery = function( query )
{
    if(DEBUG) system.print( "================================" );
    if(DEBUG) system.print( "query:  " + query );
    var parts = query.split(/[\.\[\]]/);
    var list  = this.list;
    for( var i=0; i<parts.length; ++i )
    {
        var hash = parts[i];
        // String.split returns an empty string between the array notation and
        // a dot in the query string, e.g. person[2].name splits into:
        //          [ "person", "2", "", "name" ]
        // so we just ignore empty strings in the query split
        if( hash.length === 0 )
            continue;

        if(DEBUG) system.print( "-----------------------" );
        if(DEBUG) system.print( "list is: " + JSON.stringify(list) );
        if(DEBUG) system.print( "hash is: '" + hash + "',  type=" + typeof(hash) );

        list = list[hash];
        if(DEBUG) system.print( "result:  " + JSON.stringify(list) );
    }
    system.print( query + " = " + list );
}

var plon = new PLON();
plon.run();
