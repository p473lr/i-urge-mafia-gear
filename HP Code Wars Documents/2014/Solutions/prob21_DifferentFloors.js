
"use strict";
importPackage(java.io);
importPackage(java.lang);
//
// the pupose of the stdio class is to provide console I/O support
// in the rhino JavaScript environment using native JavaScript types.
// rhino I/O support is provided via Java library calls, which
// return Java types and not JavaScript types.
//
var stdio = (

function stdioImplementation()
{
    var stdin = new BufferedReader(new InputStreamReader(System['in']));

    var stdio = {};

    stdio.readLine = function()
    {
        return new String( stdin.readLine() );
    };
    stdio.readChar = function()
    {
        return String.fromCharCode( stdin.read() );
    };
    // readToken() will read the input stream one character at a time.
    // it will ignore whitespace, then read non-whitespace characters
    // until a whitespace is read. the function returns the sequence
    // of non-whitespace characters read from stdin.
    stdio.readToken = function()
    {
        var token = '';
        var intValue = stdin.read(); // read a single character
        while( intValue <= 32 )
            intValue = stdin.read();
        while( intValue > 32 )
        {
            token += String.fromCharCode( intValue );
            intValue = stdin.read();
        }
        return token;
    };
    stdio.print = function( s )
    {
        System.out.print( s );
    };
    stdio.printLine = function( s )
    {
        System.out.printLine( s );
    };
    return stdio;
}

) ();

var statementRules =
[
    {
        regex: /^([A-Z0-9]+) NOT ON FLOOR ([1-5])$/,
        matchCount: 3,
        createRule: function( matches, nameSet )
        {
            var name = matches[1];
            var floor = matches[2];
            nameSet[ name ] = true;
            return function( floorList, nameHash )
            {
                return( floorList[ floor ] !== name );
            };
        }
    },
    {
        regex: /^([A-Z0-9]+) NOT ON FLOORS ([1-5]) OR ([1-5])$/,
        matchCount: 4,
        createRule: function( matches, nameSet )
        {
            var name = matches[1];
            var floorX = matches[2];
            var floorY = matches[3];
            nameSet[ name ] = true;
            return function( floorList, nameHash )
            {
                return( floorList[ floorX ] !== name && floorList[ floorY ] !== name );
            };
        }
    },
    {
        regex: /^([A-Z0-9]+) ON HIGHER FLOOR THAN ([A-Z0-9]+)$/,
        matchCount: 3,
        createRule: function( matches, nameSet )
        {
            var nameA = matches[1];
            var nameB = matches[2];
            nameSet[ nameA ] = true;
            nameSet[ nameB ] = true;
            return function( floorList, nameHash )
            {
                return( nameHash[ nameA ] > nameHash[ nameB ] );
            };
        }
    },
    {
        regex: /^([A-Z0-9]+) ON ADJACENT FLOOR TO ([A-Z0-9]+)$/,
        matchCount: 3,
        createRule: function( matches, nameSet )
        {
            var nameA = matches[1];
            var nameB = matches[2];
            nameSet[ nameA ] = true;
            nameSet[ nameB ] = true;
            return function( floorList, nameHash )
            {
                var difference = nameHash[ nameA ] - nameHash[ nameB ];
                return( difference * difference === 1 );
            };
        }
    },
    {
        regex: /^([A-Z0-9]+) NOT ON ADJACENT FLOOR TO ([A-Z0-9]+)$/,
        matchCount: 3,
        createRule: function( matches, nameSet )
        {
            var nameA = matches[1];
            var nameB = matches[2];
            nameSet[ nameA ] = true;
            nameSet[ nameB ] = true;
            return function( floorList, nameHash )
            {
                var difference = nameHash[ nameA ] - nameHash[ nameB ];
                return( difference * difference > 1 );
            };
        }
    }
];
function RuleFactory( promptText, nameSet )
{
    var statement = '';
    var rule = null;
    while( rule === null )
    {
        statement = stdio.readLine();
        for( var i=0; i<statementRules.length && rule === null; i++ )
        {
            var matches = statement.match( statementRules[i].regex );
            if( matches && matches.length === statementRules[i].matchCount )
                rule = statementRules[i].createRule( matches, nameSet );
        }
    }
    return rule;
};
function RuleSet()
{
    this._rule_text = "NO RULES";
    this._rule_list = [ ];
    this._name_set = {};
};
RuleSet.prototype.getNameSet = function()
{
    return this._name_set;
};
RuleSet.prototype.read = function()
{
    for( var i=0; i<6; i++ )
        this._rule_list.push( RuleFactory( "rule " + (i+1), this._name_set ) );
};
RuleSet.prototype.validate = function( floorList, nameHash )
{
    for( var i=0; i<6; i++ )
    {
        var rule = this._rule_list[i];
        if( !rule( floorList, nameHash ) )
            return false;
    }
    return true;
};
function FloorSolution( rules )
{
    this._ruleSet = rules;
    this._result = '';
    this._permutations = [ ];
    this._solve();
};
FloorSolution.prototype._solve = function()
{
    var numSolutions = 0;
    // convert the nameSet (associative array) to a normal array
    nameSet = this._ruleSet.getNameSet();
    var nameList = [ ];
    for( var name in nameSet )
        nameList.push( name );
    permutation = [ ];
    this._permutations = this._makePermutations( nameList );
    for( var i=0; i<this._permutations.length; i++ )
    {
        permutation = this._permutations[i];
        permutation.unshift( "ZERO" ); // there is no floor zero
        var hash = {};
        for( var j=0; j<permutation.length; j++ )
        {
            hash[ permutation[j] ] = j;
        }
        if( this._ruleSet.validate( permutation, hash ) )
        {
            this._solution = permutation;
            ++numSolutions;
        }
    }
    print( "" + numSolutions + " solutions" );
};
FloorSolution.prototype._makePermutations = function( nameList )
{
    var permutations = [ ];
    if( nameList.length === 1 )
    {
        permutations = [ nameList ];
    }
    else
    {
        for( var i=0; i<nameList.length; i++ )
        {
            var name = nameList[i];
            var subList = [ ];
            for( var j=0; j<nameList.length; j++ )
            {
                if( i !== j )
                    subList.push( nameList[j] );
            }
            var subPermutations = this._makePermutations( subList );
            for( var j=0; j<subPermutations.length; j++ )
            {
                subPermutations[j].push( name );
                permutations.push( subPermutations[j] );
            }
        }
    }
    return permutations;
};
FloorSolution.prototype.printPermutations = function()
{
    for( var i=0; i<this._permutations.length; i++ )
    {
        var text = '';
        var permutation = this._permutations[i];
        for( var j=0; j<permutation.length; j++ )
        {
            text += permutation[j] + " ";
        }
        print( text );
    }
};
FloorSolution.prototype.print = function()
{
    for( var i=this._solution.length-1; i>0; i-- )
        print( "" + i + " " + this._solution[i] );
};
function main()
{
    var rules = new RuleSet();
    rules.read();
    var solution = new FloorSolution( rules );
    solution.print();
};
main();

