BEGIN{tab="	";uniq = 0;print "n	load factor	min	avg	max	# unique";}
/^1	[0-9]+/ { uniq = substr($0,3);}
/[0-9]*\.\.\./ {t=$1 - 0;  }
/Load factor:/ { split($0,ar,":"); load = ar[2]; number =0; total = 0;}
/^[0-9]+	[0-9]+$/ { number += $2; total += $1*$2; }
/Slot statistics/ { split ($0,ar,"="); min =ar[2]-0; max = ar[3]-0; avg = total/number
    print t tab load tab min tab avg tab max tab uniq; }
