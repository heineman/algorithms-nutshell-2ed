#!/usr/bin/perl -w

# Combine the results of report.* as found in Code/Chapter2/ once the 'make FullReport'
# is run in that directory.

# result is something like:
#
# n  Add-g Last-g Add-none Last-none ...

# Type = Add*,Alt*,Last* are possible
# Suff = g,java,none,O1,O2,O3 are possible
my @Type = ( "Add*", "Last*" );
my @Suff = ( "g", "java", "O3" );
my @N = ( 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576 );

# $values{N-Type-Suff} = Milliseconds
my (%values);


#my $idx;
foreach (@ARGV) {
    
    my $suff = '';
    if ($_ =~ /report\.([^ \b]+)/) {
    	$suff = $1;
	# Could just extract all if you would like
	# $Suff[$idx++] = $suff;
    }

    open (RECORD, $_);
    while ($rec = <RECORD>) {
	chomp ($rec);
	if ($rec =~ /(\d+),([^:]+):(\d+),/) {
	    #print $suff . "," . $rec . " extract " . $1 . "," . $2 . "," . $3 . "\n";

	    # $1 is n, $2 is type, $3 is ms
	    $values{"$1-$2-$suff"} = $3;
	}
    }
    
}

# header
print "# N,";
foreach $t (@Type) {
    foreach $s (@Suff) {
	print "$t-$s,";
    }
}

print "\n";

foreach $n (@N) {
    print $n . "	";
    foreach $t (@Type) {
	foreach $s (@Suff) {
	    print $values{"$n-$t-$s"} . "	";
	}
    }
    print "\n";
    
}

1;
