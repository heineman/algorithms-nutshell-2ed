BEGIN{FS=",";  print "V" "	" "E" "	" "Density" "	" "DPQ" "	" "DDG" "	" "ODDG";}
/[0-9]/ {rat = $2; rat /= $1*($1-1)/2; print $1 "	" $2 "	" sprintf("%.2f%", 100*rat) "	" $3 "	" $4 "	" $5; }
