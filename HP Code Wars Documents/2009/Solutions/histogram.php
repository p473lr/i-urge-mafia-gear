#!/usr/bin/php -q
<?php


function cmp( $a, $b )
{
    if( $a["value"] > $b["value"] )
        return -1;
    if( $a["value"] < $b["value"] )
        return 1;
    if( $a["key"] < $b["key"] )
        return -1;    
    if( $a["key"] > $b["key"] )
        return 1;
    return 0;
}

/* Define STDIN in case if it is not already defined by PHP for some reason */
if( !defined( "STDIN" ) )
    define( "STDIN", fopen('php://stdin', 'r') );

    $counts = array();
    $token = "";
    while( !feof( STDIN ) )
    {
        $c = fgetc( STDIN );
        // echo "read this character: $c\n" ;
        $c = strtoupper( $c );
        if( ( $c >= 'A' && $c <= 'Z' ) || $c == '\'' )
            $token = "$token$c";
        else
        {
            if( strlen( $token ) )
            {
                $n = 0;
                if( isset( $counts[$token] ) )
                    $n = $counts[ $token ][ "value" ];
                $counts[ $token ][ "value" ] = $n+1;
                $counts[ $token ][ "key" ] = $token;
            }
            $token = "";
        }
    }

    usort( $counts, "cmp" );
    $printCount = 0;

    foreach( $counts as $node )
    {
		$key = $node["key"];
		$value = $node["value"];
		for( $i=0; $i<$value; $i++ )
		    print( "*" );

        ++$printCount;
		print( " #$printCount: $key - $value\n" );
        if( $printCount == 5 )
            break;
    }

?>
