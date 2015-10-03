# note that 'type' is a parameter to this awk script via '-v "string"'
BEGIN{FS=",";}
/[0-9]+,/{if (pr) { printf "%d	%s	%2.1f	%4.4f\n", $1, type, p, $2; }}
/p=/ {pr=1;p=substr($0,3); }

